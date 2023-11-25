package com.driver.model;

import java.util.ArrayList;
import java.util.List;

public class Spot {

    private int id;
    private SpotType spotType;
    private int price;
    private boolean isAvailable;
    private ParkingLot parkingLot;
    private List<Reservation> reservationList;
    private int total;

    public Spot() {
        this.reservationList = new ArrayList<>();
    }

    void auotGen() {
        this.setId(this.getTotal() + 1);
        this.setTotal(this.getTotal() + 1);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
    }
}
