package com.example.hw2_slidepuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;



public class RectangleSurfaceView extends SurfaceView implements View.OnTouchListener,
    View.OnClickListener{

    private final Paint rectPaint;
    private final Paint textPaint;
    private PuzzleRectangle[][] puzzleRect;
    private static final Random generator = new Random();

    public RectangleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10);

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);
        textPaint.setTextAlign(Paint.Align.CENTER);


        puzzleRect = new PuzzleRectangle[4][4];

        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                // Initialize each tile of board.
                puzzleRect[i][j] = new PuzzleRectangle();
            }
        }

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int h = getHeight()/4;
        int w = getWidth()/4;
        int id = 1;
        // Ensure this only runs at beginning of program!!!
        if (puzzleRect[0][0].getTileNum() == 0){

            for (int i = 0; i < 4; i++){
                for(int j = 0; j < 4; j++){
                    // Initialize each element of board.
                    puzzleRect[i][j].setAttributes(id, j * w, i * h, j * w + w, i * h + h);
                    id++;
                }
            }
            randomizeBoard();
        }

        id = 1;

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                // If the tile is the right spot, draw it as green.
                // Else, draw it as red.
                if(puzzleRect[i][j].getTileNum() == id){
                    rectPaint.setColor(Color.GREEN);
                }
                else{
                    rectPaint.setColor(Color.RED);
                }
                // If the tile is not the empty tile, draw the tile and tileNum.
                if (puzzleRect[i][j].getTileNum() != 16) {
                    canvas.drawRect(puzzleRect[i][j].getLeft(), puzzleRect[i][j].getTop(),
                            puzzleRect[i][j].getRight(), puzzleRect[i][j].getBottom(), rectPaint);

                    canvas.drawText("" + puzzleRect[i][j].getTileNum(),
                            j * w + (float) w / 2, (i + 1) * h - (float) h / 2, textPaint);
                }
                id++;
            }
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        float downX = event.getX();
        float downY = event.getY();

        int row = getBoardPosR(downX,downY);
        int col = getBoardPosC(downX,downY);
        // Take tile user clicked, and check if below, above, left, or right is a legal move.
        // If it is a legal move, swap the tile.
        if(checkLegalMove(row, col, 0, 1)){
            swap(row,col,0,1);
            invalidate();
            return true;
        }
        if (checkLegalMove(row, col, 0, -1)){
            swap(row,col,0,-1);
            invalidate();
            return true;
        }
        if (checkLegalMove(row, col, 1, 0)) {
            swap(row,col,1,0);
            invalidate();
            return true;
        }
        if (checkLegalMove(row, col, -1, 0)){
            swap(row,col,-1,0);
            invalidate();
            return true;
        }
        return false;
    }

    /** checkLegalMove checks if the possible move is a legal move.
     *
     * @param row Clicked tile row
     * @param col Clicked tile col
     * @param rowI Increment of row (can be -1, 0, or 1)
     * @param colI Increment of col (can be -1, 0, or 1)
     * @return Whether the move is legal.
     */
    public boolean checkLegalMove(int row, int col, int rowI, int colI){
        return isInBounds(row + rowI, col + colI)
                && puzzleRect[row + rowI][col + colI].getTileNum() == 16;
    }

    /** isInBounds checks if the requested check is in bounds. Used in checkLegalMove only.
     *
     * @param row Tile row
     * @param col Col of tile being checked.
     * @return Whether the area is legal.
     */
    public boolean isInBounds(int row, int col){
        return row >= 0 && row < 4 && col >= 0 && col < 4;
    }

    /** getBoardPosR gets the row of the tile the user clicked.
     *
     * @param x X location of where user clicked.
     * @param y Y location of where user clicked.
     * @return Row position of tile user clicked.
     */
    public int getBoardPosR(float x, float y){

        for(int i = 0; i < puzzleRect.length; i++){
            for(int j = 0; j < puzzleRect.length; j++){

                if(puzzleRect[i][j].pointInRect(x,y)){
                    return i;
                }
            }
        }
        return -1;
    }

    /** getBoardPosC gets the col of the tile the user clicked.
     *
     * @param x X location of where user clicked.
     * @param y Y location of where user clicked.
     * @return Column position of tile user clicked.
     */
    public int getBoardPosC(float x, float y){

        for(int i = 0; i < puzzleRect.length; i++){
            for(int j = 0; j < puzzleRect.length; j++){

                if(puzzleRect[i][j].pointInRect(x,y)){
                    return j;
                }
            }
        }
        return -1;
    }

    /** swap takes the tileNum of the tile the user clicks and swaps its id with the empty tile.
     *
     * @param row Row
     * @param col Column
     * @param incR Increment of row (can be -1, 0, or 1)
     * @param incC Increment of col (can be -1, 0, or 1)
     */
    public void swap(int row, int col, int incR, int incC){
        // All that needs to be is swap the tileNumber of the clicked tile and empty tile.
        int temp = puzzleRect[row + incR][col + incC].getTileNum();
        puzzleRect[row + incR][col + incC].setTileNum(puzzleRect[row][col].getTileNum());
        puzzleRect[row][col].setTileNum(temp);
    }


    @Override
    public void onClick(View view) {
        randomizeBoard();
        invalidate();
    }

    /** randomizeBoard takes all of the tiles on the board and randomly shuffles them.
     *
     */
    public void randomizeBoard(){
        /*
        External Citation
        Date: 14 September 2015
        Problem: Could not get shuffle tiles randomly.
        Resource: https://stackoverflow.com/questions/20190110/2d-int-array-shuffle
        Solution: I used the example code from this post and modified it for my needs.
        */
        for(int i = puzzleRect.length - 1; i > 0; i--){
            for(int j = puzzleRect[i].length - 1; j > 0; j--){

                int m = generator.nextInt(i + 1);
                int n = generator.nextInt(j + 1);

                int temp = puzzleRect[i][j].getTileNum();
                puzzleRect[i][j].setTileNum(puzzleRect[m][n].getTileNum());
                puzzleRect[m][n].setTileNum(temp);
            }
        }

    }

}
