package com.example.it19005836_firebase;

public class Student {
    private String Id;
    private String name;
    private String address;
    private int conNo;


    public Student() {
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getConNo() {
        return conNo;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConNo(int conNo) {
        this.conNo = conNo;
    }
}
