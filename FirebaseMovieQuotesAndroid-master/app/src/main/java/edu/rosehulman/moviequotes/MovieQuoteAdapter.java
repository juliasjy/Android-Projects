package edu.rosehulman.moviequotes;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt Boutell on 12/15/2015.
 */
public class MovieQuoteAdapter extends RecyclerView.Adapter<MovieQuoteAdapter.ViewHolder> {

    private List<MovieQuote> mMovieQuotes;
    private Callback mCallback;
    private DatabaseReference mMovieQuotesRef;


    public MovieQuoteAdapter(Callback callback) {
        mCallback = callback;
        mMovieQuotes = new ArrayList<>();
        mMovieQuotesRef = FirebaseDatabase.getInstance().getReference().child("quotes");
        mMovieQuotesRef.addChildEventListener(new QuotesChildEventListener());
        mMovieQuotesRef.keepSynced(true);
    }

    class QuotesChildEventListener implements ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            MovieQuote quote = dataSnapshot.getValue(MovieQuote.class);
            quote.setKey(dataSnapshot.getKey());
            mMovieQuotes.add(0, quote);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String keyToChange = dataSnapshot.getKey();
            for(int i = 0; i < mMovieQuotes.size(); i++){
                if(keyToChange.equals(mMovieQuotes.get(i).getKey())){
                    MovieQuote quoteToUpdate = mMovieQuotes.get(i);
                    MovieQuote newQuote = dataSnapshot.getValue(MovieQuote.class);
                    quoteToUpdate.setMovie(newQuote.getMovie());
                    quoteToUpdate.setQuote(newQuote.getQuote());
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String keyToRemove = dataSnapshot.getKey();
            for(int i = 0; i < mMovieQuotes.size(); i++){
                if(keyToRemove.equals(mMovieQuotes.get(i).getKey())){
                    mMovieQuotes.remove(i);
                    notifyDataSetChanged();
                    return;
                }
            }

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e("ERROR", "onCancelled: Firebase error");
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_quote_row_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieQuote movieQuote = mMovieQuotes.get(position);
        holder.mQuoteTextView.setText(movieQuote.getQuote());
        holder.mMovieTextView.setText(movieQuote.getMovie());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onEdit(movieQuote);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MovieQuote temp = mMovieQuotes.get(position);
                remove(temp);
                return true;
            }
        });
    }

    public void remove(MovieQuote movieQuote) {
        //TODO: Remove the next line(s) and use Firebase instead
        mMovieQuotesRef.child(movieQuote.getKey()).removeValue();
//        mMovieQuotes.remove(movieQuote);
//        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mMovieQuotes.size();
    }

    public void add(MovieQuote movieQuote) {
        //TODO: Remove the next line(s) and use Firebase instead
        mMovieQuotesRef.push().setValue(movieQuote);
        notifyDataSetChanged();
    }

    public void update(MovieQuote movieQuote, String newQuote, String newMovie) {
        //TODO: Remove the next line(s) and use Firebase instead
        movieQuote.setQuote(newQuote);
        movieQuote.setMovie(newMovie);
        mMovieQuotesRef.child(movieQuote.getKey()).setValue(movieQuote);
        notifyDataSetChanged();
    }

    public interface Callback {
        public void onEdit(MovieQuote movieQuote);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mQuoteTextView;
        private TextView mMovieTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mQuoteTextView = (TextView) itemView.findViewById(R.id.quote_text);
            mMovieTextView = (TextView) itemView.findViewById(R.id.movie_text);
        }
    }


}
