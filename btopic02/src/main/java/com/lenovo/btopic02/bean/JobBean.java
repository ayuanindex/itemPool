package com.lenovo.btopic02.bean;

/**
 * @author ayuan
 */
public class JobBean {
    private String jobName;
    private int workPostId;

    public JobBean(String jobName, int workPostId) {
        this.jobName = jobName;
        this.workPostId = workPostId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getWorkPostId() {
        return workPostId;
    }

    public void setWorkPostId(int workPostId) {
        this.workPostId = workPostId;
    }

    @Override
    public String toString() {
        return "JobBean{" +
                "jobName='" + jobName + '\'' +
                ", workPostId=" + workPostId +
                '}';
    }
}
