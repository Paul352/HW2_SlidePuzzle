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

    private PuzzleTile[][] board;

    private final Random generator = new Random();
    // Board row count can only be same as col count.
    private int boardDimen;
    // Max dimensions of board is 9x9.
    private final int MAX_SIZE = 9;

    public RectangleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(10);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(24);
        textPaint.setTextAlign(Paint.Align.CENTER);

        // Initialize board as a 4x4.
        boardDimen = 4;
        boardInit();

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Use these to draw the tiles.
        int h = getHeight()/boardDimen;
        int w = getWidth()/boardDimen;

        int id = 1;
        // Ensure this only executes once right after board is initialized!!!!! Id == 0 only just
        // after board is initialized
        // Set attributes of each tile on board.
        // After setting the attributes of each tile, randomize the board.
        if (board[0][0].getTileNum() == 0){
            // Set attributes of each element of board.
            for (int i = 0; i < boardDimen; i++){
                for(int j = 0; j < boardDimen; j++){

                    board[i][j].setAttributes(id, j * w, i * h,
                            (j * w + w), (i * h + h));
                    id++;
                }
            }
            // After the board is ordered numerically, randomize the tiles.
            randomizeBoard();
        }
        // Reset id to one so we can check each spot of the board.
        id = 1;
        // Loop through the entire board to draw each tile.
        for(int i = 0; i < boardDimen; i++){
            for(int j = 0; j < boardDimen; j++){
                // If the tile is the right spot, draw the number as green.
                // Else, draw it as red.
                if(board[i][j].getTileNum() == id){
                    textPaint.setColor(Color.GREEN);
                }
                else{
                    textPaint.setColor(Color.RED);
                }
                // If the tile is not the empty tile, draw the tile and tileNum.
                // Do NOT draw the empty tile. It will be displayed as a blank square.
                if (board[i][j].getTileNum() != boardDimen*boardDimen) {
                    // The + and - 10 is to give little gaps between tiles when drawing them.
                    canvas.drawRect(board[i][j].getLeft() + 10, board[i][j].getTop() + 10,
                            board[i][j].getRight() - 10, board[i][j].getBottom() - 10, rectPaint);

                    canvas.drawText("" + board[i][j].getTileNum(),
                            j * w + (float) w / 2, (i + 1) * h - (float) h / 2, textPaint);
                }
                id++;
            }
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        // Store where the user clicked as a float.
        float downX = event.getX();
        float downY = event.getY();
        // Figure out which tile the user clicked inside of.
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
     * @param rowIncrement Increment of row (can be -1, 0, or 1)
     * @param colIncrement Increment of col (can be -1, 0, or 1)
     * @return Whether the move is legal.
     */
    public boolean checkLegalMove(int row, int col, int rowIncrement, int colIncrement){
        return isInBounds(row + rowIncrement, col + colIncrement)
                && board[row + rowIncrement][col + colIncrement].getTileNum() == boardDimen*boardDimen;
    }

    /** isInBounds checks if the requested check is in bounds. Used in checkLegalMove only.
     *
     * @param row Tile row
     * @param col Col of tile being checked.
     * @return Whether the checked area is in bounds of the board.
     */
    public boolean isInBounds(int row, int col){
        return row >= 0 && row < boardDimen && col >= 0 && col < boardDimen;
    }

    /** getBoardPosR gets the row of the tile the user clicked.
     *
     * @param x X location of where user clicked.
     * @param y Y location of where user clicked.
     * @return Row position of tile user clicked.
     */
    public int getBoardPosR(float x, float y){

        for(int i = 0; i < boardDimen; i++){
            for(int j = 0; j < boardDimen; j++){

                if(board[i][j].pointInTile(x,y)){
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

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){

                if(board[i][j].pointInTile(x,y)){
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
        int temp = board[row + incR][col + incC].getTileNum();
        board[row + incR][col + incC].setTileNum(board[row][col].getTileNum());
        board[row][col].setTileNum(temp);
    }


    @Override
    public void onClick(View view) {
        // If user clicks reset button, randomize board.
        if (view.getId() == R.id.Reset){
            randomizeBoard();
        }
        // Increase size of board and re-initialize.
        else if (view.getId() == R.id.increaseTiles){

            if (boardDimen < MAX_SIZE) {
                boardDimen++;
                boardInit();
            }
        }
        // Decrease size of board and re-initialize.
        else if (view.getId() == R.id.decreaseTiles){

            if (boardDimen > 3) {
                boardDimen--;
                boardInit();
            }
        }
        invalidate();
    }

    /** initBoard initializes the board.
     *
     */
    public void boardInit(){
        // Initialize the board array.
        board = new PuzzleTile[boardDimen][boardDimen];

        for (int i = 0; i < boardDimen; i++){
            for(int j = 0; j < boardDimen; j++){
                // Initialize each tile of board.
                board[i][j] = new PuzzleTile();
            }
        }
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
        for(int i = boardDimen - 1; i > 0; i--){
            for(int j = boardDimen - 1; j > 0; j--){

                int m = generator.nextInt(i + 1);
                int n = generator.nextInt(j + 1);

                int temp = board[i][j].getTileNum();
                board[i][j].setTileNum(board[m][n].getTileNum());
                board[m][n].setTileNum(temp);
            }
        }

    }

}
