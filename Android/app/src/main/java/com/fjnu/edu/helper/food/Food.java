package com.fjnu.edu.helper.food;

/**
 * Created by Administrator on 2017/6/13 0013.
 */

public class Food {
    private String mno;
    private String name;
    private int storage1;
    private int storage2;
    private int storage3;

    public Food(String mno, String name, int storage1, int storage2, int storage3) {
        this.mno = mno;
        this.name = name;
        this.storage1 = storage1;
        this.storage2 = storage2;
        this.storage3 = storage3;
    }

    public String getMno() {
        return mno;
    }

    public void setMno(String mno) {
        this.mno = mno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStorage1() {
        return storage1;
    }

    public void setStorage1(int storage1) {
        this.storage1 = storage1;
    }

    public int getStorage2() {
        return storage2;
    }

    public void setStorage2(int storage2) {
        this.storage2 = storage2;
    }

    public int getStorage3() {
        return storage3;
    }

    public void setStorage3(int storage3) {
        this.storage3 = storage3;
    }
}
