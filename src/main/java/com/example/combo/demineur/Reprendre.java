package com.example.combo.demineur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;


public class Reprendre extends AppCompatActivity {

    private ListView listView;
    private Animation apparition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reprendre);
        setTitle(R.string.reprendre);


        apparition = AnimationUtils.loadAnimation(this, R.anim.apparition);
        listView = findViewById(R.id.listView);

        listView.startAnimation(apparition);
    }

}
