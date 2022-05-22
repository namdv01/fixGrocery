package com.example.authenfirebase.models;

import java.io.Serializable;

public class ViewAllModel implements Serializable {
    String name;
    String desc;
    String img_url;
    String type;
    String rating;
    int price;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String desc, String img_url, String type, String rating, int price) {
        this.name = name;
        this.desc = desc;
        this.img_url = img_url;
        this.type = type;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
