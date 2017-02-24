package edu.rose_hulman.suj1.comicviewer;

/**
 * Created by suj1 on 1/7/2017.
 */

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A placeholder fragment containing a simple view.
 */
public class ComicFragment extends Fragment implements GetComicTask.ComicConsumer, GetImageTask.ImageConsumer{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_COMIC_WRAPPER = "COMMIC_WRAPPER";

    private ComicWrapper mWrapper;
    private TextView mTitle;
    private ImageView imageView;
    private String altText;
    private Comic comic;
    private Bitmap bitmap;

    public ComicFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ComicFragment newInstance(ComicWrapper comicWrapper) {
        ComicFragment fragment = new ComicFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMIC_WRAPPER, comicWrapper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWrapper = getArguments().getParcelable(ARG_COMIC_WRAPPER);
            String urlStringComic = String.format(Locale.US, "http://xkcd.com/%d/info.0.json", mWrapper.getXkcdIssue());
            (new GetComicTask(this)).execute(urlStringComic);
        }
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.setBackgroundColor(getResources().getColor(mWrapper.getColor()));

        mTitle = (TextView) rootView.findViewById(R.id.title);
        if(comic != null) {
            mTitle.setText(comic.getSafe_title());
        }
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.update();
        return rootView;
    }

    @Override
    public void onComicLoaded(Comic comic) {
        Log.d("COMIC", "Comic Object\n" + comic);
        this.comic = comic;
        mWrapper.setComic(comic);
        altText = comic.getAlt();
        mTitle.setText(comic.getSafe_title());
        (new GetImageTask(this)).execute(comic.getImg());
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        Log.d("Image", "Image Object\n" + bitmap);
        this.bitmap = bitmap;
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_info) {
            addDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mouseover text for " + mWrapper.getXkcdIssue());
        builder.setMessage(altText);
        builder.create().show();
    }

}
