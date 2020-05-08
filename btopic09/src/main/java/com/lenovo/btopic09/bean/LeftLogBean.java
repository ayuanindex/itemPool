package com.lenovo.btopic09.bean;

/**
 * @author ayuan
 */
public class LeftLogBean {
    private String name;
    private String gold;
    private String content;
    private String time;

    public LeftLogBean() {
    }

    public LeftLogBean(String name, String gold, String content, String time) {
        this.name = name;
        this.gold = gold;
        this.content = content;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "LogBean{" +
                "name='" + name + '\'' +
                ", gold='" + gold + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
