from pathlib import Path

from src.preprocessing import Zip, PrepoznavaVrstice, Vrstica


class PrepoznavaOmegaVrstice(PrepoznavaVrstice):
    padding_left = 100
    padding_right = 100

    @classmethod
    def end_pixels(cls, vrstica: Vrstica):
        for i in range(cls.padding_left, cls.padding_left + 10):
            yield vrstica.pixels[i]

        for i in range(len(vrstica.pixels) - cls.padding_right - 10, len(vrstica.pixels)):
            yield vrstica.pixels[i]

    @classmethod
    def is_tematika_title(cls, vrstica: Vrstica):
        for pixel in cls.end_pixels(vrstica):
            if not pixel.is_red_light:
                return False
        return True

    @classmethod
    def is_teorija_title(cls, vrstica: Vrstica):
        for pixel in cls.end_pixels(vrstica):
            if not pixel.is_red_dark:
                return False
        return True

    @classmethod
    def is_naloga_title(cls, vrstica: Vrstica):
        if not cls.is_tematika_title(vrstica) and not cls.is_teorija_title(vrstica):
            for i in range(cls.padding_left, cls.padding_left + 200):
                if vrstica.pixels[i].is_red_dark and vrstica.pixels[i + 1].is_red_dark:
                    return True
        return False


def init_omega_123():
    for i, path in enumerate(
            [Path("../omega/Omega11.zip"),
             Path("../omega/Omega12.zip"),
             Path("../omega/Omega21.zip")]):
        zipObj = Zip(path)
        zipObj.init(paralel=True)


init_omega_123()
