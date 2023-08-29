import time
import zipfile
from multiprocessing import Process
from pathlib import Path

from PIL import Image
from itertools import islice


def parallel(fun, size, args):
    def batcher(iterable, batch_size):
        iterator = iter(iterable)
        while batch := list(islice(iterator, batch_size)):
            yield batch

    pool = []

    for arg in batcher(args, size):
        p = Process(target=fun, args=(arg,))
        p.start()
        pool.append(p)

    for p in pool:
        p.join()


def procesiraj_sliko(imgs):
    a = 0
    for img in imgs:
        for y in range(img.height):
            for x in range(img.width):
                a += 1


def procesiraj_omego():
    omegaPath = Path("../omega")
    files = omegaPath.iterdir()

    imgs = []

    for file in files:
        zip = zipfile.ZipFile(file)
        zip_slike = zip.infolist()
        for zip_slika in zip_slike:
            ifile = zip.open(zip_slika)
            imgs.append(Image.open(ifile))

    parallel(procesiraj_sliko, 5, imgs)


start = time.time()
procesiraj_omego()
end = time.time()
print(end - start)
