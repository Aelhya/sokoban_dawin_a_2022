package com.example.sokoban;

public class MatrixUtils {

    int largeur;
    int longueur;
    public MatrixUtils(int largeur, int longueur) {
        this.largeur = largeur;
        this.longueur = longueur;
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
