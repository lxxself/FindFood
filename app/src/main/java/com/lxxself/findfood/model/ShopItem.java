package com.lxxself.findfood.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lxxself on 2015/11/8.
 */
public class ShopItem extends BmobObject implements Serializable {
    private String name;
    private String address;
    private float original_latitude;
    private float original_longitude;
    private String phone;
    private float price;
    private int ratingNum;
    private String picPath;
    private String tags;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOriginal_latitude() {
        return original_latitude;
    }

    public void setOriginal_latitude(float original_latitude) {
        this.original_latitude = original_latitude;
    }

    public float getOriginal_longitude() {
        return original_longitude;
    }

    public void setOriginal_longitude(float original_longitude) {
        this.original_longitude = original_longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
