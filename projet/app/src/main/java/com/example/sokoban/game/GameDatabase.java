package com.example.sokoban.game;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sokoban.R;
import com.example.sokoban.game.db.Boards;
import com.example.sokoban.game.db.HelperDatabase;

public class GameDatabase extends AppCompatActivity {

    EditText _id, name, rows, columns;
    Button b;

    HelperDatabase h = new HelperDatabase(GameDatabase.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database);

        _id = findViewById(R.id.board_id);
        name = findViewById(R.id.name);
        rows = findViewById(R.id.rows);
        columns = findViewById(R.id.columns);
        b= findViewById(R.id.button);

        b.setOnClickListener(view -> {
            Boards b = new Boards(_id.getText().toString(), name.getText().toString(), Integer.parseInt(rows.getText().toString()), Integer.parseInt(columns.getText().toString()));
            h.insertBoards(b);
            Intent i = new Intent(GameDatabase.this, ListeBoard.class);
            startActivity(i);
        });

    }
}