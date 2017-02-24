package edu.rosehulman.hangman;

/**
 * Created by suj1 on 2/12/2017.
 */

public class Hangman {

    private String creator;
    private String word;

    public Hangman(){

    }

    public Hangman(String c, String w){
        this.creator = c;
        this.word = w;
    }

    public String getCreator() {
        return creator;
    }

    public String getWord() {
        return word;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
