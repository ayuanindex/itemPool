package com.lenovo.btopic02.bean;

/**
 * @author ayuan
 */
public class ChangeLineResultBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":4742,"userWorkId":1,"power":40,"peopleId":23,"userProductionLineId":"2691","workPostId":7}
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
         * id : 4742
         * userWorkId : 1
         * power : 40
         * peopleId : 23
         * userProductionLineId : 2691
         * workPostId : 7
         */

        private int id;
        private int userWorkId;
        private int power;
        private int peopleId;
        private String userProductionLineId;
        private String workPostId;

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

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public int getPeopleId() {
            return peopleId;
        }

        public void setPeopleId(int peopleId) {
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", power=" + power +
                    ", peopleId=" + peopleId +
                    ", userProductionLineId='" + userProductionLineId + '\'' +
                    ", workPostId=" + workPostId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChangeLineResultBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
