package com.example.sokoban.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.sokoban.R;
import com.example.sokoban.game.db.Boards;
import com.example.sokoban.game.db.HelperDatabase;

public class DetailBoard extends AppCompatActivity {

    EditText board_id_edit, name_edit, rows_edit, columns_edit;
    Button edit, delete;
    String board_id;
    HelperDatabase h = new HelperDatabase(DetailBoard.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_board);

        board_id_edit = findViewById(R.id.board_id_edit);
        name_edit = findViewById(R.id.name_edit);
        rows_edit = findViewById(R.id.rows_edit);
        columns_edit = findViewById(R.id.columns_edit);

        board_id = getIntent().getStringExtra("board_id");
        Boards b = h.getOneBoard(board_id);

        name_edit.setText(b.getName());
        rows_edit.setText(b.getRows());
        columns_edit.setText(b.getColumns());


    }
}