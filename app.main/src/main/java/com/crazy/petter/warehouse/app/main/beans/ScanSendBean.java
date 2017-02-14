package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/11.
 */

public class ScanSendBean extends BaseBean {


    private ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable {
        private static final long serialVersionUID = 2046605496826333213L;
        /**
         * InboundId : sample string 1
         * ExtDocId : sample string 2
         * OwnerId : sample string 3
         * OwnerName : sample string 4
         * OrderDate : 2017-02-11T14:26:50.0429006+08:00
         */
        public String OutboundId;
        public String ExtDocId;
        public String OwnerId;
        public String OwnerName;
        public String OrderDate;

        public String getOutboundId() {
            return OutboundId;
        }

        public void setOutboundId(String outboundId) {
            OutboundId = outboundId;
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
