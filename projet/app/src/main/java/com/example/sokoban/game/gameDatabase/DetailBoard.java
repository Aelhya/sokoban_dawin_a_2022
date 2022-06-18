package com.example.sokoban.game.gameDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sokoban.R;
import com.example.sokoban.game.db.Boards;
import com.example.sokoban.game.db.HelperDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DetailBoard extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 42;
    EditText board_id_edit, name_edit, rows_edit, columns_edit;
    Button edit, delete, add_file, game;
    String board_id, file_path;
    HelperDatabase h = new HelperDatabase(DetailBoard.this);
    TextView file_content, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_board);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        board_id_edit = findViewById(R.id.board_id_edit);
        name_edit = findViewById(R.id.name_edit);
        rows_edit = findViewById(R.id.rows_edit);
        columns_edit = findViewById(R.id.columns_edit);
        file_content = findViewById(R.id.board_content);
        content = findViewById(R.id.content);

        edit = findViewById(R.id.button_update);
        delete = findViewById(R.id.button_delete);
        add_file = findViewById(R.id.add_rows);
        game = findViewById(R.id.game);

        board_id = getIntent().getStringExtra("board_id");
        Boards b = h.getOneBoard(board_id);

        board_id_edit.setText(b.get_id());
        name_edit.setText(b.getName());
        rows_edit.setText(String.valueOf(b.getRows()));
        columns_edit.setText(String.valueOf(b.getColumns()));
        if (b.getDescription() != null) {
            content.setText(String.valueOf(b.getDescription()));
            game.setVisibility(View.VISIBLE);
        } else {
            game.setVisibility(View.INVISIBLE);
        }
        file_path = "";

        edit.setOnClickListener(view -> {
            Boards b1 = new Boards(board_id_edit.getText().toString(), name_edit.getText().toString(),
                    Integer.parseInt(rows_edit.getText().toString()), Integer.parseInt(columns_edit.getText().toString()), content.getText().toString());

            h.updateBoard(b1);
            Intent intent = new Intent(DetailBoard.this, ListeBoard.class);
            startActivity(intent);
        });

        delete.setOnClickListener(view -> {
            h.deleteBoard(board_id_edit.getText().toString());
            Intent intent = new Intent(DetailBoard.this, ListeBoard.class);
            startActivity(intent);
        });

        add_file.setOnClickListener(view -> importFileFromPhone());

        game.setOnClickListener(view -> {
            Intent intent = new Intent(DetailBoard.this, GameDatabase.class);
            intent.putExtra("board_id", board_id_edit.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission OK", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission manquante", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(':') + 1);
                Toast.makeText(this, " " + path, Toast.LENGTH_SHORT).show();
                file_content.setText(path);
            }
        }
    }

    /**
     * Lire le contenu du fichier
     *
     * @param input le path du fichier
     * @return le contenu sous forme de string
     */
    @NonNull
    private String readText(String input) {
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(getFilePath(input)));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    /**
     * Pouvoir importer un fichier
     */
    private void importFileFromPhone() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    /**
     * Avoir le path complet du fichier issus du dossier téléchargement du téléphone
     *
     * @param file_path String le path du fichier
     * @return le path complet du fichier
     */
    @NonNull
    private String getFilePath(String file_path) {
        ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext());
        File dir = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(dir, file_path);
        return file.getPath();
    }
}



