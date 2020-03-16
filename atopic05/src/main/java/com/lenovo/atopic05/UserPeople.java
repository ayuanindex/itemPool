package com.lenovo.atopic05;

import java.util.List;

/**
 * 全部学生员工
 */
public class UserPeople {


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
         * id : 4197
         * userFactoryId : 1
         * hp : 86
         * personId : 8
         * userLineId : 2538
         * person : {"id":8,"personName":"周正发","icon":null,"type":3,"hrmId":1,"price":4000,"power":100,"content":"汽车质检员","lastUpdateTime":1568008092}
         */

        private int id;
        private int userFactoryId;
        private int hp;
        private int personId;
        private int userLineId;
        private PersonBean person;

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

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public int getPersonId() {
            return personId;
        }

        public void setPersonId(int personId) {
            this.personId = personId;
        }

        public int getUserLineId() {
            return userLineId;
        }

        public void setUserLineId(int userLineId) {
            this.userLineId = userLineId;
        }

        public PersonBean getPerson() {
            return person;
        }

        public void setPerson(PersonBean person) {
            this.person = person;
        }

        public static class PersonBean {
            /**
             * id : 8
             * personName : 周正发
             * icon : null
             * type : 3
             * hrmId : 1
             * price : 4000
             * power : 100
             * content : 汽车质检员
             * lastUpdateTime : 1568008092
             */

            private int id;
            private String personName;
            private String icon;
            private int type;
            private int hrmId;
            private int price;
            private int power;
            private String content;
            private long lastUpdateTime;

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

            public void setIcon(String icon) {
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

            public long getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(long lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }
        }
    }
}
