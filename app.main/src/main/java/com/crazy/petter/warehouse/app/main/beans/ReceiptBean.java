package com.crazy.petter.warehouse.app.main.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class ReceiptBean implements Serializable {

    private String InboundId;
    private String ReceiptDate;
    private ArrayList<DetailsEntity> details;

    public String getInboundId() {
        return InboundId;
    }

    public void setInboundId(String InboundId) {
        this.InboundId = InboundId;
    }

    public String getReceiptDate() {
        return ReceiptDate;
    }

    public void setReceiptDate(String ReceiptDate) {
        this.ReceiptDate = ReceiptDate;
    }

    public ArrayList<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsEntity> details) {
        this.details = details;
    }

    public static class DetailsEntity {
        /**
         * SeqNo : 1
         * SkuId : sample string 2
         * SkuName : sample string 3
         * ReceiptQty : sample string 4
         * SkuProperty : sample string 5
         * LpnNo : sample string 6
         * ExtLot : sample string 7
         * ProduceDate : 2017-02-12T13:19:19.3632414+08:00
         * ExpiredDate : 2017-02-12T13:19:19.3632414+08:00
         */

        private String SeqNo;
        private String SkuId;
        private String SkuName;
        private int ReceiptQty;
        private String SkuProperty;
        private String LpnNo;
        private String ExtLot;
        private String ProduceDate;
        private String ExpiredDate;

        public String getSeqNo() {
            return SeqNo;
        }

        public void setSeqNo(String SeqNo) {
            this.SeqNo = SeqNo;
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

        public int getReceiptQty() {
            return ReceiptQty;
        }

        public void setReceiptQty(int ReceiptQty) {
            this.ReceiptQty = ReceiptQty;
        }

        public String getSkuProperty() {
            return SkuProperty;
        }

        public void setSkuProperty(String SkuProperty) {
            this.SkuProperty = SkuProperty;
        }

        public String getLpnNo() {
            return LpnNo;
        }

        public void setLpnNo(String LpnNo) {
            this.LpnNo = LpnNo;
        }

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }

        public String getProduceDate() {
            return ProduceDate;
        }

        public void setProduceDate(String ProduceDate) {
            this.ProduceDate = ProduceDate;
        }

        public String getExpiredDate() {
            return ExpiredDate;
        }

        public void setExpiredDate(String ExpiredDate) {
            this.ExpiredDate = ExpiredDate;
        }
    }
}
