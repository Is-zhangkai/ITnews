package com.example.tools.tools;

import java.util.List;

public class Data {
    /**
     * pics : ["http://122.9.2.27/media/up_image/image-20210303144726687.png","http://122.9.2.27/media/up_image/image-20210303144658311.png","http://122.9.2.27/media/up_image/image-20210303144658311.png","http://122.9.2.27/media/up_image/0.jpg"]
     */
    //轮播图
    private List<String> pics;

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<String> getPics() {
        return pics;
    }
    //新闻
    private String title,writer;


    public void setTitle(String title) {
        this.title = title;
    }
    public void setWriter(String writer) {
        this.writer = writer;
    }


    public String getTitle() {
        return title;
    }
    public String getWriter() {
        return writer;
    }
}
