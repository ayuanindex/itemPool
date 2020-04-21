package com.lenovo.btopic1.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class StatusBean {

    /**
     * status : 400
     * message : 该位置已存在生产线
     * data : []
     */

    private int status;
    private String message;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
