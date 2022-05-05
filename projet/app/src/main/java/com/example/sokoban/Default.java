package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;


public class Default extends AppCompatActivity {

    ImageView imgGauche ;
    ImageView imgDroite ;
    ImageView imgHaut ;
    ImageView imgBas ;
    GridView gridView;
    int pos = 27;
    Button reset;

    int largeur = 4;
    int longueur = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        imgGauche = findViewById(R.id.touche_gauche);
        imgDroite = findViewById(R.id.touche_droite);
        imgHaut = findViewById(R.id.touche_haut);
        imgBas = findViewById(R.id.touche_bas);
        reset = findViewById(R.id.reset);


        String[] name = {"wall","wall","wall","wall","wall","wall","wall","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","wall","floor","wall","floor","floor","wall",
                "wall","floor","floor","player","caisse","floor","floor","wall",
                "wall","floor","wall","wall","floor","floor","floor","wall",
                "wall","floor","wall","wall","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","wall","wall","wall","wall","wall","wall","wall"};
        String[] name2 = {"wall","wall","wall","wall","wall","wall","wall","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","floor","wall","floor","wall","floor","floor","wall",
                "wall","floor","floor","player","caisse","floor","floor","wall",
                "wall","floor","wall","wall","floor","floor","floor","wall",
                "wall","floor","wall","wall","floor","floor","floor","wall",
                "wall","floor","floor","floor","floor","floor","floor","wall",
                "wall","wall","wall","wall","wall","wall","wall","wall"};

        int[] images = {R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_ball_haut,R.drawable.ic_bloc_a_deplacer,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur};


        int[] images2 = {R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_ball_haut,R.drawable.ic_bloc_a_deplacer,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
                R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur};


        imgGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name[pos-1]  == "wall") {

                }
                else if (name[pos-1] == "caisse"){
                    if(name[pos-2] == "wall"){

                    }
                    else{
                        images[pos] = R.drawable.ic_carre_blanc;
                        name[pos]="floor";
                        pos = pos - 1;
                        images[pos] = R.drawable.ic_ball_haut;
                        name[pos]="player";
                        images[pos-1] = R.drawable.ic_bloc_a_deplacer;
                        name[pos-1]="caisse";
                        gridView.invalidateViews();
                    }
                }
                else{
                    images[pos] = R.drawable.ic_carre_blanc;
                    name[pos]="floor";
                    pos = pos - 1;
                    images[pos] = R.drawable.ic_ball_haut;
                    name[pos]="player";
                    gridView.invalidateViews();
                }
            }
        });
        imgDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name[pos+1]  == "wall") {

                }
                else if(name[pos+1] == "caisse"){
                    if(name[pos+2] == "wall"){

                    }
                    else{
                        images[pos] = R.drawable.ic_carre_blanc;
                        name[pos]="floor";
                        pos = pos + 1;
                        images[pos] = R.drawable.ic_ball_haut;
                        name[pos]="player";
                        images[pos+1] = R.drawable.ic_bloc_a_deplacer;
                        name[pos+1]="caisse";
                        gridView.invalidateViews();
                    }
                }
                else{
                    images[pos] = R.drawable.ic_carre_blanc;
                    name[pos]="floor";
                    pos = pos + 1;
                    images[pos] = R.drawable.ic_ball_haut;
                    name[pos]="player";
                    gridView.invalidateViews();
                }
            }
        });
        imgHaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name[pos-8]  == "wall") {

                }
                else if(name[pos-8] == "caisse"){
                    if(name[pos-16] == "wall"){

                    }
                    else{
                        images[pos] = R.drawable.ic_carre_blanc;
                        name[pos]="floor";
                        pos = pos -8;
                        images[pos] = R.drawable.ic_ball_haut;
                        name[pos]="player";
                        images[pos-8] = R.drawable.ic_bloc_a_deplacer;
                        name[pos-8]="caisse";
                        gridView.invalidateViews();
                    }
                }
                else{
                    images[pos] = R.drawable.ic_carre_blanc;
                    name[pos]="floor";
                    pos = pos - 8;
                    images[pos] = R.drawable.ic_ball_haut;
                    name[pos]="player";
                    gridView.invalidateViews();
                }
            }
        });
        imgBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name[pos+8]  == "wall") {

                }
                else if(name[pos+8] == "caisse"){
                    if(name[pos+16] == "wall"){

                    }
                    else{
                        images[pos] = R.drawable.ic_carre_blanc;
                        name[pos]="floor";
                        pos = pos + 8;
                        images[pos] = R.drawable.ic_ball_haut;
                        name[pos]="player";
                        images[pos+8] = R.drawable.ic_bloc_a_deplacer;
                        name[pos+8]="caisse";
                        gridView.invalidateViews();
                    }
                }
                else{
                    images[pos] = R.drawable.ic_carre_blanc;
                    name[pos]="floor";
                    pos = pos + 8;
                    images[pos] = R.drawable.ic_ball_haut;
                    name[pos]="player";
                    gridView.invalidateViews();
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < images.length;i++){
                    images[i] = images2[i];
                    name[i] = name2[i];
                    pos=27;
                }
                gridView.invalidateViews();
            }
        });
        // transformer la grid view en matrice -> tableau 2D
        int[][] matrix = arrayToMatrix(images);
        printMatrix(matrix);

        gridView=findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(Default.this,name,images);
        gridView.setAdapter(gridAdapter);
    }

    /**
     * Convertir un tableau 1D en matrice
     * @param images tableau 1D
     * @return un tableau 2D
     */
    public int[][] arrayToMatrix(int[] images){
        int[][] grid = new int[largeur][longueur];
        try {
            for(int i=0; i<images.length; i+=largeur){ // ligne : y
                for(int j=0; j<longueur; j++){ // col : x
                    int itemIndex = (i/largeur)*longueur+j;
                    grid[(i/largeur)][j] = images[itemIndex];
                }
            }
        }catch (Exception e){
            System.err.println(e);
        }
        return grid;
    }

    /**
     * Afficher la matrice
     * @param matrix
     */
    public void printMatrix(int[][] matrix){
        for(int i=0;i<largeur;i++){//ligne : y
            for(int j=0;j<longueur;j++){//col : x
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}