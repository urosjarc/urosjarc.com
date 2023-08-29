import datetime
import time
import zipfile
from multiprocessing import Process
from pathlib import Path

from PIL import Image
import PIL


def procesiraj_sliko(img: Image.Image):
    a = 0
    for y in range(img.height):
        for x in range(img.width):
            a += 1
    print(a)
    img.save(f"{datetime.datetime.now()}.png")


def procesiraj_zip(file):
    zip = zipfile.ZipFile(file)
    zip_slike = zip.infolist()

    procs = []
    for zip_slika in zip_slike:
        slika = Image.open(zip.open(zip_slika))
        p = Process(target=procesiraj_sliko, args=(slika,))
        p.start()
        procs.append(p)
    [p.join() for p in procs]


def procesiraj_omego():
    omegaPath = Path("../omega")
    files = omegaPath.iterdir()

    procs = []
    for file in files:
        p = Process(target=procesiraj_zip, args=(file,))
        p.start()
        procs.append(p)
    [p.join() for p in procs]

start = time.time()
procesiraj_omego()
end = time.time()
print(end-start)
