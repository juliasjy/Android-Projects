package edu.rose_hulman.suj1.historicaldocs.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rose_hulman.suj1.historicaldocs.Doc;
import edu.rose_hulman.suj1.historicaldocs.DocListAdapter;
import edu.rose_hulman.suj1.historicaldocs.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDocSelectedListener} interface
 * to handle interaction events.
 */
public class DocListFragment extends Fragment {

    private OnDocSelectedListener mListener;

    public DocListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView view = (RecyclerView)inflater.inflate(R.layout.fragment_doc_list, container, false);
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        DocListAdapter adapter = new DocListAdapter(getContext(), mListener);
        view.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDocSelectedListener) {
            mListener = (OnDocSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDocSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnDocSelectedListener {
        void onDocSelected(Doc doc);
    }
}
