package com.example.upisaver;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class transaction extends RealmObject {
    @PrimaryKey
    private int id;

    private int amount;
    private String date;
    private boolean type;
    private String usage;

    public transaction() {

    }

    public transaction(int id, int amount, String date, boolean type, String usage) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.usage = usage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
