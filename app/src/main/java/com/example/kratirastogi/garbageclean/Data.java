package com.example.kratirastogi.garbageclean;

public class Data {
    String name,add,paymentStatus,id,state;

    public Data( String name, String add,String paymentStatus,String id,String state) {

        this.name = name;
        this.add = add;
        this.paymentStatus=paymentStatus;
        this.id=id;
        this.state=state;
    }
   public Data()
   {

   }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
