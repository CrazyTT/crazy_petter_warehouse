package com.crazy.petter.warehouse.app.main.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/21.
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class ConfirmObnPickBean {

    /**
     * OutboundId : sample string 1
     * details : [{"ObnPickInc":1,"SeqNo":2,"SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"ToLoc":"sample string 6","LpnNo":"sample string 7"},{"ObnPickInc":1,"SeqNo":2,"SkuId":"sample string 3","SkuName":"sample string 4","Qty":5,"ToLoc":"sample string 6","LpnNo":"sample string 7"}]
     */

    private String OutboundId;
    private ArrayList<DetailsEntity> details;

    public String getOutboundId() {
        return OutboundId;
    }

    public void setOutboundId(String OutboundId) {
        this.OutboundId = OutboundId;
    }

    public ArrayList<DetailsEntity> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<DetailsEntity> details) {
        this.details = details;
    }

    public static class DetailsEntity {
        /**
         * ObnPickInc : 1
         * SeqNo : 2
         * SkuId : sample string 3
         * SkuName : sample string 4
         * Qty : 5.0
         * ToLoc : sample string 6
         * LpnNo : sample string 7
         */

        private int ObnPickInc;
        private int SeqNo;
        private String SkuId;
        private String SkuName;
        private int Qty;
        private String ToLoc;
        private String LpnNo;

        public int getObnPickInc() {
            return ObnPickInc;
        }

        public void setObnPickInc(int ObnPickInc) {
            this.ObnPickInc = ObnPickInc;
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

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
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
