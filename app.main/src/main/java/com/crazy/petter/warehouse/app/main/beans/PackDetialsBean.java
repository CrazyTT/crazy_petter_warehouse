package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class PackDetialsBean extends BaseBean {

    /**
     * TotalPackageQty : 3.0
     * TotalPickedQty : 4.0
     * data : [{"OutboundId":"sample string 1","ObnSeq":"sample string 2","SkuId":"sample string 3","SkuName":"sample string 4","PackageQty":5,"PickedQty":6,"TotalGrossWeight":7,"Remark":"sample string 8"},{"OutboundId":"sample string 1","ObnSeq":"sample string 2","SkuId":"sample string 3","SkuName":"sample string 4","PackageQty":5,"PickedQty":6,"TotalGrossWeight":7,"Remark":"sample string 8"}]
     */

    public int TotalPackageQty;
    public int TotalPickedQty;
    public ArrayList<DataEntity> data;

    public int getTotalPackageQty() {
        return TotalPackageQty;
    }

    public void setTotalPackageQty(int TotalPackageQty) {
        this.TotalPackageQty = TotalPackageQty;
    }

    public int getTotalPickedQty() {
        return TotalPickedQty;
    }

    public void setTotalPickedQty(int TotalPickedQty) {
        this.TotalPickedQty = TotalPickedQty;
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
         * ObnSeq : sample string 2
         * SkuId : sample string 3
         * SkuName : sample string 4
         * PackageQty : 5.0
         * PickedQty : 6.0
         * TotalGrossWeight : 7.0
         * Remark : sample string 8
         */

        public String OutboundId;
        public String ObnSeq;
        public String SkuId;
        public String SkuName;
        public int PackageQty;
        public int PickedQty;
        public double TotalGrossWeight;
        public String Remark;

        public String getOutboundId() {
            return OutboundId;
        }

        public void setOutboundId(String OutboundId) {
            this.OutboundId = OutboundId;
        }

        public String getObnSeq() {
            return ObnSeq;
        }

        public void setObnSeq(String ObnSeq) {
            this.ObnSeq = ObnSeq;
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

        public int getPackageQty() {
            return PackageQty;
        }

        public void setPackageQty(int PackageQty) {
            this.PackageQty = PackageQty;
        }

        public int getPickedQty() {
            return PickedQty;
        }

        public void setPickedQty(int PickedQty) {
            this.PickedQty = PickedQty;
        }

        public double getTotalGrossWeight() {
            return TotalGrossWeight;
        }

        public void setTotalGrossWeight(double TotalGrossWeight) {
            this.TotalGrossWeight = TotalGrossWeight;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }
    }
}
