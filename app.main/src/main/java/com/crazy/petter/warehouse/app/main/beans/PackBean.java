package com.crazy.petter.warehouse.app.main.beans;

import com.bjdv.lib.utils.base.BaseBean;

import java.util.ArrayList;

/**
 * Created by liuliuchen on 2017/2/21.
 */

public class PackBean extends BaseBean {

    public ArrayList<DataEntity> data;

    public ArrayList<DataEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DataEntity> data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * CartonTypeId : sample string 1
         * CartonTypeDesc : sample string 2
         * Length : 3.0
         * Width : 4.0
         * Height : 5.0
         * Weight : 6.0
         * Volume : 7.0
         */

        public String CartonTypeId;
        public String CartonTypeDesc;
        public int Length;
        public int Width;
        public int Height;
        public int Weight;
        public double Volume;

        public String getCartonTypeId() {
            return CartonTypeId;
        }

        public void setCartonTypeId(String CartonTypeId) {
            this.CartonTypeId = CartonTypeId;
        }

        public String getCartonTypeDesc() {
            return CartonTypeDesc;
        }

        public void setCartonTypeDesc(String CartonTypeDesc) {
            this.CartonTypeDesc = CartonTypeDesc;
        }

        public int getLength() {
            return Length;
        }

        public void setLength(int Length) {
            this.Length = Length;
        }

        public int getWidth() {
            return Width;
        }

        public void setWidth(int Width) {
            this.Width = Width;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public int getWeight() {
            return Weight;
        }

        public void setWeight(int Weight) {
            this.Weight = Weight;
        }

        public double getVolume() {
            return Volume;
        }

        public void setVolume(double Volume) {
            this.Volume = Volume;
        }
    }
}
