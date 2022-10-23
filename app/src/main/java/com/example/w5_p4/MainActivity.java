package com.example.w5_p4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if( fragmentManager.findFragmentById( R.id.game_fragment ) == null ) {
            androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction( );
            GameFragment fragment = new GameFragment();
            transaction.add(R.id.game_fragment, fragment );
            transaction.commit( );
        }

        if(fragmentManager.findFragmentById(R.id.score_fragment) == null) {
            androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            ScoreFragment fragment = new ScoreFragment();
            transaction.add(R.id.score_fragment, fragment);
            transaction.commit();
        }

    }





}