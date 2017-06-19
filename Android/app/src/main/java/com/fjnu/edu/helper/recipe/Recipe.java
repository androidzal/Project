package com.fjnu.edu.helper.recipe;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ZhouShiqiao on 2017/6/18 0018.
 */

public class Recipe {
    private String rno;
    private String rname;
    private String preprationtime;
    private String maketime;
    private String mealsnumbers;
    private String remark;
    private String introduction;
    private String picture;
    private ArrayList<Map<String, String>> steps;
    private ArrayList<Map<String, String>> materials;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getPreprationtime() {
        return preprationtime;
    }

    public void setPreprationtime(String preprationtime) {
        this.preprationtime = preprationtime;
    }

    public String getMaketime() {
        return maketime;
    }

    public void setMaketime(String maketime) {
        this.maketime = maketime;
    }

    public String getMealsnumbers() {
        return mealsnumbers;
    }

    public void setMealsnumbers(String mealsnumbers) {
        this.mealsnumbers = mealsnumbers;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<Map<String, String>> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Map<String, String>> steps) {
        this.steps = steps;
    }

    public ArrayList<Map<String, String>> getMaterials() {
        return materials;
    }

    public void setMaterials(ArrayList<Map<String, String>> materials) {
        this.materials = materials;
    }
}
