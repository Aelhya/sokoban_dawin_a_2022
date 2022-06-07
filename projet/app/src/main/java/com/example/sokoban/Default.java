package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Default extends AppCompatActivity {
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
    private int oldPosX = posX;
    private int oldPosY = posY;
    // taille du plateau
    private final int largeur = 8;
    private final int longueur = 8;
    private final int[] images = {R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_launcher_background, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_balle_haut, R.drawable.ic_bloc_a_deplacer, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_a_deplacer, R.drawable.ic_launcher_background, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_carre_blanc, R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur, R.drawable.ic_bloc_mur};
    public int[][] matrix;

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
        matrix = arrayToMatrix(images);

        gridView = findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(Default.this, images);
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
                int[][] newMatrix = arrayToMatrix(images);
                updateGridView(newMatrix, gridAdapter);
                gridView.invalidateViews();
                System.out.println("reset !");
                posX = 3;
                posY = 3;
                oldPosX = 3;
                oldPosY = 3;
                imgDroite.setVisibility(View.VISIBLE);
                imgHaut.setVisibility(View.VISIBLE);
                imgGauche.setVisibility(View.VISIBLE);
                imgBas.setVisibility(View.VISIBLE);
                imgCarreBlanc.setVisibility(View.VISIBLE);
                textFinished.setVisibility(View.INVISIBLE);
            }
        });
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
        int[] tabImages = matrixToArray(images);
        gridAdapter.setItems(tabImages);
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * Récupérer la position des cibles dans la matrice
     *
     * @param matrix int[][] la matrice qui sert de plateau
     */
    private void setPositionTarget(int[][] matrix) {
        int[] tab = matrixToArray(matrix);
        int cpt = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == targetImg) {
                targets.add(cpt, i);
                cpt++;
            }
        }
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
        // TODO : récupérer les coordonnées de target par une méthode
        // pour éviter de le faire en dur
        if (matrix[1][1] == blockValidImg && matrix[5][6] == blockValidImg) {
            isFinished = true;
        }
        return isFinished;
    }

    /**
     * Convertir un tableau 1D en matrice
     *
     * @param tabImages int[] tableau 1D
     * @return un tableau 2D (le plateau sous forme de matrice)
     */
    public int[][] arrayToMatrix(int[] tabImages) {
        int[][] grid = new int[largeur][longueur];
        try {
            for (int i = 0; i < tabImages.length; i += largeur) { // ligne : y
                for (int j = 0; j < longueur; j++) { // col : x
                    int itemIndex = (i / largeur) * longueur + j;
                    grid[(i / largeur)][j] = tabImages[itemIndex];
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return grid;
    }

    /**
     * Convertir une matrice 2D en tableau 1D
     *
     * @param matrix int [][]
     * @return un tableau 1D.
     */
    public int[] matrixToArray(int[][] matrix) {
        int[] tab = new int[largeur * longueur];
        for (int i = 0; i < largeur; i++) { //ligne : y
            for (int j = 0; j < longueur; j++) { //col : x
                tab[(i * (longueur) + j)] = matrix[i][j];
            }
        }
        return tab;
    }

    /**
     * Afficher la matrice dans la console de run
     *
     * @param matrix int[][]
     */
    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < largeur; i++) { //ligne : y
            for (int j = 0; j < longueur; j++) { //col : x
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }
}