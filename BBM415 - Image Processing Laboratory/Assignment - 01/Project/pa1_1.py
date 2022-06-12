import cv2
from pa1_2 import FloydSteinberg


def quantizedImage(quantizedImageArray, quantizedValue):
    quantizedValue = quantizedImageArray.max() / quantizedValue
    height, width = quantizedImageArray.shape
    for i in range(height):
        for j in range(width):
            quantizedImageArray[i][j] = int(round(quantizedImageArray[i][j] / quantizedValue)) * quantizedValue
    return quantizedImageArray


q = 1
image = cv2.imread('images/input/1.png', 0)
cv2.imshow('Original Image', image)

qImage = quantizedImage(image.copy(), q)
cv2.imshow('Quantized Image', qImage)

fsImage = FloydSteinberg(image.copy(), q)
cv2.imshow('Dithering Image', fsImage)

cv2.waitKey(0)
cv2.destroyAllWindows()
