from src.preprocessing import Vrstica


class Naloga:
    def __init__(self, stevilka: int, ime: str, stran: int):
        self.stran: stran
        self.stevilka = stevilka
        self.ime: ime
        self.vrstice: list[Vrstica] = []


class Tematika:
    def __init__(self, ime: str):
        self.ime = ime
        self.naloge: list[Naloga] = []


class Zvezek:
    def __init__(self, ime: str):
        self.ime = ime
        self.tematike: list[Tematika] = []
