import copy
import zipfile
from pathlib import Path
from typing import List, IO

import numpy as np
from PIL import Image

from src import utils

omegaPath = Path("../omega")
omegaSlikePath = Path("../omega_slike")


class Naloga:
    def __init__(self, stevilka: int, margin):
        self.stevilka = stevilka
        self.vrstice: list[list[tuple[int]]] = margin

    def shrani(self, base: Path):
        path = base.joinpath(f"naloga_{self.stevilka:03d}.png")
        np_array = np.array(self.vrstice, dtype=np.uint8)
        data = Image.fromarray(np_array, mode="RGB")
        data.save(path.absolute())


class Stran:
    def __init__(self, io: IO[bytes]):
        self.stevilka = int(io.name.split("_")[-1].split('.')[0])
        self.rotacija, self.rgb = utils.img_skew_correction(Image.open(io), max_angle=9)
        self.hsv: Image.Image = self.rgb.convert('HSV')
        self.visina: int = self.rgb.height
        self.sirina: int = self.rgb.width
        self.naloge: List[Naloga] = []
        self.procesiraj()

    def procesiraj(self):
        naloga = False # Ali se je zacelo parsanje prve naloge
        red_active = False # Flag ce je vrstica rdece barve
        margin: list[list[tuple[int]]] = [] # Za dodajanje zgornjega margina

        for y in range(self.visina): # Loopanje po vrstici slike
            line: list[tuple[int]] = [] # Shranjevanje pixlov trenutne vrstice

            is_red_line = False # Flag ce je trenutna vrstica z rdecim pixlom
            for x in range(120, self.sirina-120): # Loopanje po stolpcih vrstice brez margina
                rgb_pixel = self.rgb.getpixel((x, y))
                line.append(rgb_pixel)

                if x < self.sirina / 5: # Detekcija pixlov samo na prvi osmini slike
                    h, s, v = self.hsv.getpixel((x, y))
                    if not is_red_line and utils.isRed(h, s, v):
                        is_red_line = True

            if len(margin) >= 18: # Omejitev stevila vrstic ki so lahko v marginu.
                margin.pop(0)
            margin.append(line)

            if is_red_line and not red_active:

                if naloga: # Ostranjevanje zadnjih 18 vrstic slike za centralizacijo slike.
                    self.naloge[-1].vrstice = self.naloge[-1].vrstice[:-18]

                if len(self.naloge[-1].vrstice) < 50: # Ce je naloga manjsa od 50px potem jo zavrzi!
                    self.naloge.pop(-1)

                self.naloge.append(Naloga(stevilka=len(self.naloge), margin=copy.deepcopy(margin)))
                red_active = True
                naloga = True

            if red_active and not is_red_line:
                red_active = False

            if naloga:
                self.naloge[-1].vrstice.append(line)

        # Odstrani spodnji nalogi white margin
        self.naloge[-1].vrstice = self.naloge[-1].vrstice[:-200]

        return self.naloge

    def shrani(self, base: Path):
        path = base.joinpath(f"{self.stevilka:03d}")
        path.mkdir(parents=True, exist_ok=True)
        self.rgb.save(path.joinpath(f"_slika.png").absolute())
        for naloga in self.naloge:
            naloga.shrani(path)


class Zip:
    def __init__(self, path: Path, savePath: Path):
        self.path = path
        self.savePath = savePath

        self.images: List[Stran] = []

        self.procesiraj()

    def procesiraj(self):
        zip = zipfile.ZipFile(self.path)
        for i, zipEle in enumerate(zip.infolist()):
            zipFile = zip.open(zipEle)
            self.images.append(Stran(zipFile))
            if i % 5 == 0:
                self.shrani()
                self.images = []

    def shrani(self):
        self.savePath.mkdir(parents=True, exist_ok=True)
        for slika in self.images:
            print(f"{self.savePath} {slika.stevilka} | {slika.rotacija}")
            slika.shrani(self.savePath)


Zip(Path("../omega/Omega11.zip"), Path("../omega11_naloge"))
Zip(Path("../omega/Omega12.zip"), Path("../omega12_naloge"))
Zip(Path("../omega/Omega21.zip"), Path("../omega21_naloge"))
