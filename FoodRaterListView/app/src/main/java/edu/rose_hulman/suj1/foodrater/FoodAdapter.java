package edu.rose_hulman.suj1.foodrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by suj1 on 1/1/2017.
 */

public class FoodAdapter extends BaseAdapter{
    private Context mContext;
    private List<Food> mFoods;
    private HashMap<String, Integer> nameIDMap = new HashMap<String, Integer>();
    private HashMap<String, Float> nameRateMap = new HashMap<String, Float>();
    private Random r = new Random();

    public FoodAdapter(Context mContext){
        this.mContext = mContext;
        this.mFoods = new ArrayList<>();
        putDefaultFoods();
        initialFoodsMap();
    }

    public void putDefaultFoods(){
        this.mFoods.add(new Food("broccoli", R.drawable.broccoli, 0));
        this.mFoods.add(new Food("chicken", R.drawable.chicken, 0));
        this.mFoods.add(new Food("ice cream", R.drawable.icecream, 0));
        this.mFoods.add(new Food("steak", R.drawable.steak, 0));
        this.mFoods.add(new Food("homemade bread", R.drawable.bread, 0));
    }

    public void initialFoodsMap(){
        nameIDMap.put("banana", R.drawable.banana);
        nameIDMap.put("broccoli", R.drawable.broccoli);
        nameIDMap.put("homemade bread", R.drawable.bread);
        nameIDMap.put("chicken", R.drawable.chicken);
        nameIDMap.put("chocolate", R.drawable.chocolate);
        nameIDMap.put("ice cream", R.drawable.icecream);
        nameIDMap.put("lima beans", R.drawable.limabeans);
        nameIDMap.put("steak", R.drawable.steak);

        nameRateMap.put("banana", (float) 0);
        nameRateMap.put("broccoli", (float) 0);
        nameRateMap.put("homemade bread", (float) 0);
        nameRateMap.put("chicken", (float) 0);
        nameRateMap.put("chocolate", (float) 0);
        nameRateMap.put("ice cream", (float) 0);
        nameRateMap.put("lima beans", (float) 0);
        nameRateMap.put("steak", (float) 0);
    }

    private Food getRandomFood(){
        String[] names = new String[]{"banana", "broccoli", "homemade bread",
                "chicken", "chocolate", "ice cream", "lima beans", "steak"};
        String name = names[r.nextInt(names.length)];
        Food food = new Food(name, nameIDMap.get(name), nameRateMap.get(name));
        return food;
    }

    @Override
    public int getCount() {
        return this.mFoods.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if(convertView == null){
            view = LayoutInflater.from(this.mContext).inflate(R.layout.row_view, parent, false);
        } else {
            view = convertView;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.image_View);
        imageView.setImageResource(mFoods.get(position).getID());

        TextView nameTextView = (TextView) view.findViewById(R.id.name_view);
        nameTextView.setText(mFoods.get(position).getName());

        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_Bar);
        ratingBar.setRating(mFoods.get(position).getRate());
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                nameRateMap.put(mFoods.get(position).getName(), rating);
            }
        });

        return view;
    }

    public void addFood(){
        mFoods.add(getRandomFood());
        notifyDataSetChanged();
    }

    public void removeName(int position){
        mFoods.remove(position);
        notifyDataSetChanged();
    }
}
