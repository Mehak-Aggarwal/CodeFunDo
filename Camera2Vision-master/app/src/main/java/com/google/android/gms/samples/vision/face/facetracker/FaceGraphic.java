/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.face.facetracker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.nfc.Tag;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.samples.vision.face.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

import static android.content.ContentValues.TAG;

/**
 * Graphic instance for rendering face position, orientation, and landmarks within an associated
 * graphic overlay view.
 */
class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float FACE_POSITION_RADIUS = 10.0f;
    private static final float ID_TEXT_SIZE = 40.0f;
    private static final float ID_Y_OFFSET = 50.0f;
    private static final float ID_X_OFFSET = -50.0f;
    private static final float BOX_STROKE_WIDTH = 5.0f;

    private static final int COLOR_CHOICES[] = {
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.MAGENTA,
            Color.RED,
            Color.WHITE,
            Color.YELLOW
    };
    private static int mCurrentColorIndex = 0;

    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;

    private Context mContext;

    FaceGraphic(GraphicOverlay overlay, Context context) {
        super(overlay);

//        mCurrentColorIndex = (mCurrentColorIndex + 1) % COLOR_CHOICES.length;
//        final int selectedColor = COLOR_CHOICES[mCurrentColorIndex];

        mContext = context;

        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(Color.RED);
//        mFacePositionPaint.setColor(selectedColor);

        mIdPaint = new Paint();
        mIdPaint.setColor(Color.RED);
//        mIdPaint.setColor(selectedColor);
        mIdPaint.setTextSize(ID_TEXT_SIZE);

        mBoxPaint = new Paint();
//        mBoxPaint.setColor(selectedColor);
        mBoxPaint.setColor(Color.RED);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);


    }

    void setId(int id) {
        mFaceId = id;
    }


    /**
     * Updates the face instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    /**
     * Draws the face annotations for position on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = face.getWidth();
        double imageHeight = face.getHeight();
        double scale = viewWidth/imageWidth;
//        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);


        double leftEyeX = 0;
        double leftEyeY = 0;
        double rightEyeX = 0;
        double rightEyeY = 0;

        for (Landmark landmark : face.getLandmarks()) {
//            float cx = (int) (landmark.getPosition().x * scale);
//            float cy = (int) (landmark.getPosition().y * scale);
            if (landmark.getType() == Landmark.LEFT_EYE) {
                leftEyeX = translateX(landmark.getPosition().x);
                leftEyeY = translateY(landmark.getPosition().y);
            }
            if (landmark.getType() == Landmark.RIGHT_EYE) {
                rightEyeX = translateX(landmark.getPosition().x);
                rightEyeY = translateY(landmark.getPosition().y);
            }
//            canvas.drawCircle(cx, cy, 10, mBoxPaint);
        }

        if (leftEyeX != 0 && leftEyeY != 0) {
            canvas.drawCircle((float) leftEyeX, (float) leftEyeY, 10, mBoxPaint);
        }

        if (rightEyeX != 0 && rightEyeY != 0) {
            canvas.drawCircle((float) rightEyeX, (float) rightEyeY, 10, mBoxPaint);
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);
        canvas.drawCircle(x, y, FACE_POSITION_RADIUS, mFacePositionPaint);
//        canvas.drawText("id: " + mFaceId, x + ID_X_OFFSET, y + ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("happiness: " + String.format("%.2f", face.getIsSmilingProbability()), x - ID_X_OFFSET, y - ID_Y_OFFSET, mIdPaint);
//        canvas.drawText("right eye: " + String.format("%.2f", face.getIsRightEyeOpenProbability()), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("left eye: " + String.format("%.2f", face.getIsLeftEyeOpenProbability()), x - ID_X_OFFSET*2, y - ID_Y_OFFSET*2, mIdPaint);

        /**
         * Change the setMode(FaceDetector.FAST_MODE)
         */
        // Euler angles to measure head pose
//        double eulerY = face.getEulerY();
//        double eulerZ = face.getEulerZ();
//        canvas.drawText("eulerY: " + Double.toString(eulerY), x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
//        canvas.drawText("eulerZ: " + Double.toString(eulerZ), x - ID_X_OFFSET * 2, y - ID_Y_OFFSET * 2, mIdPaint);

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;
        float right = x + xOffset;
        float bottom = y + yOffset;
        canvas.drawRect(left, top, right, bottom, mBoxPaint);

        double eulerY = face.getEulerY();

        Boolean b = false;
        if (Math.abs(eulerY) < 20) {
            canvas.drawText("eulerY: " + Integer.toString((int) eulerY),
                    x + ID_X_OFFSET * 2, y + ID_Y_OFFSET * 2, mIdPaint);
            canvas.drawText("Looking",
                    x + ID_X_OFFSET * 4, y + ID_Y_OFFSET * 4, mIdPaint);
            b = true;
            mFacePositionPaint.setColor(Color.GREEN);
            mIdPaint.setColor(Color.GREEN);
            mBoxPaint.setColor(Color.GREEN);

        } else {
            canvas.drawText("Not Looking",
                    x + ID_X_OFFSET * 4, y + ID_Y_OFFSET * 4, mIdPaint);
            mFacePositionPaint.setColor(Color.RED);
            mIdPaint.setColor(Color.RED);
            mBoxPaint.setColor(Color.RED);
        }

//        // Setting text View
//        TextView textView = (TextView) ((Activity) mContext).findViewById(R.id.updates);
//        String data = textView.getText().toString();
//        int len = data.length();
//        String line = face.getId() + "  " + b;
//        if (len > 60) {
//            String partial_data = data.substring(len - 30, len);
//            if (!partial_data.contains(line)) {
//                textView.append("\nUserId:" + line);
//            }
//        } else {
//            textView.append("\nUserId:" + line);
//        }


//        final ScrollView mScrollView = (ScrollView) ((Activity) mContext).findViewById(R.id.scrollView);
//
//        mScrollView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
//            }
//        }, 600);


        final float EYE_RADIUS_PROPORTION = 0.30f;
        final float IRIS_RADIUS_PROPORTION = EYE_RADIUS_PROPORTION / 2.0f;
        float distance = (float) Math.sqrt(
                (rightEyeX - leftEyeX) * (rightEyeX - leftEyeX) +
                        (rightEyeY - leftEyeY) * (rightEyeY - leftEyeY));
        float eyeRadius = EYE_RADIUS_PROPORTION * distance;
        float irisRadius = IRIS_RADIUS_PROPORTION * distance;

//        // Left eye
//        if (leftEyeX != 0 && leftEyeY != 0) {
//            canvas.drawCircle((float) leftEyeX, (float) leftEyeY, eyeRadius, mBoxPaint);
//        }
//        // Right eye
//        if (rightEyeX != 0 && rightEyeY != 0) {
//            canvas.drawCircle((float) rightEyeX, (float) rightEyeY, eyeRadius, mBoxPaint);
//        }
        if (leftEyeX != 0 && leftEyeY != 0 && rightEyeX != 0 && rightEyeY != 0) {
            canvas.drawRect((float) leftEyeX - eyeRadius, (float) leftEyeY + eyeRadius / 2,
                    (float) leftEyeX + eyeRadius, (float) leftEyeY - eyeRadius / 2, mBoxPaint);
            canvas.drawRect((float) rightEyeX - eyeRadius, (float) rightEyeY + eyeRadius / 2,
                    (float) rightEyeX + eyeRadius, (float) rightEyeY - eyeRadius / 2, mBoxPaint);
        }
    }

}
