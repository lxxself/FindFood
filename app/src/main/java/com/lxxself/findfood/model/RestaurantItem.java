package com.lxxself.findfood.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by lxxself on 2015/10/20.
 */
public class RestaurantItem extends BmobObject{
    private String name;
    private float price;
    private float ratingNum;
    private String picPath;
    private String address;
    private float latitude;
    private float longitude;
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(float ratingNum) {
        this.ratingNum = ratingNum;
    }
}
