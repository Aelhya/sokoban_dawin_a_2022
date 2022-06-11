package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sokoban.game.GameDefault;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonNiveauUn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonNiveauUn = findViewById(R.id.buttonNiveau1);
        buttonNiveauUn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == buttonNiveauUn.getId()) {
            Intent niveauDefaut = new Intent(this, GameDefault.class);
            startActivity(niveauDefaut);
        } else {
            throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}