package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Movie;
import com.gic.cinema.booking.Model.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTest {
    private Movie movie;
    String movieTitle;
    private int numOfRows, numPerRows;

    @BeforeEach
    public void initObjects(){
        numOfRows = 5;
        numPerRows = 6;
        movieTitle = "TESTMOVIE";
        movie = new Movie("TESTMOVIE",numOfRows, numPerRows);
    }

    @Test
    public void testConstructor(){
        //Declare booked seats
        assertEquals(numOfRows, movie.getNumOfRows());
        assertEquals(numPerRows, movie.getNumofSeatsPerRow());
        assertEquals(movieTitle, movie.getMovieTitle());
    }



    @Test
    public void testPopulate(){
        //Declare booked seats
        Map<String, Seat[]> movieMap = movie.getMovieMap();
        String key = movieMap.keySet().stream().findFirst().orElse("");

        assertEquals(numOfRows * numPerRows, movie.getEmptySeatsLeft());
        assertEquals(numOfRows, movieMap.size());
        assertEquals(numPerRows, movieMap.get(key).length);
    }

    @Test
    public void testCheckAndSelectSeat(){
        String bookingId = "GIC0001";
        int numOfRequestedSeats = 2;
        Map<String, Seat[]> movieMap = movie.getMovieMap();
        String key = movieMap.keySet().stream().findFirst().orElse("");

        int remaining = movie.checkAndSelectSeat(numOfRequestedSeats, movieMap.get(key)[0],new Booking(bookingId));

        //required 2 seats and only 1 assigned
        assertEquals(numOfRequestedSeats - 1, remaining);
        assertEquals((numOfRows * numPerRows) - 1, movie.getEmptySeatsLeft());
        assertEquals(bookingId, movie.getMovieMap().get(key)[0].getBookingId());

        //required 0 seats and non will be assigned
        remaining = movie.checkAndSelectSeat(0, movieMap.get(key)[0],new Booking(bookingId));
        assertEquals(0, remaining);
        assertEquals((numOfRows * numPerRows) - 1, movie.getEmptySeatsLeft());
        assertEquals(bookingId, movie.getMovieMap().get(key)[0].getBookingId());

    }

    @Test
    public void testRevertBooking(){
        String bookingId = "GIC0001";
        int numOfRequestedSeats = 1;

        Booking booking = new Booking(bookingId);

        Map<String, Seat[]> movieMap = movie.getMovieMap();
        String key = movieMap.keySet().stream().findFirst().orElse("");
        Seat seat = movieMap.get(key)[0];

        //Booking Seats
        numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, seat, booking);

        assertEquals(bookingId, seat.getBookingId());
        assertEquals(numOfRows * numPerRows - 1, movie.getEmptySeatsLeft());
        //Revert Seats
        movie.revertBooking(seat);

        //Check if seats are reverted
        assertEquals(null, seat.getBookingId());
        assertEquals(numOfRows * numPerRows, movie.getEmptySeatsLeft());
    }

    //Book multiple seats in a single bookings
    @Test
    public void testCheckAndSelectMultipleBooking(){
        String bookingId = "GIC0001";
        int numOfRequestedSeats = 3;
        int numOfSeatsToBeBooked = numOfRequestedSeats;
        Booking booking = new Booking(bookingId);
        Map<String, Seat[]> movieMap = movie.getMovieMap();
        String key = movieMap.keySet().stream().findFirst().orElse("");
        Seat[] seats = movieMap.get(key);


        //Booking multiple seats using 1 booking and only request for 3
        for(int i = 0; i < numOfSeatsToBeBooked; i++){
            numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, seats[i], booking);
        }

        //Count the number of rows being booked in this row
        int countBookedInRow = Arrays.stream(seats)
                .map(Seat::getBookingId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).size();
        assertEquals(numOfSeatsToBeBooked, countBookedInRow);
        assertEquals((numOfRows * numPerRows) - numOfSeatsToBeBooked, movie.getEmptySeatsLeft());

    }

    //Booking multiple single bookings
    @Test
    public void testCheckAndSelectMultipleSingleBooking(){
        String bookingId = "GIC0001";
        Booking booking = new Booking(bookingId);

        String bookingId1 = "GIC0002";
        Booking booking1 = new Booking(bookingId);



        Map<String, Seat[]> movieMap = movie.getMovieMap();
        String key = movieMap.keySet().stream().findFirst().orElse("");
        Seat[] seats = movieMap.get(key);


        //Booking multiple seats using 1 booking and only request for 3
        movie.checkAndSelectSeat(1, seats[0], booking);
        movie.checkAndSelectSeat(1, seats[1], booking1);


        //Count the number of rows being booked in this row
        int countBookedInRow = Arrays.stream(seats)
                .map(Seat::getBookingId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()).size();
        assertEquals(2, countBookedInRow);
        assertEquals((numOfRows * numPerRows) - 2, movie.getEmptySeatsLeft());

    }
}
