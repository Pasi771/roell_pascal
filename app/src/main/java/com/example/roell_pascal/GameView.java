package com.example.roell_pascal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class GameView extends View {
    Cell[][] myMaze;
    Context context;
    gameArray myGameArray;
    private Paint wallPaint, pathPaint, startPaint, endPaint;
    private int width, height;


    public GameView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        pathPaint = new Paint();
        pathPaint.setColor(Color.WHITE);
        endPaint = new Paint();
        endPaint.setColor(Color.RED);
        startPaint = new Paint();
        startPaint.setColor(Color.BLUE);
        //Log.d("Debug", "GameView: " + getWidth() +getHeight());

    }


    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.GRAY);
        width = getWidth();
        height = getHeight();
        Log.d("Debug", "onDraw GameView: "+ getWidth() +getHeight());
        gameArray myGameArray = new gameArray(context, getWidth(), getHeight());
        myMaze = new Cell[gameArray.getROWS()][gameArray.getCOLS()];
        myGameArray.fillGameField();
        myMaze = myGameArray.getGameField();


        float hMargin = myGameArray.gethMargin();
        float vMargin = myGameArray.getvMargin();
        float cellSize = myGameArray.getCellSize();
       // Log.d("Debug", "onDraw: "+ hMargin + ","+vMargin);
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
    public int getWidthScreen() {
        return width;
    }


    public int getHeightScreen() {
        return height;
    }


}
