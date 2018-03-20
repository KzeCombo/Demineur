package com.example.combo.demineur;

import android.content.Context;

import com.example.combo.demineur.util.Generateur;
import com.example.combo.demineur.util.PrintGrid;

/**
 * Created by Combo on 19/03/2018.
 */

public class GameEngine {
    private static GameEngine instence;

    private static final int BOMB_NUMBER = 10;
    private static final int WIDTH = 10;
    private static final int  HEIGHT = 10;

    private Context context;

    public static GameEngine getInstence(){
        if( instence == null){
            instence = new GameEngine();
        }
        return instence;
    }

    private GameEngine(){} ;

    private void createGrid( Context context){
        this.context = context;
        //création de la grille et la stocker
        int[][] GeneratedGrid = Generateur.generate(BOMB_NUMBER, WIDTH, HEIGHT);
        PrintGrid.print(GeneratedGrid,WIDTH,HEIGHT);
    }
}
