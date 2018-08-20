package com.gainwise.gaine.randompass;

/**
 * Created by gaine on 12/26/2017.
 */

public class PasswordEntry {

    String label;
    String password;
    int imageNum;

    public PasswordEntry() {

    }
    public PasswordEntry(String label, String password, int imageNum) {
        this.label = label;
        this.password = password;
        this.imageNum = imageNum;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImageNum() {
        return imageNum;
    }

    public void setImageNum(int imageNum) {
        this.imageNum = imageNum;
    }
}
