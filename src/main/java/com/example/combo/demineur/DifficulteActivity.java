package com.example.combo.demineur;

/**
 * Created by Combo on 18/04/2018.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DifficulteActivity extends AppCompatActivity {

    //Déclaration des variables qui vont contenir les views utilisées
    private RadioGroup choix = null;
    private TextView indic = null;

    int bombe = -1; //Nombre de bombes suivant la difficulté
    int taille = -1; //Taille du plateau suivant la difficulté
    int difficulte = -1;

    int idFacile = R.id.difficulte_facile_RadioButton;
    int idMoyen = R.id.difficulte_moyen_RadioButton;
    int idDifficile = R.id.difficulte_difficile_RadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulte);

        //On initialise toutes les views à partir de R
        choix = (RadioGroup)findViewById(R.id.difficulte_choix_RadioGroup);
        indic = (TextView)findViewById(R.id.difficulte_indicateur_TextView);

        //On attache le listener au groupe
        choix.setOnCheckedChangeListener(onDifficulte);

        if(savedInstanceState!=null)
            choix.check(savedInstanceState.getInt("difficulte_checked"));
    }

    /**
     * Changement de case cochée
     */
    private RadioGroup.OnCheckedChangeListener onDifficulte = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup rg, int checked){
            if(checked == idFacile) {
                difficulte = 1;
                bombe = getResources().getInteger(R.integer.easy_bomb);
                taille = getResources().getInteger(R.integer.easy_size);
            }
            else if(checked == idMoyen) {
                difficulte = 2;
                bombe = getResources().getInteger(R.integer.medium_bomb);
                taille = getResources().getInteger(R.integer.medium_size);
            }
            else if(checked == idDifficile) {
                difficulte = 3;
                bombe = getResources().getInteger(R.integer.hard_bomb);
                taille = getResources().getInteger(R.integer.hard_size);
            }
            else return;
            String chaine = getResources().getString(R.string.indic, taille, bombe);
            indic.setText(chaine);
        }
    };

    /**
     * Passage à la page de jeu
     */
    public void pageSuivante(View v){
        //D'abord vérifier qu'une case est cochée sinon message d'erreur
        if(choix.getCheckedRadioButtonId() == -1)
            Toast.makeText(DifficulteActivity.this,getString(R.string.errorDifficulte),
                    Toast.LENGTH_SHORT).show();
        else {
            //Puis transférer info avec intent
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("EXTRA_TAILLE", taille);
            intent.putExtra("EXTRA_BOMBE", bombe);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("difficulte_checked", choix.getCheckedRadioButtonId());
    }
}
