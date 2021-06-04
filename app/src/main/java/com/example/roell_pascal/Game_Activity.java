package com.example.roell_pascal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.roell_pascal.databinding.ActivityGameBinding;

public class Game_Activity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityGameBinding binding;
    playGameView myPlayGameView;
    GameView myGameView;
    SensorManager mSensorManager;
    MovementSensor mMovementSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mMovementSensor = new MovementSensor() {
            @Override
            public void onTilt(float xVal, float yVal) {
                if (Math.abs(xVal) > 2 || Math.abs(yVal) > 2) {
                    myPlayGameView.makeMove(xVal, yVal);
                }
            }
        };
        mMovementSensor.setGravitationalConstant(SensorManager.GRAVITY_EARTH);


    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mMovementSensor);
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(mMovementSensor, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_game);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}