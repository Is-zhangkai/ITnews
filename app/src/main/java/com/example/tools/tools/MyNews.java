package com.example.tools.tools;

public class MyNews {

    private int id,tag;
    private String title,img,error,no;


    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag() {
        return tag;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getNo() {
        return no;
    }

    public String getError() {
        return error;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public String getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }
}
