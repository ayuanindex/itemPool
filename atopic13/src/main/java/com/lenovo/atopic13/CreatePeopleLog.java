package com.lenovo.atopic13;

import java.util.List;

public class CreatePeopleLog {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"userWorkId":"1","userPeopleId":"1","time":"1","id":"5"}]
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
         * userPeopleId : 1
         * time : 1
         * id : 5
         */

        private String userWorkId;
        private String userPeopleId;
        private String time;
        private String id;

        public String getUserWorkId() {
            return userWorkId;
        }

        public void setUserWorkId(String userWorkId) {
            this.userWorkId = userWorkId;
        }

        public String getUserPeopleId() {
            return userPeopleId;
        }

        public void setUserPeopleId(String userPeopleId) {
            this.userPeopleId = userPeopleId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
