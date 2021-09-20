package com.example.hw2_slidepuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;


public class RectangleSurfaceView extends SurfaceView {

    private Paint rectPaint, textPaint;
    private RectangleModel rectangleViewModel;


    public RectangleSurfaceView(Context context,AttributeSet attrs) {
        super(context, attrs);

        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        textPaint.setTextAlign(Paint.Align.CENTER);

        rectangleViewModel = new RectangleModel(4);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int h = canvas.getHeight()/4;
        int w = canvas.getWidth()/4;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){

                if(rectangleViewModel.rectangles[i][j] == rectangleViewModel.solvedPuzzle[i][j]){
                    rectPaint.setColor(Color.GREEN);
                }
                else{
                    rectPaint.setColor(Color.RED);
                }


                if (rectangleViewModel.rectangles[i][j] != -1) {
                    canvas.drawRect(i * w, j * h, i * w + w, j * h + h, rectPaint);
                    canvas.drawText(""+ rectangleViewModel.rectangles[i][j],i*w + w/2,(j+1)*h - h/2,textPaint);
                }
            }
        }

    }

}
