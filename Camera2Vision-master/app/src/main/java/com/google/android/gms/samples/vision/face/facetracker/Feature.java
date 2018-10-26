package com.google.android.gms.samples.vision.face.facetracker;

import android.graphics.Point;
import android.graphics.PointF;

import static java.lang.Math.abs;

public class Feature {

    private float width;
    private float height;
    private PointF midEye;
    private float pose;
    private float distance;
    private int id;
    private boolean looking = false;
    private PointF position;

    public Feature() {
        id = 0;
        width = 0;
        height = 0;
        distance = 0;
        midEye = new PointF(0.0f, 0.0f);
        pose = 0.0f;
        position = new PointF(0.0f, 0.0f);
    }

    public void setFace(int id, float width, float height, PointF midEye, float pose, PointF position) {
        set(id, width, height, midEye, pose, position);
    }

    public void clear() {
        set(0, 0, 0, new PointF(0.0f, 0.0f), 0.0f, new PointF(0.0f, 0.0f));
    }

    public synchronized void set(int id, float width, float height, PointF midEye, float pose, PointF position) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.midEye.set(midEye);
        this.pose = pose;
        this.distance = distance;
        if (abs(this.pose) < 20) {
            this.looking = true;
        } else {
            this.looking = false;
        }
        this.position = position;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public PointF getMidEye() {
        return midEye;
    }

    public void setMidEye(PointF midEye) {
        this.midEye = midEye;
    }

    public float getPose() {
        return pose;
    }

    public void setPose(float pose) {
        this.pose = pose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistance() {
        this.distance = setDistance();
        return (int) this.distance;
    }

    private float setDistance(){
        return (58000 * 36) / (this.width * this.height);
    }

    public void setLooking(boolean looking) {
        this.looking = looking;
    }

    public String isLooking() {
        if (looking){
            return "Looking";
        }
        else{
            return "Not Looking";
        }
    }

    @Override
    public String toString() {
        return " " + this.getHeight() + " " + this.getWidth() + " " + this.getId();
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public PointF getPosition() {
        return position;
    }
}
