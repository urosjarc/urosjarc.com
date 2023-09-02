from multiprocessing import Process

import cv2
import numpy as np
import pytesseract
from PIL import Image
from PIL import ImageOps
from deskew import determine_skew


def parallel(fun, args):
    pool = []

    for arg in args:
        p = Process(target=fun, args=(arg,))
        p.start()
        pool.append(p)

    for p in pool:
        p.join()


def image_to_text(img):
    gray_image = ImageOps.grayscale(img)
    return pytesseract.image_to_string(gray_image, 'slv')


def inRange(pxl, mn, mx):
    for i in range(len(pxl)):
        if not (mn[i] <= pxl[i] <= mx[i]):
            return False
    return True


def img_skew_correction(image: Image.Image, max_angle: int):
    image_cv = cv2.cvtColor(np.array(image), cv2.COLOR_RGB2GRAY)
    angle = determine_skew(image_cv, min_deviation=0.5)
    if abs(angle) > max_angle:
        return angle, image
    return angle, image.rotate(angle)
