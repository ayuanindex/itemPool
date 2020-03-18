package com.lenovo.atopic10;

import java.util.List;

/**
 * 新增学生备料
 */
public class CreatePart {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"userWorkId":"1","userProductionLineId":"2514","partId":"1","num":"100","id":"6266"}]
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
         * userProductionLineId : 2514
         * partId : 1
         * num : 100
         * id : 6266
         */

        private String userWorkId;
        private String userProductionLineId;
        private String partId;
        private String num;
        private String id;

        public String getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(String userWorkId) {
            this.userWorkId = userWorkId;
        }

        public String getUserProductionLineId() {
            return userProductionLineId;
        }

        public void setUserProductionLineId(String userProductionLineId) {
            this.userProductionLineId = userProductionLineId;
        }

        public String getPartId() {
            return partId;
        }

        public void setPartId(String partId) {
            this.partId = partId;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
