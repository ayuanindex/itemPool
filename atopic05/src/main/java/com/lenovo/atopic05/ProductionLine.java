package com.lenovo.atopic05;

import java.util.List;
/**
 * 全部生产线
 */
public class ProductionLine {
    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"productionLineName":"轿车车型生产线","content":"生产轿车汽车","carId":1},{"id":2,"productionLineName":"MPV车型生产线","content":"生产MPV汽车","carId":2},{"id":3,"productionLineName":"SUV车型生产线","content":"生产SUV汽车","carId":3}]
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
         * productionLineName : 轿车车型生产线
         * content : 生产轿车汽车
         * carId : 1
         */

        private int id;
        private String productionLineName;
        private String content;
        private int carId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductionLineName() {
            return productionLineName;
        }

        public void setProductionLineName(String productionLineName) {
            this.productionLineName = productionLineName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }
    }
}
