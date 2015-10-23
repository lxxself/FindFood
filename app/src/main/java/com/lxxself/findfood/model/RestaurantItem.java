package com.lxxself.findfood.model;

/**
 * Created by Administrator on 2015/10/20.
 */
public class RestaurantItem {
    private String name;
    private float price;
    private float ratingNum;
    private float distance;
    private String picPath;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
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
