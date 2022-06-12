def calculateNeighborValues(image, x, y, quanterror, ratio):
    return image[x][y] + quanterror * ratio


def FindQuantizedValue(oldpixel, q):
    return round(oldpixel / q) * q


def FloydSteinberg(image, q):
    q = image.max() / q
    height, width = image.shape
    for x in range(0, height - 1):
        for y in range(0, width - 1):
            oldpixel = image[x][y]
            newpixel = FindQuantizedValue(oldpixel, q)
            image[x][y] = newpixel
            quanterror = oldpixel - newpixel

            xp1y = calculateNeighborValues(image, x + 1, y, quanterror, 7 / 16)
            image[x + 1][y] = xp1y if 255 > xp1y > 0 else 255 if xp1y > 255 else 0

            xm1yp1 = calculateNeighborValues(image, x - 1, y + 1, quanterror, 3 / 16)
            image[x - 1][y + 1] = xm1yp1 if 255 > xm1yp1 > 0 else 255 if xm1yp1 > 255 else 0

            xyp1 = calculateNeighborValues(image, x, y + 1, quanterror, 5 / 16)
            image[x][y + 1] = xyp1 if 255 > xyp1 > 0 else 255 if xyp1 > 255 else 0

            xp1yp1 = calculateNeighborValues(image, x + 1, y + 1, quanterror, 1 / 16)
            image[x + 1][y + 1] = xp1yp1 if 255 > xp1yp1 > 0 else 255 if xp1yp1 > 255 else 0
    return image
