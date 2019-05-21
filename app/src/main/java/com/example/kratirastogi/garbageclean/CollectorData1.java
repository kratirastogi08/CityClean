package com.example.kratirastogi.garbageclean;

public class CollectorData1 {
    String name,numplate,number,date;

    public CollectorData1(String name, String numplate, String number,String date) {
        this.name = name;
        this.numplate = numplate;
        this.number = number;
        this.date=date;
    }
    public CollectorData1()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumplate() {
        return numplate;
    }

    public void setNumplate(String numplate) {
        this.numplate = numplate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
