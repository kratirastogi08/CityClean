package com.example.kratirastogi.garbageclean;

public class Date {
    Date1 d1;
    Date2 d2;
    Date3 d3;

    public Date(Date1 d1, Date2 d2, Date3 d3) {
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }
    public Date()
    {

    }

    public Date1 getD1() {
        return d1;
    }

    public void setD1(Date1 d1) {
        this.d1 = d1;
    }

    public Date2 getD2() {
        return d2;
    }

    public void setD2(Date2 d2) {
        this.d2 = d2;
    }

    public Date3 getD3() {
        return d3;
    }

    public void setD3(Date3 d3) {
        this.d3 = d3;
    }
}