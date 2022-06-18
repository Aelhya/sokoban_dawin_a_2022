package com.example.sokoban.game.gameDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.sokoban.R;

public class Administrator extends AppCompatActivity {
    Button admin, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        admin = findViewById(R.id.admin);
        admin.setOnClickListener(view -> {
            Intent listBoard = new Intent(Administrator.this, GameDatabaseChoice.class);
            startActivity(listBoard);
        });
        list = findViewById(R.id.list);
        list.setOnClickListener(view -> {
            Intent listBoard = new Intent(Administrator.this, ListeBoard.class);
            startActivity(listBoard);
        });
    }
}