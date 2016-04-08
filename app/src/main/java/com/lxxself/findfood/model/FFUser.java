package com.lxxself.findfood.model;


import cn.bmob.v3.BmobUser;

/**
 * Created by lxxself on 2015/11/6.
 */
public class FFUser extends BmobUser {
    private int sex;
    private String avatar;
    private boolean isSeller;
    private ShopItem shopItem;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }

    public void setShopItem(ShopItem shopItem) {
        this.shopItem = shopItem;
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setIsSeller(boolean isSeller) {
        this.isSeller = isSeller;
    }
}
