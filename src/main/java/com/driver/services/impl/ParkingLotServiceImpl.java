package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).orElse(null);
        Spot spot = new Spot();
        if(parkingLot != null) {
            SpotType spotType = (numberOfWheels == 2) ? SpotType.TWO_WHEELER : numberOfWheels == 4 ? SpotType.FOUR_WHEELER : SpotType.OTHERS;
            spot.setSpotType(spotType);
            spot.setPricePerHour(pricePerHour);
            spot.setOccupied(false);
            spot.setParkingLot(parkingLot);
            spotRepository1.save(spot);

            List<Spot> spotList = parkingLot.getSpotList();
            spotList.add(spot);
            parkingLot.setSpotList(spotList);

            parkingLotRepository1.save(parkingLot);
        }

        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).orElse(null);
        if(spot != null) {
            ParkingLot parkingLot = spot.getParkingLot();
            List<Spot> spotList = parkingLot.getSpotList();
            spotList.remove(spot);
            spotRepository1.delete(spot);
            parkingLot.setSpotList(spotList);
            parkingLotRepository1.save(parkingLot);
        }
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).orElse(null);
        if(spot != null) {
            ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).orElse(null);
            if(parkingLot != null) {
                List<Spot> spotList = parkingLot.getSpotList();
                spotList.remove(spot);
                spot.setPricePerHour(pricePerHour);
                spotRepository1.save(spot);
                spotList.add(spot);
                parkingLot.setSpotList(spotList);
                parkingLotRepository1.save(parkingLot);
            }
        }
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {

        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).orElse(null);
        if (parkingLot != null) {
            List<Spot> spotList = parkingLot.getSpotList();
            for (Spot s: spotList) {
                spotRepository1.delete(s);
            }
            parkingLotRepository1.delete(parkingLot);
        }
    }
}
