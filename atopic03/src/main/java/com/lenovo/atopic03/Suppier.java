package com.lenovo.atopic03;

import java.util.List;

/**
 * 供货商
 */
public class Suppier {



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
         * suppierName : 新星汽车配件
         * content : 新星汽车配件供货商
         */

        private int id;
        private String suppierName;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSuppierName() {
            return suppierName;
        }

        public void setSuppierName(String suppierName) {
            this.suppierName = suppierName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
