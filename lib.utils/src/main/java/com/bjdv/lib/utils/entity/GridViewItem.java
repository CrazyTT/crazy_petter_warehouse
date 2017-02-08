package com.bjdv.lib.utils.entity;

/**
 * Created by liuliuchen on 2016/11/19.
 */

public class GridViewItem {
    int itmeImage = 0;
    String itemText = "";

    public GridViewItem(int itmeImage, String itemText) {
        this.itmeImage = itmeImage;
        this.itemText = itemText;
    }

    public int getItmeImage() {
        return itmeImage;
    }

    public void setItmeImage(int itmeImage) {
        this.itmeImage = itmeImage;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }
}
