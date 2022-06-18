package com.example.sokoban.game.gameDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.game.db.HelperDatabase;

public class ListeBoard extends AppCompatActivity {

    ListView ls;
    HelperDatabase h = new HelperDatabase(ListeBoard.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_board);

        ls = findViewById(R.id.lst);
        Cursor c = h.getAllBoards();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListeBoard.this, R.layout.item, c,
                new String[]{c.getColumnName(0), c.getColumnName(1), c.getColumnName(2), c.getColumnName(3)},
                new int[]{R.id.board_id_list, R.id.name_list, R.id.columns_list, R.id.rows_list}, 1);

        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.board_id_list);
                Intent intent = new Intent(ListeBoard.this, DetailBoard.class);
                intent.putExtra("board_id", t.getText().toString());
                startActivity(intent);
            }
        });

    }
}