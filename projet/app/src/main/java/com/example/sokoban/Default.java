package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.AdapterView;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import android.os.Bundle;

import com.example.test.databinding.ActivityMainBinding;

public class Default extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_default);
    }

    int[] images = {R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.wall,R.drawable.floor,R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.wall,R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.wall,R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.floor,R.drawable.wall,
            R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall,R.drawable.wall};

    GridAdapter gridAdapter = new GridAdapter(MainActivity.this,images);
        binding.gridView.setAdapter(gridAdapter);
}