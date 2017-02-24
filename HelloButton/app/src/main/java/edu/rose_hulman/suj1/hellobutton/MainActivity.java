package edu.rose_hulman.suj1.hellobutton;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mNumClicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // capturing the views
        final TextView textView = (TextView) findViewById(R.id.textView);
        Button button = (Button) findViewById(R.id.button);

        //Listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumClicks++;
                textView.setText(getResources().getQuantityString(R.plurals.message_format, mNumClicks, mNumClicks));
                if(mNumClicks > 10){
                    textView.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.background));
                    //textView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
