import matplotlib.pyplot as plt
from pa2_2 import *

sourceimageoriginal = cv2.imread('images/input/fallingwater.jpg')
sourceimage = cv2.cvtColor(sourceimageoriginal, cv2.COLOR_BGR2RGB).astype(np.float32) / 255
plt.imshow(sourceimage)
plt.title("Source")
plt.show()

targetimageoriginal = cv2.imread('images/input/woods.jpg')
targetimage = cv2.cvtColor(targetimageoriginal, cv2.COLOR_BGR2RGB).astype(np.float32) / 255
plt.imshow(targetimage)
plt.title("Target")
plt.show()

resultimage = colortransfer(sourceimage, targetimage).astype(np.float32) / 255
plt.imshow(resultimage)
plt.title("Result")
plt.show()

resultimagecvt = colortransferwithcvt(sourceimageoriginal, targetimageoriginal)
plt.imshow(cv2.cvtColor(resultimagecvt, cv2.COLOR_BGR2RGB))
plt.title("Result with cvt color transformation")
plt.show()
