package com.fjnu.edu.helper.message;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class MyMessage {
    private int head;
    private String detail;
    private int raw=0;

    public MyMessage(int head, String detail) {
        this.head = head;
        this.detail = detail;
    }

    public MyMessage() {
    }

    public int gethead() {
        return this.head;
    }

    public String getdetail() {
        return this.detail;
    }

    public void sethead(int head) {
        this.head = head;
    }

    public void setdetail(String detail) {
        this.detail = detail;
    }

    public void setRaw(int raw){
        this.raw=raw;
    }

    public int getRaw(){
        return raw;
    }

}
