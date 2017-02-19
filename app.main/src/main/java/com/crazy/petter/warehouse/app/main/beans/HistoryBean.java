package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/19.
 */

public class HistoryBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * InboundId : sample string 1
         * SeqNo : 2
         * SkuId : sample string 3
         * SkuName : sample string 4
         * Qty : 5.0
         * ReceivedQty : 6.0
         * ExtLot : sample string 7
         */

        public String InboundId;
        public int SeqNo;
        public String SkuId;
        public String SkuName;
        public int Qty;
        public int ReceivedQty;
        public String ExtLot;

        public String getInboundId() {
            return InboundId;
        }

        public void setInboundId(String InboundId) {
            this.InboundId = InboundId;
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

        public int getReceivedQty() {
            return ReceivedQty;
        }

        public void setReceivedQty(int ReceivedQty) {
            this.ReceivedQty = ReceivedQty;
        }

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }
    }
}
