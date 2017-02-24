package edu.rose_hulman.suj1.pointofsale;

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
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {
    private Item currentItem;
    private TextView mNameTextView;
    // Repeat for Quantity and Date
    private TextView mQuantityTextView;
    private TextView mDateTextView;
    private ArrayList<Item> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameTextView = (TextView) findViewById(R.id.name_text);
        mQuantityTextView = (TextView) findViewById(R.id.quantity_text);
        mDateTextView = (TextView) findViewById(R.id.date_text);

        mItems = new ArrayList<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                currentItem = Item.getDefaultItem();
//                showCurrentItem();
                addEditItem(false);
            }
        });
        registerForContextMenu(mNameTextView);
    }

    private void addEditItem(final boolean isEditing){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = this.getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        final EditText nameET = (EditText) view.findViewById(R.id.edit_name);
        final EditText quantityET = (EditText) view.findViewById(R.id.edit_quantity);
        final CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar_view);
        final GregorianCalendar calendar = new GregorianCalendar();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                calendar.set(year, month, day);
            }
        });
        if (isEditing) {
            nameET.setText(currentItem.getName());
            quantityET.setText("" + currentItem.getQuantity());
            calendarView.setDate(currentItem.getDeliveryDateTime());
        }

        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("POS", "OK clicked");
                String name = nameET.getText().toString();
                int quantity = Integer.parseInt(quantityET.getText().toString());
                if (isEditing) {
                    currentItem.setName(name);
                    currentItem.setQuantity(quantity);
                    currentItem.setDeliveryDate(calendar);
                } else {
                    currentItem = new Item(name, quantity, calendar);
                    mItems.add(currentItem);
                }
                showCurrentItem();
            }
        });
        builder.create().show();
    }

    private void showCurrentItem(){
        mNameTextView.setText(currentItem.getName());
        if(currentItem.getQuantity() == 0){
            mQuantityTextView.setText("Quantity");
        } else {
            mQuantityTextView.setText("Quantity: " + currentItem.getQuantity());
        }
        mDateTextView.setText(currentItem.getDeliveryDateString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_reset:
                final Item tempItem = currentItem;
                if(tempItem == null){
                    return true;
                }
                currentItem = new Item();
                showCurrentItem();
                View coodinator = findViewById(R.id.coordinator_layout);
                Snackbar snackbar = Snackbar.make(coodinator, "Romoving", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        currentItem = tempItem;
                        showCurrentItem();
                    }
                });
                snackbar.show();
                return true;
            case R.id.action_search:
                showSearchDialog();
                return true;
            case R.id.action_Clear:
                showClearDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_context_edit){
            addEditItem(true);
            return true;
        } else if (id == R.id.menu_context_remove) {
            mItems.remove(currentItem);
            currentItem = new Item();
            showCurrentItem();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showClearDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.clear_item_title);
        builder.setMessage("Are you sure you want to remove all items?");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mItems = new ArrayList<>();
                currentItem = new Item();
                showCurrentItem();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private void showSearchDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.search_item_title);
        builder.setItems(getNames(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentItem = mItems.get(which);
                showCurrentItem();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    private String[] getNames() {
        String[] names = new String[mItems.size()];
        for (int i = 0; i < mItems.size(); i++) {
            names[i] = mItems.get(i).getName();
        }
        return names;
    }
}
