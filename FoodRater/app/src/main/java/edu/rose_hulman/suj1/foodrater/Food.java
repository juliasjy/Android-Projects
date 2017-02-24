package edu.rose_hulman.suj1.foodrater;

/**
 * Created by suj1 on 1/1/2017.
 */

public class Food {
    private String name;
    private int ID;
    private float rate;

    public Food(String name, int ID, float rate){
        this.name = name;
        this.ID = ID;
        this.rate = rate;
    }

    public String getName(){
        return this.name;
    }

    public int getID(){
        return this.ID;
    }

    public float getRate(){
        return this.rate;
    }

    public void changeRate(float rate){
        this.rate = rate;
    }

    public String toString(){
        return this.getName() + " " + this.getRate();
    }
}
