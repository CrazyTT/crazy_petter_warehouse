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
        public double Length;
        public double Width;
        public double Height;
        public double Weight;
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

        public double getLength() {
            return Length;
        }

        public void setLength(double Length) {
            this.Length = Length;
        }

        public double getWidth() {
            return Width;
        }

        public void setWidth(double Width) {
            this.Width = Width;
        }

        public double getHeight() {
            return Height;
        }

        public void setHeight(double Height) {
            this.Height = Height;
        }

        public double getWeight() {
            return Weight;
        }

        public void setWeight(double Weight) {
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
