package com.example.roell_pascal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

public class playGameView extends View {

    Cell[][] myMaze;
    Context context;
    private Cell player,exit;
    gameArray myGameArray;
    private Paint playerPaint;
    int height,width;
    public float cellSize, hMargin, vMargin;
    private SoundPool mySoundPool;
    public playGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        playerPaint = new Paint();
        playerPaint.setColor(Color.GREEN);
        player = new Cell(1,1);
        myMaze = new Cell[gameArray.getROWS()][gameArray.getCOLS()];
        myGameArray = new gameArray();

    }

    public void getMazeData(Cell[][] getMaze){
        myMaze = getMaze;
        calculatePositions();
    }

    private void checkExit(){
        if(player == exit){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
                mySoundPool = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build();
            }
            else {
                mySoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
            }
            int sound1 = mySoundPool.load(context, R.raw.sound1, 1);
            mySoundPool.play(sound1, 1, 1, 0, 0, 1);
            mySoundPool.autoPause();
            // Ton einfügen und game neu starten
            Log.d("Game", "checkExit: Spiel beendet");
            //calculatePositions();
        }
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

        hMargin = myGameArray.gethMargin(getWidth(),getHeight());
        vMargin = myGameArray.getvMargin(getWidth(),getHeight());
        cellSize = myGameArray.getCellSize(getWidth(),getHeight());

        canvas.drawColor(Color.TRANSPARENT);
        canvas.translate(hMargin,vMargin);
        canvas.drawCircle((player.col * cellSize)+hMargin,((player.row+0.5f)*cellSize),hMargin,playerPaint);

    }



}
