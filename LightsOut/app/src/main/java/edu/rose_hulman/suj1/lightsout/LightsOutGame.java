package edu.rose_hulman.suj1.lightsout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by suj1 on 12/2/2016.
 */

public class LightsOutGame {
    private int[] buttons;
    private int count = 0;
    private boolean ifWin;

    public LightsOutGame(int numberOfButtons) {
        this.buttons = new int[numberOfButtons];
        this.setButtons();
    }

    private void setButtons() {
        Random r = new Random();
        for (int i = 0; i < this.buttons.length; i++) {
            this.buttons[i] = r.nextInt(2);
        }
        this.ifWin = checkWin();
    }
    public void flip(int num) {
        for (int i = num - 1; i <= num + 1; i++) {
            if (i >= 0 && i < this.buttons.length) {
                int buttonValue = buttons[i] == 1 ? 0 : 1;
                this.buttons[i] = buttonValue;
            }
        }
        this.count++;
        this.ifWin = checkWin();
    }

    public boolean checkWin() {
        for (int i = 0; i < buttons.length - 1; i++) {
            if (buttons[i] != buttons[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public String gameStageString() {
        if (this.ifWin) {
            return "You have taken " + Integer.toString(this.count) + " step to win.";
        } else if (this.count == 0) {
            return "Make the button match.";
        }
        return "You have taken " + Integer.toString(this.count) + " step.";
    }

    public String gameButtonString(int num) {
        return buttons[num]+"";
    }

    public void reset(){
        this.count = 0;
        this.setButtons();
        this.ifWin = false;
    }

    public int[] getButtons(){
        return this.buttons;
    }

    public int getCount(){
        return this.count;
    }

    public void setButtons(int[] b){
        this.buttons = b;
        this.ifWin = checkWin();
    }

    public void setCount(int c){
        this.count = c;
    }
}
