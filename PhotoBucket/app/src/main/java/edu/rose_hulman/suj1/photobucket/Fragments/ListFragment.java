package edu.rose_hulman.suj1.photobucket.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import edu.rose_hulman.suj1.photobucket.Constants;
import edu.rose_hulman.suj1.photobucket.ListAdapter;
import edu.rose_hulman.suj1.photobucket.Photo;
import edu.rose_hulman.suj1.photobucket.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListFragment.OnPhoteSelectedListener} interface
 * to handle interaction events.
 */
public class ListFragment extends Fragment implements Toolbar.OnMenuItemClickListener, View.OnClickListener{

    private OnPhoteSelectedListener mPhotoSelectedListener;
    private OnLogoutListener mLogoutListener;
    private ListAdapter mAdapter;
    private RecyclerView rView;
    private View view;
    private LinearLayoutManager manager;
    private Activity mContext;
    private String currentUserID;
    private boolean isALL = false;
    private DatabaseReference mRef;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        currentUserID = getArguments().getString(Constants.FIREBASE_UID);
        mRef = FirebaseDatabase.getInstance().getReference().child("photos");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        createToolbar(view);
        final FloatingActionButton fab = createFab(view);
        rView = (RecyclerView) view.findViewById(R.id.recycle_view);
        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rView.setLayoutManager(manager);
        mAdapter = new ListAdapter(mContext, mRef, mPhotoSelectedListener, currentUserID);
        rView.setAdapter(mAdapter);
        createItemTouchHelper(rView, fab);
        return view;
    }

    private void createToolbar(View view){
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);
        mContext.getMenuInflater().inflate(R.menu.menu_main, mToolbar.getMenu());
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    private FloatingActionButton createFab(View view){
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        return fab;
    }

    private void createItemTouchHelper(RecyclerView rView, FloatingActionButton f){
        final FloatingActionButton fab = f;
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                final int position = viewHolder.getAdapterPosition();
                final Photo pic = mAdapter.hide(position);
                final Snackbar snackbar = Snackbar
                        .make(fab, "Photo removed!", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAdapter.add(pic, position);
                                Snackbar snackbar1 = Snackbar.make(fab, "Password restored!", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                            }
                        })
                        .setCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION && event != Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                                    mAdapter.firebaseRemove(pic);
                                }
                            }
                        });

                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rView);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.action_logout:
                Log.d("PK", "LOGOUT Menu Item Clicked!");
                mLogoutListener.onLogout();
                return true;
            case R.id.action_SHOW:
                Query q = null;
                if(isALL){
                    menuItem.setTitle(R.string.show_all);
                    mAdapter.cleanListAdapter();
                    q = mRef.orderByChild("uid").equalTo(currentUserID);
                    mAdapter.setListAdapter(q);
                } else {
                    menuItem.setTitle(R.string.show_mine);
                    mAdapter.cleanListAdapter();
                    q = mRef.orderByChild("uid");
                    mAdapter.setListAdapter(q);
                }
                isALL = !isALL;
                return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPhoteSelectedListener) {
            mPhotoSelectedListener = (OnPhoteSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        try {
            mLogoutListener = (OnLogoutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLogoutListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mPhotoSelectedListener = null;
        mLogoutListener = null;
    }

    @Override
    public void onClick(View v) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View contentView = inflater.inflate(R.layout.dialog_add, null);
        final EditText titleView = (EditText) contentView.findViewById(R.id.dialog_add_title_text);
        final EditText urlView = (EditText) contentView.findViewById(R.id.dialog_add_url_text);
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .setTitle(R.string.dialog_add_title)
                .setView(contentView)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Photo pic = new Photo(
                                currentUserID,
                                titleView.getText().toString(),
                                urlView.getText().toString());
                        mAdapter.firebasePush(pic);
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPhoteSelectedListener {
        void onPhotoSelected(Photo pic);
    }

    public interface OnLogoutListener {
        void onLogout();
    }
}
