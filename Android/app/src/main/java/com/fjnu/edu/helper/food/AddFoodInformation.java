package com.fjnu.edu.helper.food;

/**
 * Created by ZhouShiqiao on 2017/6/7 0007.
 */

public class AddFoodInformation {
    private String id;
    private String name;
    private String picture;

    public AddFoodInformation(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
