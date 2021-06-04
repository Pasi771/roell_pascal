package com.example.roell_pascal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class GameActivity extends AppCompatActivity {

    TabLayout myTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initViews();
    }

    protected void initViews(){
        myTabLayout = findViewById(R.id.tabLayout);
        for(int i = 1; i <= 5; i++) {
            String tabText = "Maze_" + i;
            myTabLayout.addTab(myTabLayout.newTab().setText(tabText));
        }
        myTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}