package com.lxxself.findfood.model;


import cn.bmob.v3.BmobUser;

/**
 * Created by lxxself on 2015/11/6.
 */
public class FFUser extends BmobUser{
    private int sex;
    private String avatar;

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


}
