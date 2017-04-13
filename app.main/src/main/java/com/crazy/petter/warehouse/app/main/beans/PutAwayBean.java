package com.crazy.petter.warehouse.app.main.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class PutAwayBean implements Serializable {


    /**
     * InboundId : sample string 1
     * details : [{"IbnReceiveInc":1,"SeqNo":2,"SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"ToLoc":"sample string 6","LpnNo":"sample string 7"},{"IbnReceiveInc":1,"SeqNo":2,"SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"ToLoc":"sample string 6","LpnNo":"sample string 7"}]
     */

    private String InboundId;
    private ArrayList<DetailsEntity> details;

    public String getInboundId() {
        return InboundId;
    }

    public void setInboundId(String InboundId) {
        this.InboundId = InboundId;
    }

    public ArrayList<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsEntity> details) {
        this.details = details;
    }

    public static class DetailsEntity {
        /**
         * IbnReceiveInc : 1
         * SeqNo : 2
         * SkuId : sample string 3
         * SkuName : sample string 4
         * Qty : 5.0
         * ToLoc : sample string 6
         * LpnNo : sample string 7
         */

        private int IbnReceiveInc;
        private int SeqNo;
        private String SkuId;
        private String SkuName;
        private double Qty;
        private String ToLoc;
        private String LpnNo;

        public int getIbnReceiveInc() {
            return IbnReceiveInc;
        }

        public void setIbnReceiveInc(int IbnReceiveInc) {
            this.IbnReceiveInc = IbnReceiveInc;
        }

        public int getSeqNo() {
            return SeqNo;
        }

        public void setSeqNo(int SeqNo) {
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

        public double getQty() {
            return Qty;
        }

        public void setQty(double Qty) {
            this.Qty = Qty;
        }

        public String getToLoc() {
            return ToLoc;
        }

        public void setToLoc(String ToLoc) {
            this.ToLoc = ToLoc;
        }

        public String getLpnNo() {
            return LpnNo;
        }

        public void setLpnNo(String LpnNo) {
            this.LpnNo = LpnNo;
        }
    }
}
