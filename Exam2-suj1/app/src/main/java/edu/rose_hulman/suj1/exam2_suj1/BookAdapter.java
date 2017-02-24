package edu.rose_hulman.suj1.exam2_suj1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suj1 on 1/8/2017.
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{
    private List<Book> mBookList;
    private Context mContext;
    private Random r = new Random();
    private TextView scoreView;
    private List<Book> mBooks;
    private RecyclerView mRecyclerView;
    private int num;
    private int state = 0;
    private int score = 0;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        ImageView checkView;
        ImageView cancelView;
        int position;

        public ViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.textView);
            checkView = (ImageView) view.findViewById(R.id.check_view);
            cancelView = (ImageView) view.findViewById(R.id.cancel_view);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(state == 1) {
                        state = 0;
                    } else {
                        state = 2;
                    }
                    position = getAdapterPosition();
                    updateState(checkView, cancelView, nameTextView, position);
                    checkView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            state = 0;
                            position = getAdapterPosition();
                            updateState(checkView, cancelView, nameTextView, position);
                            removeBook(getAdapterPosition(), checkView, cancelView);
                            score += 2;
                            scoreView.setText(getScoreMessage());
                            if(mBooks.size() == 0) {
                                Intent winIntent = new Intent(mContext, WinActivity.class);
                                winIntent.putExtra("EXTRA_MESSAGE", getScore());
                                update(num);
                                mContext.startActivity(winIntent);
                            }
                        }
                    });
                    cancelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            state = 0;
                            position = getAdapterPosition();
                            updateState(checkView, cancelView, nameTextView, position);
                            removeBook(getAdapterPosition(), checkView, cancelView);
                            score -= 1;
                            scoreView.setText(getScoreMessage());
                            if(mBooks.size() == 0) {
                                Intent winIntent = new Intent(mContext, WinActivity.class);
                                winIntent.putExtra("EXTRA_MESSAGE", getScore());
                                update(num);
                                mContext.startActivity(winIntent);
                            }
                        }
                    });
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    state = 1;
                    position = getAdapterPosition();
                    updateState(checkView, cancelView, nameTextView, position);
                    score -= 1;
                    scoreView.setText(getScoreMessage());
                    return true;
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
        holder.nameTextView.setText(mBooks.get(position).getTitle());
    }

    public BookAdapter(Context mContext, RecyclerView recyclerView, int num, TextView scoreView){
        this.mContext = mContext;
        this.scoreView = scoreView;
        this.mBooks = new ArrayList<>();
        this.mRecyclerView = recyclerView;
        this.num = num;
        this.mBookList = FileUtils.loadWordsFromJsonArray(mContext);
        putDefaultFoods();
    }

    public void putDefaultFoods(){
        for(int i = 0; i < this.num; i ++){
            mBooks.add(getRandomBook());
        }
    }

    private Book getRandomBook(){
        return mBookList.get(r.nextInt(mBookList.size()));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void removeBook(int position, ImageView i, ImageView j){
        i.setVisibility(View.GONE);
        j.setVisibility(View.GONE);
        mBooks.remove(position);
        notifyItemRemoved(position);
    }

    public void update(int num){
        this.mBooks = new ArrayList<>();
        this.num = num;
        this.score = 0;
        putDefaultFoods();
        scoreView.setText(getScoreMessage());
        notifyDataSetChanged();
    }

    private String getScoreMessage(){
        return "Score: " + this.score + "/" + this.num * 2;
    }

    private String getScore(){
        return "You got " + this.score + "/" + this.num * 2 + "points!";
    }

    public void addFavourite(){
        mBooks.add(0, mBookList.get(10));
        num ++;
        scoreView.setText(getScoreMessage());
        notifyDataSetChanged();
        mRecyclerView.getLayoutManager().scrollToPosition(0);
    }

    private void updateState(ImageView checkView, ImageView cancelView, TextView nameTextView, int position){
        if(state == 0){
            checkView.setVisibility(View.GONE);
            cancelView.setVisibility(View.GONE);
            nameTextView.setText(mBooks.get(position).getTitle());
        } else if (state == 1) {
            checkView.setVisibility(View.INVISIBLE);
            cancelView.setVisibility(View.INVISIBLE);
            nameTextView.setText(mBooks.get(position).getFirst());
        } else if (state == 2) {
            checkView.setVisibility(View.VISIBLE);
            cancelView.setVisibility(View.VISIBLE);
            nameTextView.setText(mBooks.get(position).getFirst() + " " + mBooks.get(position).getLast());
        }
    }
}