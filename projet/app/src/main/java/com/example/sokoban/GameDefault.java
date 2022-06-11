package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GameDefault extends AppCompatActivity {
    private GridView gridView;

    private final ArrayList<Integer> targets = new ArrayList<>();
    public int targetImg = R.drawable.ic_launcher_background;
    public int wallImg = R.drawable.ic_bloc_mur;
    public int whiteImg = R.drawable.ic_carre_blanc;
    public int blockImg = R.drawable.ic_bloc_a_deplacer;
    public int blockValidImg = R.drawable.ic_bloc_valide;
    public int characterUpImg = R.drawable.ic_balle_haut;
    public int characterDownImg = R.drawable.ic_balle_bas;
    public int characterLeftImg = R.drawable.ic_balle_gauche;
    public int characterRightImg = R.drawable.ic_balle_droite;
    public ImageView imgGauche, imgDroite, imgHaut, imgBas, imgCarreBlanc;
    public TextView textFinished;

    // valeurs par défaut
    private boolean isFinished = false;
    // placement du personnage
    private int posX = 3;
    private int posY = 3;
    // taille du plateau
    private final int largeur = 8;
    private final int longueur = 8;
    private final int[] images = {wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg,
            wallImg, targetImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, whiteImg, wallImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, whiteImg, characterUpImg, blockImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, wallImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, wallImg, whiteImg, blockImg, targetImg, wallImg,
            wallImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg};
    public int[][] matrix;
    private final MatrixUtils matrixUtils = new MatrixUtils(largeur, longueur);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        // les images des boutons
        imgGauche = findViewById(R.id.touche_gauche);
        imgDroite = findViewById(R.id.touche_droite);
        imgHaut = findViewById(R.id.touche_haut);
        imgBas = findViewById(R.id.touche_bas);
        imgCarreBlanc = findViewById(R.id.imageCarreBlanc);
        textFinished = findViewById(R.id.textFinished);
        textFinished.setVisibility(View.INVISIBLE);
        Button reset = findViewById(R.id.reset);

        // transformer la grid view en matrice (tableau 2D)
        matrix = matrixUtils.arrayToMatrix(images);

        gridView = findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(GameDefault.this, images);
        gridView.setAdapter(gridAdapter);

        // avoir la position des cibles
        setPositionTarget(matrix);
        // déplacement à Gauche
        imgGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deplacement(matrix, posX, posY - 1, posX, posY - 2, gridAdapter, characterLeftImg);
            }
        });
        // déplacement à Droite
        imgDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deplacement(matrix, posX, posY + 1, posX, posY + 2, gridAdapter, characterRightImg);
            }
        });
        // déplacement en Haut
        imgHaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deplacement(matrix, posX - 1, posY, posX - 2, posY, gridAdapter, characterUpImg);
            }
        });
        // déplacement en Bas
        imgBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deplacement(matrix, posX + 1, posY, posX + 2, posY, gridAdapter, characterDownImg);
            }
        });

        // remise en l'état
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartActivity();
            }
        });
    }

    /**
     * Relancer l'activité en cours
     */
    public void restartActivity(){
        Intent intent=new Intent();
        intent.setClass(this, this.getClass());
        this.startActivity(intent);
        this.finish();

    }

    /**
     * Réaliser les intéractions (déplacements, collisions)
     *
     * @param matrix      int[][] le plateau
     * @param newPosX     int  nouvelle coordonnée de X
     * @param newPosY     int nouvelle coordonnée de Y
     * @param afterPosX   int nouvelle coordonnée la case après X (si x changeant)
     * @param afterPosY   int nouvelle coordonnée la case après Y (si y changeant)
     * @param gridAdapter GridAdapter
     * @param sprite      int image du joueur
     */
    public void deplacement(int[][] matrix, int newPosX, int newPosY,
                            int afterPosX, int afterPosY, GridAdapter gridAdapter, int sprite) {
        int oldPosY = posY;
        int oldPosX = posX;
        if (matrix[newPosX][newPosY] == wallImg) {
            System.out.println("Obstacle Mur ! Impossible de bouger");
        } else if (matrix[newPosX][newPosY] == blockImg || matrix[newPosX][newPosY] == blockValidImg) {
            if (matrix[afterPosX][afterPosY] == wallImg
                    || matrix[afterPosX][afterPosY] == blockImg
                    || matrix[afterPosX][afterPosY] == blockValidImg
            ) {
                System.out.println(" Obstacle Mur + Caisse ! Impossible de bouger la caisse");
            } else {
                System.out.println("Caisse bougée");
                if (isOnTarget(afterPosX, afterPosY)) {
                    matrix[afterPosX][afterPosY] = blockValidImg;
                } else {
                    matrix[afterPosX][afterPosY] = blockImg;
                }
                matrix[posX][posY] = whiteImg;
                oldPosX = posX;
                oldPosY = posY;
                posX = newPosX;
                posY = newPosY;
                matrix[posX][posY] = sprite;
                // remettre la case de la cible
                if (isOnTarget(oldPosX, oldPosY)) {
                    matrix[oldPosX][oldPosY] = targetImg;
                }
                gridView.invalidateViews();
            }
        } else {
            // cas où pas d'obstacle
            matrix[posX][posY] = whiteImg;
            oldPosX = posX;
            oldPosY = posY;
            posX = newPosX;
            posY = newPosY;
            matrix[posX][posY] = sprite;
            if (isOnTarget(oldPosX, oldPosY)) {
                matrix[oldPosX][oldPosY] = targetImg;
            }
            gridView.invalidateViews();
        }
        updateGridView(matrix, gridAdapter);
        if (isAllTargetValid()) {
            // TODO : indiquer au joueur qu'il a réussi le niveau
            // enlever les boutons et mettre un texte + bouton au menu ?
            System.out.println("FINI !!");
            imgDroite.setVisibility(View.INVISIBLE);
            imgHaut.setVisibility(View.INVISIBLE);
            imgGauche.setVisibility(View.INVISIBLE);
            imgBas.setVisibility(View.INVISIBLE);
            imgCarreBlanc.setVisibility(View.INVISIBLE);
            textFinished.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Mettre à jour la matrice
     *
     * @param images      int[][] la matrice (plateau) à mettre à jour
     * @param gridAdapter GridAdapter
     */
    public void updateGridView(int[][] images, GridAdapter gridAdapter) {
        int[] tabImages = matrixUtils.matrixToArray(images);
        gridAdapter.setItems(tabImages);
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * Récupérer la position des cibles dans la matrice
     *
     * @param matrix int[][] la matrice qui sert de plateau
     */
    private void setPositionTarget(int[][] matrix) {
        int[] tab = matrixUtils.matrixToArray(matrix);
        int cpt = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == targetImg) {
                targets.add(cpt, i);
                System.out.println(i);
                cpt++;
            }
        }
        System.out.println(MatrixUtils.getTargetsPositionMatrix(targets, largeur, longueur));
    }

    /**
     * Checker si on est sur une cible ou non
     *
     * @param posX int coordonnée x
     * @param posY int coordonnée y
     * @return true si la position donnée correspond à une cible
     */
    private boolean isOnTarget(int posX, int posY) {
        int position = (posX) * (longueur) + posY;
        return targets.contains(position);
    }

    /**
     * Checker si le jeu est fini (= caisses sur toutes les cibles)
     *
     * @return vrai si toute les cibles sont atteintes
     */
    private boolean isAllTargetValid() {
        ArrayList<ArrayList<Integer>> positions = MatrixUtils.getTargetsPositionMatrix(targets, largeur, longueur);
        int nbTargets = targets.size();
        int cptTargetsValid = 0;
        for (int i=0; i< nbTargets; i++){
            int x = positions.get(0).get(i);
            int y = positions.get(1).get(i);
            if (matrix[x][y] == blockValidImg) {
                cptTargetsValid++;
                if(cptTargetsValid == nbTargets){
                    isFinished = true;
                }
            }
        }
        return isFinished;
    }
}