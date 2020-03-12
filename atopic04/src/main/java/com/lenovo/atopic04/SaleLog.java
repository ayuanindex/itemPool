package com.lenovo.atopic04;

import java.util.List;

public class SaleLog {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":26489,"userWorkId":1,"carId":2,"gold":3000,"time":1576135879,"num":5},{"id":26488,"userWorkId":1,"carId":3,"gold":4000,"time":1574452584,"num":10},{"id":26487,"userWorkId":1,"carId":2,"gold":5000,"time":1570112584,"num":1},{"id":26486,"userWorkId":1,"carId":1,"gold":1999,"time":1570236932,"num":1},{"id":26485,"userWorkId":1,"carId":2,"gold":1666,"time":1576703568,"num":1},{"id":26484,"userWorkId":1,"carId":1,"gold":999,"time":1575215879,"num":5},{"id":26483,"userWorkId":1,"carId":2,"gold":980,"time":1571211111,"num":5},{"id":26482,"userWorkId":1,"carId":2,"gold":1804,"time":1570101258,"num":3},{"id":26481,"userWorkId":1,"carId":3,"gold":1840,"time":1570610147,"num":2},{"id":26480,"userWorkId":1,"carId":1,"gold":1950,"time":1570512547,"num":5},{"id":26479,"userWorkId":1,"carId":2,"gold":1910,"time":1570332589,"num":20},{"id":26478,"userWorkId":1,"carId":3,"gold":3210,"time":1576425879,"num":2},{"id":26477,"userWorkId":1,"carId":1,"gold":2450,"time":1574512584,"num":5},{"id":26476,"userWorkId":1,"carId":2,"gold":3600,"time":1570512584,"num":5},{"id":26475,"userWorkId":1,"carId":1,"gold":4100,"time":1570356932,"num":10},{"id":26474,"userWorkId":1,"carId":2,"gold":5500,"time":1576796742,"num":5},{"id":26473,"userWorkId":1,"carId":1,"gold":5600,"time":1575237888,"num":1},{"id":26472,"userWorkId":1,"carId":1,"gold":5700,"time":1571273888,"num":1},{"id":26471,"userWorkId":1,"carId":3,"gold":2070,"time":1570136120,"num":1},{"id":26470,"userWorkId":1,"carId":3,"gold":2080,"time":1570635120,"num":1},{"id":26469,"userWorkId":1,"carId":1,"gold":1533,"time":1570535840,"num":2},{"id":26468,"userWorkId":1,"carId":3,"gold":1266,"time":1570343085,"num":2},{"id":26467,"userWorkId":1,"carId":3,"gold":2666,"time":1576452058,"num":5},{"id":26466,"userWorkId":1,"carId":3,"gold":2660,"time":1574534059,"num":2},{"id":26465,"userWorkId":1,"carId":2,"gold":2055,"time":1570578909,"num":2},{"id":26464,"userWorkId":1,"carId":3,"gold":2065,"time":1570356239,"num":1},{"id":26463,"userWorkId":1,"carId":2,"gold":1555,"time":1576769321,"num":15},{"id":26462,"userWorkId":1,"carId":1,"gold":1550,"time":1575233214,"num":7},{"id":26461,"userWorkId":1,"carId":1,"gold":1660,"time":1571278234,"num":1},{"id":26460,"userWorkId":1,"carId":3,"gold":1665,"time":1570159678,"num":1},{"id":26459,"userWorkId":1,"carId":3,"gold":1600,"time":1570646989,"num":3},{"id":26458,"userWorkId":1,"carId":1,"gold":2060,"time":1570545839,"num":25},{"id":26457,"userWorkId":1,"carId":3,"gold":1080,"time":1570345639,"num":6},{"id":26456,"userWorkId":1,"carId":1,"gold":1800,"time":1576456639,"num":5},{"id":26455,"userWorkId":1,"carId":3,"gold":1070,"time":1574535569,"num":20},{"id":26454,"userWorkId":1,"carId":2,"gold":1060,"time":1570557339,"num":2},{"id":26453,"userWorkId":1,"carId":1,"gold":2160,"time":1570345239,"num":1},{"id":26452,"userWorkId":1,"carId":1,"gold":2010,"time":1576789321,"num":1},{"id":26451,"userWorkId":1,"carId":2,"gold":700,"time":1574133214,"num":7},{"id":26450,"userWorkId":1,"carId":1,"gold":600,"time":1571231234,"num":13},{"id":26449,"userWorkId":1,"carId":1,"gold":900,"time":1570125678,"num":2},{"id":26448,"userWorkId":1,"carId":3,"gold":2667,"time":1570646789,"num":15},{"id":26447,"userWorkId":1,"carId":1,"gold":2441,"time":1570553839,"num":5},{"id":26446,"userWorkId":1,"carId":3,"gold":2468,"time":1570343239,"num":6},{"id":26445,"userWorkId":1,"carId":3,"gold":5874,"time":1576443639,"num":10},{"id":26444,"userWorkId":1,"carId":2,"gold":5222,"time":1574552569,"num":10},{"id":26443,"userWorkId":1,"carId":1,"gold":5111,"time":1570567339,"num":1},{"id":26442,"userWorkId":1,"carId":1,"gold":5123,"time":1570355239,"num":10},{"id":26441,"userWorkId":1,"carId":1,"gold":5410,"time":1570456321,"num":3},{"id":26440,"userWorkId":1,"carId":2,"gold":4630,"time":1570223214,"num":7},{"id":26439,"userWorkId":1,"carId":1,"gold":4563,"time":1570321234,"num":3},{"id":26438,"userWorkId":1,"carId":3,"gold":4550,"time":1570235678,"num":2},{"id":26437,"userWorkId":1,"carId":3,"gold":4501,"time":1570356789,"num":6},{"id":26436,"userWorkId":1,"carId":3,"gold":6100,"time":1570567839,"num":5},{"id":26435,"userWorkId":1,"carId":2,"gold":7410,"time":1570321239,"num":4},{"id":26434,"userWorkId":1,"carId":2,"gold":2148,"time":1570355639,"num":10},{"id":26433,"userWorkId":1,"carId":2,"gold":2000,"time":1573452569,"num":1},{"id":26432,"userWorkId":1,"carId":2,"gold":2115,"time":1570567339,"num":1},{"id":26431,"userWorkId":1,"carId":1,"gold":3256,"time":1570356239,"num":1},{"id":26430,"userWorkId":1,"carId":3,"gold":1546,"time":1570412439,"num":3},{"id":26429,"userWorkId":1,"carId":3,"gold":3648,"time":1570229643,"num":5},{"id":26428,"userWorkId":1,"carId":1,"gold":6325,"time":1570323239,"num":1},{"id":26427,"userWorkId":1,"carId":3,"gold":1597,"time":1570232979,"num":2},{"id":26426,"userWorkId":1,"carId":3,"gold":2468,"time":1570354589,"num":30},{"id":26425,"userWorkId":1,"carId":1,"gold":2974,"time":1570236839,"num":25},{"id":26424,"userWorkId":1,"carId":2,"gold":2486,"time":1570371239,"num":4},{"id":26423,"userWorkId":1,"carId":2,"gold":3546,"time":1570357839,"num":20},{"id":26422,"userWorkId":1,"carId":1,"gold":2890,"time":1570362569,"num":13},{"id":26421,"userWorkId":1,"carId":1,"gold":2040,"time":1570324339,"num":6},{"id":26420,"userWorkId":1,"carId":3,"gold":2030,"time":1570352239,"num":8},{"id":26419,"userWorkId":1,"carId":3,"gold":2020,"time":1570423439,"num":5},{"id":26418,"userWorkId":1,"carId":3,"gold":2010,"time":1570226543,"num":3},{"id":26417,"userWorkId":1,"carId":1,"gold":1126,"time":1570352239,"num":15},{"id":26416,"userWorkId":1,"carId":3,"gold":1123,"time":1570232239,"num":10},{"id":26415,"userWorkId":1,"carId":3,"gold":4210,"time":1570354239,"num":3},{"id":26414,"userWorkId":1,"carId":1,"gold":1360,"time":1570232239,"num":7},{"id":26413,"userWorkId":1,"carId":2,"gold":2489,"time":1570372239,"num":5},{"id":26412,"userWorkId":1,"carId":2,"gold":2390,"time":1570352239,"num":6},{"id":26411,"userWorkId":1,"carId":1,"gold":1280,"time":1570362339,"num":6},{"id":26410,"userWorkId":1,"carId":1,"gold":2540,"time":1570325239,"num":2},{"id":26409,"userWorkId":1,"carId":3,"gold":6320,"time":1570342239,"num":1},{"id":26408,"userWorkId":1,"carId":1,"gold":1493,"time":1570692060,"num":2},{"id":26407,"userWorkId":1,"carId":3,"gold":4651,"time":1570323439,"num":5},{"id":26406,"userWorkId":1,"carId":3,"gold":2871,"time":1570326543,"num":3},{"id":26405,"userWorkId":1,"carId":1,"gold":2161,"time":1570332239,"num":8},{"id":26404,"userWorkId":1,"carId":3,"gold":1637,"time":1570372239,"num":1},{"id":26403,"userWorkId":1,"carId":3,"gold":2116,"time":1570352239,"num":2},{"id":26402,"userWorkId":1,"carId":1,"gold":2148,"time":1570222239,"num":6},{"id":26401,"userWorkId":1,"carId":2,"gold":1262,"time":1570352239,"num":6},{"id":26400,"userWorkId":1,"carId":2,"gold":2541,"time":1570312239,"num":2},{"id":26399,"userWorkId":1,"carId":1,"gold":8541,"time":1570322339,"num":3},{"id":26398,"userWorkId":1,"carId":1,"gold":1567,"time":1570325239,"num":5},{"id":26397,"userWorkId":1,"carId":3,"gold":1681,"time":1570312239,"num":5},{"id":26396,"userWorkId":1,"carId":3,"gold":1420,"time":1570322239,"num":10},{"id":26395,"userWorkId":1,"carId":1,"gold":1600,"time":1570311239,"num":5},{"id":26394,"userWorkId":1,"carId":2,"gold":1500,"time":1570611239,"num":2},{"id":26393,"userWorkId":1,"carId":1,"gold":1300,"time":1570611239,"num":2},{"id":26392,"userWorkId":1,"carId":3,"gold":5000,"time":1570638239,"num":6},{"id":26391,"userWorkId":1,"carId":2,"gold":2000,"time":1570628239,"num":2},{"id":26390,"userWorkId":1,"carId":1,"gold":1500,"time":1570681239,"num":1}]
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
         * id : 26489
         * userWorkId : 1
         * carId : 2
         * gold : 3000
         * time : 1576135879
         * num : 5
         */

        private String timeStr;

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        private int id;
        private int userWorkId;
        private int carId;
        private int gold;
        private int time;
        private int num;
        private String carType;


        public String getCarType() {
            return carType;
        }

        public void setCarType(String carType) {
            this.carType = carType;
        }

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

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
