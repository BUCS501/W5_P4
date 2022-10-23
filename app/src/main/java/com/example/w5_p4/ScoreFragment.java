package com.example.w5_p4;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ScoreFragment extends Fragment {
    private Callbacks mCallbacks = sDummyCallbacks;

    public interface Callbacks {
        public void newGame();
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        public void newGame() {
        }


    };
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public ScoreFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScoreFragment newInstance(String param1, String param2) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof GameFragment.Callbacks)) {
            throw new IllegalStateException(
                    "Context must implement fragment's callbacks.");
        }
        mCallbacks = (ScoreFragment.Callbacks) context;
    }

    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_state, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Button newGame_btn = (Button) getView().findViewById(R.id.new_game_button);
        newGame_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.newGame();
            }
        });
    }

    //method to update the score
    public void updateScore(int score) {
        View fragmentView = getView();
        TextView scoreTV = (TextView) fragmentView.findViewById(R.id.fragment_state_text_view);
        scoreTV.setText("Score: " + score);


    }
}