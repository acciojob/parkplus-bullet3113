package com.driver.services.impl;

import com.driver.Exception.InsufficientAmountException;
import com.driver.Exception.PaymentModeException;
import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {

        Reservation reservation = reservationRepository2.findById(reservationId).orElse(null);

        mode = mode.toUpperCase();

        List<PaymentMode> list = Arrays.asList(PaymentMode.values());

        PaymentMode paymentMode = null;
        boolean check = false;
        for(PaymentMode p: list) {
            if(mode.equals(p.toString())) {
                paymentMode = p;
            }
        }

        if(paymentMode == null) {
            throw new PaymentModeException("Payment mode not detected");
        }

        int numberOfHours = reservation.getNumberOfHours();
        int pricePerHour = reservation.getSpot().getPricePerHour();

        int paymentDue = numberOfHours * pricePerHour;

        if(amountSent < paymentDue) {
            throw new InsufficientAmountException("Insufficient Amount");
        }

        Payment payment = new Payment();
        payment.setPaymentMode(paymentMode);
        payment.setPaymentCompleted(true);
        payment.setReservation(reservation);

        reservation.setPayment(payment);

        paymentRepository2.save(payment);
        reservationRepository2.save(reservation);

        return payment;
    }
}
