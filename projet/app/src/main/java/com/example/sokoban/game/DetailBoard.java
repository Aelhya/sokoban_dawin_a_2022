package com.example.sokoban.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        edit = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);

        board_id = getIntent().getStringExtra("board_id");
        Boards b = h.getOneBoard(board_id);

        board_id_edit.setText(b.get_id());
        name_edit.setText(b.getName());
        rows_edit.setText(String.valueOf(b.getRows()));
        columns_edit.setText(String.valueOf(b.getColumns()));

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boards b = new Boards(board_id_edit.getText().toString(), name_edit.getText().toString(),
                        Integer.parseInt(rows_edit.getText().toString()), Integer.parseInt(columns_edit.getText().toString()));

                h.updateBoard(b);
                Intent intent = new Intent(DetailBoard.this, ListeBoard.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                h.deleteBoard(board_id_edit.getText().toString());
                Intent intent = new Intent(DetailBoard.this, ListeBoard.class);
                startActivity(intent);
            }
        });

    }
}