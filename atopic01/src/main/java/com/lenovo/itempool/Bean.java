package com.lenovo.itempool;

import java.util.List;

public class Bean
{


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":25789,"userFactoryId":1,"carTypeId":2,"price":3000,"time":1576135879,"num":5,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25788,"userFactoryId":1,"carTypeId":3,"price":4000,"time":1574452584,"num":10,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25787,"userFactoryId":1,"carTypeId":2,"price":5000,"time":1570112584,"num":1,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25786,"userFactoryId":1,"carTypeId":1,"price":1999,"time":1570236932,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25785,"userFactoryId":1,"carTypeId":2,"price":1666,"time":1576703568,"num":1,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25784,"userFactoryId":1,"carTypeId":1,"price":999,"time":1575215879,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25783,"userFactoryId":1,"carTypeId":2,"price":980,"time":1571211111,"num":5,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25782,"userFactoryId":1,"carTypeId":2,"price":1804,"time":1570101258,"num":3,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25781,"userFactoryId":1,"carTypeId":3,"price":1840,"time":1570610147,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25780,"userFactoryId":1,"carTypeId":1,"price":1950,"time":1570512547,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25779,"userFactoryId":1,"carTypeId":2,"price":1910,"time":1570332589,"num":20,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25778,"userFactoryId":1,"carTypeId":3,"price":3210,"time":1576425879,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25777,"userFactoryId":1,"carTypeId":1,"price":2450,"time":1574512584,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25776,"userFactoryId":1,"carTypeId":2,"price":3600,"time":1570512584,"num":5,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25775,"userFactoryId":1,"carTypeId":1,"price":4100,"time":1570356932,"num":10,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25774,"userFactoryId":1,"carTypeId":2,"price":5500,"time":1576796742,"num":5,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25773,"userFactoryId":1,"carTypeId":1,"price":5600,"time":1575237888,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25772,"userFactoryId":1,"carTypeId":1,"price":5700,"time":1571273888,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25771,"userFactoryId":1,"carTypeId":3,"price":2070,"time":1570136120,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25770,"userFactoryId":1,"carTypeId":3,"price":2080,"time":1570635120,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25769,"userFactoryId":1,"carTypeId":1,"price":1533,"time":1570535840,"num":2,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25768,"userFactoryId":1,"carTypeId":3,"price":1266,"time":1570343085,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25767,"userFactoryId":1,"carTypeId":3,"price":2666,"time":1576452058,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25766,"userFactoryId":1,"carTypeId":3,"price":2660,"time":1574534059,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25765,"userFactoryId":1,"carTypeId":2,"price":2055,"time":1570578909,"num":2,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25764,"userFactoryId":1,"carTypeId":3,"price":2065,"time":1570356239,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25763,"userFactoryId":1,"carTypeId":2,"price":1555,"time":1576769321,"num":15,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25762,"userFactoryId":1,"carTypeId":1,"price":1550,"time":1575233214,"num":7,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25761,"userFactoryId":1,"carTypeId":1,"price":1660,"time":1571278234,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25760,"userFactoryId":1,"carTypeId":3,"price":1665,"time":1570159678,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25759,"userFactoryId":1,"carTypeId":3,"price":1600,"time":1570646989,"num":3,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25758,"userFactoryId":1,"carTypeId":1,"price":2060,"time":1570545839,"num":25,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25757,"userFactoryId":1,"carTypeId":3,"price":1080,"time":1570345639,"num":6,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25756,"userFactoryId":1,"carTypeId":1,"price":1800,"time":1576456639,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25755,"userFactoryId":1,"carTypeId":3,"price":1070,"time":1574535569,"num":20,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25754,"userFactoryId":1,"carTypeId":2,"price":1060,"time":1570557339,"num":2,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25753,"userFactoryId":1,"carTypeId":1,"price":2160,"time":1570345239,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25752,"userFactoryId":1,"carTypeId":1,"price":2010,"time":1576789321,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25751,"userFactoryId":1,"carTypeId":2,"price":700,"time":1574133214,"num":7,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25750,"userFactoryId":1,"carTypeId":1,"price":600,"time":1571231234,"num":13,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25749,"userFactoryId":1,"carTypeId":1,"price":900,"time":1570125678,"num":2,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25748,"userFactoryId":1,"carTypeId":3,"price":2667,"time":1570646789,"num":15,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25747,"userFactoryId":1,"carTypeId":1,"price":2441,"time":1570553839,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25746,"userFactoryId":1,"carTypeId":3,"price":2468,"time":1570343239,"num":6,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25745,"userFactoryId":1,"carTypeId":3,"price":5874,"time":1576443639,"num":10,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25744,"userFactoryId":1,"carTypeId":2,"price":5222,"time":1574552569,"num":10,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25743,"userFactoryId":1,"carTypeId":1,"price":5111,"time":1570567339,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25742,"userFactoryId":1,"carTypeId":1,"price":5123,"time":1570355239,"num":10,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25741,"userFactoryId":1,"carTypeId":1,"price":5410,"time":1570456321,"num":3,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25740,"userFactoryId":1,"carTypeId":2,"price":4630,"time":1570223214,"num":7,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25739,"userFactoryId":1,"carTypeId":1,"price":4563,"time":1570321234,"num":3,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25738,"userFactoryId":1,"carTypeId":3,"price":4550,"time":1570235678,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25737,"userFactoryId":1,"carTypeId":3,"price":4501,"time":1570356789,"num":6,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25736,"userFactoryId":1,"carTypeId":3,"price":6100,"time":1570567839,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25735,"userFactoryId":1,"carTypeId":2,"price":7410,"time":1570321239,"num":4,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25734,"userFactoryId":1,"carTypeId":2,"price":2148,"time":1570355639,"num":10,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25733,"userFactoryId":1,"carTypeId":2,"price":2000,"time":1573452569,"num":1,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25732,"userFactoryId":1,"carTypeId":2,"price":2115,"time":1570567339,"num":1,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25731,"userFactoryId":1,"carTypeId":1,"price":3256,"time":1570356239,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25730,"userFactoryId":1,"carTypeId":3,"price":1546,"time":1570412439,"num":3,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25729,"userFactoryId":1,"carTypeId":3,"price":3648,"time":1570229643,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25728,"userFactoryId":1,"carTypeId":1,"price":6325,"time":1570323239,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25727,"userFactoryId":1,"carTypeId":3,"price":1597,"time":1570232979,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25726,"userFactoryId":1,"carTypeId":3,"price":2468,"time":1570354589,"num":30,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25725,"userFactoryId":1,"carTypeId":1,"price":2974,"time":1570236839,"num":25,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25724,"userFactoryId":1,"carTypeId":2,"price":2486,"time":1570371239,"num":4,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25723,"userFactoryId":1,"carTypeId":2,"price":3546,"time":1570357839,"num":20,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25722,"userFactoryId":1,"carTypeId":1,"price":2890,"time":1570362569,"num":13,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25721,"userFactoryId":1,"carTypeId":1,"price":2040,"time":1570324339,"num":6,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25720,"userFactoryId":1,"carTypeId":3,"price":2030,"time":1570352239,"num":8,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25719,"userFactoryId":1,"carTypeId":3,"price":2020,"time":1570423439,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25718,"userFactoryId":1,"carTypeId":3,"price":2010,"time":1570226543,"num":3,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25717,"userFactoryId":1,"carTypeId":1,"price":1126,"time":1570352239,"num":15,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25716,"userFactoryId":1,"carTypeId":3,"price":1123,"time":1570232239,"num":10,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25715,"userFactoryId":1,"carTypeId":3,"price":4210,"time":1570354239,"num":3,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25714,"userFactoryId":1,"carTypeId":1,"price":1360,"time":1570232239,"num":7,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25713,"userFactoryId":1,"carTypeId":2,"price":2489,"time":1570372239,"num":5,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25712,"userFactoryId":1,"carTypeId":2,"price":2390,"time":1570352239,"num":6,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25711,"userFactoryId":1,"carTypeId":1,"price":1280,"time":1570362339,"num":6,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25710,"userFactoryId":1,"carTypeId":1,"price":2540,"time":1570325239,"num":2,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25709,"userFactoryId":1,"carTypeId":3,"price":6320,"time":1570342239,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25708,"userFactoryId":1,"carTypeId":1,"price":1493,"time":1570692060,"num":2,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25707,"userFactoryId":1,"carTypeId":3,"price":4651,"time":1570323439,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25706,"userFactoryId":1,"carTypeId":3,"price":2871,"time":1570326543,"num":3,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25705,"userFactoryId":1,"carTypeId":1,"price":2161,"time":1570332239,"num":8,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25704,"userFactoryId":1,"carTypeId":3,"price":1637,"time":1570372239,"num":1,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25703,"userFactoryId":1,"carTypeId":3,"price":2116,"time":1570352239,"num":2,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25702,"userFactoryId":1,"carTypeId":1,"price":2148,"time":1570222239,"num":6,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25701,"userFactoryId":1,"carTypeId":2,"price":1262,"time":1570352239,"num":6,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25700,"userFactoryId":1,"carTypeId":2,"price":2541,"time":1570312239,"num":2,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25699,"userFactoryId":1,"carTypeId":1,"price":8541,"time":1570322339,"num":3,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25698,"userFactoryId":1,"carTypeId":1,"price":1567,"time":1570325239,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25697,"userFactoryId":1,"carTypeId":3,"price":1681,"time":1570312239,"num":5,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25696,"userFactoryId":1,"carTypeId":3,"price":1420,"time":1570322239,"num":10,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25695,"userFactoryId":1,"carTypeId":1,"price":1600,"time":1570311239,"num":5,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25694,"userFactoryId":1,"carTypeId":2,"price":1500,"time":1570611239,"num":2,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25693,"userFactoryId":1,"carTypeId":1,"price":1300,"time":1570611239,"num":2,"lastUpdateTime":null,"carTypeName":"轿车汽车"},{"id":25692,"userFactoryId":1,"carTypeId":3,"price":5000,"time":1570638239,"num":6,"lastUpdateTime":null,"carTypeName":"SUV汽车"},{"id":25691,"userFactoryId":1,"carTypeId":2,"price":2000,"time":1570628239,"num":2,"lastUpdateTime":null,"carTypeName":"MPV汽车"},{"id":25690,"userFactoryId":1,"carTypeId":1,"price":1500,"time":1570681239,"num":1,"lastUpdateTime":null,"carTypeName":"轿车汽车"}]
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
         * id : 25789
         * userFactoryId : 1
         * carTypeId : 2
         * price : 3000
         * time : 1576135879
         * num : 5
         * lastUpdateTime : null
         * carTypeName : MPV汽车
         */


        private String timeStr;



        private int id;
        private int userFactoryId;
        private int carTypeId;
        private int price;
        private int time;
        private int num;
        private Object lastUpdateTime;
        private String carTypeName;

        public String getTimeStr() {
            return timeStr;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

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

        public int getCarTypeId() {
            return carTypeId;
        }

        public void setCarTypeId(int carTypeId) {
            this.carTypeId = carTypeId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
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

        public Object getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Object lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getCarTypeName() {
            return carTypeName;
        }

        public void setCarTypeName(String carTypeName) {
            this.carTypeName = carTypeName;
        }
    }
}
