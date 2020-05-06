package com.lenovo.btopic04.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class ResultDataBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"userWorkId":"1","userAppointmentName":"订单二副本二","content":"重下订单","type":"1","carId":"2","time":"1571639400","num":"1","gold":"2000","engine":"2","speed":"1","wheel":"1","control":"1","brake":"1","hang":"2","color":"1","id":"524"}]
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
         * userWorkId : 1
         * userAppointmentName : 订单二副本二
         * content : 重下订单
         * type : 1
         * carId : 2
         * time : 1571639400
         * num : 1
         * gold : 2000
         * engine : 2
         * speed : 1
         * wheel : 1
         * control : 1
         * brake : 1
         * hang : 2
         * color : 1
         * id : 524
         */

        private String userWorkId;
        private String userAppointmentName;
        private String content;
        private String type;
        private String carId;
        private String time;
        private String num;
        private String gold;
        private String engine;
        private String speed;
        private String wheel;
        private String control;
        private String brake;
        private String hang;
        private String color;
        private String id;

        public String getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(String userWorkId) {
            this.userWorkId = userWorkId;
        }

        public String getUserAppointmentName() {
            return userAppointmentName;
        }

        public void setUserAppointmentName(String userAppointmentName) {
            this.userAppointmentName = userAppointmentName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCarId() {
            return carId;
        }

        public void setCarId(String carId) {
            this.carId = carId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getGold() {
            return gold;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getWheel() {
            return wheel;
        }

        public void setWheel(String wheel) {
            this.wheel = wheel;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getBrake() {
            return brake;
        }

        public void setBrake(String brake) {
            this.brake = brake;
        }

        public String getHang() {
            return hang;
        }

        public void setHang(String hang) {
            this.hang = hang;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "userWorkId='" + userWorkId + '\'' +
                    ", userAppointmentName='" + userAppointmentName + '\'' +
                    ", content='" + content + '\'' +
                    ", type='" + type + '\'' +
                    ", carId='" + carId + '\'' +
                    ", time='" + time + '\'' +
                    ", num='" + num + '\'' +
                    ", gold='" + gold + '\'' +
                    ", engine='" + engine + '\'' +
                    ", speed='" + speed + '\'' +
                    ", wheel='" + wheel + '\'' +
                    ", control='" + control + '\'' +
                    ", brake='" + brake + '\'' +
                    ", hang='" + hang + '\'' +
                    ", color='" + color + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultDataBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
