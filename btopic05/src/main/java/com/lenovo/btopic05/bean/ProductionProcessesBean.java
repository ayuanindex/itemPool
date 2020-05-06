package com.lenovo.btopic05.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class ProductionProcessesBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":25268,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":-1},{"id":25267,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":24},{"id":25266,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":23},{"id":25265,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":22},{"id":25264,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":21},{"id":25263,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":20},{"id":25262,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":19},{"id":25261,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":18},{"id":25260,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":17},{"id":25259,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":16},{"id":25258,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":15},{"id":25257,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":14},{"id":25256,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":13},{"id":25255,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":12},{"id":25254,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":11},{"id":25253,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":10},{"id":25252,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":9},{"id":25251,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":8},{"id":25250,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":7},{"id":25249,"userWorkId":1,"userProductionLineId":2689,"nextUserPlStepId":6}]
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
         * id : 25268
         * userWorkId : 1
         * userProductionLineId : 2689
         * nextUserPlStepId : -1
         */

        private int id;
        private int userWorkId;
        private int userProductionLineId;
        private int nextUserPlStepId;

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

        public int getNextUserPlStepId() {
            return nextUserPlStepId;
        }

        public void setNextUserPlStepId(int nextUserPlStepId) {
            this.nextUserPlStepId = nextUserPlStepId;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userProductionLineId=" + userProductionLineId +
                    ", nextUserPlStepId=" + nextUserPlStepId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductionProcessesBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
