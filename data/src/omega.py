import time
import zipfile
from pathlib import Path

from PIL import Image

from src import utils


def procesiraj_sliko(imgs):
    for img in imgs:
        print(utils.image_to_text(img))


def procesiraj_omego():
    omegaPath = Path("../omega")
    files = omegaPath.iterdir()

    imgs = []

    for file in files:
        zip = zipfile.ZipFile(file)
        zip_slike = zip.infolist()
        for zip_slika in zip_slike:

            ifile = zip.open(zip_slika)
            fileNum = int(ifile.name.split("_")[-1].split('.')[0])

            if fileNum <= 3:
                continue

            img = Image.open(ifile)
            imgs.append(img)

            img.show()
            break

    utils.parallel(procesiraj_sliko, 5, imgs)


start = time.time()
procesiraj_omego()
end = time.time()
print(end - start)
