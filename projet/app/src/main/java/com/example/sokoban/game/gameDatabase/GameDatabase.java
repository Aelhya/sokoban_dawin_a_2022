package com.example.sokoban.game.gameDatabase;


import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.NonNull;

import com.example.sokoban.R;
import com.example.sokoban.game.Game;
import com.example.sokoban.game.db.Boards;
import com.example.sokoban.game.db.HelperDatabase;
import com.example.sokoban.utils.GridAdapter;
import com.example.sokoban.utils.MatrixUtils;


public class GameDatabase extends Game {
    private GridView gridView;
    private int posX, posY, tailleColonne, tailleLigne;
    private MatrixUtils matrixUtils;
    String board_id;
    HelperDatabase h = new HelperDatabase(GameDatabase.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_database);
        setElementsOnGame();

        board_id = getIntent().getStringExtra("board_id");
        Boards b = h.getOneBoard(board_id);
        // taille du plateau
        tailleColonne = b.getColumns();
        tailleLigne = b.getRows();

        String content = b.getDescription();
        // set du tableau 1D
        int[] images = setArray(content);
        matrixUtils = new MatrixUtils(tailleLigne, tailleColonne);
        posX = this.setPosXStart(images, tailleColonne);
        posY = this.setPosYStart(images, tailleColonne);

        // transformer la grid view en matrice (tableau 2D)
        matrix = matrixUtils.arrayToMatrix(images);

        gridView = findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(GameDatabase.this, images);
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

    @NonNull
    private int[] setArray(@NonNull String content) {
        int[] tabImage = new int[tailleLigne * tailleColonne];
        for (int i = 0; i < content.length(); i++) {
            switch (content.charAt(i)) {
                case '#':
                    tabImage[i] = wallImg;
                    break;
                case '°':
                    tabImage[i] = whiteImg;
                    break;
                case 'C':
                    tabImage[i] = blockImg;
                    break;
                case 'x':
                    tabImage[i] = targetImg;
                    break;
                case 'P':
                    tabImage[i] = characterUpImg;
                    break;
            }
        }

        return tabImage;
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
        if (isAllTargetValid(tailleColonne, tailleLigne)) {
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
                System.out.println("Obstacle Mur + Caisse ! Impossible de bouger la caisse");
            } else {
                System.out.println("Caisse bougée");
                if (isOnTarget(afterPosX, afterPosY, tailleColonne)) {
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
                if (isOnTarget(oldPosX, oldPosY, tailleColonne)) {
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

    /**
     * Retourner au menu
     */
    @Override
    public void goHome() {
        Intent home = new Intent(this, ListeBoard.class);
        startActivity(home);
    }
}