package com.lenovo.btopic13.bean;

/**
 * @author ayuan
 */
public class SwitchAiResultBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":2690,"userWorkId":1,"stageId":25,"productionLineId":1,"type":0,"position":10,"isAI":"1"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
         * isAI : 1
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
                    ", isAI='" + isAI + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SwitchAiResultBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
