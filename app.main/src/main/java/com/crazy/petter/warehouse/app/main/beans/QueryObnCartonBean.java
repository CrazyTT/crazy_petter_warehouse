package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class QueryObnCartonBean extends BaseBean {

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
         * Status : 5
         * StatusDesc : sample string 6
         * OrderDate : 2017-02-21T21:12:42.2721492+08:00
         */

        public String OutboundId;
        public String ExtDocId;
        public String OwnerId;
        public String OwnerName;
        public int Status;
        public String StatusDesc;
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

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getStatusDesc() {
            return StatusDesc;
        }

        public void setStatusDesc(String StatusDesc) {
            this.StatusDesc = StatusDesc;
        }

        public String getOrderDate() {
            return OrderDate;
        }

        public void setOrderDate(String OrderDate) {
            this.OrderDate = OrderDate;
        }
    }
}
