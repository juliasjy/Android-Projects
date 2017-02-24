package edu.rose_hulman.suj1.exam1_suj1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Gift mGift;
    private TextView mShopListView;
    private ArrayList<Gift> mShoppingList = new ArrayList<Gift>();
    private Button mEnterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mShopListView = (TextView) findViewById(R.id.shopListTextView);
        mEnterButton = (Button) findViewById(R.id.Button);

        final EditText mGiftNameEditText = (EditText) findViewById(R.id.giteName_EditView);
        mGiftNameEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        final EditText mRecipientEditText = (EditText) findViewById(R.id.recipient_EditView);
        mRecipientEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        mEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGiftNameEditText.getText().toString().equals("") || mRecipientEditText.getText().toString().equals("")) {
                }else{
                    mGift = new Gift(mGiftNameEditText.getText().toString(), mRecipientEditText.getText().toString());
                    mShoppingList.add(mGift);
                    showShopList();
                    mGiftNameEditText.setText("");
                    mRecipientEditText.setText("");
                }
            }
        });
    }

    public void showShopList(){
        String temp = "";
        for(int i = 0; i < mShoppingList.size(); i ++){
            temp = mShoppingList.get(i).getString();
        }
        mShopListView.setText(temp);
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
            return true;
        }else if (id == R.id.action_edit) {
            addDialog();
            return true;
        } else if (id == R.id.action_reset) {
            final ArrayList<Gift> temp= mShoppingList;
            mShoppingList = new ArrayList<Gift>();
            showShopList();
            View coodinator = findViewById(R.id.coordinator_layout);
            Snackbar snackbar = Snackbar.make(coodinator, "Romoving", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    mShoppingList = temp;
                    showShopList();
                }
            });
            snackbar.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialogTitle);
        String[] temp = new String[mShoppingList.size()];
        boolean[] choosed = new boolean[mShoppingList.size()];
        for(int i = 0; i < mShoppingList.size(); i ++) {
            final Gift currentGift = mShoppingList.get(i);
            temp[i] = currentGift.getGiftName();
            choosed[i] = false;

        }
//        builder.setItems(temp, new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if(mShoppingList.get(which).isUpper()){
//                    mShoppingList.get(which).toLower();
//                } else {
//                    mShoppingList.get(which).toUpper();
//                }
//                showShopList();
//            }
//        });
        builder.setMultiChoiceItems(temp, choosed, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(mShoppingList.get(which).isUpper()){
                    mShoppingList.get(which).toLower();
                } else {
                    mShoppingList.get(which).toUpper();
                }
            }
        });
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showShopList();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

}
