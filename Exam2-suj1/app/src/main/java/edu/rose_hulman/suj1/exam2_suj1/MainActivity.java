package edu.rose_hulman.suj1.exam2_suj1;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BookAdapter mAdapter;
    private int num;
    private boolean fav = true;

    @Override
    protected void onPause() {
        super.onPause();
        this.storeLastNumOfBooksInSharePreferences(num);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        num = getLastNumOfBooksFromSharePreferences();

        TextView scoreView = (TextView) findViewById(R.id.score_view);
        scoreView.setText("Score: " + "0/" + this.num * 2);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);

        mAdapter = new BookAdapter(this, recyclerView, num, scoreView);
        recyclerView.setAdapter(mAdapter);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setNumOfBooks();
            return true;
        } else if (id == R.id.action_reset) {
            fav = true;
            mAdapter.update(num);
        } else if (id == R.id.action_like) {
            if(fav) {
                mAdapter.addFavourite();
                fav = false;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void setNumOfBooks(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.set_item_title);
        final String[] numberOfBooks = new String[]{"5", "10", "25", "100"};
        int temp = 0;
        if(num == 5) temp = 0;
        else if(num == 10) temp = 1;
        else if(num == 25) temp = 2;
        else if(num == 100) temp = 3;
        builder.setSingleChoiceItems(numberOfBooks, temp, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                num = Integer.parseInt(numberOfBooks[which]);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fav = true;
                mAdapter.update(num);
            }
        });
        builder.create().show();
    }

    private int getLastNumOfBooksFromSharePreferences(){
        SharedPreferences prefs = getSharedPreferences("NUM", MODE_PRIVATE);
        int num = prefs.getInt("number", 10);
        return num;
    }

    private void storeLastNumOfBooksInSharePreferences(int num){
        SharedPreferences prefs = getSharedPreferences("NUM", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("number", num);
        editor.commit();
    }

}
