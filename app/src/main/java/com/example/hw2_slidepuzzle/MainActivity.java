package com.example.hw2_slidepuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RectangleSurfaceView rectangles = findViewById(R.id.rectangleSurfaceView);
        rectangles.setOnTouchListener(rectangles);

        Button Reset = findViewById(R.id.Reset);
        Reset.setOnClickListener(rectangles);

        Button increaseTiles = findViewById(R.id.increaseTiles);
        increaseTiles.setOnClickListener(rectangles);

        Button decreaseTiles = findViewById(R.id.decreaseTiles);
        decreaseTiles.setOnClickListener(rectangles);
    }
}