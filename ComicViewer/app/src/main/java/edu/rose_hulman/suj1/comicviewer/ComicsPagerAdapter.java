package edu.rose_hulman.suj1.comicviewer;

/**
 * Created by suj1 on 1/7/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class ComicsPagerAdapter extends FragmentPagerAdapter{
    private int numOfPage = 5;
    private List<ComicWrapper> mListComicWrapper;

    public ComicsPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mListComicWrapper = new ArrayList<ComicWrapper>();
        initialize();
    }

    private void initialize(){
        for(int i = 0; i < 5; i ++) {
            this.mListComicWrapper.add(new ComicWrapper());
        }
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ComicFragment (defined as a static inner class below).
        ComicWrapper mWrapper = this.mListComicWrapper.get(position);
        int left = -1;
        int right = -1;
        if (position > 0) {
            left = this.mListComicWrapper.get(position - 1).getXkcdIssue();
        }
        if (position < this.mListComicWrapper.size() - 1){
            right = this.mListComicWrapper.get(position + 1).getXkcdIssue();
        }
        return ComicFragment.newInstance(mWrapper);
    }

    @Override
    public int getCount() {
        return numOfPage;
    }

    public void addNewWrapper(){
        this.numOfPage++;
        this.mListComicWrapper.add(new ComicWrapper());
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Issue " + mListComicWrapper.get(position).getXkcdIssue();
    }

    public int getNumOfPage() {
        return numOfPage;
    }

    public List<ComicWrapper> getmListComicWrapper() {
        return mListComicWrapper;
    }

    public int setNumOfPage(int numOfPage) {
        this.numOfPage = numOfPage;
        return numOfPage;
    }

    public void setmListComicWrapper(List<ComicWrapper> mListComicWrapper) {
        this.mListComicWrapper = mListComicWrapper;
    }
}
