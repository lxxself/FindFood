package com.lxxself.findfood.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;

import static com.lxxself.findfood.util.ToastUtil.show;

/**
 * Created by Suleiman on 14-04-2015.
 */
public class VersionModel {
    public String name;

    public static final String[] data = {"Cupcake", "Donut", "Eclair",
            "Froyo", "Gingerbread", "Honeycomb",
            "Icecream Sandwich", "Jelly Bean", "Kitkat", "Lollipop"};
    public static final String tags= "下沙,美食,杭州,环境优雅,价格实惠";
    public static final List<RestaurantItem> getRsList() {
        List<RestaurantItem> list = new ArrayList<RestaurantItem>();
        for (int i = 0; i < data.length; i++) {
            RestaurantItem ri = new RestaurantItem();
            ri.setName(data[i]);
            ri.setPrice(50);
            ri.setRatingNum(4);
            ri.setPicPath(null);
            ri.setTags(tags);
            list.add(ri);
        }
        return list;
    }
    VersionModel(String name){
        this.name=name;
    }

}

