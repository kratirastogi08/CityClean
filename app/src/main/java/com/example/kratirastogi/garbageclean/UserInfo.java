package com.example.kratirastogi.garbageclean;

import com.google.android.gms.maps.model.LatLng;

public class UserInfo {
    String email,pass,paymentStatus,id,state;
   String ImageUrl;
   String name,add;

    double lat,lng;
    public UserInfo()
    {}
    public UserInfo(String email, String pass,double lat,double lng,String ImageUrl,String name,String add,String paymentStatus,String id,String state) {
        this.email = email;
        this.pass = pass;
        this.lat=lat;
        this.lng=lng;
        this.ImageUrl=ImageUrl;
        this.name=name;
        this.add=add;
        this.paymentStatus=paymentStatus;
        this.id=id;
        this.state=state;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
