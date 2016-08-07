package com.jscheng.rssmvpapplication.model;

import java.io.Serializable;

/**
 * Created by cheng on 16-7-24.
 */
public class RssInfo implements Serializable{

    private String title;
    private String description;
    private String link;
    private String category;
    private String pubdate;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String toString() {
        return  "title: "+getTitle()+"\n link:"+getLink()+
                "\n Img: "+getImg();
    }
}
