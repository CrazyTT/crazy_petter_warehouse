package com.bjdv.lib.utils.entity;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/3/8.
 */

public class TitleBean {

    private ArrayList<OrderBean.CaptionEntity> mCaptionEntities;
    private ArrayList<JSONObject> orders;

    public ArrayList<OrderBean.CaptionEntity> getCaptionEntities() {
        return mCaptionEntities;
    }

    public void setCaptionEntities(ArrayList<OrderBean.CaptionEntity> captionEntities) {
        mCaptionEntities = captionEntities;
    }

    public ArrayList<JSONObject> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<JSONObject> orders) {
        this.orders = orders;
    }
}
