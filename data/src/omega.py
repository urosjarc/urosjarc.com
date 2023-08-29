import time
import zipfile
from pathlib import Path

from PIL import Image

from src import utils

def rdeci_pixel(r,g,b):
    pass

def razrezi_sliko(img: Image.Image):
    deli_slike = [[]]
    for y in range(img.height):
        nova_naloga = False
        for x in range(img.width / 5):
            pixel = img.getpixel((x, y))
            if pixel:
                pass


def slika(img: Image.Image):
    for del_slike in razrezi_sliko(img):
        print(utils.image_to_text(img))


def slike(imgs: list[Image.Image]):
    for img in imgs:
        slika(img)


def zip():
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
            hsv_img = img.convert('HSV')
            imgs.append(img)

            img.show()
            break

    utils.parallel(slike, 5, imgs)


start = time.time()
zip()
end = time.time()
print(end - start)
