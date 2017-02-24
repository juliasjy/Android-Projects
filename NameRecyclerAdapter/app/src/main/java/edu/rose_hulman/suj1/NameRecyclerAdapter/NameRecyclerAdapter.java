package edu.rose_hulman.suj1.NameRecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suj1 on 12/12/2016.
 */

public class NameRecyclerAdapter extends RecyclerView.Adapter<NameRecyclerAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mNames;
    private Random mRandom;
    private RecyclerView mRecyclerView;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView ;
        TextView positionTextView ;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    removeName(getAdapterPosition());
                    return false;
                }
            });
            nameTextView = (TextView) itemView.findViewById(R.id.name_view);
            positionTextView = (TextView) itemView.findViewById(R.id.position_view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.row_view, parent, false);
        return new ViewHolder(view);
    }

    public NameRecyclerAdapter(Context mContext, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.mNames = new ArrayList<>();
        mRandom = new Random();
        for(int i = 0; i < 5; i ++){
            mNames.add(getRandomName());
        }
        this.mRecyclerView = recyclerView;
    }

    private String getRandomName(){
        String[] names = new String[]{"Hannah", "Emaily", "Sarah", "Madison",
                "Brlanna", "Kaylee", "Kaitlyn", "Alexis", "Elizabeth",
                "Michael", "Jacob", "Matthew", "Nicholas", "Christopher",
                "Joseph", "Zachary", "Joshua", "Andrew", "William"
        };
        return names[mRandom.nextInt(names.length)];
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nameTextView.setText(mNames.get(position));
        holder.positionTextView.setText("I am #" + (position + 1));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public void addName(){
        mNames.add(0, getRandomName());
        notifyItemInserted(0);
        notifyDataSetChanged();
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    public void removeName(int position){
        mNames.remove(position);
        notifyDataSetChanged();
    }
}
