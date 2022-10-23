package com.example.w5_p4;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements GameFragment.Callbacks {


    private BoggleGame game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (game == null)
            game = new BoggleGame();


        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.game_fragment) == null) {
            androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            GameFragment fragment = new GameFragment();
            transaction.add(R.id.game_fragment, fragment);
            transaction.commit();
        }

        if (fragmentManager.findFragmentById(R.id.score_fragment) == null) {
            androidx.fragment.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            ScoreFragment fragment = new ScoreFragment();
            transaction.add(R.id.score_fragment, fragment);
            transaction.commit();
        }

    }

    public BoggleGame getGame() {
        return game;
    }

    public boolean pressLetter(int row, int col) {
        char res = game.letterButton(row, col);

        if (res == ' ') {
            Toast.makeText(this, "Invalid move", Toast.LENGTH_SHORT).show();
            return false;
        }
        String currentWord = game.getCurrWord();
        FragmentManager fragmentManager = getSupportFragmentManager();
        GameFragment gFragment = (GameFragment)
                fragmentManager.findFragmentById(R.id.game_fragment);
        gFragment.updateWord(currentWord);
        return true;
    }

    public void updateScore() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ScoreFragment sFragment = (ScoreFragment)
                fragmentManager.findFragmentById(R.id.score_fragment);
        sFragment.updateScore(game.getGameScore());
    }


}