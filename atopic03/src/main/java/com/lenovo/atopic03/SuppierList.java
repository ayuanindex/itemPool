package com.lenovo.atopic03;

import java.util.List;

/**
 * 供货列表
 */
public class SuppierList {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"suppierId":1,"partId":26,"gold":1600,"num":2},{"id":8,"suppierId":1,"partId":22,"gold":300,"num":1},{"id":3,"suppierId":1,"partId":25,"gold":1500,"num":1},{"id":4,"suppierId":1,"partId":24,"gold":1666,"num":1},{"id":5,"suppierId":1,"partId":23,"gold":1350,"num":1},{"id":9,"suppierId":1,"partId":21,"gold":690,"num":1},{"id":10,"suppierId":1,"partId":20,"gold":4630,"num":10},{"id":11,"suppierId":1,"partId":19,"gold":26000,"num":3},{"id":12,"suppierId":1,"partId":18,"gold":4500,"num":3},{"id":13,"suppierId":1,"partId":17,"gold":2555,"num":1},{"id":14,"suppierId":1,"partId":16,"gold":1630,"num":1},{"id":15,"suppierId":1,"partId":15,"gold":200,"num":2},{"id":16,"suppierId":1,"partId":14,"gold":750,"num":2},{"id":17,"suppierId":1,"partId":13,"gold":8000,"num":12},{"id":18,"suppierId":1,"partId":12,"gold":2600,"num":4},{"id":19,"suppierId":1,"partId":11,"gold":600,"num":1},{"id":20,"suppierId":1,"partId":10,"gold":1500,"num":1},{"id":21,"suppierId":1,"partId":9,"gold":1400,"num":1},{"id":22,"suppierId":1,"partId":7,"gold":1888,"num":1},{"id":23,"suppierId":1,"partId":8,"gold":1666,"num":1},{"id":24,"suppierId":1,"partId":5,"gold":1666,"num":4},{"id":25,"suppierId":1,"partId":4,"gold":800,"num":1},{"id":26,"suppierId":1,"partId":1,"gold":700,"num":1},{"id":30,"suppierId":1,"partId":30,"gold":3450,"num":6},{"id":29,"suppierId":1,"partId":29,"gold":9000,"num":15},{"id":31,"suppierId":1,"partId":31,"gold":2560,"num":7},{"id":32,"suppierId":1,"partId":32,"gold":3200,"num":4},{"id":33,"suppierId":1,"partId":33,"gold":1500,"num":1},{"id":34,"suppierId":1,"partId":34,"gold":1600,"num":3},{"id":35,"suppierId":1,"partId":35,"gold":2810,"num":7},{"id":36,"suppierId":1,"partId":36,"gold":688,"num":1},{"id":37,"suppierId":1,"partId":37,"gold":485,"num":1},{"id":38,"suppierId":1,"partId":38,"gold":6000,"num":25},{"id":39,"suppierId":2,"partId":38,"gold":1650,"num":3},{"id":40,"suppierId":2,"partId":37,"gold":1660,"num":9},{"id":41,"suppierId":2,"partId":36,"gold":2060,"num":1},{"id":42,"suppierId":2,"partId":35,"gold":2080,"num":3},{"id":43,"suppierId":2,"partId":34,"gold":5700,"num":10},{"id":44,"suppierId":2,"partId":33,"gold":2600,"num":5},{"id":45,"suppierId":2,"partId":32,"gold":6500,"num":10},{"id":46,"suppierId":2,"partId":31,"gold":8700,"num":22},{"id":47,"suppierId":2,"partId":30,"gold":3200,"num":5},{"id":48,"suppierId":2,"partId":29,"gold":300,"num":1},{"id":49,"suppierId":2,"partId":26,"gold":488,"num":1},{"id":50,"suppierId":2,"partId":25,"gold":3666,"num":3},{"id":51,"suppierId":2,"partId":24,"gold":5000,"num":6},{"id":52,"suppierId":2,"partId":23,"gold":9700,"num":10},{"id":53,"suppierId":2,"partId":22,"gold":6600,"num":25},{"id":54,"suppierId":2,"partId":21,"gold":1300,"num":2},{"id":55,"suppierId":2,"partId":20,"gold":4810,"num":5},{"id":56,"suppierId":2,"partId":19,"gold":2148,"num":8},{"id":57,"suppierId":2,"partId":18,"gold":2669,"num":1},{"id":58,"suppierId":2,"partId":17,"gold":200,"num":1},{"id":59,"suppierId":2,"partId":16,"gold":499,"num":1},{"id":60,"suppierId":2,"partId":15,"gold":1400,"num":1},{"id":61,"suppierId":2,"partId":14,"gold":1989,"num":3},{"id":62,"suppierId":2,"partId":13,"gold":1999,"num":5},{"id":63,"suppierId":2,"partId":12,"gold":3999,"num":8},{"id":64,"suppierId":2,"partId":11,"gold":3666,"num":7},{"id":65,"suppierId":2,"partId":10,"gold":1550,"num":6},{"id":66,"suppierId":2,"partId":9,"gold":3690,"num":12},{"id":67,"suppierId":2,"partId":7,"gold":6400,"num":12},{"id":68,"suppierId":2,"partId":8,"gold":600,"num":1},{"id":69,"suppierId":2,"partId":5,"gold":420,"num":2},{"id":70,"suppierId":2,"partId":4,"gold":3333,"num":6},{"id":71,"suppierId":2,"partId":1,"gold":1908,"num":2},{"id":72,"suppierId":5,"partId":38,"gold":1804,"num":5},{"id":73,"suppierId":5,"partId":37,"gold":1840,"num":4},{"id":74,"suppierId":5,"partId":36,"gold":5500,"num":10},{"id":75,"suppierId":5,"partId":35,"gold":15000,"num":50},{"id":76,"suppierId":5,"partId":34,"gold":3600,"num":5},{"id":77,"suppierId":5,"partId":33,"gold":5888,"num":11},{"id":78,"suppierId":5,"partId":32,"gold":548,"num":1},{"id":79,"suppierId":5,"partId":31,"gold":5900,"num":11},{"id":80,"suppierId":5,"partId":30,"gold":328,"num":1},{"id":81,"suppierId":5,"partId":29,"gold":3250,"num":11},{"id":82,"suppierId":5,"partId":26,"gold":299,"num":1},{"id":83,"suppierId":5,"partId":25,"gold":229,"num":1},{"id":84,"suppierId":5,"partId":24,"gold":449,"num":1},{"id":85,"suppierId":5,"partId":23,"gold":960,"num":3},{"id":86,"suppierId":5,"partId":22,"gold":7800,"num":12},{"id":87,"suppierId":5,"partId":21,"gold":690,"num":1},{"id":88,"suppierId":5,"partId":20,"gold":800,"num":2},{"id":89,"suppierId":5,"partId":19,"gold":4900,"num":2},{"id":90,"suppierId":5,"partId":18,"gold":3600,"num":2},{"id":91,"suppierId":5,"partId":17,"gold":3450,"num":5},{"id":92,"suppierId":5,"partId":16,"gold":16000,"num":25},{"id":93,"suppierId":5,"partId":15,"gold":4800,"num":6},{"id":94,"suppierId":5,"partId":14,"gold":7510,"num":12},{"id":95,"suppierId":5,"partId":13,"gold":2600,"num":3},{"id":96,"suppierId":5,"partId":12,"gold":6500,"num":7},{"id":97,"suppierId":5,"partId":11,"gold":3888,"num":5},{"id":98,"suppierId":5,"partId":10,"gold":480,"num":1},{"id":99,"suppierId":5,"partId":9,"gold":960,"num":1},{"id":100,"suppierId":5,"partId":7,"gold":9600,"num":12},{"id":101,"suppierId":5,"partId":8,"gold":460,"num":1},{"id":102,"suppierId":5,"partId":5,"gold":5300,"num":12},{"id":103,"suppierId":5,"partId":4,"gold":6000,"num":5},{"id":104,"suppierId":5,"partId":1,"gold":2500,"num":5},{"id":105,"suppierId":6,"partId":38,"gold":3600,"num":6},{"id":106,"suppierId":6,"partId":37,"gold":2850,"num":9},{"id":107,"suppierId":6,"partId":36,"gold":2666,"num":5},{"id":108,"suppierId":6,"partId":35,"gold":16000,"num":30},{"id":109,"suppierId":6,"partId":34,"gold":4325,"num":10},{"id":110,"suppierId":6,"partId":33,"gold":960,"num":1},{"id":111,"suppierId":6,"partId":32,"gold":6700,"num":10},{"id":112,"suppierId":6,"partId":31,"gold":3600,"num":6},{"id":113,"suppierId":6,"partId":30,"gold":1555,"num":5},{"id":114,"suppierId":6,"partId":29,"gold":350,"num":1},{"id":115,"suppierId":6,"partId":26,"gold":2800,"num":10},{"id":116,"suppierId":6,"partId":25,"gold":300,"num":1},{"id":117,"suppierId":6,"partId":24,"gold":4555,"num":5},{"id":118,"suppierId":6,"partId":23,"gold":960,"num":3},{"id":119,"suppierId":6,"partId":22,"gold":180,"num":1},{"id":120,"suppierId":6,"partId":21,"gold":1080,"num":5},{"id":121,"suppierId":6,"partId":20,"gold":300,"num":1},{"id":122,"suppierId":6,"partId":19,"gold":160,"num":1},{"id":123,"suppierId":6,"partId":18,"gold":860,"num":2},{"id":124,"suppierId":6,"partId":17,"gold":1060,"num":2},{"id":125,"suppierId":6,"partId":16,"gold":677,"num":1},{"id":126,"suppierId":6,"partId":15,"gold":3487,"num":8},{"id":127,"suppierId":6,"partId":14,"gold":1265,"num":2},{"id":128,"suppierId":6,"partId":13,"gold":1854,"num":1},{"id":129,"suppierId":6,"partId":12,"gold":4698,"num":2},{"id":130,"suppierId":6,"partId":11,"gold":1489,"num":10},{"id":131,"suppierId":6,"partId":10,"gold":4891,"num":7},{"id":132,"suppierId":6,"partId":9,"gold":600,"num":2},{"id":133,"suppierId":6,"partId":7,"gold":1500,"num":2},{"id":134,"suppierId":6,"partId":8,"gold":999,"num":3},{"id":135,"suppierId":6,"partId":5,"gold":780,"num":2},{"id":136,"suppierId":6,"partId":4,"gold":960,"num":2},{"id":137,"suppierId":6,"partId":1,"gold":3056,"num":10}]
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
         * suppierId : 1
         * partId : 26
         * gold : 1600
         * num : 2
         */

        private int id;
        private int suppierId;
        private int partId;
        private int gold;
        private int num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSuppierId() {
            return suppierId;
        }

        public void setSuppierId(int suppierId) {
            this.suppierId = suppierId;
        }

        public int getPartId() {
            return partId;
        }

        public void setPartId(int partId) {
            this.partId = partId;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
