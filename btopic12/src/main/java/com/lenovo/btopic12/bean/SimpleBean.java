package com.lenovo.btopic12.bean;

/**
 * @author ayuan
 */
public class SimpleBean {
    private int icon;
    private String linkName;
    private String des;
    private int plStepId;
    private int hp;
    private int consume;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getPlStepId() {
        return plStepId;
    }

    public void setPlStepId(int plStepId) {
        this.plStepId = plStepId;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getConsume() {
        return consume;
    }

    public void setConsume(int consume) {
        this.consume = consume;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "icon=" + icon +
                ", linkName='" + linkName + '\'' +
                ", des='" + des + '\'' +
                ", plStepId=" + plStepId +
                ", hp=" + hp +
                ", consume=" + consume +
                '}';
    }
}
