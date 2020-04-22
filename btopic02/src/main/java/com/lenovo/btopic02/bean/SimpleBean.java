package com.lenovo.btopic02.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class SimpleBean {
    private String labelName;
    private List<StudentStaffBean.DataBean> dataBeans;

    public SimpleBean(String labelName, List<StudentStaffBean.DataBean> dataBeans) {
        this.labelName = labelName;
        this.dataBeans = dataBeans;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<StudentStaffBean.DataBean> getDataBeans() {
        return dataBeans;
    }

    public void setDataBeans(List<StudentStaffBean.DataBean> dataBeans) {
        this.dataBeans = dataBeans;
    }

    /**
     * 添加对应岗位的工人进入集合
     *
     * @param dataBean 工人
     */
    public void addStudentStaff(StudentStaffBean.DataBean dataBean) {
        this.dataBeans.add(dataBean);
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "labelName='" + labelName + '\'' +
                ", dataBeans=" + dataBeans +
                '}';
    }
}
