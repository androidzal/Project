package com.fjnu.edu.recipe;

public class SimpleRecipe {
	private String rno;
    private String rname;
    private String introduction;
    private String picture;

    public SimpleRecipe(){

    }

    public SimpleRecipe(String rno, String rname, String introduction, String picture) {
        this.rno = rno;
        this.rname = rname;
        this.introduction = introduction;
        this.picture = picture;
    }

    public String getRno() {
        return rno;
    }

    public void setRno(String rno) {
        this.rno = rno;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
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
}
