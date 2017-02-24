package edu.rose_hulman.suj1.photobucket;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import edu.rose_hulman.suj1.photobucket.Fragments.ListFragment;

/**
 * Created by suj1 on 1/27/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.PhotoView> implements ChildEventListener{

    private List<Photo> mPhotos;
    private DatabaseReference mPhotoRef;
    private Query mQuery;
    private final LayoutInflater mInflater;
    private final Context mContext;
    private final String currentUserID;

    private ListFragment.OnPhoteSelectedListener mListener;


    public ListAdapter(Context context, DatabaseReference ref, ListFragment.OnPhoteSelectedListener listener, String uid) {
        mPhotos = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mPhotoRef = ref;
        mQuery = mPhotoRef.orderByChild("uid").equalTo(uid);

        Log.d("TTT", "UserID: " + uid);
        mListener = listener;
        currentUserID = uid;
        mQuery.addChildEventListener(this);
    }

    @Override
    public PhotoView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_view_doc, parent, false);
        return new PhotoView(view);
    }

    @Override
    public void onBindViewHolder(PhotoView holder, int position) {
        final Photo pic = mPhotos.get(position);
        holder.bindToView(pic);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPhotoSelected(pic);
            }
        });
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showAddEditDialog(pic);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public void firebasePush(Photo pic) {
        mPhotoRef.push().setValue(pic);
    }

    public void firebaseRemove(Photo pic) {
        mPhotoRef.child(pic.getKey()).removeValue();
    }

    public void add(Photo pic, int pos) {
        mPhotos.add(pos,pic);
        notifyItemInserted(pos);
    }

    public Photo hide(int pos){
        Photo pic = mPhotos.remove(pos);
        notifyItemRemoved(pos);
        return pic;
    }

    private Photo remove(String key) {
        for (int i = 0; i < mPhotos.size(); i++) {
            Photo pw = mPhotos.get(i);
            if (key.equals(pw.getKey())) {
                mPhotos.remove(i);
                notifyItemRemoved(i);
                return pw;
            }
        }
        return null;
    }

    public void clear() {
        mPhotos.clear();
    }

    public Photo get(int position) {
        return mPhotos.get(position);
    }

    public void update(Photo pic, String newTitle, String newURL) {
        pic.setTitle(newTitle);
        pic.setUrl(newURL);
        mPhotoRef.child(pic.getKey()).setValue(pic);
        notifyDataSetChanged();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
        String key = dataSnapshot.getKey();
        Photo pic = dataSnapshot.getValue(Photo.class);
        pic.setKey(key);
        mPhotos.add(0, pic);
        notifyDataSetChanged();
        //notifyItemInserted(0); // seems to cause problem when first loading after app is paused.
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String previousChild) {
        String title = dataSnapshot.child("title").getValue(String.class);
        String url = dataSnapshot.child("url").getValue(String.class);
        for (int i = 0; i < mPhotos.size(); i++) {
            Photo pic = mPhotos.get(i);
            if (dataSnapshot.getKey().equals(pic.getKey())) {
                pic.setTitle(title);
                pic.setUrl(url);
                notifyItemChanged(i);
                break;
            }
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        String key = dataSnapshot.getKey();
        remove(key);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Log.e(Constants.TAG, "Cancelled, error: " + databaseError.getMessage());
    }

    public void showAddEditDialog(final Photo pic) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(pic == null ? R.string.dialog_add_title : R.string.dialog_edit_title));
        final View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        final EditText titleEditText = (EditText) view.findViewById(R.id.dialog_add_title_text);
        final EditText urlEditText = (EditText) view.findViewById(R.id.dialog_add_url_text);
        if (pic != null) {
            // pre-populate
            titleEditText.setText(pic.getTitle());
            urlEditText.setText(pic.getUrl());

            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    update(pic, titleEditText.getText().toString(), urlEditText.getText().toString());
                }
            });

            builder.setNeutralButton("delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(pic.getUid().equals(currentUserID)) {
                        mPhotoRef.child(pic.getKey()).removeValue();
                    } else {
                        Toast.makeText(mContext, "Permission denied", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = titleEditText.getText().toString();
                    String url = urlEditText.getText().toString();
                    add(new Photo(currentUserID, title, url), 0);
                }
            });
        }
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.create().show();
    }

    public class PhotoView extends RecyclerView.ViewHolder {

        private final TextView mTitleTextView;
        private final TextView mImageTextView;
        private final CardView mCardView;

        public PhotoView(View itemView) {
            super(itemView);
            mTitleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            mImageTextView = (TextView) itemView.findViewById(R.id.image_text_view);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
        }

        public void bindToView(final Photo pic) {
            mTitleTextView.setText(pic.getTitle());
            mImageTextView.setText(pic.getUrl());
        }
    }

    public void setListAdapter(Query q){
        this.mQuery = q;
        this.mQuery.addChildEventListener(this);
    }

    public void cleanListAdapter(){
        this.mQuery.removeEventListener(this);
        this.mPhotos = new ArrayList<>();
    }
}