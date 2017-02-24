package edu.rose_hulman.suj1.comicviewer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by suj1 on 1/11/2017.
 */

public class ComicWrapper implements Parcelable{
    private int xkcdIssue;
    private int color;
    private Comic comic;
    private static int colorInt = 0;
    private static int[] colors = new int[]{android.R.color.holo_green_light, android.R.color.holo_blue_light,
            android.R.color.holo_orange_light, android.R.color.holo_red_light};

    protected ComicWrapper(Parcel in) {
        xkcdIssue = in.readInt();
        color = in.readInt();
    }

    public void setComic(Comic comic){
        this.comic = comic;
    }

    public ComicWrapper(){
        this.xkcdIssue = Utils.getRandomCleanIssue();
        this.color = colors[colorInt % colors.length];
        this.colorInt = colorInt + 1;
    }

    public int getXkcdIssue(){
        return this.xkcdIssue;
    }

    public int getColor(){
        return this.color;
    }

    public static final Creator<ComicWrapper> CREATOR = new Creator<ComicWrapper>() {
        @Override
        public ComicWrapper createFromParcel(Parcel in) {
            return new ComicWrapper(in);
        }

        @Override
        public ComicWrapper[] newArray(int size) {
            return new ComicWrapper[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.xkcdIssue);
        dest.writeInt(this.color);
    }
}
