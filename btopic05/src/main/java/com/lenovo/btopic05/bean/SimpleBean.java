package com.lenovo.btopic05.bean;

import androidx.annotation.NonNull;

/**
 * @author ayuan
 */
public class SimpleBean {
    private String title;
    private String des;
    private int plStepId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getPlStepId() {
        return plStepId;
    }

    public void setPlStepId(int plStepId) {
        this.plStepId = plStepId;
    }

    @NonNull
    @Override
    public String toString() {
        return "SimpleBean{" +
                "title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", plStepId=" + plStepId +
                '}';
    }
}
