package edu.rose_hulman.suj1.comicviewer;

/**
 * Created by suj1 on 1/11/2017.
 */

public class Comic {
    private int num;
    private int month;
    private int day;
    private int year;
    private String link;
    private String news;
    private String transcript;
    private String safe_title;
    private String alt;
    private String img;
    private String title;

    public void setNum(int num) {
        this.num = num;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public void setSafe_title(String safe_title) {
        this.safe_title = safe_title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {

        return num;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public String getLink() {
        return link;
    }

    public String getNews() {
        return news;
    }

    public String getTranscript() {
        return transcript;
    }

    public String getSafe_title() {
        return safe_title;
    }

    public String getAlt() {
        return alt;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }
}

