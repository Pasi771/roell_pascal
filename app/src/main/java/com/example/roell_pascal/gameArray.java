package com.example.roell_pascal;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class gameArray   {

    Context context;
    private Cell[][] gameField;
    private String maze;
    private static final int COLS = 21, ROWS = 21;
    public float cellSize, hMargin, vMargin;
    private int width, height;

    public gameArray(Context context, int width, int height){
        this.context = context;
        this.width = width;
        this.height = height;
       // Log.d("Debug", "gameArray: "+ width + ","+ height);
        createGameField();
        fillGameField();
    }

    private void createGameField(){
       // Log.d("Debug", "createGameField: ");
        gameField = new Cell[COLS][ROWS];
        for (int x = 0; x < COLS; x++){
            for (int y = 0; y < ROWS; y++){
                gameField[x][y] = new Cell(x,y);
            }
        }
    }
    private void getFileData() {
        Log.d("Debug", "getFileData: ");
        try {
            InputStream is = context.getAssets().open("Maze_1.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            maze = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillGameField(){
        //Log.d("Debug", "fillGameField: ");
        createGameField();
        getFileData();

        char[] chars = maze.toCharArray();
        int y = 0;
        int x = 0;
        for (int i = 0; i < chars.length; i++){
            if(chars[i] == 'w') {
                gameField[x][y].wall = true;
                Log.d("Hashtag", y + "," + x);
            }
            else if (chars[i] == '.') {
                gameField[x][y].wall = false;
                Log.d("Dot", y + "," + x);
            }
            else if (chars[i] == '#') {
                gameField[x][y].start = true;
                Log.d("start", y + "," + x);
            }
            else if (chars[i] == ',') {
                gameField[x][y].end = true;
                Log.d("end", y + "," + x);
            }
            x++;
            if (chars[i] == '\n'){
                y++;
                x = 0;
            }
        }
    }
    public void calculateSizes(){
        Log.d("Debug", "calculateSizes: "+ width + "," +height);
        Log.d("Debug", "calculateSizes: "+ COLS + "," +ROWS);
        if(width/height < COLS/ROWS) {
            //Log.d("Debug", "Ich bin in der IF ");
            cellSize = width / (COLS + 1);
            //Log.d("Debug", "Ich bin nach der IF ");
        }
        else{
            //Log.d("Debug", "Ich bin in der ELSE ");
            cellSize = height/(ROWS +1);
        }

       // Log.d("Debug", "calculateSizes: cellsize "+ cellSize);
        hMargin = (width - COLS*cellSize)/2;
        vMargin = (height - ROWS*cellSize)/2;
    }

    public float getCellSize() {
        calculateSizes();
        return cellSize;
    }

    public float getvMargin() {
        calculateSizes();
        return vMargin;
    }

    public float gethMargin() {
        calculateSizes();
        return hMargin;
    }

    public Cell[][] getGameField() {
        return gameField;
    }

    public static int getCOLS() {
        return COLS;
    }

    public static int getROWS() {
        return ROWS;
    }
}
