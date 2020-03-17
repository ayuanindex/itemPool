package com.lenovo.atopic09;

import java.util.List;

public class Bean {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"userWorkId":"1","price":"100","endPrice":"10000","time":"1000000","type":"5","id":"2906"}]
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
         * price : 100
         * endPrice : 10000
         * time : 1000000
         * type : 5
         * id : 2906
         */

        private String userWorkId;
        private String price;
        private String endPrice;
        private String time;
        private String type;
        private String id;

        public String getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(String userWorkId) {
            this.userWorkId = userWorkId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getEndPrice() {
            return endPrice;
        }

        public void setEndPrice(String endPrice) {
            this.endPrice = endPrice;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
