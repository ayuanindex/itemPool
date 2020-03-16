package com.lenovo.atopic06;

import java.util.List;

/**
 * 全部生产工序
 */
public class Stage {

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
         * id : 5
         * stageName : MPV汽车放置底盘
         * content : 将汽车底盘放置在作业线上
         * plStepId : 5
         * nextStageId : 6
         */

        private int id;
        private String stageName;
        private String content;
        private int plStepId;
        private int nextStageId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPlStepId() {
            return plStepId;
        }

        public void setPlStepId(int plStepId) {
            this.plStepId = plStepId;
        }

        public int getNextStageId() {
            return nextStageId;
        }

        public void setNextStageId(int nextStageId) {
            this.nextStageId = nextStageId;
        }
    }
}
