package edu.rose_hulman.suj1.comicviewer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by suj1 on 1/11/2017.
 */

public class GetImageTask extends AsyncTask<String, Void, Bitmap> {
    private GetImageTask.ImageConsumer mImageConsumer;

    protected GetImageTask(GetImageTask.ImageConsumer activity){
        this.mImageConsumer = activity;
    }

    @Override
    protected Bitmap doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(urlString).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.d("Tag", "ERROR:" + e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mImageConsumer.onImageLoaded(bitmap);
    }

    public interface ImageConsumer {
        public void onImageLoaded(Bitmap bitmap);
    }
}
