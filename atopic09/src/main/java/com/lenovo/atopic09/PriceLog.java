package com.lenovo.atopic09;

import android.icu.text.PluralRules;

import java.util.List;

public class PriceLog {
    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2842,"userWorkId":1,"price":1000,"endPrice":15500,"time":"1576652100","type":5},{"id":2841,"userWorkId":1,"price":4500,"endPrice":14500,"time":"1576651500","type":5},{"id":2840,"userWorkId":1,"price":800,"endPrice":10000,"time":"1576650900","type":5},{"id":2839,"userWorkId":1,"price":2000,"endPrice":9200,"time":"1576650600","type":5}]
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
         * id : 2842
         * userWorkId : 1
         * price : 1000
         * endPrice : 15500
         * time : 1576652100
         * type : 5
         */

        private int id;
        private int userWorkId;
        private int price;
        private int endPrice;
        private String time;
        private int type;
        private String typeStr;
        private String timeStr;

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(int endPrice) {
            this.endPrice = endPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
