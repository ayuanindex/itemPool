package com.lenovo.btopic04.bean;

import android.drm.DrmStore;
import android.opengl.ETC1;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.processors.PublishProcessor;

import static android.content.ContentValues.TAG;

/**
 * @author ayuan
 */
public class OrderBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":522,"userWorkId":1,"userAppointmentName":"订单二","content":"初始订单","type":0,"carId":2,"time":1571639400,"num":1,"gold":2000,"engine":2,"speed":1,"wheel":1,"control":1,"brake":1,"hang":2,"color":1},{"id":521,"userWorkId":1,"userAppointmentName":"订单一","content":"初始订单","type":0,"carId":1,"time":1571639400,"num":1,"gold":1000,"engine":2,"speed":0,"wheel":0,"control":0,"brake":0,"hang":0,"color":0}]
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
         * id : 522
         * userWorkId : 1
         * userAppointmentName : 订单二
         * content : 初始订单
         * type : 0
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
         */

        private int id;
        private int userWorkId;
        private String userAppointmentName;
        private String content;
        private int type;
        private int carId;
        private int time;
        private int num;
        private int gold;
        private int engine;
        private int speed;
        private int wheel;
        private int control;
        private int brake;
        private int hang;
        private int color;
        private boolean isChecked = false;

        public DataBean(DataBean item) {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(int userWorkId) {
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getEngine() {
            return engine;
        }

        public void setEngine(int engine) {
            this.engine = engine;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        public int getWheel() {
            return wheel;
        }

        public void setWheel(int wheel) {
            this.wheel = wheel;
        }

        public int getControl() {
            return control;
        }

        public void setControl(int control) {
            this.control = control;
        }

        public int getBrake() {
            return brake;
        }

        public void setBrake(int brake) {
            this.brake = brake;
        }

        public int getHang() {
            return hang;
        }

        public void setHang(int hang) {
            this.hang = hang;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public HashMap<String, Object> getParameter() {
            long time = System.currentTimeMillis();
            Log.d(TAG, "getParameter: " + time);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userWorkId", 1);
            hashMap.put("userAppointmentName", this.userAppointmentName);
            hashMap.put("content", this.content);
            hashMap.put("type", 1);
            hashMap.put("carId", 1);
            hashMap.put("time", String.valueOf(time).substring(0, 10));
            hashMap.put("gold", this.gold);
            hashMap.put("engine", this.engine);
            hashMap.put("speed", this.speed);
            hashMap.put("wheel", this.wheel);
            hashMap.put("num", this.num);
            hashMap.put("control", this.control);
            hashMap.put("brake", this.brake);
            hashMap.put("hang", this.hang);
            hashMap.put("color", this.color);
            return hashMap;
        }

        public String getOrderStatus() {
            switch (this.type) {
                case 0:
                    return "已下单";
                case 1:
                    return "生产中";
                case 2:
                    return "完成";
                default:
                    return "";
            }
        }

        public String getSpeedStr() {
            return this.speed == 0 ? "自动" : "手动";
        }

        public String getWheelHub() {
            return this.wheel == 0 ? "烤漆" : "电镀";
        }

        public String getInTheControl() {
            return this.control == 0 ? "低配" : "高配";
        }

        public String getBrakeStr() {
            return this.brake == 0 ? "鼓式制动器" : "盘式制动器";
        }

        public String getSuspension() {
            switch (this.hang) {
                case 0:
                    return "独立悬挂系统";
                case 1:
                    return "主动悬挂系统";
                case 2:
                    return "横臂式悬挂系统";
                case 3:
                    return "纵臂式悬挂系统";
                case 4:
                    return "烛式悬挂系统";
                case 5:
                    return "多连杆式悬挂系统";
                case 6:
                    return "麦弗逊式悬挂系 统";
                default:
                    return "";
            }
        }

        public String getExterior() {
            switch (this.color) {
                case 0:
                    return "红色";
                case 1:
                    return "蓝色";
                case 2:
                    return "绿色";
                default:
                    return "";
            }
        }

        public String getOrderContent() {
            return "定制信息:" +
                    "发动机排量" + this.engine + "," +
                    "" + getSpeedStr() + "变速箱," +
                    "" + getWheelHub() + "轮毂," +
                    "" + getInTheControl() + "中控," +
                    "" + getBrakeStr() + "刹车," +
                    "" + getSuspension() + "," +
                    "外观" + getExterior() + "" +
                    "";
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userAppointmentName='" + userAppointmentName + '\'' +
                    ", content='" + content + '\'' +
                    ", type=" + type +
                    ", carId=" + carId +
                    ", time=" + time +
                    ", num=" + num +
                    ", gold=" + gold +
                    ", engine=" + engine +
                    ", speed=" + speed +
                    ", wheel=" + wheel +
                    ", control=" + control +
                    ", brake=" + brake +
                    ", hang=" + hang +
                    ", color=" + color +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
