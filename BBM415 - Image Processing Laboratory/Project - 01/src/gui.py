from decimal import Decimal

from PyQt5 import QtCore
from PyQt5.QtWidgets import *
from PyQt5.QtGui import *
from filters import *


class App(QWidget):

    def __init__(self):
        super().__init__()
        self.uploadButton = QPushButton(self)
        self.reloadButton = QPushButton(self)
        self.saveButton = QPushButton(self)
        self.resetButton = QPushButton(self)
        self.showOriginalImageButton = QPushButton(self)
        self.showProcessedImageButton = QPushButton(self)
        self.getDetectEdgeButton = QPushButton(self)
        self.getReverseButton = QPushButton(self)
        self.getFlipButton = QPushButton(self)
        self.getGrayscaleButton = QPushButton(self)
        self.getBlurButton = QPushButton(self)
        self.noiseValueButton = QLineEdit()
        self.noiseValue = 0
        self.blurValueButton = QLineEdit()
        self.blurValue = 0
        self.getMirrorButton = QPushButton(self)
        self.getRotateImageButton = QPushButton(self)
        self.rotateValueButton = QLineEdit()
        self.rotateValue = 0
        self.getSaturationButton = QPushButton(self)
        self.saturationValueButton = QLineEdit()
        self.saturationValue = 0
        self.getContrastButton = QPushButton(self)
        self.contrastValueButton = QLineEdit()
        self.contrastValue = 0
        self.getBrightnessButton = QPushButton(self)
        self.getRGBButton = QPushButton(self)
        self.brightnessValueButton = QLineEdit()
        self.brightnessValue = 0
        self.getCropButton = QPushButton(self)
        self.leftLabel = QLabel(self)
        self.leftValue = 0
        self.leftValueButton = QLineEdit()
        self.rightLabel = QLabel(self)
        self.rightValue = 0
        self.rightValueButton = QLineEdit()
        self.topLabel = QLabel(self)
        self.topValue = 0
        self.topValueButton = QLineEdit()
        self.bottomLabel = QLabel(self)
        self.bottomValue = 0
        self.bottomValueButton = QLineEdit()
        self.orginalPic = QLabel(self)
        self.processedImage = Image
        self.infoLabel = QLabel(self)
        self.redLabel = QLabel(self)
        self.r = 0
        self.rValueButton = QLineEdit()
        self.greenLabel = QLabel(self)
        self.g = 0
        self.gValueButton = QLineEdit()
        self.blueLabel = QLabel(self)
        self.b = 0
        self.bValueButton = QLineEdit()
        self.getDeblurButton = QPushButton(self)
        self.getNoiseButton = QPushButton(self)
        self.getConsecutiveButton = QPushButton(self)
        self.cropInfo = QLabel(self)
        self.consecutive = True
        self.title = 'BBM415 TERM PROJECT | EMRE HANCI - SADIK CAN ACAR'
        self.left = 10
        self.top = 10
        self.width = 700
        self.height = 700
        self.imagePath = ""
        self.initUI()

    def initUI(self):
        self.setWindowTitle(self.title)
        self.setGeometry(self.left, self.top, self.width, self.height)

        grid = QGridLayout()
        self.setLayout(grid)

        self.infoLabel.setText("Load Image To Start Using Program")
        self.infoLabel.setAlignment(QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.infoLabel, 0, 0, 1, 3)

        self.uploadButton.setText("Upload Image")
        self.uploadButton.clicked.connect(lambda: self.setImagePath())
        self.uploadButton.setToolTip("Upload Image")
        grid.addWidget(self.uploadButton, 1, 0, 1, 3)

        self.reloadButton.setText("Reload Image")
        self.reloadButton.clicked.connect(lambda: self.setImagePath())
        self.reloadButton.setToolTip("Reload Image")
        self.reloadButton.setVisible(False)
        grid.addWidget(self.reloadButton, 1, 0)

        self.saveButton.setText("Save Image")
        self.saveButton.clicked.connect(lambda: self.setSaveImagePath())
        self.saveButton.setToolTip("Save Image")
        self.saveButton.setVisible(False)
        grid.addWidget(self.saveButton, 1, 1)

        self.resetButton.setText("Reset To Original")
        self.resetButton.clicked.connect(lambda: self.setResetImagePath())
        self.resetButton.setToolTip("Reset To Original")
        self.resetButton.setVisible(False)
        grid.addWidget(self.resetButton, 1, 2)

        self.showOriginalImageButton.setText("Show Original Image")
        self.showOriginalImageButton.clicked.connect(lambda: self.showOriginalImage())
        self.showOriginalImageButton.setToolTip("Show Image")
        self.showOriginalImageButton.setVisible(False)
        grid.addWidget(self.showOriginalImageButton, 2, 0)

        self.showProcessedImageButton.setText("Show Processed Image")
        self.showProcessedImageButton.clicked.connect(lambda: self.showProcessedImage())
        self.showProcessedImageButton.setToolTip("Show Image")
        self.showProcessedImageButton.setVisible(False)
        grid.addWidget(self.showProcessedImageButton, 2, 1)

        self.getDetectEdgeButton.setText("Detect Edge")
        self.getDetectEdgeButton.clicked.connect(lambda: self.detectEdge())
        self.getDetectEdgeButton.setToolTip("Detect Edge")
        self.getDetectEdgeButton.setVisible(False)
        grid.addWidget(self.getDetectEdgeButton, 2, 2)

        self.getReverseButton.setText("Reverse Image")
        self.getReverseButton.clicked.connect(lambda: self.reverseImage())
        self.getReverseButton.setToolTip("Reverse Image")
        self.getReverseButton.setVisible(False)
        grid.addWidget(self.getReverseButton, 3, 0)

        self.getFlipButton.setText("Flip")
        self.getFlipButton.clicked.connect(lambda: self.flipImage())
        self.getFlipButton.setToolTip("Flip")
        self.getFlipButton.setVisible(False)
        grid.addWidget(self.getFlipButton, 3, 1)

        self.getGrayscaleButton.setText("Grayscale")
        self.getGrayscaleButton.clicked.connect(lambda: self.grayscaleImage())
        self.getGrayscaleButton.setToolTip("Grayscale")
        self.getGrayscaleButton.setVisible(False)
        grid.addWidget(self.getGrayscaleButton, 3, 2)

        self.getMirrorButton.setText("Mirror")
        self.getMirrorButton.clicked.connect(lambda: self.mirrorImage())
        self.getMirrorButton.setToolTip("Mirror")
        self.getMirrorButton.setVisible(False)
        grid.addWidget(self.getMirrorButton, 4, 0)

        self.getDeblurButton.setText("Deblur")
        self.getDeblurButton.clicked.connect(lambda: self.deBlur())
        self.getDeblurButton.setToolTip("Deblur")
        self.getDeblurButton.setVisible(False)
        grid.addWidget(self.getDeblurButton, 4, 1)

        self.getConsecutiveButton.setText("Make Process Non-Consecutive")
        self.getConsecutiveButton.clicked.connect(lambda: self.setConsecutive())
        self.getConsecutiveButton.setToolTip("Process working with consecutive")
        self.getConsecutiveButton.setVisible(False)
        grid.addWidget(self.getConsecutiveButton, 4, 2)

        self.getNoiseButton.setText("Noise")
        self.getNoiseButton.clicked.connect(lambda: self.addNoise())
        self.getNoiseButton.setToolTip("Noise")
        self.getNoiseButton.setVisible(False)
        grid.addWidget(self.getNoiseButton, 5, 0)

        self.noiseValueButton.setValidator(QDoubleValidator())
        self.noiseValueButton.setMaxLength(4)
        self.noiseValueButton.setVisible(False)
        self.noiseValueButton.setPlaceholderText("Input Noise Value")
        self.noiseValueButton.textChanged.connect(self.noiseValueChanged)
        grid.addWidget(self.noiseValueButton, 5, 1, 1, 2)

        self.getBlurButton.setText("Blur")
        self.getBlurButton.clicked.connect(lambda: self.blurImage())
        self.getBlurButton.setToolTip("Blur")
        self.getBlurButton.setVisible(False)
        grid.addWidget(self.getBlurButton, 6, 0)

        self.blurValueButton.setValidator(QIntValidator())
        self.blurValueButton.setMaxLength(4)
        self.blurValueButton.setVisible(False)
        self.blurValueButton.setPlaceholderText("Input Blur Value")
        self.blurValueButton.textChanged.connect(self.blurValueChanged)
        grid.addWidget(self.blurValueButton, 6, 1, 1, 2)

        self.getRotateImageButton.setText("Rotate")
        self.getRotateImageButton.clicked.connect(lambda: self.rotateImage())
        self.getRotateImageButton.setToolTip("Rotate")
        self.getRotateImageButton.setVisible(False)
        grid.addWidget(self.getRotateImageButton, 7, 0)

        self.rotateValueButton.setValidator(QIntValidator())
        self.rotateValueButton.setMaxLength(4)
        self.rotateValueButton.setVisible(False)
        self.rotateValueButton.setPlaceholderText("Input Rotate Value")
        self.rotateValueButton.textChanged.connect(self.rotateValueChanged)
        grid.addWidget(self.rotateValueButton, 7, 1, 1, 2)

        self.getSaturationButton.setText("Saturation")
        self.getSaturationButton.clicked.connect(lambda: self.adjustSaturation())
        self.getSaturationButton.setToolTip("Saturation")
        self.getSaturationButton.setVisible(False)
        grid.addWidget(self.getSaturationButton, 8, 0)

        self.saturationValueButton.setValidator(QIntValidator())
        self.saturationValueButton.setMaxLength(4)
        self.saturationValueButton.setVisible(False)
        self.saturationValueButton.setPlaceholderText("Input Saturation Value")
        self.saturationValueButton.textChanged.connect(self.saturationValueChanged)
        grid.addWidget(self.saturationValueButton, 8, 1, 1, 2)

        self.getContrastButton.setText("Contrast")
        self.getContrastButton.clicked.connect(lambda: self.adjustContrast())
        self.getContrastButton.setToolTip("Contrast")
        self.getContrastButton.setVisible(False)
        grid.addWidget(self.getContrastButton, 9, 0)

        self.contrastValueButton.setValidator(QIntValidator())
        self.contrastValueButton.setMaxLength(4)
        self.contrastValueButton.setVisible(False)
        self.contrastValueButton.setPlaceholderText("Input Contrast Value")
        self.contrastValueButton.textChanged.connect(self.contrastValueChanged)
        grid.addWidget(self.contrastValueButton, 9, 1, 1, 2)

        self.getBrightnessButton.setText("Brightness")
        self.getBrightnessButton.clicked.connect(lambda: self.adjustBrightness())
        self.getBrightnessButton.setToolTip("Brightness")
        self.getBrightnessButton.setVisible(False)
        grid.addWidget(self.getBrightnessButton, 10, 0)

        self.brightnessValueButton.setValidator(QIntValidator())
        self.brightnessValueButton.setMaxLength(4)
        self.brightnessValueButton.setVisible(False)
        self.brightnessValueButton.setPlaceholderText("Input Brightness Value")
        self.brightnessValueButton.textChanged.connect(self.brightnessValueChanged)
        grid.addWidget(self.brightnessValueButton, 10, 1, 1, 2)

        self.cropInfo.setText("Crop Info:")
        self.cropInfo.setVisible(False)
        self.cropInfo.setAlignment(QtCore.Qt.AlignLeft | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.cropInfo, 11, 0, 1, 3)

        self.leftLabel.setText("Crop Left Value:")
        self.leftLabel.setVisible(False)
        self.leftLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.leftLabel, 12, 0)

        self.leftValueButton.setValidator(QIntValidator())
        self.leftValueButton.setMaxLength(4)
        self.leftValueButton.setVisible(False)
        self.leftValueButton.setPlaceholderText("Input Left Value")
        self.leftValueButton.textChanged.connect(self.leftValueChanged)
        grid.addWidget(self.leftValueButton, 12, 1, 1, 2)

        self.rightLabel.setText("Crop Right Value:")
        self.rightLabel.setVisible(False)
        self.rightLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.rightLabel, 13, 0)

        self.rightValueButton.setValidator(QIntValidator())
        self.rightValueButton.setMaxLength(4)
        self.rightValueButton.setVisible(False)
        self.rightValueButton.setPlaceholderText("Input Right Value")
        self.rightValueButton.textChanged.connect(self.rightValueChanged)
        grid.addWidget(self.rightValueButton, 13, 1, 1, 2)

        self.topLabel.setText("Crop Top Value:")
        self.topLabel.setVisible(False)
        self.topLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.topLabel, 14, 0)

        self.topValueButton.setValidator(QIntValidator())
        self.topValueButton.setMaxLength(4)
        self.topValueButton.setVisible(False)
        self.topValueButton.setPlaceholderText("Input Top Value")
        self.topValueButton.textChanged.connect(self.topValueChanged)
        grid.addWidget(self.topValueButton, 14, 1, 1, 2)

        self.bottomLabel.setText("Crop Bottom Value:")
        self.bottomLabel.setVisible(False)
        self.bottomLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.bottomLabel, 15, 0)

        self.bottomValueButton.setValidator(QIntValidator())
        self.bottomValueButton.setMaxLength(4)
        self.bottomValueButton.setVisible(False)
        self.bottomValueButton.setPlaceholderText("Input Bottom Value")
        self.bottomValueButton.textChanged.connect(self.bottomValueChanged)
        grid.addWidget(self.bottomValueButton, 15, 1, 1, 2)

        self.getCropButton.setText("Crop")
        self.getCropButton.clicked.connect(lambda: self.cropImage())
        self.getCropButton.setToolTip("Crop")
        self.getCropButton.setVisible(False)
        grid.addWidget(self.getCropButton, 16, 0, 1, 3)

        self.redLabel.setText("Red Value:")
        self.redLabel.setVisible(False)
        self.redLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.redLabel, 17, 0)

        self.rValueButton.setValidator(QIntValidator(-256, 256, self))
        self.rValueButton.setMaxLength(4)
        self.rValueButton.setVisible(False)
        self.rValueButton.setPlaceholderText("Input Red Value")
        self.rValueButton.textChanged.connect(self.rValueChanged)
        grid.addWidget(self.rValueButton, 17, 1, 1, 2)

        self.greenLabel.setText("Green Value:")
        self.greenLabel.setVisible(False)
        self.greenLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.greenLabel, 18, 0)

        self.gValueButton.setValidator(QIntValidator())
        self.gValueButton.setMaxLength(4)
        self.gValueButton.setVisible(False)
        self.gValueButton.setPlaceholderText("Input Green Value")
        self.gValueButton.textChanged.connect(self.gValueChanged)
        grid.addWidget(self.gValueButton, 18, 1, 1, 2)

        self.blueLabel.setText("Blue Value:")
        self.blueLabel.setVisible(False)
        self.blueLabel.setAlignment(QtCore.Qt.AlignRight | QtCore.Qt.AlignVCenter)
        grid.addWidget(self.blueLabel, 19, 0)

        self.bValueButton.setValidator(QIntValidator())
        self.bValueButton.setMaxLength(4)
        self.bValueButton.setVisible(False)
        self.bValueButton.setPlaceholderText("Input Blue Value")
        self.bValueButton.textChanged.connect(self.bValueChanged)
        grid.addWidget(self.bValueButton, 19, 1, 1, 2)

        self.getRGBButton.setText("Adjust RGB Values")
        self.getRGBButton.clicked.connect(lambda: self.adjustRGB())
        self.getRGBButton.setToolTip("Adjust RGB Values")
        self.getRGBButton.setVisible(False)
        grid.addWidget(self.getRGBButton, 20, 0, 1, 3)

        self.show()

    def setConsecutive(self):
        self.consecutive = not self.consecutive
        if self.consecutive:
            self.getConsecutiveButton.setText("Make Process Non-Consecutive")
            self.getConsecutiveButton.setToolTip("Process working with consecutive")
        else:
            self.getConsecutiveButton.setText("Make Process Consecutive")
            self.getConsecutiveButton.setToolTip("Process working with non-consecutive")

    def noiseValueChanged(self, text):
        try:
            if text == "":
                self.noiseValue = 0
            else:
                self.noiseValue = Decimal(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for noise value")

    def blurValueChanged(self, text):
        try:
            if text == "":
                self.blurValue = 0
            elif text == "-":
                return
            else:
                self.blurValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for blur value")

    def rValueChanged(self, text):
        try:
            if text == "":
                self.r = 0
            elif text == "-":
                return
            else:
                self.r = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for red value")

    def gValueChanged(self, text):
        try:
            if text == "":
                self.g = 0
            elif text == "-":
                return
            else:
                self.g = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for green value")

    def bValueChanged(self, text):
        try:
            if text == "":
                self.b = 0
            elif text == "-":
                return
            else:
                self.b = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for blue value")

    def rotateValueChanged(self, text):
        try:
            if text == "":
                self.rotateValue = 0
            else:
                self.rotateValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for rotate value")

    def brightnessValueChanged(self, text):
        try:
            if text == "":
                self.brightnessValue = 0
            else:
                self.brightnessValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for brightness value")

    def contrastValueChanged(self, text):
        try:
            if text == "":
                self.contrastValue = 0
            else:
                self.contrastValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for contrast value")

    def saturationValueChanged(self, text):
        try:
            if text == "":
                self.saturationValue = 0
            else:
                self.saturationValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for saturation value")

    def topValueChanged(self, text):
        try:
            if text == "":
                self.topValue = 0
            else:
                self.topValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for top value")

    def bottomValueChanged(self, text):
        try:
            if text == "":
                self.bottomValue = 0
            else:
                self.bottomValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for bottom value")

    def leftValueChanged(self, text):
        try:
            if text == "":
                self.leftValue = 0
            else:
                self.leftValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for left value")

    def rightValueChanged(self, text):
        try:
            if text == "":
                self.rightValue = 0
            else:
                self.rightValue = int(text)
        except:
            self.infoLabel.setText("Latest Error: Invalid values for right value")

    def setImagePath(self):
        self.imagePath = self.openFileNameDialog()

        if self.imagePath != "":
            self.infoLabel.setText("Errors and Warnings Will Be Display Here")
            self.uploadButton.setVisible(False)
            self.processedImage = Image.open(self.imagePath)
            self.showOriginalImageButton.setVisible(True)
            self.saveButton.setVisible(True)
            self.getDetectEdgeButton.setVisible(True)
            self.showProcessedImageButton.setVisible(True)
            self.resetButton.setVisible(True)
            self.getReverseButton.setVisible(True)
            self.getFlipButton.setVisible(True)
            self.getGrayscaleButton.setVisible(True)
            self.getBlurButton.setVisible(True)
            self.getMirrorButton.setVisible(True)
            self.getRotateImageButton.setVisible(True)
            self.blurValueButton.setVisible(True)
            self.rotateValueButton.setVisible(True)
            self.getSaturationButton.setVisible(True)
            self.saturationValueButton.setVisible(True)
            self.getContrastButton.setVisible(True)
            self.contrastValueButton.setVisible(True)
            self.getBrightnessButton.setVisible(True)
            self.brightnessValueButton.setVisible(True)
            self.getCropButton.setVisible(True)
            self.leftValueButton.setVisible(True)
            self.rightValueButton.setVisible(True)
            self.topValueButton.setVisible(True)
            self.bottomValueButton.setVisible(True)
            self.leftLabel.setVisible(True)
            self.rightLabel.setVisible(True)
            self.topLabel.setVisible(True)
            self.bottomLabel.setVisible(True)
            self.getRGBButton.setVisible(True)
            self.redLabel.setVisible(True)
            self.greenLabel.setVisible(True)
            self.blueLabel.setVisible(True)
            self.rValueButton.setVisible(True)
            self.gValueButton.setVisible(True)
            self.bValueButton.setVisible(True)
            self.getNoiseButton.setVisible(True)
            self.getDeblurButton.setVisible(True)
            self.getConsecutiveButton.setVisible(True)
            self.noiseValueButton.setVisible(True)
            self.reloadButton.setVisible(True)
            self.cropInfo.setVisible(True)
            if self.isGreyScale():
                self.getRGBButton.setEnabled(False)
                self.redLabel.setEnabled(False)
                self.greenLabel.setEnabled(False)
                self.blueLabel.setEnabled(False)
                self.rValueButton.setEnabled(False)
                self.gValueButton.setEnabled(False)
                self.bValueButton.setEnabled(False)
                self.getDetectEdgeButton.setEnabled(False)
                self.getGrayscaleButton.setEnabled(False)
                self.getReverseButton.setEnabled(False)
                self.getBlurButton.setEnabled(False)
                self.getSaturationButton.setEnabled(False)
                self.getContrastButton.setEnabled(False)
                self.getBrightnessButton.setEnabled(False)
                self.blurValueButton.setEnabled(False)
                self.saturationValueButton.setEnabled(False)
                self.contrastValueButton.setEnabled(False)
                self.brightnessValueButton.setEnabled(False)
                self.infoLabel.setText("Latest Info: Grayscale Image Loaded")
            else:
                self.getRGBButton.setEnabled(True)
                self.redLabel.setEnabled(True)
                self.greenLabel.setEnabled(True)
                self.blueLabel.setEnabled(True)
                self.rValueButton.setEnabled(True)
                self.gValueButton.setEnabled(True)
                self.bValueButton.setEnabled(True)
                self.getDetectEdgeButton.setEnabled(True)
                self.getGrayscaleButton.setEnabled(True)
                self.getReverseButton.setEnabled(True)
                self.getBlurButton.setEnabled(True)
                self.getSaturationButton.setEnabled(True)
                self.getContrastButton.setEnabled(True)
                self.getBrightnessButton.setEnabled(True)
                self.blurValueButton.setEnabled(True)
                self.saturationValueButton.setEnabled(True)
                self.contrastValueButton.setEnabled(True)
                self.brightnessValueButton.setEnabled(True)
                self.infoLabel.setText("Latest Info: Image Loaded")
        else:
            self.infoLabel.setText("Load Image To Start Using Program")
            self.uploadButton.setVisible(True)
            self.showOriginalImageButton.setVisible(False)
            self.reloadButton.setVisible(False)
            self.saveButton.setVisible(False)
            self.showProcessedImageButton.setVisible(False)
            self.getDetectEdgeButton.setVisible(False)
            self.resetButton.setVisible(False)
            self.getReverseButton.setVisible(False)
            self.getFlipButton.setVisible(False)
            self.getGrayscaleButton.setVisible(False)
            self.getBlurButton.setVisible(False)
            self.blurValueButton.setVisible(False)
            self.getMirrorButton.setVisible(False)
            self.getRotateImageButton.setVisible(False)
            self.rotateValueButton.setVisible(False)
            self.getSaturationButton.setVisible(False)
            self.saturationValueButton.setVisible(False)
            self.getContrastButton.setVisible(False)
            self.contrastValueButton.setVisible(False)
            self.getBrightnessButton.setVisible(False)
            self.brightnessValueButton.setVisible(False)
            self.getCropButton.setVisible(False)
            self.leftValueButton.setVisible(False)
            self.rightValueButton.setVisible(False)
            self.topValueButton.setVisible(False)
            self.bottomValueButton.setVisible(False)
            self.leftLabel.setVisible(False)
            self.rightLabel.setVisible(False)
            self.topLabel.setVisible(False)
            self.bottomLabel.setVisible(False)
            self.getRGBButton.setVisible(False)
            self.redLabel.setVisible(False)
            self.greenLabel.setVisible(False)
            self.blueLabel.setVisible(False)
            self.rValueButton.setVisible(False)
            self.gValueButton.setVisible(False)
            self.bValueButton.setVisible(False)
            self.getNoiseButton.setVisible(False)
            self.getDeblurButton.setVisible(False)
            self.getConsecutiveButton.setVisible(False)
            self.noiseValueButton.setVisible(False)
            self.cropInfo.setVisible(False)

    def addNoise(self):
        if self.consecutive:
            self.processedImage = add_noise(np.asarray(self.processedImage), self.noiseValue)
        else:
            self.processedImage = add_noise(np.asarray(Image.open(self.imagePath)), self.noiseValue)
        self.showProcessedImage()

    def deBlur(self):
        if self.consecutive:
            self.processedImage = de_blur(self.processedImage)
        else:
            self.processedImage = de_blur(Image.open(self.imagePath))
        self.showProcessedImage()

    def reverseImage(self):
        if self.consecutive:
            self.processedImage = reverse_image(self.processedImage)
        else:
            self.processedImage = reverse_image(Image.open(self.imagePath))
        self.showProcessedImage()

    def cropImage(self):
        try:
            if self.consecutive:
                self.processedImage = crop_image(self.processedImage, self.leftValue, self.topValue, self.rightValue,
                                                 self.bottomValue)
            else:
                self.processedImage = crop_image(Image.open(self.imagePath), self.leftValue, self.topValue,
                                                 self.rightValue, self.bottomValue)
            self.showProcessedImage()
        except:
            self.infoLabel.setText("Latest Error: Invalid values for crop")

    def grayscaleImage(self):
        if self.consecutive:
            self.processedImage = grayscale_image(self.processedImage)
        else:
            self.processedImage = grayscale_image(Image.open(self.imagePath))
        self.showProcessedImage()

    def blurImage(self):
        if self.consecutive:
            self.processedImage = blur_image(self.processedImage, self.blurValue)
        else:
            self.processedImage = blur_image(Image.open(self.imagePath), self.blurValue)
        self.showProcessedImage()

    def saveImage(self):
        save_image(self.processedImage, self.saveImagePath, self.imagePath)

    def mirrorImage(self):
        if self.consecutive:
            self.processedImage = mirror_image(self.processedImage)
        else:
            self.processedImage = mirror_image(Image.open(self.imagePath))
        self.showProcessedImage()

    def adjustRGB(self):
        if self.consecutive:
            self.processedImage = adjust_rgb(self.processedImage, self.r, self.g, self.b)
        else:
            self.processedImage = adjust_rgb(Image.open(self.imagePath), self.r, self.g, self.b)
        self.showProcessedImage()

    def rotateImage(self):
        if self.consecutive:
            self.processedImage = rotate_image(self.processedImage, self.rotateValue)
        else:
            self.processedImage = rotate_image(Image.open(self.imagePath), self.rotateValue)
        self.showProcessedImage()

    def adjustSaturation(self):
        if self.consecutive:
            self.processedImage = adjust_saturation(self.processedImage, self.saturationValue)
        else:
            self.processedImage = adjust_saturation(Image.open(self.imagePath), self.saturationValue)
        self.showProcessedImage()

    def adjustContrast(self):
        if self.consecutive:
            self.processedImage = adjust_contrast(self.processedImage, self.contrastValue)
        else:
            self.processedImage = adjust_contrast(Image.open(self.imagePath), self.contrastValue)
        self.showProcessedImage()

    def adjustBrightness(self):
        if self.consecutive:
            self.processedImage = adjust_brightness(self.processedImage, self.brightnessValue)
        else:
            self.processedImage = adjust_brightness(Image.open(self.imagePath), self.brightnessValue)
        self.showProcessedImage()

    def flipImage(self):
        if self.consecutive:
            self.processedImage = flip_image(self.processedImage)
        else:
            self.processedImage = flip_image(Image.open(self.imagePath))
        self.showProcessedImage()

    def detectEdge(self):
        if self.consecutive:
            self.processedImage = detect_edge(self.processedImage)
        else:
            self.processedImage = detect_edge(Image.open(self.imagePath))
        self.showProcessedImage()

    def setSaveImagePath(self):
        self.saveImagePath = self.saveFileDialog()
        if self.saveImagePath != "":
            self.saveImage()

    def setResetImagePath(self):
        self.processedImage = Image.open(self.imagePath)
        self.showProcessedImage()

    def getImagePath(self):
        return self.imagePath

    def getSaveImagePath(self):
        return self.saveImagePath

    def showOriginalImage(self):
        Image.open(self.imagePath).show()

    def showProcessedImage(self):
        self.processedImage.show()

    def isGreyScale(self):
        image = Image.open(self.imagePath).convert('RGB')
        w, h = image.size
        self.cropInfo.setText("Image Size: " + str(w) + "x" + str(h) + " Consider this for applying crop function.")
        for i in range(w):
            for j in range(h):
                r, g, b = image.getpixel((i, j))
                if r != g != b:
                    return False
        return True

    def openFileNameDialog(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        fileName, _ = QFileDialog.getOpenFileName(self, "Load Image", "",
                                                  "Image Files (*.jpg *.jpeg *.png)", options=options)
        return fileName

    def saveFileDialog(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        fileName, _ = QFileDialog.getSaveFileName(self, "Save Image", "",
                                                  "Image Files (*.jpg *.jpeg *.png)", options=options)
        return fileName
