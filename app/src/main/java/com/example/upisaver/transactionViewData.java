package com.example.upisaver;

public class transactionViewData{
    private int amount;
    private String data;
    private String usage;
    private boolean type;
    private int id;

    public transactionViewData() {

    }

    public transactionViewData(int amount, String data, String usage, boolean type, int id) {
        this.amount = amount;
        this.data = data;
        this.usage = usage;
        this.type = type;
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
