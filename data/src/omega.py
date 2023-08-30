import shutil
import time
import zipfile
from pathlib import Path

import numpy as np
from PIL import Image

from src import utils

omegaPath = Path("../omega")
omegaSlikePath = Path("../omega_slike")


def razrezi_sliko(img_input_rgb: Image.Image, img_input_hsv: Image.Image):
    naloge = []

    naloga = False
    red_active = False

    for y in range(img_input_rgb.height):
        line = []

        is_red_line = False

        for x in range(img_input_rgb.width):
            rgb_pixel = img_input_rgb.getpixel((x, y))
            h, s, v = img_input_hsv.getpixel((x, y))
            line.append(rgb_pixel)
            if not is_red_line and utils.isRed(h, s, v):
                is_red_line = True

        if is_red_line and not red_active:
            naloge.append([])
            red_active = True
            naloga = True

        if red_active and not is_red_line:
            red_active = False

        if naloga:
            naloge[-1].append(line)

    return naloge


def slike(args):
    for img in args:
        naloge = razrezi_sliko(*img)
        for i, naloga in enumerate(naloge):
            data = Image.fromarray(np.array(naloga, dtype=np.uint8), mode="RGB")
            data.save(f"naloga_{i}.png")


def zip():
    files = omegaPath.iterdir()
    if omegaSlikePath.exists():
        shutil.rmtree(omegaSlikePath.absolute())
    omegaSlikePath.mkdir(parents=True, exist_ok=True)

    imgs = []

    img_file = None
    for img_file in files:
        zip = zipfile.ZipFile(img_file)
        zip_slike = zip.infolist()
        for zip_slika in zip_slike:

            ifile = zip.open(zip_slika)
            fileNum = int(ifile.name.split("_")[-1].split('.')[0])

            if fileNum <= 10:
                continue

            img_input_rgb = Image.open(ifile)
            img_input_hsv = img_input_rgb.convert('HSV')
            imgs.append((img_input_rgb, img_input_hsv))
            break

    utils.parallel(slike, 5, imgs)


start = time.time()
zip()
end = time.time()
print(end - start)
