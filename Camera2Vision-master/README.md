# Camera2Vision

# Documentation

You can download the apk from the link below :)
https://drive.google.com/file/d/1As5otj4VqyF7aNmcTXysO7dWNygX9Dtp/view?usp=sharing

This app aims at developing a non intrusive technique at imaging based automatic analysis to
extract and deliver relevant information to a blind person in a social setting.

The entire process can be divided into 3 stages
1. Using a camera source
2. Face Detection
3. Extracting relevant features such as landmarks
4. Computing a rough estimate of the distance from the camera
5. Using the Head Pose, give a rough estimate whether the person in front is Looking or not

# Face Detection

The face detection algorithm used is the standard Google vision API. The link for the standard implementation
of the Face Tracker is https://github.com/googlesamples/android-vision

**Note**:
Face tracking extends face detection to video sequences. Any face appearing in a video for any length of time can be tracked.
That is, faces that are detected in consecutive video frames can be identified as being the same person. Note that this is not
a form of face recognition; this mechanism just makes inferences based on the position and motion of the face(s) in a video
sequence.

A feature class has been implemented that helps us in extracting relevant information such as the No of faces, distance from each face, 
mid-point between the eyes etc. successfully. We were inspired by @betri28/FaceDetectCamera.

# Touch Feature of App
To assist blind people in locating different people, i.e., whether they are on the right side, front, or left side, a touch feature has been built. This feature can be used by touching anywhere on the preview screen. The touch mechanism detects a face/faces if the face lies anywhere above or below the place where we have touched (in a column). One can move his finger from the left side of the screen to the right side, to locate all the faces one by one. The top part of the screen has a small textview which displays the information.
If face is found, it will display the person nummber and it's corresponding ID, else  it will just state no face found.

Each person will have a number attached to him. This number can be used to correspond with the information one obtains in the display info button.

# Basic UI of the App
![ss](https://user-images.githubusercontent.com/28651490/38755155-4f845f08-3f82-11e8-8396-b5595f1b3c90.jpeg)



