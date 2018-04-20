package com.example.combo.demineur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.combo.demineur.DifficulteActivity;
import com.example.combo.demineur.R;

public class AccueilActivity extends AppCompatActivity {

    //Déclaration des variables qui vont contenir les views utilisées
    private Animation apparition = null;
    private TextView instructionTitle = null;
    private TextView instructionContent = null;
    private Button buttonNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        //On initialise toutes les views à partir de R
        apparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
        instructionTitle = (TextView)findViewById(R.id.instruction_title_TextView);
        instructionContent = (TextView)findViewById(R.id.instruction_content_textView);
        buttonNext = (Button)findViewById(R.id.instruction_next_Button);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageSuivante();
            }
        });

        //Lancement des animations
        instructionTitle.startAnimation(apparition);
        instructionContent.startAnimation(apparition);
        buttonNext.startAnimation(apparition);
    }

    /**
     * Transition vers l'activité de choix de difficulté
     */
    public void pageSuivante(){
        Intent intent = new Intent(this, DifficulteActivity.class);
        startActivity(intent);
        finish();
    }
}