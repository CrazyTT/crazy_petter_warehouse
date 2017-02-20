package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/20.
 */

public class PickWaveBean extends BaseBean {


    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * WaveDocId : sample string 1
         * Status : 2
         * StatusDesc : sample string 3
         * TotalCount : 4
         */

        public String WaveDocId;
        public int Status;
        public String StatusDesc;
        public int TotalCount;

        public String getWaveDocId() {
            return WaveDocId;
        }

        public void setWaveDocId(String WaveDocId) {
            this.WaveDocId = WaveDocId;
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

        public int getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(int TotalCount) {
            this.TotalCount = TotalCount;
        }
    }
}
