package com.lenovo.atopic13;

import java.util.List;

public class UserPeopleLog
{

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":8,"userWorkId":1,"userPeopleId":3,"time":1585107820},{"id":7,"userWorkId":1,"userPeopleId":2,"time":1585107817},{"id":6,"userWorkId":1,"userPeopleId":1,"time":1585107816},{"id":5,"userWorkId":1,"userPeopleId":1,"time":1}]
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
         * id : 8
         * userWorkId : 1
         * userPeopleId : 3
         * time : 1585107820
         */

        private int id;
        private int userWorkId;
        private int userPeopleId;
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

        public int getUserPeopleId() {
            return userPeopleId;
        }

        public void setUserPeopleId(int userPeopleId) {
            this.userPeopleId = userPeopleId;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
