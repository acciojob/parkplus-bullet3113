package com.driver.services.impl;

import com.driver.Exception.CannotMakeReservationException;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {

        SpotType spotType = (numberOfWheels == 2) ? SpotType.TWO_WHEELER : numberOfWheels == 4 ? SpotType.FOUR_WHEELER : SpotType.OTHERS;
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).orElse(null);

        // parking lot not found
        if(parkingLot == null) {
            throw new CannotMakeReservationException("Cannot make reservation");
        }

        List<Spot> spotList = parkingLot.getSpotList();
        List<SpotType> spotTypeList = new ArrayList<>();
        spotTypeList.add(spotType);
        if(spotType.equals(SpotType.TWO_WHEELER)) {
            spotTypeList.add(SpotType.FOUR_WHEELER);
            spotTypeList.add(SpotType.OTHERS);
        } else if(spotType.equals(SpotType.FOUR_WHEELER)) {
            spotTypeList.add(SpotType.OTHERS);
        }

        int min = Integer.MAX_VALUE;
        int spotId = -1;
        for (Spot s: spotList) {
            SpotType sType = s.getSpotType();
            if(spotTypeList.contains(sType)) {
                if(min > s.getPricePerHour() && !s.getOccupied()) {
                    min = s.getPricePerHour();
                    spotId = s.getId();
                }
            }
        }

        if(spotId == -1) throw new CannotMakeReservationException("Cannot make reservation");

        int totalPrice = min * timeInHours;

        User user = userRepository3.findById(userId).orElse(null);

        if(user == null) throw new CannotMakeReservationException("Cannot make reservation");

        List<Reservation> reservationList = user.getReservationList();

        // making spot occupied
        Spot spot = spotRepository3.findById(spotId).orElse(null);

        spot.setOccupied(true);
        List<Reservation> spotReservationList = spot.getReservationList();

        Reservation reservation = new Reservation();

        reservation.setSpot(spot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);

        reservationRepository3.save(reservation);
        spotReservationList.add(reservation);
        spot.setReservationList(spotReservationList);

        user.setReservationList(reservationList);

        return reservation;
    }
}
