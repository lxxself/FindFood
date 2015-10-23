package com.lxxself.findfood.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class VersionModel {
    public String name;

    public static final String[] data = {"Cupcake", "Donut", "Eclair",
            "Froyo", "Gingerbread", "Honeycomb",
            "Icecream Sandwich", "Jelly Bean", "Kitkat", "Lollipop"};
    public static final List<RestaurantItem> getRsList() {
        List<RestaurantItem> list = new ArrayList<RestaurantItem>();
        for (int i = 0; i < data.length; i++) {
            RestaurantItem ri = new RestaurantItem();
            ri.setName(data[i]);
            ri.setPrice(50);
            ri.setRatingNum(4);
            ri.setDistance(1.5f);
            ri.setPicPath(null);
            list.add(ri);
        }
        return list;
    }
    VersionModel(String name){
        this.name=name;
    }
}

