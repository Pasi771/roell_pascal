package com.example.roell_pascal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class GameView extends View {
    Cell[][] myMaze;
    Context context;
    gameArray myGameArray;
    private Paint wallPaint, pathPaint, startPaint, endPaint;
    private int width, height;
    String maze;
    public float cellSize, hMargin, vMargin;


    public GameView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        this.context = context.getApplicationContext();

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        pathPaint = new Paint();
        pathPaint.setColor(Color.WHITE);
        endPaint = new Paint();
        endPaint.setColor(Color.RED);
        startPaint = new Paint();
        startPaint.setColor(Color.BLUE);
        //Log.d("Debug", "GameView: " + getWidth() +getHeight());
        myGameArray = new gameArray();
        myMaze = new Cell[21][21];
        myMaze = myGameArray.getGameField();
        fillGameField();
    }

    public Cell[][] getMyMaze() {
        return myMaze;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.GRAY);

        Log.d("Debug", "onDraw GameView: "+ getWidth() +getHeight());
        hMargin = myGameArray.gethMargin(getWidth(),getHeight());
        vMargin = myGameArray.getvMargin(getWidth(),getHeight());
        cellSize = myGameArray.getCellSize(getWidth(),getHeight());

        canvas.translate(hMargin,vMargin);

        for(int x= 0; x < gameArray.getCOLS(); x++){
            for(int y = 0; y < gameArray.getROWS() ; y++){
                if (myMaze[x][y].wall)
                    canvas.drawRect(x*cellSize,y*cellSize,(x+1)*cellSize,(y+1)*cellSize,wallPaint);
                else if(myMaze[x][y].start)
                    canvas.drawRect(x*cellSize,y*cellSize,(x+1)*cellSize,(y+1)*cellSize,startPaint);
                else if(myMaze[x][y].end)
                    canvas.drawRect(x*cellSize,y*cellSize,(x+1)*cellSize,(y+1)*cellSize,endPaint);
                else if(!myMaze[x][y].wall)
                    canvas.drawRect(x*cellSize,y*cellSize,(x+1)*cellSize,(y+1)*cellSize,pathPaint);

            }
        }
    }



    public void fillGameField(){

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

        char[] chars = maze.toCharArray();
        int y = 0;
        int x = 0;
        for (int i = 0; i < chars.length; i++){
            if(chars[i] == 'w') {
                myMaze[x][y].wall = true;
                Log.d("Hashtag", y + "," + x);
            }
            else if (chars[i] == '.') {
                myMaze[x][y].wall = false;
                Log.d("Dot", y + "," + x);
            }
            else if (chars[i] == '#') {
                myMaze[x][y].start = true;
                Log.d("start", y + "," + x);
            }
            else if (chars[i] == ',') {
                myMaze[x][y].end = true;
                Log.d("end", y + "," + x);
            }
            x++;
            if (chars[i] == '\n'){
                y++;
                x = 0;
            }
        }
    }
}
