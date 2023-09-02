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
    i = 0

    def __init__(self, stevilka: int, margin):
        self.stevilka = stevilka
        self.vrstice: list[list[tuple[int]]] = margin
        self.tekst: str

    def shrani(self, base: Path):
        Naloga.i += 1
        path = base.joinpath(f"naloga_{Naloga.i:03d}.png")
        textPath = base.joinpath(f"naloga_{Naloga.i:03d}.txt")

        np_array = np.array(self.vrstice, dtype=np.uint8)
        data = Image.fromarray(np_array, mode="RGB")
        meta = utils.image_to_text(data)
        print(f">{path.absolute()}")
        data.save(path.absolute())
        textPath.write_text(meta)


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
        naloga = False  # Ali se je zacelo parsanje prve naloge
        red_active = False  # Flag ce je vrstica rdece barve
        margin: list[list[tuple[int]]] = []  # Za dodajanje zgornjega margina

        for y in range(self.visina):  # Loopanje po vrstici slike
            line: list[tuple[int]] = []  # Shranjevanje pixlov trenutne vrstice

            is_red_line = False  # Flag ce je trenutna vrstica z rdecim pixlom
            for x in range(100, self.sirina - 100):  # Loopanje po stolpcih vrstice brez margina
                rgb_pixel = self.rgb.getpixel((x, y))
                line.append(rgb_pixel)

                h, s, v = self.hsv.getpixel((x, y))
                is_pixel_red = utils.isRed(h, s, v)
                is_pixel_light_red = utils.isLightRed(h, s, v)
                if 120 < x < 3 * 120:  # Detekcija rdecih pixlov samo na specificnem pasu
                    if is_pixel_red and not is_pixel_light_red:  # Aktiviraj ce ni light red na tem pasu
                        is_red_line = True
                elif is_pixel_red or is_pixel_light_red:  # Ce je pixel rdec kjer koli drugje v mocni ali light rdeci barvi ni aktivacije rdece vrstice
                    is_red_line = False

            # Color the line correctly ==================
            self.rgb.putpixel((100, y), (0, 0, 0))
            self.rgb.putpixel((self.sirina - 100, y), (0, 0, 0))
            self.rgb.putpixel((120, y), (0, 255, 0))
            self.rgb.putpixel((3 * 120, y), (0, 255, 0))
            self.rgb.putpixel((105, y), (255, 0, 0))
            # ===========================================

            if len(margin) >= 18:  # Omejitev stevila vrstic ki so lahko v marginu.
                margin.pop(0)
            margin.append(line)

            # END NALOGA
            is_red_block = utils.isRed(*self.hsv.getpixel((100 + 5, y)))

            # START NEW NALOGA
            if is_red_block or (is_red_line and not red_active):  # Prva aktivacija rdece vrstice

                if naloga:  # Ostranjevanje zadnjih 18 vrstic slike za centralizacijo slike.
                    self.naloge[-1].vrstice = self.naloge[-1].vrstice[:-18]
                    if len(self.naloge[-1].vrstice) < 50:  # Ce je naloga manjsa od 50px potem jo zavrzi!
                        self.naloge.pop(-1)

                red_active = True

                if not is_red_block:
                    self.naloge.append(Naloga(stevilka=len(self.naloge), margin=copy.deepcopy(margin)))
                    naloga = True
                else:
                    naloga = False

            if red_active and not is_red_line:  # Deaktivacija rdece vrstice
                red_active = False

            if naloga:
                self.naloge[-1].vrstice.append(line)

        if len(self.naloge) > 0:
            # Odstrani spodnji nalogi white margin
            self.naloge[-1].vrstice = self.naloge[-1].vrstice[:-150]

        return self.naloge

    def shrani(self, base: Path):
        path = base
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
            try:
                print(f"{self.savePath} {slika.stevilka} | {slika.rotacija}")
                slika.shrani(self.savePath)
            except:
                print(f"FAIL: {self.savePath} {slika.stevilka} | {slika.rotacija}")


Zip(Path("../omega/Omega11.zip"), Path("../omega11_naloge"))
Zip(Path("../omega/Omega12.zip"), Path("../omega12_naloge"))
Zip(Path("../omega/Omega21.zip"), Path("../omega21_naloge"))
