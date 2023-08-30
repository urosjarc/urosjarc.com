from itertools import islice
from multiprocessing import Process

import pytesseract
from PIL import ImageOps


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


def image_to_text(img):
    gray_image = ImageOps.grayscale(img)
    print(pytesseract.image_to_string(gray_image, 'slv'))


def inRange(pxl, mn, mx):
    for i in range(len(pxl)):
        if not (mn[i] <= pxl[i] <= mx[i]):
            return False
    return True


def isRed(h, s, v):
    return ((220 <= h <= 255) or (0 <= h <= 25)) and 50 < s and 70 < v
