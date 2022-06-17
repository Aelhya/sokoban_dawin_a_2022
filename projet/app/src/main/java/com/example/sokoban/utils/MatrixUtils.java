package com.example.sokoban.utils;

import java.util.ArrayList;

public class MatrixUtils {

    private final int largeur;
    private final int longueur;

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
        try {
            for (int i = 0; i < largeur; i++) { //ligne : y
                for (int j = 0; j < longueur; j++) { //col : x
                    tab[(i * (longueur) + j)] = matrix[i][j];
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return tab;
    }

    /**
     * Afficher la matrice dans la console de run
     *
     * @param matrix int[][]
     */
    public void printMatrix(int[][] matrix) {
        try {
            for (int i = 0; i < largeur; i++) { //ligne : y
                for (int j = 0; j < longueur; j++) { //col : x
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println(" ");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Avoir la position de toutes les cibles
     * @param targets ArrayList<Integer> tableau avec la position des cibles dans un tab 1D
     * @param largeur int largeur
     * @param longueur int longueur
     * @return Une liste de 2 tableaux : le premier correspond aux x et le second aux y de chaque cible
     */
    public static ArrayList<ArrayList<Integer>> getTargetsPositionMatrix(ArrayList<Integer> targets, int largeur, int longueur) {
        ArrayList<Integer> positionX = new ArrayList<>();
        ArrayList<Integer> positionY = new ArrayList<>();
        for(Integer element : targets){
            int y = element % longueur;
            int x = (element - y) / longueur;
            int[][] pos = new int[2][1];
            pos[0][0] = x;
            pos[1][0] = y;
            positionX.add(x);
            positionY.add(y);
        }
        ArrayList<ArrayList<Integer>> position = new ArrayList<>();
        position.add(positionX);
        position.add(positionY);
        return position;
    }
}
