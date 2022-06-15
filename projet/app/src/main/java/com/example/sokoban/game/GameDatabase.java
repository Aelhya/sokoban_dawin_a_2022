package com.example.sokoban.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sokoban.R;
import com.example.sokoban.utils.db.Boards;

public class GameDatabase extends AppCompatActivity {

    EditText boardId, name, rows, columns;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_database);

        boardId = findViewById(R.id.board_id);
        name = findViewById(R.id.name);
        rows = findViewById(R.id.rows);
        columns = findViewById(R.id.columns);
        b= findViewById(R.id.button);

        b.setOnClickListener(view -> {
            Boards b = new Boards(boardId.getText().toString(), name.getText().toString(), Integer.parseInt(rows.getText().toString()), Integer.parseInt(columns.getText().toString()));
        });

    }
}