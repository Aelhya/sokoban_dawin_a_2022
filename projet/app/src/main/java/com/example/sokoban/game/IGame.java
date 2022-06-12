package com.example.sokoban.game;

public interface IGame {
    int targetImg = GameRules.getTargetImg();
    int wallImg = GameRules.getWallImg();
    int whiteImg = GameRules.getWhiteImg();
    int blockImg = GameRules.getBlockImg();
    int blockValidImg = GameRules.getBlockValidImg();
    int characterUpImg = GameRules.getCharacterUpImg();
    int characterDownImg = GameRules.getCharacterDownImg();
    int characterLeftImg = GameRules.getCharacterLeftImg();
    int characterRightImg = GameRules.getCharacterRightImg();
}
