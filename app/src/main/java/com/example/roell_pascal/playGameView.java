package com.example.roell_pascal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class playGameView extends View {

    Cell[][] myMaze;
    Context context;
    private Cell player,exit;
    gameArray myGameArray;
    private Paint playerPaint;
    int height,witdh;
    public float cellSize, hMargin, vMargin;

    public playGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        playerPaint = new Paint();
        playerPaint.setColor(Color.GREEN);

        Log.d("Debug", "Init: "+ height +", getHeight:"+ getHeight() + ", witdh"+ witdh+ ", getwitdh: " +getWidth());
        //setPlayerEndLocation();
    }

    private void checkExit(){
        if(player == exit){
            // Ton einfügen und game neu starten
            Log.d("Game", "checkExit: Spiel beendet");
            calculatePositions();
        }
    }

    public void setPlayerEndLocation(int with, int height){

        Log.d("Debug", "setPlayerEndLocation: "+ height +", getHeight:"+ getHeight() + ", witdh"+ witdh+ ", getwitdh: " +getWidth());
        gameArray myGameArray = new gameArray(context, with, height);
        myMaze = new Cell[gameArray.getROWS()][gameArray.getCOLS()];
        myGameArray.fillGameField();
        myMaze = myGameArray.getGameField();

        hMargin = myGameArray.gethMargin();
        vMargin = myGameArray.getvMargin();
        cellSize = myGameArray.getCellSize();

        calculatePositions();
    }
    private void calculatePositions(){
        for(int x = 0; x < gameArray.getCOLS(); x++){
            for(int y = 0; y < gameArray.getROWS(); y++){
                if(myMaze[x][y].start)
                    player = myMaze[x][y];
                if(myMaze[x][y].end)
                    exit = myMaze[x][y];
            }
        }
    }

    public void makeMove(float xAchsis, float yAchsis){
        float x = xAchsis;
        float y = yAchsis;

        Log.d("DebugMove", "makeMove: "+x + " ," + y);
        if (Math.abs(x) > 2 || Math.abs(y) > 2) {
            if (Math.abs(x) > Math.abs(y)) {
                // move in x-Direction
                if (x > 2) {
                    movePlayer(Direction.LEFT);
                } else {
                    movePlayer(Direction.RIGHT);
                }
            } else {
                if (y > 2) {
                    movePlayer(Direction.DOWN);
                } else {
                    movePlayer(Direction.UP);
                }
            }
        }

    }

    private void movePlayer(Direction direction){
        switch (direction){
            case UP:
                if (!myMaze[player.col][player.row -1].wall)
                    player = myMaze[player.col][player.row -1];
                break;
            case DOWN:
                if (!myMaze[player.col][player.row +1].wall)
                    player = myMaze[player.col][player.row + 1];
                break;
            case LEFT:
                if (!myMaze[player.col -1][player.row].wall)
                    player = myMaze[player.col - 1][player.row];
                break;
            case RIGHT:

                if (!myMaze[player.col +1][player.row].wall)
                     player = myMaze[player.col + 1][player.row];
                break;
        }
        checkExit();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){

        if(height != getHeight() && witdh != getWidth()){
            height = getHeight();
            witdh = getWidth();
            setPlayerEndLocation(witdh,height);
        }


        canvas.drawColor(Color.TRANSPARENT);
        canvas.translate(hMargin,vMargin);
        canvas.drawCircle((player.col * cellSize)+hMargin,((player.row+0.5f)*cellSize),hMargin,playerPaint);

    }

}
