package com.example.combo.demineur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    private Button button;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //button <=> Nouvelle Partie

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityNouvellePartie();
            }
        });

        //button2 <=> Reprendre

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityReprendre();
            }
        });

        //button3 <=> Scores

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityScores();
            }
        });

    }

    public void openActivityNouvellePartie(){
        Intent intent = new Intent(this, AccueilActivity.class);
        startActivity(intent);
    }

    public void openActivityReprendre(){
        Intent intent = new Intent (this, Reprendre.class);
        startActivity(intent);
    }

    public void openActivityScores(){
        Intent intent = new Intent(this, Scores.class);
        startActivity(intent);
    }

}
