package com.lenovo.btopic02.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class StudentStaffBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":4742,"userWorkId":1,"power":40,"peopleId":23,"userProductionLineId":2689,"workPostId":7},{"id":4741,"userWorkId":1,"power":100,"peopleId":25,"userProductionLineId":2689,"workPostId":5},{"id":4740,"userWorkId":1,"power":100,"peopleId":22,"userProductionLineId":2689,"workPostId":""},{"id":4739,"userWorkId":1,"power":50,"peopleId":17,"userProductionLineId":2689,"workPostId":""},{"id":4738,"userWorkId":1,"power":100,"peopleId":15,"userProductionLineId":2689,"workPostId":""},{"id":4737,"userWorkId":1,"power":100,"peopleId":16,"userProductionLineId":2689,"workPostId":""},{"id":4736,"userWorkId":1,"power":100,"peopleId":4,"userProductionLineId":2688,"workPostId":4},{"id":4735,"userWorkId":1,"power":100,"peopleId":3,"userProductionLineId":2688,"workPostId":3},{"id":4734,"userWorkId":1,"power":20,"peopleId":2,"userProductionLineId":2688,"workPostId":2},{"id":4733,"userWorkId":1,"power":100,"peopleId":1,"userProductionLineId":2688,"workPostId":1}]
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
         * id : 4742
         * userWorkId : 1
         * power : 40
         * peopleId : 23
         * userProductionLineId : 2689
         * workPostId : 7
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
                    ", workPostId=" + workPostId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StudentStaffBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
