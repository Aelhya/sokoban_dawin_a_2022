package com.example.sokoban;

import androidx.appcompat.app.AppCompatActivity;

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
    // placement de base du personnage
    int posX = 3;
    int posY = 3;
    int[][] matrix;
    Button reset;

    // valeur par défaut
    int largeur = 8;
    int longueur = 8;
    // tableau initial
    int[] images = {R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_ball_haut,R.drawable.ic_bloc_a_deplacer,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_a_deplacer,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_carre_blanc,R.drawable.ic_bloc_mur,
            R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur,R.drawable.ic_bloc_mur};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
        imgGauche = findViewById(R.id.touche_gauche);
        imgDroite = findViewById(R.id.touche_droite);
        imgHaut = findViewById(R.id.touche_haut);
        imgBas = findViewById(R.id.touche_bas);
        reset = findViewById(R.id.reset);

        // transformer la grid view en matrice -> tableau 2D
        matrix = arrayToMatrix(images);

        gridView=findViewById(R.id.gridView);
        GridAdapter gridAdapter = new GridAdapter(Default.this, images);
        gridView.setAdapter(gridAdapter);

        // déplacement à Gauche
        imgGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_deplacement(matrix, posX, posY-1, posX, posY-2, gridAdapter);
            }
        });
        // déplacement à Droite
        imgDroite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_deplacement(matrix, posX, posY+1,  posX, posY+2, gridAdapter);
            }
        });
        // déplacement en Haut
        imgHaut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_deplacement(matrix, posX-1, posY,  posX - 2 , posY, gridAdapter);
            }
        });
        // déplacement en Bas
        imgBas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_deplacement(matrix, posX + 1, posY,  posX + 2, posY, gridAdapter);
            }
        });
        // remise en l'état
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[][] resetMatrix = arrayToMatrix(images);
                updateGridView(resetMatrix, gridAdapter);
                printMatrix(matrix);
                gridView.invalidateViews();
                System.out.println("reset ");
                printMatrix(resetMatrix);
            }
        });
    }

    /**
     * Convertir un tableau 1D en matrice
     * @param tabImages int[] tableau 1D
     * @return un tableau 2D
     */
    public int[][] arrayToMatrix(int[] tabImages){
        int[][] grid = new int[largeur][longueur];
        try {
            for(int i=0; i<tabImages.length; i+=largeur){ // ligne : y
                for(int j=0; j<longueur; j++){ // col : x
                    int itemIndex = (i/largeur)*longueur+j;
                    grid[(i/largeur)][j] = tabImages[itemIndex];
                }
            }
        }catch (Exception e){
            System.err.println(e);
        }
        return grid;
    }

    /**
     * Afficher la matrice
     * @param matrix int[][]
     */
    public void printMatrix(int[][] matrix){
        for(int i=0;i<largeur;i++){//ligne : y
            for(int j=0;j<longueur;j++){//col : x
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println(" ");
        }
    }

    public void check_deplacement(int[][] matrix, int newPosX, int newPosY, int afterPosX, int afterPosY, GridAdapter gridAdapter){
        System.out.println( "afterPosX " + newPosX + "afterPosY " + newPosY);
        if (matrix[newPosX][newPosY] == R.drawable.ic_bloc_mur) {
            System.out.println("Obstacle Mur ! Impossible de bouger");
        }
        else if (matrix[newPosX][newPosY] == R.drawable.ic_bloc_a_deplacer) {
            if(matrix[afterPosX][afterPosY] == R.drawable.ic_bloc_mur|| matrix[afterPosX][afterPosY] == R.drawable.ic_bloc_a_deplacer){
                System.out.println(" Obstacle Mur + Caisse ! Impossible de bouger la caisse");
            }
            else{
                System.out.println("Caisse bougée");
                matrix[posX][posY] = R.drawable.ic_carre_blanc;
                posX = newPosX;
                posY = newPosY;
                matrix[posX][posY] = R.drawable.ic_ball_haut;
                matrix[afterPosX][afterPosY] = R.drawable.ic_bloc_a_deplacer;
                gridView.invalidateViews();
            }
        }
        else{
            // cas où pas d'obstacle
            matrix[posX][posY] = R.drawable.ic_carre_blanc;
            posX = newPosX;
            posY = newPosY;
            matrix[posX][posY] = R.drawable.ic_ball_haut;
            gridView.invalidateViews();
        }
        updateGridView(matrix, gridAdapter);
    }

    /**
     * Mettre à jour l'affichage avec matrice 2D
     */
    public void updateGridView(int[][] matrix, GridAdapter gridAdapter){
        int[] tabImages = matrixToArray(matrix);
        gridAdapter.setItems(tabImages);
        gridAdapter.notifyDataSetChanged();
    }

    /**
     * Convertir une matrice 2D en tableau 1D
     * @param matrix int [][]
     * @return un tableau 1D.
     */
    public int[] matrixToArray(int[][] matrix){
        int[] tab = new int[largeur*longueur];
        for(int i=0;i<largeur;i++){//ligne : y
            for(int j=0;j<longueur;j++){//col : x
                tab[(i*(longueur)+j)] = matrix[i][j];
            }
        }
        return tab;
    }
}