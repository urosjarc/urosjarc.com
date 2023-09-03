import zipfile
from pathlib import Path
from typing import List, IO

import numpy as np
from PIL import Image

from src import utils


class Piksel:
    def __init__(self, rgb, hsv):
        self.rgb = rgb
        self.hsv = hsv

    @property
    def is_white(self):
        return (0 <= self.hsv[1] <= 10) and (200 <= self.hsv[2] <= 255)

    @property
    def is_black(self):
        return 0 <= self.hsv[2] <= 50

    @property
    def is_red(self):
        return (220 <= self.hsv[0] <= 255 or 0 <= self.hsv[0] <= 25) and not self.is_white and not self.is_black

    @property
    def is_red_dark(self):
        return self.is_red and 130 < self.hsv[1]

    @property
    def is_red_light(self):
        return self.is_red and not self.is_red_dark


class Vrstica:
    def __init__(self, y):
        self.pixels: List[Piksel] = []

        self.red_pixels: int = 0
        self.red_dark_pixels: int = 0
        self.red_light_pixels: int = 0
        self.black_pixels: int = 0
        self.white_pixels: int = 0

        self.start = int(len(self.pixels) * 0.03)
        self.end = int(len(self.pixels) * 0.93)

    def add_pixel(self, pixel: Piksel):
        count = 0
        if pixel.is_red:
            self.red_pixels += 1
            count += 1
        if pixel.is_red_dark:
            self.red_dark_pixels += 1
            count += 1
        if pixel.is_red_light:
            self.red_light_pixels += 1
            count += 1
        if pixel.is_black:
            self.black_pixels += 1
            count += 1
        if pixel.is_white:
            self.white_pixels += 1
            count += 1

        self.pixels.append(pixel)


class PrepoznavaVrstice:
    @classmethod
    def is_tematika_title(self, vrstica: Vrstica):
        pass

    @classmethod
    def is_naloga_title(self, vrstica: Vrstica):
        pass

    @classmethod
    def is_teorija_title(self, vrstica: Vrstica):
        pass


class Stran:
    def __init__(self, src: str, image: Image.Image):
        self.image = image
        self.src = src
        self.vrstice: List[Vrstica] = []
        self.visina = image.height
        self.sirina = image.width

        self.text: str = None
        self.rotacija: float = None
        self.rgb: Image.Image = None
        self.hsv: Image.Image = None

    def init(self):
        self.rotacija, self.rgb = utils.img_skew_correction(self.image, max_angle=9)
        self.hsv: Image = self.rgb.convert('HSV')

        for y in range(self.visina):
            vrstica = Vrstica(y)
            for x in range(self.sirina):
                pixel = Piksel(
                    rgb=self.rgb.getpixel((x, y)),
                    hsv=self.hsv.getpixel((x, y))
                )
                vrstica.add_pixel(pixel)
            self.vrstice.append(vrstica)

        self.save()

    def save(self):
        print("save")
        matrix = []
        for vrstica in self.vrstice:
            line = []
            for pixel in vrstica.pixels:
                line.append(pixel.rgb)
            matrix.append(line)

        np_array = np.array(matrix, dtype=np.uint8)
        img = Image.fromarray(np_array)
        img.save(f"{self.__hash__()}.png")


class Zip:
    def __init__(self, pot: Path):
        print("INIT ZIP: ", pot)
        self.pot = pot
        self.strani: List[Stran] = []

    def init(self, paralel: bool):
        zip = zipfile.ZipFile(self.pot)
        for i, zipEle in enumerate(zip.infolist()):
            zipio: IO[bytes] = zip.open(zipEle)
            image: Image.Image = Image.open(zipio).convert('RGB')
            stran = Stran(image=image, src=zipio.name)
            if not paralel:
                stran.init()
            self.strani.append(stran)

        if paralel:
            utils.parallel(lambda x: x.init(), self.strani)

    def pre_processing(self):
        pass
