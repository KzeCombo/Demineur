package com.example.combo.demineur;

/**
 * Created by Combo on 18/04/2018.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private TableLayout table = null;
    private Switch flagSwitch = null;

    //private Chronometer chronometer;
    //private Button OnChronometer;
    //private boolean running;
    //private long pauseOffset;


    private Cellule[][] plateau;
    private int taille;
    private int bombe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        flagSwitch = (Switch)findViewById(R.id.flag_Switch);
        table = (TableLayout) findViewById(R.id.table_Game);
        /**
        //OnChronometer = findViewById(R.id.gameRestartButton);
        //OnChronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetChronometer();
            }
        });
        //chronometer = findViewById(R.id.chronometer);
        //chronometer.setFormat("Temps: ");
         */

        if(savedInstanceState != null){
            //On récupère l'état du switch et du plateau
            flagSwitch.setChecked(savedInstanceState.getBoolean("flag"));
            plateau = (Cellule[][]) savedInstanceState.getSerializable("plateau");
            taille = plateau.length;

            //On recrée le plateau à partir des infos récoltées
            fillTableau(plateau);

            //Au cas où l'arrêt est effectué sur l'écran de victoire ou défaite
            for(int x = 0; x < taille; x++){
                for(int y = 0; y < taille; y++){
                    if(plateau[x][y].isBombe() && plateau[x][y].isRevele())
                        defaite();
                }
            }
            testVictoire();
        }
        else{
            Bundle extras = getIntent().getExtras();
            /*On aurait aussi pu passer la difficulté et aller chercher taille et bombe
            dans les ressources*/
            taille = extras.getInt("EXTRA_TAILLE");
            bombe = extras.getInt("EXTRA_BOMBE");

            fillTableau();
            addBombes();
        }




    }

    /**
     * Remplit le plateau de jeu à partir de rien
     */
    private void fillTableau(){
        plateau = new Cellule[taille][taille];
        // On remplit le plateau
        for (int y = 0; y < taille; y++) {
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < taille; x++) {
                Cellule c = new Cellule(this,x,y);
                c.setOnClickListener(onClickCellule);
                r.addView(c);
                plateau[x][y] = c;
            }
        }
    }

    /**
     * Remplit le tableau de jeu à partir d'une sauvegarde antérieure
     * @param plateau le plateau à réutiliser
     */
    private void fillTableau(Cellule[][] plateau){
        for(int y = 0; y < taille; y++){
            TableRow r = new TableRow(this);
            table.addView(r);
            for (int x = 0; x < taille; x++) {
                Cellule c = plateau[x][y];

                ((ViewGroup)c.getParent()).removeView(c);
                if(!c.isRevele())
                    c.setOnClickListener(onClickCellule);
                r.addView(c);
            }
        }
    }

    /**
     * Clic sur une cellule
     */
    private View.OnClickListener onClickCellule = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Cellule c = (Cellule)v;
                /*On aurait peut être pu faire autrement, plus "propre", mais je n'ai pas vraiment
                trouvé de méthode alternative*/
                //Try catch au cas où v ne serait pas une cellule

                if (flagSwitch.isChecked()) {
                    /*pas besoin de vérifier que la case n'est pas déjà révélée
                     Parce que le listener ne serait alors plus attribué à la cellule
                      */
                    c.poseDrapeau();
                    return;
                }

                if(c.getText() == c.getCharFlag())
                    return;
                //Impossible de révéler une case avec un drapeau

                c.setOnClickListener(null);
                ArrayList<Cellule> voisins = getVoisins(c.getCaseX(), c.getCaseY());
                c.addVoisins(voisins);

                if(c.reveler()){
                    c.setBackgroundColor(getResources().getColor(R.color.red));
                    defaite();
                }
                else {
                    c.setBackgroundColor(getResources().getColor(R.color.silver));
                    if(c.getNbBombesVoisines() == 0){
                        for(Cellule cVoisine : voisins){
                            if(!cVoisine.isBombe() && !cVoisine.isRevele())
                                this.onClick(cVoisine);
                        }
                    }
                }
                testVictoire();

            }catch(Exception e){
                e.printStackTrace();
            }

        }

    };

    /**
     * Récupère la liste des voisins d'une cellule
     * @param x coordonnée x de la cellule
     * @param y coordonnée y de la cellule
     * @return l'arraylist des voisins
     */
    private ArrayList<Cellule> getVoisins(int x, int y){
        ArrayList<Cellule> voisins = new ArrayList<Cellule>();

        for(int i = -1; i<2; i++){
            for(int j = -1; j<2; j++){
                if(x+i>=0 && x+i<taille && y+j>=0 && y+j<taille && (i!=0 || j!=0)){
                    voisins.add(plateau[x+i][y+j]);
                    if(plateau[x+i][y+j].isBombe())
                        plateau[x][y].incrBombesVoisines();
                }
            }
        }

        return voisins;
    }

    /**
     * Place les bombes sur le plateau
     */
    private void addBombes(){
        int cptBombe = bombe;
        while (cptBombe>0){
            Random gen = new Random();
            int x = gen.nextInt(taille);
            int y = gen.nextInt(taille);

            if(!plateau[x][y].isBombe()){
                plateau[x][y].devenirBombe();
                cptBombe--;
            }
        }
    }

    /**
     * Gestion du Chronomètre

    public void startChronometer(){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer(View view){
        if(running){
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        startChronometer();
    }
     */

    /**
     * Termine la partie en cas de défaite
     */
    private void defaite(){
        final AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle(getString(R.string.defeat_title));
        alertDialog.setMessage(getString(R.string.defeat_content));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.restart),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        recommencer();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.leave),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    /**
     * Termine la partie en cas de victoire
     */
    public void victoire(){
        final AlertDialog alertDialog = new AlertDialog.Builder(GameActivity.this).create();
        alertDialog.setTitle(getString(R.string.victory_title));
        alertDialog.setMessage(getString(R.string.victory_content));
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.restart),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        recommencer();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.leave),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    /**
     * Recommence le jeu (méthode onClick)
     */
    public void recommencer(View v){
        recommencer();
    }

    /**
     * Recommence le jeu
     */
    public void recommencer(){
        Intent intent = new Intent(this, DifficulteActivity.class);
        startActivity(intent);
        finish(); //On aura plus besoin de cette activité
    }

    /**
     * Vérifie si la partie est terminée
     */
    private void testVictoire(){
        for(int i=0; i<taille; i++) {
            for (int j = 0; j < taille; j++) {
                if ((!plateau[i][j].isRevele()) && (!plateau[i][j].isBombe())) {
                    return;
                }
            }
        }
        victoire();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putSerializable("plateau", plateau);
        outState.putBoolean("flag", flagSwitch.isChecked());

        super.onSaveInstanceState(outState);
    }

}
