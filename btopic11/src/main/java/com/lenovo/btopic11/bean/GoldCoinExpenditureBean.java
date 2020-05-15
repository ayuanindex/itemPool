package com.lenovo.btopic11.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class GoldCoinExpenditureBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":3686,"userWorkId":1,"price":2000,"endPrice":10000,"time":1576657800,"type":2},{"id":3685,"userWorkId":1,"price":3000,"endPrice":12000,"time":1576656900,"type":2},{"id":3684,"userWorkId":1,"price":5000,"endPrice":15000,"time":1576655700,"type":1},{"id":3683,"userWorkId":1,"price":6000,"endPrice":20000,"time":1576652700,"type":0},{"id":3678,"userWorkId":1,"price":4000,"endPrice":11000,"time":1576649700,"type":0},{"id":3677,"userWorkId":1,"price":3000,"endPrice":15000,"time":1576649400,"type":0},{"id":3676,"userWorkId":1,"price":2000,"endPrice":18000,"time":1576648800,"type":0},{"id":3675,"userWorkId":1,"price":3000,"endPrice":20000,"time":1576648500,"type":0},{"id":3674,"userWorkId":1,"price":500,"endPrice":23000,"time":1576647900,"type":4},{"id":3673,"userWorkId":1,"price":500,"endPrice":23500,"time":1576647600,"type":4},{"id":3672,"userWorkId":1,"price":1000,"endPrice":24000,"time":1576647000,"type":4},{"id":3671,"userWorkId":1,"price":1000,"endPrice":24500,"time":1576646100,"type":4},{"id":3670,"userWorkId":1,"price":1000,"endPrice":25000,"time":1576646400,"type":4},{"id":3669,"userWorkId":1,"price":1000,"endPrice":26000,"time":1576645800,"type":4},{"id":3668,"userWorkId":1,"price":1000,"endPrice":27000,"time":1576644900,"type":4},{"id":3667,"userWorkId":1,"price":2000,"endPrice":28000,"time":1576643700,"type":0},{"id":3666,"userWorkId":1,"price":3000,"endPrice":30000,"time":1576642800,"type":0},{"id":3665,"userWorkId":1,"price":1500,"endPrice":33000,"time":1576641600,"type":0},{"id":3664,"userWorkId":1,"price":500,"endPrice":34500,"time":1576641300,"type":3},{"id":3663,"userWorkId":1,"price":1500,"endPrice":33000,"time":1576640700,"type":3},{"id":3662,"userWorkId":1,"price":500,"endPrice":34500,"time":1576640520,"type":3},{"id":3661,"userWorkId":1,"price":6000,"endPrice":35000,"time":1576640400,"type":2},{"id":3660,"userWorkId":1,"price":1000,"endPrice":42000,"time":1576639800,"type":2},{"id":3659,"userWorkId":1,"price":2000,"endPrice":43000,"time":1576638720,"type":1},{"id":3658,"userWorkId":1,"price":5000,"endPrice":45000,"time":1576635000,"type":0}]
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
         * id : 3686
         * userWorkId : 1
         * price : 2000
         * endPrice : 10000
         * time : 1576657800
         * type : 2
         */

        private int id;
        private int userWorkId;
        private int price;
        private int endPrice;
        private int time;
        private int type;

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

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", price=" + price +
                    ", endPrice=" + endPrice +
                    ", time=" + time +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GoldCoinExpenditureBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
