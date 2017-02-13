package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/13.
 */

public class GoodsPutAwayBean extends BaseBean {

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
         * IbnReceiveInc : 2
         * SeqNo : 3
         * SkuId : sample string 4
         * SkuName : sample string 5
         * LpnNo : sample string 6
         * ProduceDate : 2017-02-13T13:26:22.9132529+08:00
         * ExtLot : sample string 7
         * Qty : 8.0
         * PutAwayQty : 9.0
         * WaitPutAwayQty : 10.0
         * SkuProperty : sample string 11
         * SkuPropertyDesc : sample string 12
         * ShelfLifeCtrl : true
         * ShelfLifeCtrlType : sample string 14
         * ShelfLifeDays : 15
         * ShelfLifeDaysIn : 16
         * ShelfLifeDaysOt : 17
         * SingleScan : true
         * PutawayRuleId : sample string 19
         * LocMultiSku : true
         * LocMultiLot : true
         */

        public String InboundId;
        public int IbnReceiveInc;
        public int SeqNo;
        public String SkuId;
        public String SkuName;
        public String LpnNo;
        public String ProduceDate;
        public String ExtLot;
        public int Qty;
        public int PutAwayQty;
        public int WaitPutAwayQty;
        public String SkuProperty;
        public String SkuPropertyDesc;
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

        public String getLpnNo() {
            return LpnNo;
        }

        public void setLpnNo(String LpnNo) {
            this.LpnNo = LpnNo;
        }

        public String getProduceDate() {
            return ProduceDate;
        }

        public void setProduceDate(String ProduceDate) {
            this.ProduceDate = ProduceDate;
        }

        public String getExtLot() {
            return ExtLot;
        }

        public void setExtLot(String ExtLot) {
            this.ExtLot = ExtLot;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public int getPutAwayQty() {
            return PutAwayQty;
        }

        public void setPutAwayQty(int PutAwayQty) {
            this.PutAwayQty = PutAwayQty;
        }

        public int getWaitPutAwayQty() {
            return WaitPutAwayQty;
        }

        public void setWaitPutAwayQty(int WaitPutAwayQty) {
            this.WaitPutAwayQty = WaitPutAwayQty;
        }

        public String getSkuProperty() {
            return SkuProperty;
        }

        public void setSkuProperty(String SkuProperty) {
            this.SkuProperty = SkuProperty;
        }

        public String getSkuPropertyDesc() {
            return SkuPropertyDesc;
        }

        public void setSkuPropertyDesc(String SkuPropertyDesc) {
            this.SkuPropertyDesc = SkuPropertyDesc;
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
