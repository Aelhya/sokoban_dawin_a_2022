package com.example.sokoban.game;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.GridView;

import com.example.sokoban.R;
import com.example.sokoban.utils.GridAdapter;
import com.example.sokoban.utils.MatrixUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GameWithTxtFile extends Game {
    private GridView gridView;

    // placement du personnage
    private int posX;
    private int posY;
    // taille du plateau
    private int tailleColonne, tailleLigne;
    private MatrixUtils matrixUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_txt_file);
        setElementsOnGame();

        tailleColonne = this.getTailleColumns();
        tailleLigne = this.getTailleLigne();
        matrixUtils = new MatrixUtils(tailleLigne, tailleColonne);
        int[] images = this.getImages();
        posX = this.setPosXStart(images, tailleColonne);
        posY = this.setPosYStart(images, tailleColonne);

        // transformer la grid view en matrice (tableau 2D)
        matrix = matrixUtils.arrayToMatrix(images);

        gridView = findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(GameWithTxtFile.this, images);
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


    private int getTailleLigne() {
        // -1 car on ne compte pas la 1ere ligne correspondant aux nums de colonnes
        int countLines = -1;
        InputStream streamCountLines;
        BufferedReader readerCountLines;
        try {
            AssetManager am = getAssets();
            streamCountLines = am.open("gameboardLvl2.txt");
            readerCountLines = new BufferedReader(new InputStreamReader(streamCountLines));
            while (readerCountLines.readLine() != null) {
                countLines++;
            }
            streamCountLines.close();
            readerCountLines.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return countLines;
    }

    private int getTailleColumns() {
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
            // récupération du dernier nb de la ligne +1 (ça commence à 0)
            // qui est le num de colonne
            countColumns = Integer.parseInt(line.substring(line.length() - 1)) + 1;
            streamCountColumns.close();
            readerCountColumns.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return countColumns;

    }

    /**
     * Renvoie un tableau d'int 1D comportant toute la map récupérée depuis un
     * fichier txt avec la valeur de l'img associée pour chaque élément
     *
     * @return tableau 1D d'int
     */
    private int[] getImages() {
        InputStream inputStream;
        BufferedReader readerEachLines;
        String line = "";
        int[] array = null;
        try {
            AssetManager am = getAssets();
            inputStream = am.open("gameboardLvl2.txt");
            readerEachLines = new BufferedReader(new InputStreamReader(inputStream));
            for (int x = 0; x <= this.tailleColonne * this.tailleLigne; x++) {
                // readline lit une ligne à la fois
                line += readerEachLines.readLine();
            }
            // on remplace tous les espaces et les nombres par rien puis converison en tableau
            String[] string = line.replaceAll("\\s+", "")
                    .replaceAll("\\d", "")
                    .split("");
            // initialisation d'un tableau de la taille de la map
            array = new int[this.tailleColonne * this.tailleLigne];
//
            for (int i = 0; i < this.tailleColonne * this.tailleLigne; i++) {
                switch (string[i]) {
                    case "#":
                        string[i] = String.valueOf(wallImg);
                        break;
                    case ".":
                        string[i] = String.valueOf((whiteImg));
                        break;
                    case "C":
                        string[i] = String.valueOf((blockImg));
                        break;
                    case "x":
                        string[i] = String.valueOf((targetImg));
                        break;
                    case "P":
                        string[i] = String.valueOf((characterUpImg));
                        break;
                }
                array[i] = Integer.parseInt(string[i]);
            }
            // on ferme le fichier lu et le lecteur du fichier
            inputStream.close();
            readerEachLines.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return array;
    }

}
