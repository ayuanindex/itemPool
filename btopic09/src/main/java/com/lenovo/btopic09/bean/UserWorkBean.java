package com.lenovo.btopic09.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class UserWorkBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":"1","partCapacity":100,"carCapacity":50000,"price":10000}]
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
         * partCapacity : 100
         * carCapacity : 50000
         * price : 10000
         */

        private String id;
        private int partCapacity;
        private int carCapacity;
        private int price;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPartCapacity() {
            return partCapacity;
        }

        public void setPartCapacity(int partCapacity) {
            this.partCapacity = partCapacity;
        }

        public int getCarCapacity() {
            return carCapacity;
        }

        public void setCarCapacity(int carCapacity) {
            this.carCapacity = carCapacity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", partCapacity=" + partCapacity +
                    ", carCapacity=" + carCapacity +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserWorkBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
