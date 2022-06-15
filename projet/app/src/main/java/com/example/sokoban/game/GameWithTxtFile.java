package com.example.sokoban.game;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sokoban.R;
import com.example.sokoban.utils.MatrixUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameWithTxtFile extends Game {
    private GridView gridView;
    // taille du plateau
    private int tailleColonne, tailleLigne;
    private MatrixUtils matrixUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("coucou");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_txt_file);
        // les images des boutons
        imgGauche = findViewById(R.id.touche_gauche);
        imgDroite = findViewById(R.id.touche_droite);
        imgHaut = findViewById(R.id.touche_haut);
        imgBas = findViewById(R.id.touche_bas);
        imgCarreBlanc = findViewById(R.id.imageCarreBlanc);
        textFinished = findViewById(R.id.textFinished);
        textFinished.setVisibility(View.INVISIBLE);
        Button reset = findViewById(R.id.reset);
        goHome = findViewById(R.id.goHome);
        goHome.setVisibility(View.INVISIBLE);

        Button readValuesFromTxtFile = findViewById(R.id.buttonReadTxtFile);
        TextView test = findViewById(R.id.test);
        tailleColonne = this.getTailleColumns();
        tailleLigne = this.getTailleLigne();
        matrixUtils = new MatrixUtils(tailleColonne,tailleLigne);

        // transformer la grid view en matrice (tableau 2D)
//        matrix = matrixUtils.arrayToMatrix(images);

//        gridView = findViewById(R.id.gridView);
//        GridAdapter gridAdapter = new GridAdapter(GameDefault.this, images);
//        gridView.setAdapter(gridAdapter);
//
//        // avoir la position des cibles
//        setPositionTarget(matrix, matrixUtils);
//        // déplacement à Gauche
//        imgGauche.setOnClickListener(view -> interactions(matrix, posX, posY - 1, posX, posY - 2, gridAdapter, characterLeftImg));
//        // déplacement à Droite
//        imgDroite.setOnClickListener(view -> interactions(matrix, posX, posY + 1, posX, posY + 2, gridAdapter, characterRightImg));
//        // déplacement en Haut
//        imgHaut.setOnClickListener(view -> interactions(matrix, posX - 1, posY, posX - 2, posY, gridAdapter, characterUpImg));
//        // déplacement en Bas
//        imgBas.setOnClickListener(view -> interactions(matrix, posX + 1, posY, posX + 2, posY, gridAdapter, characterDownImg));

        // remise en l'état
//        reset.setOnClickListener(view -> restartActivity());
//        // retour à l'accueil
//        goHome.setOnClickListener(view -> goHome());
    }


    public int getTailleLigne(){
        // -1 car on ne compte pas la 1ere ligne correspondant aux nums de colonnes
        int countLines = -1;
        InputStream streamCountLines;
        BufferedReader readerCountLines;
        try {
            AssetManager am = getAssets();
            streamCountLines = am.open("gameboardLvl2.txt");
            readerCountLines = new BufferedReader(new InputStreamReader(streamCountLines));
            while(readerCountLines.readLine()!=null){
                countLines++;
            }
            streamCountLines.close();
            readerCountLines.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return countLines;
    }
    public int getTailleColumns(){
        InputStream streamCountColumns;
        BufferedReader readerCountColumns;
        String line;
        int countColumns = 0;
        try {
            AssetManager am = getAssets();
            streamCountColumns = am.open("gameboardLvl2.txt");
            readerCountColumns = new BufferedReader(new InputStreamReader(streamCountColumns));
            // lecture de la 1ere ligne
            line = readerCountColumns.readLine();
            // récupération du derniere nb de la ligne qui est le num de colonne
            countColumns = Integer.parseInt(line.substring(line.length()-1));
            streamCountColumns.close();
            readerCountColumns.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        return countColumns;

    }

}
