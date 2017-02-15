package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/15.
 */

public class PickBean extends BaseBean {


    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * OutboundId : sample string 1
         * ExtDocId : sample string 2
         * OwnerId : sample string 3
         * OwnerName : sample string 4
         * OrderDate : 2017-02-15T13:49:34.7402151+08:00
         */

        public String OutboundId;
        public String ExtDocId;
        public String OwnerId;
        public String OwnerName;
        public String OrderDate;

        public String getOutboundId() {
            return OutboundId;
        }

        public void setOutboundId(String OutboundId) {
            this.OutboundId = OutboundId;
        }

        public String getExtDocId() {
            return ExtDocId;
        }

        public void setExtDocId(String ExtDocId) {
            this.ExtDocId = ExtDocId;
        }

        public String getOwnerId() {
            return OwnerId;
        }

        public void setOwnerId(String OwnerId) {
            this.OwnerId = OwnerId;
        }

        public String getOwnerName() {
            return OwnerName;
        }

        public void setOwnerName(String OwnerName) {
            this.OwnerName = OwnerName;
        }

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String OrderDate) {
            this.OrderDate = OrderDate;
        }
    }
}
