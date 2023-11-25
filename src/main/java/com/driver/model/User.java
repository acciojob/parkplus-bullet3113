package com.driver.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String phone;
    private String password;
    private int total;

    private List<Reservation> reservationList;

    public User() {
        this.reservationList = new ArrayList<>();
    }

    void autoGen() {
        this.setId(this.getTotal() + 1);
        this.setTotal(this.getTotal() + 1);
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
