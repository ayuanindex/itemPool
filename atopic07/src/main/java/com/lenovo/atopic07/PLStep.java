package com.lenovo.atopic07;

import java.util.List;

/**
 * 查询全部生产环节
 */
public class PLStep {



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
         * id : 5
         * plStepName : MPV车型生产环节1
         * step : 1
         * power : 100
         * consume : 30
         * icon : line02_01
         */

        private int id;
        private String plStepName;
        private int step;
        private int power;
        private int consume;
        private String icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlStepName() {
            return plStepName;
        }

        public void setPlStepName(String plStepName) {
            this.plStepName = plStepName;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public int getConsume() {
            return consume;
        }

        public void setConsume(int consume) {
            this.consume = consume;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
