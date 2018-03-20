package com.example.combo.demineur.util;

import java.util.Random;

/**
 * Created by Combo on 19/03/2018.
 */

public class Generateur {
    public static int[][] generate(int bombnumber, final int width, final  int height){
    //Génération d'un Nombre aléatoire
        Random r = new Random();
        int [][] grid = new int[width][height];
        for(int x=0; x< width;x++){
            grid[x]= new int[height];
        }

        //Placement de la Bombe
        while(bombnumber  > 0 ){
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            //-1 représente la bombe
            if(grid[x][y]!= -1){
                grid[x][y]=-1;
                bombnumber--;
            }
        }
        grid = calculateNeighbours(grid,width,height);
        return grid;
    }

    private static int[][] calculateNeighbours(int[][] grid, final int width, final  int height){
        for(int x=0;x < width ;x++){
            for(int y=0;y< height ;y++){
                grid[x][y]= getNeighbourNumber(grid,x,y,width,height);
            }
        }
        return grid;
    }

    private static int getNeighbourNumber(final int[][] grid,final int x, final int y, final int width, final  int height){
        if(grid[x][y] == -1){
            return -1;
        }

        int count =0;

        if( isMineAt(grid, x-1 , y+1 , width, height) )count++; // haut-gauche
        if( isMineAt(grid,    x   , y+1 , width, height) )count++; // haut
        if( isMineAt(grid, x+1 , y+1 , width, height) )count++; // haut-droite
        if( isMineAt(grid, x-1 ,    y   , width, height) )count++; // gauche
        if( isMineAt(grid, x+1 ,    y   , width, height) )count++; // droite
        if( isMineAt(grid, x-1 , y-1 , width, height) )count++; // bas-gauche
        if( isMineAt(grid,    x   , y-1 , width, height) )count++; // bas
        if( isMineAt(grid, x+1 , y-1 , width, height) )count++; // bas-droite

        return count;
    }
    private static boolean isMineAt(final int[][] grid,final int x, final int y, final int width, final  int height){
        if(x >= 0 && y >= 0 && x < width && y < height){
            if(grid[x][y]==-1)return true;
        }
        return false;
    }
}
















