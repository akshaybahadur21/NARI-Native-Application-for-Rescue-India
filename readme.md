## WristBand Module for NARI
This code acts as a wristband module for Raspberry Pi.
It also has a blackbook that gives you the intuition about how the project works.
It also consists of the Android ASync class which connects to the wristband module using Socket Programming

## Applications
The main application is aimed at providing safety for women in India. This module manages to act as a perfect fitness tracker as well as a rescue module.

## Specifications
##### NARI (Native Application for Rescue, India)
##### Jan 2017 – May 2017
##### Features of Android App: Online Registration, Google Maps Integration, Firebase Integration
##### Features of Wristband: SOS signal with just a push of a button, display time and date, Firebase integration, pedometer used for counting steps and distance, integration with the android app.
##### Generated predictive results from Crimes against woman using Machine Learning (ARIMA)
##### Hardware used- Raspberry pi-3B
##### Technology used: Android SDK, Notepad++, Firebase DB
##### Programming Languages: Java, Python
##### Academic Course Code: 0801 


### Code Requirements
The example code is in Python ([version 2.7](https://www.python.org/download/releases/2.7/) or higher will work). 

### Dependencies

1) import cv2
2) import immutils
3) import dlib
4) import scipy


### Description

A computer vision system that can automatically detect driver drowsiness in a real-time video stream and then play an alarm if the driver appears to be drowsy.

### Algorithm

Each eye is represented by 6 (x, y)-coordinates, starting at the left-corner of the eye (as if you were looking at the person), and then working clockwise around the eye:.

<img src="https://github.com/akshaybahadur21/Drowsiness_Detection/blob/master/eye1.jpg">

### Condition

It checks 20 consecutive frames and if the Eye Aspect ratio is lesst than 0.25, Alert is generated.

#### Relationship

<img src="https://github.com/akshaybahadur21/Drowsiness_Detection/blob/master/eye2.png">

#### Summing up

<img src="https://github.com/akshaybahadur21/Drowsiness_Detection/blob/master/eye3.jpg">


For more information, [see](https://www.pyimagesearch.com/2017/05/08/drowsiness-detection-opencv/)

### Working Example

<img src="https://github.com/akshaybahadur21/Drowsiness_Detection/blob/master/drowsy.gif">



### Execution
To run the code, type `python Drowsiness_Detection.py`

```
python Drowsiness_Detection.py
```
