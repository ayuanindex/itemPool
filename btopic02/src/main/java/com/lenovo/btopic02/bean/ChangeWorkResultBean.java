package com.lenovo.btopic02.bean;

/**
 * @author ayuan
 */
public class ChangeWorkResultBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":4781,"userWorkId":1,"power":100,"peopleId":23,"userProductionLineId":2688,"workPostId":"2"}
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
         * id : 4781
         * userWorkId : 1
         * power : 100
         * peopleId : 23
         * userProductionLineId : 2688
         * workPostId : 2
         */

        private int id;
        private int userWorkId;
        private int power;
        private int peopleId;
        private int userProductionLineId;
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

        public int getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(int userProductionLineId) {
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
                    ", userProductionLineId=" + userProductionLineId +
                    ", workPostId='" + workPostId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ChangeWorkResultBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
