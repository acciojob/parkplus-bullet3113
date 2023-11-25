package com.driver.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

    private int id;
    private String name;
    private String address;
    private int total;
    private List<Spot> spotList;

    public ParkingLot() {
        this.spotList = new ArrayList<>();
    }

    public List<Spot> getSpotList() {
        return spotList;
    }

    public void setSpotList(List<Spot> spotList) {
        this.spotList = spotList;
    }

    void autoGen() {
        this.setId(this.getTotal()+1);
        this.setTotal(this.getTotal()+1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
