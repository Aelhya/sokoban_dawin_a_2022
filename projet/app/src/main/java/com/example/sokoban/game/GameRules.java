package com.example.sokoban.game;

import com.example.sokoban.R;

public class GameRules {

    public static int getTargetImg() {
        return R.drawable.ic_launcher_background;
    }

    public static int getWallImg() {
        return R.drawable.ic_bloc_mur;
    }

    public static int getWhiteImg() {
        return R.drawable.ic_carre_blanc;
    }

    public static int getBlockImg() {
        return R.drawable.ic_bloc_a_deplacer;
    }

    public static int getBlockValidImg() {
        return R.drawable.ic_bloc_valide;
    }

    public static int getCharacterUpImg() {
        return R.drawable.ic_balle_haut;
    }

    public static int getCharacterDownImg() {
        return R.drawable.ic_balle_bas;
    }

    public static int getCharacterLeftImg() {
        return R.drawable.ic_balle_gauche;
    }

    public static int getCharacterRightImg() {
        return R.drawable.ic_balle_droite;
    }
}