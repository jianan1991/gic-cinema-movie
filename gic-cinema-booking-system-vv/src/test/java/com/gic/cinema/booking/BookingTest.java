package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Seat;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingTest {

    @Test
    public void testConstructor(){
        //Declare booked seats
        Seat seat = new Seat(0,0);
        seat.setBookingId("GIC0001");

        //Declare booking
        Booking booking = new Booking("GIC0001");
        booking.addSeats(seat);

        assertEquals("GIC0001", booking.getBookingId());
        assertEquals(seat, booking.getSeats().first());
    }

    @Test
    public void testTreeSet(){
        //Declare booked seats and booking and assign
        Seat seat;
        Booking booking = new Booking("GIC0001");

        for (int i = 3;i > 0; i-- ){
            seat = new Seat(i,1);
            seat.setBookingId("GIC0001");
            booking.addSeats(seat);
        }
        assertEquals("[B02, C02, D02]", booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList()).toString());
    }

    @Test
    public void testClearSeats(){
        //Declare booked seats and booking and assign
        Seat seat;
        Booking booking = new Booking("GIC0001");

        for (int i = 3;i > 0; i-- ){
            seat = new Seat(i,1);
            seat.setBookingId("GIC0001");
            booking.addSeats(seat);
        }

        booking.clearSeats();
        System.out.println(booking);
        assertEquals("[]", booking.getSeats().toString());
    }

    @Test
    public void testToString(){
        Booking booking = new Booking("GIC0001");
        assertEquals("Booking{bookingId='GIC0001', seats=[]}", booking.toString());
    }

}
