package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/12.
 */

public class GoodsBean extends BaseBean {
    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * InboundId : IB160612000001
         * SeqNo : 2
         * SkuId : 007233
         * SkuName : RSTPQ200-OPTIMA
         * LPN :
         * Qty : 23.0
         * ReceivedQty : 10.0
         * WaitReceiveQty : 13.0
         * ExtLot :
         * SkuProperty :
         * ShelfLifeCtrl : false
         * ShelfLifeCtrlType :
         * ShelfLifeDays : 0
         * ShelfLifeDaysIn : 0
         * ShelfLifeDaysOt : 0
         * SingleScan : false
         * PutawayRuleId :
         * LocMultiSku : false
         * LocMultiLot : false
         */

        public String InboundId;
        public String SeqNo;
        public String SkuId;
        public String SkuName;
        public String LPN;
        public int Qty;
        public int ReceivedQty;
        public int WaitReceiveQty;
        public String ExtLot;
        public String SkuProperty;
        public boolean ShelfLifeCtrl;
        public String ShelfLifeCtrlType;
        public int ShelfLifeDays;
        public int ShelfLifeDaysIn;
        public int ShelfLifeDaysOt;
        public boolean SingleScan;
        public String PutawayRuleId;
        public boolean LocMultiSku;
        public boolean LocMultiLot;

        public String getInboundId() {
            return InboundId;
        }

        public void setInboundId(String InboundId) {
            this.InboundId = InboundId;
        }

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

        public String getLPN() {
            return LPN;
        }

        public void setLPN(String LPN) {
            this.LPN = LPN;
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

        public int getWaitReceiveQty() {
            return WaitReceiveQty;
        }

        public void setWaitReceiveQty(int WaitReceiveQty) {
            this.WaitReceiveQty = WaitReceiveQty;
        }

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }

        public String getSkuProperty() {
            return SkuProperty;
        }

        public void setSkuProperty(String SkuProperty) {
            this.SkuProperty = SkuProperty;
        }

        public boolean isShelfLifeCtrl() {
            return ShelfLifeCtrl;
        }

        public void setShelfLifeCtrl(boolean ShelfLifeCtrl) {
            this.ShelfLifeCtrl = ShelfLifeCtrl;
        }

        public String getShelfLifeCtrlType() {
            return ShelfLifeCtrlType;
        }

        public void setShelfLifeCtrlType(String ShelfLifeCtrlType) {
            this.ShelfLifeCtrlType = ShelfLifeCtrlType;
        }

        public int getShelfLifeDays() {
            return ShelfLifeDays;
        }

        public void setShelfLifeDays(int ShelfLifeDays) {
            this.ShelfLifeDays = ShelfLifeDays;
        }

        public int getShelfLifeDaysIn() {
            return ShelfLifeDaysIn;
        }

        public void setShelfLifeDaysIn(int ShelfLifeDaysIn) {
            this.ShelfLifeDaysIn = ShelfLifeDaysIn;
        }

        public int getShelfLifeDaysOt() {
            return ShelfLifeDaysOt;
        }

        public void setShelfLifeDaysOt(int ShelfLifeDaysOt) {
            this.ShelfLifeDaysOt = ShelfLifeDaysOt;
        }

        public boolean isSingleScan() {
            return SingleScan;
        }

        public void setSingleScan(boolean SingleScan) {
            this.SingleScan = SingleScan;
        }

        public String getPutawayRuleId() {
            return PutawayRuleId;
        }

        public void setPutawayRuleId(String PutawayRuleId) {
            this.PutawayRuleId = PutawayRuleId;
        }

        public boolean isLocMultiSku() {
            return LocMultiSku;
        }

        public void setLocMultiSku(boolean LocMultiSku) {
            this.LocMultiSku = LocMultiSku;
        }

        public boolean isLocMultiLot() {
            return LocMultiLot;
        }

        public void setLocMultiLot(boolean LocMultiLot) {
            this.LocMultiLot = LocMultiLot;
        }
    }
}
