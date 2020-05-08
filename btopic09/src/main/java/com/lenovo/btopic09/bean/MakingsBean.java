package com.lenovo.btopic09.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class MakingsBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":10,"userWorkId":1,"userProductionLineId":2690,"userStageId":12,"PartId":17,"num":2,"time":1588908607}]
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
         * id : 10
         * userWorkId : 1
         * userProductionLineId : 2690
         * userStageId : 12
         * PartId : 17
         * num : 2
         * time : 1588908607
         */

        private int id;
        private int userWorkId;
        private int userProductionLineId;
        private int userStageId;
        private int PartId;
        private int num;
        private int time;

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

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public int getUserStageId() {
            return userStageId;
        }

        public void setUserStageId(int userStageId) {
            this.userStageId = userStageId;
        }

        public int getPartId() {
            return PartId;
        }

        public void setPartId(int PartId) {
            this.PartId = PartId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", userStageId=" + userStageId +
                    ", PartId=" + PartId +
                    ", num=" + num +
                    ", time=" + time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MakingsBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
