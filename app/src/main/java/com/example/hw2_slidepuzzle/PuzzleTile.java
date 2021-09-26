package com.example.hw2_slidepuzzle;

public class PuzzleTile {
    private int tileNum;
    private int left, top, right, bottom;

    public PuzzleTile(){
        tileNum = 0;
        left = 0;
        top = 0;
        right = 0;
        bottom = 0;
    }

    public void setAttributes(int idNum, int l, int t, int r, int b){
        tileNum = idNum;
        left = l;
        top = t;
        right = r;
        bottom = b;

    }

    public boolean pointInTile(float x, float y){
        return this.left <= x && this.right >= x && this.top <= y && this.bottom >= y;
    }

    public int getTileNum(){
        return this.tileNum;
    }

    public void setTileNum(int num){
        this.tileNum = num;
    }

    public int getLeft(){
        return this.left;
    }

    public int getTop(){
        return this.top;
    }

    public int getRight(){
        return this.right;
    }

    public int getBottom(){
        return this.bottom;
    }

}
