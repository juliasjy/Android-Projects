package edu.rose_hulman.suj1.lightsout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int NUM_BUTTON = 7;
    private LightsOutGame mGame;
    private TextView mTextView;
    private Button[] mButtons;
    private Button mNewGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGame = new LightsOutGame(NUM_BUTTON);
        if(savedInstanceState != null){
            mGame.setButtons(savedInstanceState.getIntArray("buttons"));
            mGame.setCount(savedInstanceState.getInt("count"));
        }
        setViews();
    }

    public void setViews(){
        mTextView = (TextView) findViewById(R.id.textView);

        mNewGameButton = (Button) findViewById(R.id.buttonNewGame);
        mNewGameButton.setOnClickListener(this);

        mButtons = new Button[NUM_BUTTON];

        for(int i = 0; i < NUM_BUTTON; i ++){
            int id = getResources().getIdentifier("button" + i, "id", this.getPackageName());
            mButtons[i] = (Button) findViewById(id);
            mButtons[i].setText(mGame.gameButtonString(i));
            mButtons[i].setOnClickListener(this);
        }
        updateState();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonNewGame){
            mGame.reset();
        }
        for(int i = 0; i < NUM_BUTTON; i ++){
            if(v.getId() == mButtons[i].getId()){
                mGame.flip(i);
                for(int j = -1; j < 2; j ++){
                    if(i + j >= 0 && i + j < 7)
                    mButtons[i + j].setText(mGame.gameButtonString(i + j));
                }
            }
        }
        updateState();
    }

    public void updateState(){
        mTextView.setText(mGame.gameStageString());
        for(int i = 0; i < NUM_BUTTON; i ++) {
            mButtons[i].setText(mGame.gameButtonString(i));
        }
        boolean win = mGame.checkWin();
        for(int i = 0; i < NUM_BUTTON; i ++) {
            mButtons[i].setEnabled(!win);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putIntArray("buttons", mGame.getButtons());
        outState.putInt("count", mGame.getCount());
        super.onSaveInstanceState(outState);
        // Save off data using outState.putXX(key, value)
        // Hint: you will use the appropriate methods to store int[] and ints,
        // maybe a String.
    }
}
