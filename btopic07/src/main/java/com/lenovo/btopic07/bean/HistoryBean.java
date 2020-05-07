package com.lenovo.btopic07.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author ayuan
 */
@DatabaseTable(tableName = "history")
public class HistoryBean {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "label")
    private String label;

    public HistoryBean() {
    }

    public HistoryBean(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "id=" + id +
                ", label='" + label + '\'' +
                '}';
    }
}
