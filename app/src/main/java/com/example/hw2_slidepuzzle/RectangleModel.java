package com.example.hw2_slidepuzzle;

public class RectangleModel {
    public int[][] rectangles;
    public int[][] solvedPuzzle;

    RectangleModel(int row){

        rectangles = new int[4][4];
        solvedPuzzle = new int[4][4];

        int id = 1;
        for (int i = 0; i < row; i++){
            for(int j = 0; j < row; j++){

                rectangles[i][j] = id;
                solvedPuzzle[j][j] = id;
                id++;
            }
        }
        rectangles[3][3] = -1;
    }

}
