package com.example.kratirastogi.garbageclean;

public class CollectorData {
    String name,time,numplate,number;

    public CollectorData(String name, String time, String numplate, String number) {
        this.name = name;
        this.time = time;
        this.numplate=numplate;
        this.number=number;
    }
    public CollectorData()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
