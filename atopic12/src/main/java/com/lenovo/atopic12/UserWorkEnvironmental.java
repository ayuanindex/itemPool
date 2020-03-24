package com.lenovo.atopic12;

import java.util.List;

/**
 * 工厂环境数据
 */
public class UserWorkEnvironmental {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":"1","day":"0","lightSwitch":0,
     * "acOnOff":0,"beam":"245","workshopTemp":"36℃","
     * outTemp":"36℃","power":"110","powerConsume":"15","time":"13:30"}]
     */

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
         * id : 1
         * day : 0
         * lightSwitch : 0
         * acOnOff : 0
         * beam : 245
         * workshopTemp : 36℃
         * outTemp : 36℃
         * power : 110
         * powerConsume : 15
         * time : 13:30
         */

        private String id;
        private String day;
        private int lightSwitch;
        private int acOnOff;
        private String beam;
        private String workshopTemp;
        private String outTemp;
        private String power;
        private String powerConsume;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public int getLightSwitch() {
            return lightSwitch;
        }

        public void setLightSwitch(int lightSwitch) {
            this.lightSwitch = lightSwitch;
        }

        public int getAcOnOff() {
            return acOnOff;
        }

        public void setAcOnOff(int acOnOff) {
            this.acOnOff = acOnOff;
        }

        public String getBeam() {
            return beam;
        }

        public void setBeam(String beam) {
            this.beam = beam;
        }

        public String getWorkshopTemp() {
            return workshopTemp;
        }

        public void setWorkshopTemp(String workshopTemp) {
            this.workshopTemp = workshopTemp;
        }

        public String getOutTemp() {
            return outTemp;
        }

        public void setOutTemp(String outTemp) {
            this.outTemp = outTemp;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getPowerConsume() {
            return powerConsume;
        }

        public void setPowerConsume(String powerConsume) {
            this.powerConsume = powerConsume;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
