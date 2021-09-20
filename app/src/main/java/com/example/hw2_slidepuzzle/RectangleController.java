package com.example.hw2_slidepuzzle;

import android.view.MotionEvent;
import android.view.View;

public class RectangleController implements View.OnTouchListener {

    private RectangleSurfaceView theRect;

    public RectangleController(RectangleSurfaceView rect){
        theRect = rect;
    }



    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
