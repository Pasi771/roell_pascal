package com.example.roell_pascal;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.roell_pascal.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    SensorManager mSensorManager;
    MovementSensor mMovementSensor;
    playGameView myPlayGameView;
    GameView myGameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myPlayGameView = findViewById(R.id.playGameView);
        myGameView = findViewById(R.id.GameView);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void test(){

    }
}