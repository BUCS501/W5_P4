package com.example.w5_p4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements GameFragment.Callbacks {


    private BoggleGame game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( game == null )
            game = new BoggleGame( );

        AssetManager am = getAssets( );
        try {
            game.setPossibleWords( readDictionaryFile( am ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public BoggleGame getGame( ) {
        return game;
    }

    public boolean pressLetter( int row, int col ) {
        char res = game.letterButton( row, col );

        if (res == ' ') {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show();
            return false;
        }
        String currentWord = game.getCurrWord();
        FragmentManager fragmentManager = getSupportFragmentManager();
        GameFragment gFragment = ( GameFragment )
                fragmentManager.findFragmentById( R.id.game_fragment );
        gFragment.updateWord(currentWord);


        return true;
    }

    public HashSet<String> readDictionaryFile(AssetManager am) throws IOException {
        // Initializes the HashSet to hold all unique words
        HashSet<String> wordSet = new HashSet<String>();


        InputStream is = null;
        try {
            is = am.open( "words.txt" );
        } catch ( Exception e ) {
            Toast.makeText( this, "Error opening words.txt", Toast.LENGTH_LONG ).show( );
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        while (bufferedReader.ready()) {
            wordSet.add(bufferedReader.readLine());
        }
        return wordSet;
    }






}