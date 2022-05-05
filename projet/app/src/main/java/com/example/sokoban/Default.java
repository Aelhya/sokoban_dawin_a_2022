package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

public class Default extends AppCompatActivity {

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        String[] name = {"wall","wall","wall","wall","wall","wall","wall","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","wall","wall","wall","wall","wall","wall","wall"};

        int[] images = {R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur};

        gridView=findViewById(R.id.gridView);

        GridAdapter gridAdapter = new GridAdapter(Default.this,name,images);
        gridView.setAdapter(gridAdapter);
    }
}