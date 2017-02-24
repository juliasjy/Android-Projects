package edu.rose_hulman.suj1.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TicTacToeGame mGame = new TicTacToeGame(this);
    private TextView mGameStateTextView;
    private Button[][] mTicTacToeBottons;
    private Button newGameBotton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGame.resetGame();

        mGameStateTextView = (TextView) findViewById(R.id.textView);

        newGameBotton = (Button) findViewById(R.id.newGameButton);
        newGameBotton.setOnClickListener(this);

        mTicTacToeBottons = new Button[TicTacToeGame.NUM_ROWS][TicTacToeGame.NUM_COLUMNS];

        for(int rol = 0; rol < TicTacToeGame.NUM_ROWS; rol ++) {
            for (int col = 0; col < TicTacToeGame.NUM_COLUMNS; col++) {
                int id = getResources().getIdentifier("button" + rol + col, "id", this.getPackageName());
                mTicTacToeBottons[rol][col] = (Button) findViewById(id);
                mTicTacToeBottons[rol][col].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.newGameButton){
            mGame.resetGame();
        }
        for(int row = 0; row < TicTacToeGame.NUM_ROWS; row ++){
            for(int col = 0; col < TicTacToeGame.NUM_COLUMNS; col++){
                if(v.getId() == mTicTacToeBottons[row][col].getId()) {
                    //Log.d("TTT", "Press button at " + row + " " + col);
                    mGame.pressedButtonAtLocation(row, col);
                }
                mTicTacToeBottons[row][col].setText(mGame.stringForButtonAtLocation(row, col));
            }
        }
        mGameStateTextView.setText(mGame.stringForGameState());
    }
}
