package com.fjnu.edu.helper.food;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZhouShiqiao on 2017/5/30 0030.
 */

public class MyFood {
    private String mno;
    private String name;
    private String position;
    private String addtime;
    private double residue;
    private String unit;
    private String image;
    private int storagetime;

    public MyFood(String mno, String name, String storage, String addtime, double residue, String unit, String image, int storagetime) {
        this.mno = mno;
        this.name = name;
        this.position = storage;
        this.addtime = addtime;
        this.residue = residue;
        this.unit = unit;
        this.image = image;
        this.storagetime = storagetime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStoragetime() {
        return storagetime;
    }

    public void setStoragetime(int storagetime) {
        this.storagetime = storagetime;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String storage) {
        this.position = storage;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getFreshness() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long between = 0;
        try {
            java.util.Date begin = dfs.parse(this.addtime);
            java.util.Date end = dfs.parse(str);
            between = (end.getTime() - begin.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        double daychange = (24 * 60 * 60 * 1000) / 1.0;
        int day = (int) Math.ceil(between / daychange);
        return storagetime-day;
    }

    public void setFreshness() {

    }

    public double getResidue() {
        return residue;
    }

    public void setResidue(double residue) {
        this.residue = residue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
