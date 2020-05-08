package com.lenovo.btopic09.bean;

/**
 * @author ayuan
 */
public class RightLogBean {
    private String name;
    private String supply;
    private String num;
    private String price;
    private String time;

    public RightLogBean() {
    }

    public RightLogBean(String name, String supply, String num, String price, String time) {
        this.name = name;
        this.supply = supply;
        this.num = num;
        this.price = price;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RightLogBean{" +
                "name='" + name + '\'' +
                ", supply='" + supply + '\'' +
                ", num='" + num + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
