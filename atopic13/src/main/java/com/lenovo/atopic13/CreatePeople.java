package com.lenovo.atopic13;

import java.util.List;

public class CreatePeople {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"userWorkId":"1","power":"100","peopleId":"1","userProductionLineId":"1","workPostId":"1","id":"4309"}]
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
         * power : 100
         * peopleId : 1
         * userProductionLineId : 1
         * workPostId : 1
         * id : 4309
         */

        private String userWorkId;
        private String power;
        private String peopleId;
        private String userProductionLineId;
        private String workPostId;
        private String id;

        public String getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(String userWorkId) {
            this.userWorkId = userWorkId;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getPeopleId() {
            return peopleId;
        }

        public void setPeopleId(String peopleId) {
            this.peopleId = peopleId;
        }

        public String getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(String userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public String getWorkPostId() {
            return workPostId;
        }

        public void setWorkPostId(String workPostId) {
            this.workPostId = workPostId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
