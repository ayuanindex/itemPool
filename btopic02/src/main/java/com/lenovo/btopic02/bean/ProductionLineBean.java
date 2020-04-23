package com.lenovo.btopic02.bean;

import java.util.List;

/**
 * @author ayuan
 */
public class ProductionLineBean {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2688,"userFactoryId":1,"lineStepId":25,"lineId":1,"status":0,"costTime":0,"pos":2,"isAI":0,"personList":[{"id":4,"personName":"省均","icon":null,"type":3,"hrmId":1,"price":150,"power":100,"content":"汽车质检员","lastUpdateTime":1566182913},{"id":3,"personName":"方华高","icon":null,"type":2,"hrmId":1,"price":300,"power":100,"content":"汽车工厂技术人员","lastUpdateTime":1566182884},{"id":2,"personName":"丁运生","icon":null,"type":1,"hrmId":1,"price":50,"power":100,"content":"汽车厂工人","lastUpdateTime":1566182411},{"id":1,"personName":"李刚","icon":null,"type":0,"hrmId":1,"price":200,"power":100,"content":"汽车工程师","lastUpdateTime":1566181974}]}]
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
         * id : 2688
         * userFactoryId : 1
         * lineStepId : 25
         * lineId : 1
         * status : 0
         * costTime : 0
         * pos : 2
         * isAI : 0
         * personList : [{"id":4,"personName":"省均","icon":null,"type":3,"hrmId":1,"price":150,"power":100,"content":"汽车质检员","lastUpdateTime":1566182913},{"id":3,"personName":"方华高","icon":null,"type":2,"hrmId":1,"price":300,"power":100,"content":"汽车工厂技术人员","lastUpdateTime":1566182884},{"id":2,"personName":"丁运生","icon":null,"type":1,"hrmId":1,"price":50,"power":100,"content":"汽车厂工人","lastUpdateTime":1566182411},{"id":1,"personName":"李刚","icon":null,"type":0,"hrmId":1,"price":200,"power":100,"content":"汽车工程师","lastUpdateTime":1566181974}]
         */

        private int id;
        private int userFactoryId;
        private int lineStepId;
        private int lineId;
        private int status;
        private int costTime;
        private int pos;
        private int isAI;
        private List<PersonListBean> personList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserFactoryId() {
            return userFactoryId;
        }

        public void setUserFactoryId(int userFactoryId) {
            this.userFactoryId = userFactoryId;
        }

        public int getLineStepId() {
            return lineStepId;
        }

        public void setLineStepId(int lineStepId) {
            this.lineStepId = lineStepId;
        }

        public int getLineId() {
            return lineId;
        }

        public void setLineId(int lineId) {
            this.lineId = lineId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCostTime() {
            return costTime;
        }

        public void setCostTime(int costTime) {
            this.costTime = costTime;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public int getIsAI() {
            return isAI;
        }

        public void setIsAI(int isAI) {
            this.isAI = isAI;
        }

        public List<PersonListBean> getPersonList() {
            return personList;
        }

        public void setPersonList(List<PersonListBean> personList) {
            this.personList = personList;
        }

        public static class PersonListBean {
            /**
             * id : 4
             * personName : 省均
             * icon : null
             * type : 3
             * hrmId : 1
             * price : 150
             * power : 100
             * content : 汽车质检员
             * lastUpdateTime : 1566182913
             */

            private int id;
            private String personName;
            private Object icon;
            private int type;
            private int hrmId;
            private int price;
            private int power;
            private String content;
            private int lastUpdateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPersonName() {
                return personName;
            }

            public void setPersonName(String personName) {
                this.personName = personName;
            }

            public Object getIcon() {
                return icon;
            }

            public void setIcon(Object icon) {
                this.icon = icon;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getHrmId() {
                return hrmId;
            }

            public void setHrmId(int hrmId) {
                this.hrmId = hrmId;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getPower() {
                return power;
            }

            public void setPower(int power) {
                this.power = power;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(int lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            @Override
            public String toString() {
                return "PersonListBean{" +
                        "id=" + id +
                        ", personName='" + personName + '\'' +
                        ", icon=" + icon +
                        ", type=" + type +
                        ", hrmId=" + hrmId +
                        ", price=" + price +
                        ", power=" + power +
                        ", content='" + content + '\'' +
                        ", lastUpdateTime=" + lastUpdateTime +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", userFactoryId=" + userFactoryId +
                    ", lineStepId=" + lineStepId +
                    ", lineId=" + lineId +
                    ", status=" + status +
                    ", costTime=" + costTime +
                    ", pos=" + pos +
                    ", isAI=" + isAI +
                    ", personList=" + personList +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProductionLineBean{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
