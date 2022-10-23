package com.example.w5_p4;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {


    private Callbacks mCallbacks = sDummyCallbacks;

    public interface Callbacks {
        public BoggleGame getGame();
        public void updateScore();
        public boolean pressLetter(int row, int col);

    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        public BoggleGame getGame() {
            return null;
        }
        public void updateScore() {
        }

        public boolean pressLetter(int row, int col) {
            return false;
        }

        ;

    };

    private Button[][] buttons = new Button[4][4];
    private Button clear_btn;
    private Button submit_btn;

    public GameFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance() {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Callbacks)) {
            throw new IllegalStateException(
                    "Context must implement fragment's callbacks.");
        }
        mCallbacks = (Callbacks) context;
    }

    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        clear_btn = (Button) view.findViewById(R.id.clear_button);
        submit_btn = (Button) view.findViewById(R.id.submit_button);

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.getGame().clearButton();

                updateButtons();

            }
        });
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.getGame().submitButton();
                updateButtons();
                mCallbacks.updateScore();
            }
        });
        GridLayout gridLayout = view.findViewById(R.id.fragment_game_button_grid_layout);
        //add a 4x4 grid of buttons to the gridlayout programmatically
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        int w = (size.x / 4);
        int h = (size.y / 8);
        BoggleGame game = mCallbacks.getGame();
        char[][] board = game.getBoard();


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new Button(getActivity());
                buttons[i][j].setText("0");
                buttons[i][j].setTextSize(40);
                buttons[i][j].setPadding(0, 0, 0, 0);
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.white));
                buttons[i][j].setGravity(Gravity.CENTER);
                buttons[i][j].setText("" + board[i][j]);
                int finalJ = j;
                int finalI = i;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mCallbacks.pressLetter(finalI, finalJ)) {
                            return;
                        }
                        Button b = (Button) v;
                        b.setBackgroundColor(getResources().getColor(R.color.teal_200));


                    }
                });
                gridLayout.addView(buttons[i][j], w, h);

            }
        }


    }

    public void updateWord(String word) {
        //update the word on the screen
        View view = getView();
        TextView wordView = view.findViewById(R.id.fragment_game_current_word_text_view);
        wordView.setText(word);
    }

    public void updateButtons() {
        //update the buttons on the screen
        BoggleGame game = mCallbacks.getGame();
        char[][] board = game.getBoard();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText("" + board[i][j]);
                buttons[i][j].setBackgroundColor(getResources().getColor(R.color.white));
            }
        }
        updateWord("<Current Word>");
    }


}