package com.google.android.gms.samples.vision.face.facetracker;

import android.content.Intent;
import android.media.FaceDetector;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SplashScreen extends AppCompatActivity {

    public RelativeLayout splash;

    public void init() {
        splash = (RelativeLayout) findViewById(R.id.splash);

        splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(SplashScreen.this, FaceTrackerActivity.class);
                startActivity(toy);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }
}