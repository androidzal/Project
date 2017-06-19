package com.fjnu.edu.helper.food;

/**
 * Created by ZhouShiqiao on 2017/5/30 0030.
 */

public class FoodSimpleInformation {
    private String mno;
    private String name;
    private String picture;
    private double coldstoragetime;
    private double freezestoragetime;
    private double roomstoragetime;

    public FoodSimpleInformation(String mno, String name, String picture, double coldstoragetime, double freezestoragetime, double roomstoragetime) {
        this.mno = mno;
        this.name = name;
        this.picture = picture;
        this.coldstoragetime = coldstoragetime;
        this.freezestoragetime = freezestoragetime;
        this.roomstoragetime = roomstoragetime;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public double getColdstoragetime() {
        return coldstoragetime;
    }

    public void setColdstoragetime(double coldstoragetime) {
        this.coldstoragetime = coldstoragetime;
    }

    public double getFreezestoragetime() {
        return freezestoragetime;
    }

    public void setFreezestoragetime(double freezestoragetime) {
        this.freezestoragetime = freezestoragetime;
    }

    public double getRoomstoragetime() {
        return roomstoragetime;
    }

    public void setRoomstoragetime(double roomstoragetime) {
        this.roomstoragetime = roomstoragetime;
    }
}
