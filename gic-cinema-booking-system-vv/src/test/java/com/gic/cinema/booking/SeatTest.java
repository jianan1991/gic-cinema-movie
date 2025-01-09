package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Seat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeatTest {

    @Test
    public void testConstructor(){
        //Declare empty seats
        Seat seat = new Seat(0,0);
        assertEquals("A01", seat.getSeatName());
        assertEquals(null, seat.getBookingId());

        //Declare seats and assign booking ID

        Seat seat1 = new Seat(25,49);
        seat1.setBookingId("GIC0001");
        assertEquals("GIC0001", seat1.getBookingId());
        assertEquals("Z50", seat1.getSeatName());
    }

    @Test
    public void testEqual(){
        Seat seat = new Seat(0,0);
        Seat seat1 = new Seat(0,0);
        assertTrue(seat1.equals(seat));

        seat.setBookingId("GIC0001");
        assertFalse(seat.equals(seat1));

        assertFalse(seat.equals(null));
    }

    @Test
    public void testHashCode(){
        Seat seat = new Seat(0,0);
        Seat seat1 = new Seat(0,0);
        assertTrue(seat.hashCode() == seat1.hashCode());

        seat = new Seat(0,0);
        seat1 = new Seat(0,0);
        seat1.setBookingId("GIC0001");
        assertFalse(seat.hashCode() == seat1.hashCode());
    }

    @Test
    public void testToString(){
        Seat seat = new Seat(0,0);
        assertEquals("Seat{seatName='A01', bookingId='null', row=0, col=0}", seat.toString());

        seat.setBookingId("GIC0001");
        assertEquals("Seat{seatName='A01', bookingId='GIC0001', row=0, col=0}", seat.toString());;
    }

    @Test
    public void testCompare(){
        Seat seat = new Seat(0,0);
        Seat seat1 = new Seat(0,0);
        assertEquals(0, seat.compareTo(seat1));

        seat = new Seat(1,1);
        seat1 = new Seat(0,0);
        assertEquals(1, seat.compareTo(seat1));

        seat = new Seat(0,1);
        seat1 = new Seat(0,0);
        assertEquals(1, seat.compareTo(seat1));

        seat = new Seat(0,0);
        seat1 = new Seat(0,1);
        assertEquals(-1, seat.compareTo(seat1));

    }

}
