package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sokoban.game.gameDatabase.Administrator;
import com.example.sokoban.game.GameDefault;
import com.example.sokoban.game.GameWithTxtFile;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonNiveauUn, buttonNiveauDeux, buttonNiveauTrois;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNiveauUn = findViewById(R.id.buttonNiveau1);
        buttonNiveauUn.setOnClickListener(this);
        buttonNiveauDeux = findViewById(R.id.buttonNiveau2);
        buttonNiveauDeux.setOnClickListener(this);

        buttonNiveauTrois = findViewById(R.id.buttonNiveau3);
        buttonNiveauTrois.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonNiveauUn.getId()) {
            Intent niveauDefaut = new Intent(this, GameDefault.class);
            startActivity(niveauDefaut);
        } else if (view.getId() == buttonNiveauDeux.getId()) {
            Intent niveauAvecFichierText = new Intent(this, GameWithTxtFile.class);
            startActivity(niveauAvecFichierText);
        } else if (view.getId() == buttonNiveauTrois.getId()) {
            Intent niveauDatabase = new Intent(this, Administrator.class);
            startActivity(niveauDatabase);
        } else {
            throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}