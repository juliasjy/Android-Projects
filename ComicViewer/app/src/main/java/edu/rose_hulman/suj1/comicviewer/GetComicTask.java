package edu.rose_hulman.suj1.comicviewer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Created by suj1 on 1/11/2017.
 */

public class GetComicTask extends AsyncTask<String, Void, Comic>{
    private ComicConsumer mComicConsumer;

    protected GetComicTask(ComicConsumer activity){
        this.mComicConsumer = activity;
    }
    @Override
    protected Comic doInBackground(String... urlStrings) {
        String urlString = urlStrings[0];
        Comic comic = null;
        try {
            comic = new ObjectMapper().readValue(new URL(urlString), Comic.class);
        } catch (IOException e) {
            Log.d("Tag", "ERROR:" + e.getMessage());
        }
        return comic;
    }

    @Override
    protected void onPostExecute(Comic comic) {
        super.onPostExecute(comic);
        mComicConsumer.onComicLoaded(comic);
    }

    public interface ComicConsumer {
        public void onComicLoaded(Comic comic);
    }
}
