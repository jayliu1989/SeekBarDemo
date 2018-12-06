package com.jay.seekbardemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.jay.seekbar.CustomSeekBar;

public class MainActivity extends AppCompatActivity {
    private CustomSeekBar customSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        customSeekbar = findViewById(R.id.cSeekBar);

        customSeekbar.setOnItemSelectedClickLienter(new CustomSeekBar.OnItemSelectedClickLienter() {
            @Override
            public void onItemClick(int value) {
            }
        });
    }
}
