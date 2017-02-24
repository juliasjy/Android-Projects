package edu.rose_hulman.suj1.jersey;

/**
 * Created by suj1 on 12/10/2016.
 */

public class Jersey {

    private String playerName;
    private int playerNum;
    private boolean isRed;

    public Jersey(){
        this.playerName = "ANDROID";
        this.playerNum = 17;
        this.isRed = true;
    }

    public Jersey(String name, int num, boolean isRed){
        this.playerName = name;
        this.playerNum = num;
        this.isRed = isRed;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public void setPlayerName(String name){
        this.playerName = name;
    }

    public int getPlayerNum(){
        return this.playerNum;
    }

    public void setPlayerNum(int num){
        this.playerNum = num;
    }

    public boolean getRed(){
        return this.isRed;
    }

    public void setRed(boolean isRed){
        this.isRed = isRed;
    }
}
