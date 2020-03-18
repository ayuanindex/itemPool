package com.lenovo.atopic10;

import java.io.PipedReader;
import java.util.List;

public class Bean {
    private int lineId;
    private boolean ai;
    private List<Data> datas;

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }

    public static class Data {
        private String pratName;
        private int num;
        private int area;
        private String iconName;

        public String getIconName() {
            return iconName;
        }

        public void setIconName(String iconName) {
            this.iconName = iconName;
        }

        public String getPratName() {
            return pratName;
        }

        public void setPratName(String pratName) {
            this.pratName = pratName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }
    }

}
