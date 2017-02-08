package com.bjdv.lib.utils.entity;

/**
 * Title: <br>
 * Description: <br>
 * Date: 16/5/31 <br>
 * Copyright (c) 2015 DATANG BJDV<br>
 *
 * @author phoon-think
 */
public class Order {
    private String orderTitle;
    private String sendTime;
    private String content;

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderTitle='" + orderTitle + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
