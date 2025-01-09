package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Movie;
import com.gic.cinema.booking.Model.Seat;
import com.gic.cinema.booking.Utils.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    private Movie movie;
    private Booking booking;

    @BeforeEach
    public void initCinma(){
        movie = new Movie("Test", 3, 3);
        Seat[] movieSeat = movie.getMovieMap().get("A");
        movieSeat[0].setBookingId("GIC0001");
        movieSeat[1].setBookingId("GIC0001");

        booking = new Booking("GIC0001");
        Seat seat = new Seat(0,0);
        seat.setBookingId("GIC0001");

        Seat seat1 = new Seat(0,1);
        seat1.setBookingId("GIC0001");

        booking.addSeats(seat);
        booking.addSeats(seat1);
    }
    @Test
    public void testNumerictoAlphabet() {
        assertEquals("A",Util.NumerictoAlphabet(0));
        assertEquals("H",Util.NumerictoAlphabet(7));
        assertEquals("P",Util.NumerictoAlphabet(15));
        assertEquals("Z",Util.NumerictoAlphabet(25));
    }

    @Test
    public void testAlphabetToNumeric() {
        assertEquals(0,Util.AlphabetToNumeric('A'));
        assertEquals(7,Util.AlphabetToNumeric('H'));
        assertEquals(15,Util.AlphabetToNumeric('P'));
        assertEquals(25,Util.AlphabetToNumeric('Z'));
    }

    @Test
    public void testSeatDisplay() {

        char [][] charOutput = Util.generateSeatDisplay(booking, movie);
        assertEquals(6, charOutput.length);

        assertEquals("S C R E E N", String.valueOf(charOutput[0]));
        assertEquals("--------", String.valueOf(charOutput[1]));
        assertEquals("C . . . ", String.valueOf(charOutput[2]));
        assertEquals("B . . . ", String.valueOf(charOutput[3]));
        assertEquals("A o o . ", String.valueOf(charOutput[4]));
    }
}
