package com.lenovo.btopic09.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class EnlistLogBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"userWorkId":1,"userPeopleId":12,"time":158654582}]
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
         * id : 1
         * userWorkId : 1
         * userPeopleId : 12
         * time : 158654582
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userWorkId=" + userWorkId +
                    ", userPeopleId=" + userPeopleId +
                    ", time=" + time +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EnlistLogBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
