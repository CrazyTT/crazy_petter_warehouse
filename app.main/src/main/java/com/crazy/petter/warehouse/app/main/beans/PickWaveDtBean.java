package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/28.
 */

public class PickWaveDtBean extends BaseBean {

    /**
     * TotalQty : 3.0
     * TotalPickQty : 4.0
     * data : [{"OutboundId":"sample string 1","ObnSeq":2,"OwnerId":"sample string 3","OwnerName":"sample string 4","CustomerId":"sample string 5","CustomerName":"sample string 6","ExtId":"sample string 7","ContainerId":"sample string 8","SkuId":"sample string 9","SkuName":"sample string 10","PickQty":11},{"OutboundId":"sample string 1","ObnSeq":2,"OwnerId":"sample string 3","OwnerName":"sample string 4","CustomerId":"sample string 5","CustomerName":"sample string 6","ExtId":"sample string 7","ContainerId":"sample string 8","SkuId":"sample string 9","SkuName":"sample string 10","PickQty":11}]
     */

    public int TotalQty;
    public int TotalPickQty;
    public ArrayList<DataEntity> data;

    public int getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(int TotalQty) {
        this.TotalQty = TotalQty;
    }

    public int getTotalPickQty() {
        return TotalPickQty;
    }

    public void setTotalPickQty(int TotalPickQty) {
        this.TotalPickQty = TotalPickQty;
    }

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * OutboundId : sample string 1
         * ObnSeq : 2
         * OwnerId : sample string 3
         * OwnerName : sample string 4
         * CustomerId : sample string 5
         * CustomerName : sample string 6
         * ExtId : sample string 7
         * ContainerId : sample string 8
         * SkuId : sample string 9
         * SkuName : sample string 10
         * PickQty : 11.0
         */

        public String OutboundId;
        public int ObnSeq;
        public String OwnerId;
        public String OwnerName;
        public String CustomerId;
        public String CustomerName;
        public String ExtId;
        public String ContainerId;
        public String SkuId;
        public String SkuName;
        public int PickQty;

        public String getOutboundId() {
            return OutboundId;
        }

        public void setOutboundId(String OutboundId) {
            this.OutboundId = OutboundId;
        }

        public int getObnSeq() {
            return ObnSeq;
        }

        public void setObnSeq(int ObnSeq) {
            this.ObnSeq = ObnSeq;
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

        public String getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(String CustomerId) {
            this.CustomerId = CustomerId;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public String getExtId() {
            return ExtId;
        }

        public void setExtId(String ExtId) {
            this.ExtId = ExtId;
        }

        public String getContainerId() {
            return ContainerId;
        }

        public void setContainerId(String ContainerId) {
            this.ContainerId = ContainerId;
        }

        public String getSkuId() {
            return SkuId;
        }

        public void setSkuId(String SkuId) {
            this.SkuId = SkuId;
        }

        public String getSkuName() {
            return SkuName;
        }

        public void setSkuName(String SkuName) {
            this.SkuName = SkuName;
        }

        public int getPickQty() {
            return PickQty;
        }

        public void setPickQty(int PickQty) {
            this.PickQty = PickQty;
        }
    }
}
