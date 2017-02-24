package edu.rose_hulman.suj1.jersey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Jersey mCurrentJersey;
    private TextView mPlayerNameTV;
    private TextView mPlayerNumTV;
    private ImageView mImage;

    @Override
    protected void onPause() {
        super.onPause();
        this.storeJerseyInSharePreferences(this.mCurrentJersey);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCurrentJersey = getLastJerseyFromSharePreferences();

        mPlayerNameTV = (TextView) findViewById(R.id.player_Name_TV);
        mPlayerNumTV = (TextView) findViewById(R.id.player_Number_TV);
        mImage = (ImageView) findViewById(R.id.color_jersey);
        showCurrentJersey();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItem();
            }
        });
    }

    private void editItem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_edit, null, false);
        builder.setView(view);
        final EditText playNameET = (EditText) view.findViewById(R.id.edit_name);
        final EditText playNumberET = (EditText) view.findViewById(R.id.edit_number);
        final Switch switchRW = (Switch) view.findViewById(R.id.switchRW);
        playNameET.setText(mCurrentJersey.getPlayerName());
        playNumberET.setText("" + mCurrentJersey.getPlayerNum());
        switchRW.setChecked(mCurrentJersey.getRed());

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final boolean isRed = switchRW.isChecked();
                String playName = playNameET.getText().toString();
                int playNum = 0;
                if(playNumberET.getText().toString().equals("")) {
                } else {
                    playNum = Integer.parseInt(playNumberET.getText().toString());
                }
                mCurrentJersey = new Jersey(playName, playNum, isRed);
                showCurrentJersey();
            }
        });
        builder.create().show();
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
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.action_reset) {
            final Jersey temp = mCurrentJersey;
            if(temp == null){
                return true;
            }
            mCurrentJersey = new Jersey();
            showCurrentJersey();
            View coodinator = findViewById(R.id.coordinator_layout);
            Snackbar snackbar = Snackbar.make(coodinator, "Reset", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mCurrentJersey = temp;
                    showCurrentJersey();
                }
            });
            snackbar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCurrentJersey(){
        mPlayerNameTV.setText(mCurrentJersey.getPlayerName());
        mPlayerNumTV.setText(mCurrentJersey.getPlayerNum() + "");
        if(mCurrentJersey.getRed())
            mImage.setImageResource(R.drawable.red_jersey);
        else
            mImage.setImageResource(R.drawable.blue_jersey);
    }

    private Jersey getLastJerseyFromSharePreferences(){
        SharedPreferences prefs = getSharedPreferences("LastJersey", MODE_PRIVATE);
        Jersey lastJersey = new Jersey();
        String name = prefs.getString("nameSP", getString(R.string.default_jersey_name));
        int number = prefs.getInt("numSP", 17);
        boolean isRed = prefs.getBoolean("isRedSP", true);
        lastJersey.setPlayerName(name);
        lastJersey.setPlayerNum(number);
        lastJersey.setRed(isRed);
        return lastJersey;
    }

    private void storeJerseyInSharePreferences(Jersey storeJersey){
        SharedPreferences prefs = getSharedPreferences("LastJersey", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nameSP", storeJersey.getPlayerName());
        editor.putInt("numSP", storeJersey.getPlayerNum());
        editor.putBoolean("isRedSP", storeJersey.getRed());
        editor.commit();
    }
}
