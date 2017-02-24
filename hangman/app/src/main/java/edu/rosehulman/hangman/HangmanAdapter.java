package edu.rosehulman.hangman;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by suj1 on 2/12/2017.
 */

public class HangmanAdapter extends RecyclerView.Adapter<HangmanAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView word;
        private TextView creator;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.word = (TextView) itemView.findViewById(R.id.textView);
            this.creator = (TextView) itemView.findViewById(R.id.textView3);
        }
    }
}
