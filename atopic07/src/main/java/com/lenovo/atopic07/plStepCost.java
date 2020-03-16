package com.lenovo.atopic07;

import java.util.List;

/**
 * 查询全部生产环节原材料消耗
 */
public class plStepCost {
    private int status;
    private String message;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 22
         * plStepId : 22
         * partId : 8
         * num : 1
         */

        private int id;
        private int plStepId;
        private int partId;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPlStepId() {
            return plStepId;
        }

        public void setPlStepId(int plStepId) {
            this.plStepId = plStepId;
        }

        public int getPartId() {
            return partId;
        }

        public void setPartId(int partId) {
            this.partId = partId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}



