package com.lenovo.btopic13.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class ProductionLineBean {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2690,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":10,"isAI":0},{"id":2689,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":3,"isAI":1},{"id":2688,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":2,"isAI":0},{"id":2691,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":0,"isAI":0},{"id":2692,"userWorkId":1,"stageId":5,"productionLineId":2,"type":0,"position":1,"isAI":0},{"id":2693,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":12,"isAI":0},{"id":2694,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":13,"isAI":0},{"id":2695,"userWorkId":1,"stageId":45,"productionLineId":3,"type":0,"position":14,"isAI":0},{"id":2696,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":4,"isAI":0},{"id":2697,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":7,"isAI":0},{"id":2698,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":6,"isAI":0},{"id":2699,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":5,"isAI":0},{"id":2700,"userWorkId":1,"stageId":45,"productionLineId":3,"type":0,"position":8,"isAI":0},{"id":2701,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":9,"isAI":0},{"id":2702,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":11,"isAI":0},{"id":2703,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":30,"isAI":0}]
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
         * id : 2690
         * userWorkId : 1
         * stageId : 25
         * productionLineId : 1
         * type : 0
         * position : 10
         * isAI : 0
         */

        private int id;
        private int userWorkId;
        private int stageId;
        private int productionLineId;
        private int type;
        private int position;
        private String isAI;
        private int pageStatus;

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

        public int getStageId() {
            return stageId;
        }

        public void setStageId(int stageId) {
            this.stageId = stageId;
        }

        public int getProductionLineId() {
            return productionLineId;
        }

        public void setProductionLineId(int productionLineId) {
            this.productionLineId = productionLineId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getIsAI() {
            return isAI;
        }

        public void setIsAI(String isAI) {
            this.isAI = isAI;
        }

        public int getPageStatus() {
            return pageStatus;
        }

        public void setPageStatus(int pageStatus) {
            this.pageStatus = pageStatus;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", stageId=" + stageId +
                    ", productionLineId=" + productionLineId +
                    ", type=" + type +
                    ", position=" + position +
                    ", isAI=" + isAI +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductionLineBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
