package edu.rose_hulman.suj1.photobucket.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import edu.rose_hulman.suj1.photobucket.GetImageTask;
import edu.rose_hulman.suj1.photobucket.Photo;
import edu.rose_hulman.suj1.photobucket.R;
import uk.co.senab.photoview.PhotoViewAttacher;

import static edu.rose_hulman.suj1.photobucket.R.id.imageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment implements GetImageTask.ImageConsumer{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PHOTO = "PHOTO";

    private TextView mTextView;
    private ImageView mImageView;
    private Photo mPic;
    private Bitmap bitmap;



    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pic Parameter 1.
     * @return A new instance of fragment DocDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(Photo pic) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO, pic);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPic = getArguments().getParcelable(ARG_PHOTO);
            String urlStringPhoto = mPic.getUrl();
            (new GetImageTask(this)).execute(urlStringPhoto);
        }
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mTextView = (TextView) view.findViewById(R.id.textView);
        if(mPic != null){
            mTextView.setText(mPic.getTitle());
        }

        mImageView = (ImageView) view.findViewById(imageView);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        }

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mImageView);
        photoViewAttacher.update();
        return view;
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        Log.d("Image", "Image Object\n" + bitmap);
        this.bitmap = bitmap;
        mImageView.setImageBitmap(bitmap);
    }

}
