from PIL import Image, ImageFilter, ImageOps, ImageEnhance
from skimage.util import random_noise
import numpy as np
import cv2


def de_blur(image):
    enhancer = ImageEnhance.Sharpness(image)
    factor = 100
    deblurred_image = enhancer.enhance(factor)

    return deblurred_image


def grayscale_image(image):
    gray_image = ImageOps.grayscale(image)
    return gray_image


def blur_image(image, blur_value):
    blurred_image = image.filter(ImageFilter.BoxBlur(blur_value))
    return blurred_image


def add_noise(image, amount):
    noised_image = random_noise(image, mode='s&p', amount=amount)
    noised_image = np.array(255 * noised_image, dtype='uint8')
    return Image.fromarray(noised_image)


def adjust_rgb(image, r, g, b):
    rr, gg, bb = image.split()
    rr = rr.point(lambda p: 0 if p == 0 else p + r)
    gg = gg.point(lambda p: 0 if p == 0 else p + g)
    bb = bb.point(lambda p: 0 if p == 0 else p + b)
    out_img = Image.merge("RGB", (rr, gg, bb))
    out_img.getextrema()
    return out_img


def save_image(image, image_path, original_image_path):
    image.save(image_path.split(".")[0] + "." + original_image_path.split(".")[1])


def detect_edge(image):
    enhancer = image.filter(ImageFilter.FIND_EDGES)
    return enhancer


def reverse_image(image):
    reversed_image = ImageOps.invert(image)
    return reversed_image


def rotate_image(image, rotation):
    rotated_image = image.rotate(rotation)
    return rotated_image


def mirror_image(image):
    image_mirror = ImageOps.mirror(image)
    return image_mirror


def flip_image(image):
    flipped_image = ImageOps.flip(image)
    return flipped_image


def crop_image(image, left, top, right, bottom):
    cropped_image = image.crop((left, top, right, bottom))
    return cropped_image


def adjust_saturation(image, saturation):
    enhancer = ImageEnhance.Color(image)
    new_image = enhancer.enhance(saturation)
    return new_image


def adjust_contrast(image, contrast):
    enhancer = ImageEnhance.Contrast(image)
    new_image = enhancer.enhance(contrast)
    return new_image


def adjust_brightness(image, brightness):
    enhancer = ImageEnhance.Brightness(image)
    new_image = enhancer.enhance(brightness)
    return new_image
