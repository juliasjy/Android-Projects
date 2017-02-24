package edu.rose_hulman.suj1.foodrater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{
    private Context mContext;
    private List<Food> mFoods;
    private HashMap<String, Food> nameMap = new HashMap<String, Food>();
    private Random r = new Random();
    private RecyclerView mRecyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView nameTextView;
        RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removeName(getAdapterPosition());
                    return false;
                }
            });
            imageView = (ImageView) view.findViewById(R.id.image_View);
            nameTextView = (TextView) view.findViewById(R.id.name_view);
            ratingBar = (RatingBar) view.findViewById(R.id.rating_Bar);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    mFoods.get(getAdapterPosition()).changeRate(rating);
//                    String name = mFoods.get(getAdapterPosition()).getName();
//                    nameMap.get(name).changeRate(rating);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mFoods.get(position).getName();
        holder.imageView.setImageResource(mFoods.get(position).getID());

        holder.nameTextView.setText(mFoods.get(position).getName());

        holder.ratingBar.setRating(mFoods.get(position).getRate());

    }

    public FoodAdapter(Context mContext, RecyclerView recyclerView){
        this.mContext = mContext;
        this.mFoods = new ArrayList<>();
        this.mRecyclerView = recyclerView;
        initialFoodsMap();
        putDefaultFoods();
    }

    public void putDefaultFoods(){
//        mFoods.add(nameMap.get("banana"));
//        mFoods.add(nameMap.get("homemade bread"));
//        mFoods.add(nameMap.get("steak"));
//        mFoods.add(nameMap.get("chocolate"));
//        mFoods.add(nameMap.get("ice cream"));

        mFoods.add(new Food("banana", R.drawable.banana, 0));
        mFoods.add(new Food("broccoli", R.drawable.broccoli, 0));
        mFoods.add(new Food("chocolate", R.drawable.chocolate, 0));
        mFoods.add(new Food("lima beans", R.drawable.limabeans, 0));
        mFoods.add(new Food("steak", R.drawable.steak, 0));
    }

    public void initialFoodsMap(){
        nameMap.put("banana", new Food("banana", R.drawable.banana, 0));
        nameMap.put("broccoli", new Food("broccoli", R.drawable.broccoli, 0));
        nameMap.put("homemade bread", new Food("homemade bread", R.drawable.bread, 0));
        nameMap.put("chicken", new Food("chicken", R.drawable.chicken, 0));
        nameMap.put("chocolate", new Food("chocolate", R.drawable.chocolate, 0));
        nameMap.put("ice cream", new Food("ice cream", R.drawable.icecream, 0));
        nameMap.put("lima beans", new Food("lima beans", R.drawable.limabeans, 0));
        nameMap.put("steak", new Food("steak", R.drawable.steak, 0));
    }

    private Food getRandomFood(){
        String[] names = new String[]{"banana", "broccoli", "homemade bread",
                "chicken", "chocolate", "ice cream", "lima beans", "steak"};
        String name = names[r.nextInt(names.length)];
        Food food = nameMap.get(name);
        return new Food(food.getName(), food.getID(), 0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mFoods.size();
    }

    public void addFood(){
        mFoods.add(0, getRandomFood());
        notifyDataSetChanged();
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    public void removeName(int position){
        mFoods.remove(position);
        notifyItemRemoved(position);
    }
}
