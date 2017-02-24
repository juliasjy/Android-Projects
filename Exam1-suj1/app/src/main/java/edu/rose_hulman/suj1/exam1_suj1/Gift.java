package edu.rose_hulman.suj1.exam1_suj1;

/**
 * Created by suj1 on 12/13/2016.
 */

public class Gift {
    private String giftName;
    private String recipient;
    private boolean isUpper = false;

    public Gift(String giftName, String recipient){
        this.giftName = giftName;
        this.recipient = recipient;
    }

    public void setGiftName(String giftName){
        this.giftName = giftName;
    }

    public String getGiftName(){
        return this.giftName;
    }

    public void setRecipient(String recipient){
        this.recipient = recipient;
    }

    public String getRecipient(){
        return this.recipient;
    }

    public void toUpper(){
        this.giftName = this.giftName.toUpperCase();
        this.recipient = this.recipient.toUpperCase();
        this.isUpper = true;
    }

    public boolean isUpper(){
        return this.isUpper;
    }

    public void toLower(){
        this.giftName = this.giftName.charAt(0) + this.giftName.substring(1).toLowerCase();
        this.recipient = this.recipient.charAt(0) + this.recipient.substring(1).toLowerCase();
        this.isUpper = false;
    }

    public String getString(){
        String result = this.giftName + " for " + this.recipient;
        if(this.isUpper) {
            return result.toUpperCase();
        }
        return result;
    }

}
