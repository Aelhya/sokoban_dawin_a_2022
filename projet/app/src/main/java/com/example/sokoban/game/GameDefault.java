package com.example.sokoban.game;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.sokoban.R;
import com.example.sokoban.utils.GridAdapter;
import com.example.sokoban.utils.MatrixUtils;

public class GameDefault extends Game {
    private GridView gridView;

    // valeurs par défaut
    // placement du personnage
    private int posX = 3;
    private int posY = 3;
    // taille du plateau
    private final int tailleColonne = 8;
    private final int tailleLigne = 8;
    private final int[] images = {wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg,
            wallImg, targetImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, whiteImg, wallImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, whiteImg, characterUpImg, blockImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, wallImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, whiteImg, wallImg, wallImg, whiteImg, blockImg, targetImg, wallImg,
            wallImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, whiteImg, wallImg,
            wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg, wallImg};
    private final MatrixUtils matrixUtils = new MatrixUtils(tailleColonne, tailleLigne);

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
        goHome = findViewById(R.id.goHome);
        goHome.setVisibility(View.INVISIBLE);

        // transformer la grid view en matrice (tableau 2D)
        matrix = matrixUtils.arrayToMatrix(images);

        gridView = findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(GameDefault.this, images);
        gridView.setAdapter(gridAdapter);

        // avoir la position des cibles
        setPositionTarget(matrix, matrixUtils);
        // déplacement à Gauche
        imgGauche.setOnClickListener(view -> interactions(matrix, posX, posY - 1, posX, posY - 2, gridAdapter, characterLeftImg));
        // déplacement à Droite
        imgDroite.setOnClickListener(view -> interactions(matrix, posX, posY + 1, posX, posY + 2, gridAdapter, characterRightImg));
        // déplacement en Haut
        imgHaut.setOnClickListener(view -> interactions(matrix, posX - 1, posY, posX - 2, posY, gridAdapter, characterUpImg));
        // déplacement en Bas
        imgBas.setOnClickListener(view -> interactions(matrix, posX + 1, posY, posX + 2, posY, gridAdapter, characterDownImg));

        // remise en l'état
        reset.setOnClickListener(view -> restartActivity());
        // retour à l'accueil
        goHome.setOnClickListener(view -> goHome());
    }

    /**
     * Réaliser les intéractions (déplacement) + déterminer si le jeu est fini
     *
     * @param matrix      int[][] le plateau
     * @param newPosX     int  nouvelle coordonnée de X
     * @param newPosY     int nouvelle coordonnée de Y
     * @param afterPosX   int nouvelle coordonnée la case après X (si x changeant)
     * @param afterPosY   int nouvelle coordonnée la case après Y (si y changeant)
     * @param gridAdapter GridAdapter
     * @param sprite      int image du joueur
     */
    private void interactions(int[][] matrix, int newPosX, int newPosY,
                            int afterPosX, int afterPosY, GridAdapter gridAdapter, int sprite) {
        deplacements(matrix, newPosX, newPosY,
                afterPosX, afterPosY, gridAdapter, sprite);
        gridAdapter.updateGridView(matrixUtils.matrixToArray(matrix));
        if (isAllTargetValid(tailleLigne, tailleColonne)) {
            cleanFinishedGame(imgDroite, imgHaut, imgGauche, imgBas, imgCarreBlanc, textFinished, goHome);
        }
    }

    /**
     * Réaliser les déplacements
     *
     * @param matrix      int[][] le plateau
     * @param newPosX     int  nouvelle coordonnée de X
     * @param newPosY     int nouvelle coordonnée de Y
     * @param afterPosX   int nouvelle coordonnée la case après X (si x changeant)
     * @param afterPosY   int nouvelle coordonnée la case après Y (si y changeant)
     * @param gridAdapter GridAdapter
     * @param sprite      int image du joueur
     */
    private void deplacements(int[][] matrix, int newPosX, int newPosY,
                              int afterPosX, int afterPosY, GridAdapter gridAdapter, int sprite) {
        int oldPosY;
        int oldPosX;
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
                if (isOnTarget(afterPosX, afterPosY, tailleLigne)) {
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
                if (isOnTarget(oldPosX, oldPosY, tailleLigne)) {
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
            if (isOnTarget(oldPosX, oldPosY, tailleLigne)) {
                matrix[oldPosX][oldPosY] = targetImg;
            }
            gridView.invalidateViews();
        }
    }
}