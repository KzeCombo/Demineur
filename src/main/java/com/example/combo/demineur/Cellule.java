package com.example.combo.demineur;

/**
 * Created by Combo on 18/04/2018.
 */

import android.app.Activity;
//import android.widget.Button;
import java.io.Serializable;
import java.util.ArrayList;

public class Cellule extends android.support.v7.widget.AppCompatButton implements Serializable{

    private int caseX;
    private int caseY;
    private int nbBombesVoisines;
    private boolean isRevele;
    private boolean isBombe;
    private static final String charBombe ="B";
    private static final String charFlag = "D";
    private ArrayList<Cellule> voisins;
    /*Sur une application avec un vrai aspect graphique, on pourrait utiliser
    des images en ressources (par exemple en héritant plutot de ImageButton)
     */

    public Cellule(Activity a, int caseX, int caseY){
        super(a);
        this.caseX = caseX;
        this.caseY = caseY;
        this.isRevele = false;
        this.isBombe = false;
        this.nbBombesVoisines = 0;
        this.voisins = new ArrayList<Cellule>();
    }

    public int getCaseX(){
        return caseX;
    }

    public int getCaseY(){
        return caseY;
    }

    /**
     * Révèle la case
     * @return true si bombe, false sinon
     */
    public boolean reveler() {
        isRevele = true;
        if (isBombe) {
            this.setText(charBombe);
            return true;
        }
        else if(nbBombesVoisines!=0){
            this.setText(Integer.toString(nbBombesVoisines));
            return false;
        }
        else return false; //La case reste vide quand il n'y a aucune bombe autour
    }

    public boolean isBombe(){
        return isBombe;
    }

    public void devenirBombe(){
        isBombe = true;
    }

    public boolean isRevele(){
        return isRevele;
    }

    public void poseDrapeau(){
        if(this.getText() == charFlag)
            this.setText("");
        else
            this.setText(charFlag);
    }

    public void incrBombesVoisines(){
        nbBombesVoisines++;
    }

    public int getNbBombesVoisines(){
        return nbBombesVoisines;
    }

    public void addVoisins(ArrayList<Cellule> voisins){
        this.voisins = voisins;
    }

    public String getCharFlag(){
        return charFlag;
    }
}
