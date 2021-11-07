package com.anandabayu.alami.entity;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "history")
public class History {

    public static final String COLLECTION_NAME = "history";

    @Id
    private String id;

    private String log;
    private Date history_date;

    public History(String log, Date history_date) {
        this.log = log;
        this.history_date = history_date;
    }

    public History() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Date getHistory_date() {
        return history_date;
    }

    public void setHistory_date(Date history_date) {
        this.history_date = history_date;
    }

    @Override
    public String toString() {
        return "History{" +
                "id='" + id + '\'' +
                ", log='" + log + '\'' +
                ", history_date=" + history_date +
                '}';
    }
}
