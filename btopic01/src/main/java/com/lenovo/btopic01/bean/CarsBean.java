package com.lenovo.btopic01.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class CarsBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":5810,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5806,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5805,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5803,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5801,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5800,"userFactoryId":1,"userLineId":2689,"carTypeId":2,"type":0,"num":1,"CarType":{"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}},{"id":5797,"userFactoryId":1,"userLineId":2688,"carTypeId":1,"type":0,"num":1,"CarType":{"id":1,"carTypeName":"轿车汽车","content":"轿车汽车标准型","price":2000,"size":6,"repairPrice":50,"lastUpdateTime":1575103866}},{"id":5796,"userFactoryId":1,"userLineId":2688,"carTypeId":1,"type":0,"num":1,"CarType":{"id":1,"carTypeName":"轿车汽车","content":"轿车汽车标准型","price":2000,"size":6,"repairPrice":50,"lastUpdateTime":1575103866}},{"id":5794,"userFactoryId":1,"userLineId":2688,"carTypeId":1,"type":0,"num":1,"CarType":{"id":1,"carTypeName":"轿车汽车","content":"轿车汽车标准型","price":2000,"size":6,"repairPrice":50,"lastUpdateTime":1575103866}},{"id":5791,"userFactoryId":1,"userLineId":2688,"carTypeId":1,"type":0,"num":1,"CarType":{"id":1,"carTypeName":"轿车汽车","content":"轿车汽车标准型","price":2000,"size":6,"repairPrice":50,"lastUpdateTime":1575103866}}]
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
         * id : 5810
         * userFactoryId : 1
         * userLineId : 2689
         * carTypeId : 2
         * type : 0
         * num : 1
         * CarType : {"id":2,"carTypeName":"MPV汽车","content":"MPV汽车标准型","price":3000,"size":7,"repairPrice":60,"lastUpdateTime":1568880985}
         */

        private int id;
        private int userFactoryId;
        private int userLineId;
        private int carTypeId;
        private int type;
        private int num;
        private CarTypeBean CarType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserFactoryId() {
            return userFactoryId;
        }

        public void setUserFactoryId(int userFactoryId) {
            this.userFactoryId = userFactoryId;
        }

        public int getUserLineId() {
            return userLineId;
        }

        public void setUserLineId(int userLineId) {
            this.userLineId = userLineId;
        }

        public int getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(int carTypeId) {
            this.carTypeId = carTypeId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public CarTypeBean getCarType() {
            return CarType;
        }

        public void setCarType(CarTypeBean CarType) {
            this.CarType = CarType;
        }

        public static class CarTypeBean {
            /**
             * id : 2
             * carTypeName : MPV汽车
             * content : MPV汽车标准型
             * price : 3000
             * size : 7
             * repairPrice : 60
             * lastUpdateTime : 1568880985
             */

            private int id;
            private String carTypeName;
            private String content;
            private int price;
            private int size;
            private int repairPrice;
            private int lastUpdateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCarTypeName() {
                return carTypeName;
            }

            public void setCarTypeName(String carTypeName) {
                this.carTypeName = carTypeName;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getRepairPrice() {
                return repairPrice;
            }

            public void setRepairPrice(int repairPrice) {
                this.repairPrice = repairPrice;
            }

            public int getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(int lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            @Override
            public String toString() {
                return "CarTypeBean{" +
                        "id=" + id +
                        ", carTypeName='" + carTypeName + '\'' +
                        ", content='" + content + '\'' +
                        ", price=" + price +
                        ", size=" + size +
                        ", repairPrice=" + repairPrice +
                        ", lastUpdateTime=" + lastUpdateTime +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userFactoryId=" + userFactoryId +
                    ", userLineId=" + userLineId +
                    ", carTypeId=" + carTypeId +
                    ", type=" + type +
                    ", num=" + num +
                    ", CarType=" + CarType +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CarsBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
