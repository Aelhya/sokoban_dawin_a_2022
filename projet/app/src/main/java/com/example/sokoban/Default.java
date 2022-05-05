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

        int[] images = {R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur};

        gridView=findViewById(R.id.gridView);

        GridAdapter gridAdapter = new GridAdapter(Default.this,images);
        gridView.setAdapter(gridAdapter);
    }
}