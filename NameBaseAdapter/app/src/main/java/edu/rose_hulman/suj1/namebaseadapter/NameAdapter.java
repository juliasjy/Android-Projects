package edu.rose_hulman.suj1.namebaseadapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suj1 on 12/12/2016.
 */

public class NameAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mNames;
    private Random mRandom;

    public NameAdapter(Context mContext) {
        this.mContext = mContext;
        this.mNames = new ArrayList<>();
        mRandom = new Random();
        for(int i = 0; i < 5; i ++){
            mNames.add(getRandomName());
        }
    }

    private String getRandomName(){
        String[] names = new String[]{
            "Hannah", "Emaily", "Sarah", "Madison", "Brlanna",
                "Kaylee", "Kaitlyn", "Alexis", "Elizabeth",
                "Michael", "Jacob", "Matthew", "Nicholas", "Christopher",
                "Joseph", "Zachary", "Joshua", "Andrew", "William"
        };
        return names[mRandom.nextInt(names.length)];
    }

    @Override
    public int getCount() {
        return this.mNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            view = LayoutInflater.from(this.mContext).inflate(R.layout.row_view, parent, false);
        } else {
            view = convertView;
        }
        TextView nameTextView = (TextView) view.findViewById(R.id.name_view);
        nameTextView.setText(mNames.get(position));

        TextView positionTextView = (TextView) view.findViewById(R.id.position_view);
        positionTextView.setText("I am #" + position);
        return view;
    }

    public void addName(){
        mNames.add(getRandomName());
        notifyDataSetChanged();
    }

    public void removeName(int position){
        mNames.remove(position);
        notifyDataSetChanged();
    }
}
