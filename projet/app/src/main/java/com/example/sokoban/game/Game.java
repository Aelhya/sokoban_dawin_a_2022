package com.example.sokoban.game;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sokoban.MainActivity;
import com.example.sokoban.utils.MatrixUtils;

import java.util.ArrayList;

public abstract class Game extends AppCompatActivity implements IGame {
    public int[][] matrix;
    public ImageView imgGauche, imgDroite, imgHaut, imgBas, imgCarreBlanc;
    public TextView textFinished;
    public Button goHome;
    private final ArrayList<Integer> targets = new ArrayList<>();
    private boolean isFinished = false;

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
     * Retourner au menu
     */
    public void goHome(){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    /**
     * Nettoyer l'écran (enlever les boutons de déplacement)
     * @param imgDroite ImageView image bouton droite
     * @param imgHaut ImageView image bouton haut
     * @param imgGauche ImageView image bouton gauche
     * @param imgBas ImageView image bouton bas
     * @param imgCarreBlanc ImageView image carré blanc
     * @param textFinished TextView texte niveau fini
     * @param goHome Button bouton retour à l'accueil
     */
    public static void cleanFinishedGame(ImageView imgDroite, ImageView imgHaut, ImageView imgGauche,
                                         ImageView imgBas, ImageView imgCarreBlanc, TextView textFinished,
                                         Button goHome){
        System.out.println("FINI !!");
        imgDroite.setVisibility(View.INVISIBLE);
        imgHaut.setVisibility(View.INVISIBLE);
        imgGauche.setVisibility(View.INVISIBLE);
        imgBas.setVisibility(View.INVISIBLE);
        imgCarreBlanc.setVisibility(View.INVISIBLE);
        textFinished.setVisibility(View.VISIBLE);
        goHome.setVisibility(View.VISIBLE);
    }

    /**
     * Checker si on est sur une cible ou non
     *
     * @param posX int coordonnée x
     * @param posY int coordonnée y
     * @return true si la position donnée correspond à une cible
     */
    protected boolean isOnTarget(int posX, int posY, int tailleLigne) {
        int position = (posX) * (tailleLigne) + posY;
        return targets.contains(position);
    }

    /**
     * Récupérer la position des cibles dans la matrice
     *
     * @param matrix int[][] la matrice qui sert de plateau
     */
    void setPositionTarget(int[][] matrix, MatrixUtils matrixUtils) {
        int[] tab = matrixUtils.matrixToArray(matrix);
        int cpt = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == targetImg) {
                targets.add(cpt, i);
                System.out.println(i);
                cpt++;
            }
        }
    }

    /**
     * Checker si le jeu est fini (= caisses sur toutes les cibles)
     *
     * @return vrai si toute les cibles sont atteintes
     */
    protected boolean isAllTargetValid(int tailleLigne, int tailleColonne) {
        ArrayList<ArrayList<Integer>> positions = MatrixUtils.getTargetsPositionMatrix(targets, tailleColonne, tailleLigne);
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