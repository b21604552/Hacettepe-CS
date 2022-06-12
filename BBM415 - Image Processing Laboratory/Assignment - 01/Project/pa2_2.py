import numpy as np
import math
import cv2 as cv2

def image_stats(image):
	(l, a, b) = cv2.split(image)
	(lMean, lVar) = (l.mean(), l.var())
	(aMean, aVar) = (a.mean(), a.var())
	(bMean, bVar) = (b.mean(), b.var())
	return (lMean, lVar, aMean, aVar, bMean, bVar)

def image_stats_std(image):
    (l, a, b) = cv2.split(image)
    (lMean, lStd) = (l.mean(), l.std())
    (aMean, aStd) = (a.mean(), a.std())
    (bMean, bStd) = (b.mean(), b.std())
    return (lMean, lStd, aMean, aStd, bMean, bStd)

def colortransfer(sourceImage, targetImage):
    x1, y1, z1 = sourceImage.shape
    x2, y2, z2 = targetImage.shape

    #Constants
    rgbToLMSMatrix = np.array([[0.3811, 0.5783, 0.0402], [0.1967, 0.7244, 0.0782], [0.0241, 0.1288, 0.8444]])
    lmsToRGBMatrix = np.array([[4.4679, -3.5873, 0.1193], [-1.2186, 2.3809, -0.1624], [0.0497, -0.2439, 1.2045]])
    labSpaceMatrix = np.array([[1/math.sqrt(3), 0, 0], [0, 1/math.sqrt(6), 0], [0, 0, 1/math.sqrt(2)]])
    labSpaceMatrix2 = np.array([[1, 1, 1], [1, 1, -2], [1, -1, 0]])
    lmsSpaceMatrix = np.array([[math.sqrt(3) / 3, 0, 0], [0, math.sqrt(6) / 6, 0], [0, 0, math.sqrt(2) / 2]])
    lmsSpaceMatrix2 = np.array([[1, 1, 1], [1, 1, -1], [1, -2, 0]])

    #Step 1
    sourceImageReshaped = sourceImage.transpose(2, 0, 1).reshape(3, -1)
    targetImageReshaped = targetImage.transpose(2, 0, 1).reshape(3, -1)
    lmsSource = rgbToLMSMatrix @ sourceImageReshaped
    lmsSource = lmsSource.reshape(z1, x1, y1).transpose(1, 2, 0)
    lmsTarget = rgbToLMSMatrix @ targetImageReshaped
    lmsTarget = lmsTarget.reshape(z2, x2, y2).transpose(1, 2, 0)

    #Step 2
    logSource = np.log10(lmsSource + 1e-6)
    logTarget = np.log10(lmsTarget + 1e-6)

    #Step 3
    logSourceImageReshaped = logSource.transpose(2, 0, 1).reshape(3, -1)
    logTargetImageReshaped = logTarget.transpose(2, 0, 1).reshape(3, -1)
    labSource = labSpaceMatrix @ labSpaceMatrix2 @ logSourceImageReshaped
    labSource = labSource.reshape(z1, x1, y1).transpose(1, 2, 0)
    labTarget = labSpaceMatrix @ labSpaceMatrix2 @ logTargetImageReshaped
    labTarget = labTarget.reshape(z2, x2, y2).transpose(1, 2, 0)

    #Step 4
    (l, a, b) = cv2.split(sourceImage)
    (lsMean, lsVar, asMean, asVar, bsMean, bsVar) = image_stats(labSource)
    (ltMean, ltVar, atMean, atVar, btMean, btVar) = image_stats(labTarget)

    #Step 5
    l -= lsMean
    a -= asMean
    b -= bsMean

    #Step 6
    l = (ltVar / lsVar) * l
    a = (atVar / asVar) * a
    b = (btVar / bsVar) * b

    #Step 7
    l += ltMean
    a += atMean
    b += btMean
    transfer = cv2.merge([l, a, b])

    #Step 8
    labToLMS = transfer.transpose(2, 0, 1).reshape(3, -1)
    lmsSource = lmsSpaceMatrix2 @ lmsSpaceMatrix @ labToLMS
    lmsSource = lmsSource.reshape(z1, x1, y1).transpose(1, 2, 0)

    #Step 9
    (l, a, b) = cv2.split(lmsSource)
    l = np.power(10, l)
    a = np.power(10, a)
    b = np.power(10, b)
    logSource = cv2.merge([l, a, b])

    #Step 10
    labToLMS = logSource.transpose(2, 0, 1).reshape(3, -1)
    lmsSource = lmsToRGBMatrix @ labToLMS
    lmsSource = lmsSource.reshape(z1, x1, y1).transpose(1, 2, 0)
    lmsSource = np.clip(lmsSource, 0, 255)

    return lmsSource

def colortransferwithcvt(sourceimage, targetimage):
    #Step 1-2-3
    source = cv2.cvtColor(sourceimage, cv2.COLOR_BGR2LAB).astype("float32")
    target = cv2.cvtColor(targetimage, cv2.COLOR_BGR2LAB).astype("float32")

    #Step 4
    (lsMean, lsStd, asMean, asStd, bsMean, bsStd) = image_stats_std(source)
    (ltMean, ltStd, atMean, atStd, btMean, btStd) = image_stats_std(target)
    (l, a, b) = cv2.split(source)

    #Step 5
    l -= lsMean
    a -= asMean
    b -= bsMean

    #Step 6
    l = (lsStd / ltStd) * l
    a = (asStd / atStd) * a
    b = (bsStd / btStd) * b

    #Step 7
    l += ltMean
    a += atMean
    b += btMean

    l = np.clip(l, 0, 255)
    a = np.clip(a, 0, 255)
    b = np.clip(b, 0, 255)

    transfer = cv2.merge([l, a, b])
    #Step 8-9-10
    transfer = cv2.cvtColor(transfer.astype("uint8"), cv2.COLOR_LAB2BGR)

    return transfer