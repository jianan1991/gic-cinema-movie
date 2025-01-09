package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Movie;
import com.gic.cinema.booking.Model.Seat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CinemaManagerTest {

    private CinemaManager cinemaManager;
    private String movieTitle;
    private int numOfRows, numPerRows;

    @BeforeEach
    public void initObjects(){
        numOfRows = 5;
        numPerRows = 6;
        movieTitle = "TESTMOVIE";
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);
    }

    @Test
    public void testConstructor(){
        assertEquals(numOfRows * numPerRows, cinemaManager.getEmptySeatsLeft());
        assertEquals(movieTitle, cinemaManager.getMovieTitle());
    }

    //Even number of Rows per column
    @Test
    public void testSelectByDefault(){

        //Even number of rows (Default is 6)
        Booking booking = cinemaManager.selectByDefault(2);
        int totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );


        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);

        booking = cinemaManager.selectByDefault(8);
        totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );
    }

    //Multiple booking without collision
    @Test
    public void testSelectByDefaultMultipleBooking(){
        //Even number of rows (Default is 6)
        Booking booking = cinemaManager.selectByDefault(2);
        Booking booking1 = cinemaManager.selectByDefault(2);

        List<String> seatsForBooking = booking.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        List<String> seatsForBooking1 = booking1.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        int totalSeats = booking.getSeats().size() + booking1.getSeats().size() + cinemaManager.getEmptySeatsLeft();


        assertEquals("[A03, A04]",seatsForBooking.toString());
        assertEquals("[A02, A05]",seatsForBooking1.toString());

        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("GIC0001", booking.getBookingId());;
        assertEquals("GIC0002", booking1.getBookingId());

        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);
        booking = cinemaManager.selectByDefault(2);
        booking1 = cinemaManager.selectByDefault(2);

        seatsForBooking = booking.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        seatsForBooking1 = booking1.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        totalSeats = booking.getSeats().size() + booking1.getSeats().size() + cinemaManager.getEmptySeatsLeft();


        assertEquals("[A03, A04]",seatsForBooking.toString());
        assertEquals("[A02, A05]",seatsForBooking1.toString());

        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("GIC0001", booking.getBookingId());;
        assertEquals("GIC0002", booking1.getBookingId());

    }

    //Multiple booking flowing to next row
    @Test
    public void testSelectByDefaultMultipleBookingFlowtoNextRow(){
        //Even number of rows (Default is 6)
        Booking booking = cinemaManager.selectByDefault(4);
        Booking booking1 = cinemaManager.selectByDefault(4);

        List<String> seatsForBooking = booking.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        List<String> seatsForBooking1 = booking1.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        int totalSeats = booking.getSeats().size() + booking1.getSeats().size() + cinemaManager.getEmptySeatsLeft();


        assertEquals("[A02, A03, A04, A05]",seatsForBooking.toString());
        assertEquals("[A01, A06, B03, B04]",seatsForBooking1.toString());

        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("GIC0001", booking.getBookingId());;
        assertEquals("GIC0002", booking1.getBookingId());

        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);
        booking = cinemaManager.selectByDefault(5);
        booking1 = cinemaManager.selectByDefault(5);

        seatsForBooking = booking.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        seatsForBooking1 = booking1.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());
        totalSeats = booking.getSeats().size() + booking1.getSeats().size() + cinemaManager.getEmptySeatsLeft();


        assertEquals("[A02, A03, A04, A05, A06]",seatsForBooking.toString());
        assertEquals("[A01, A07, B03, B04, B05]",seatsForBooking1.toString());

        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("GIC0001", booking.getBookingId());;
        assertEquals("GIC0002", booking1.getBookingId());
    }

    @Test
    public void testSelectByDefaultExceed(){
        //Even number of rows (Default is 6)
        Booking booking = cinemaManager.selectByDefault(numOfRows* numPerRows+1);

        assertEquals(booking, null );
        assertEquals(numOfRows * numPerRows, cinemaManager.getEmptySeatsLeft());

        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);

        booking = cinemaManager.selectByDefault(numOfRows* numPerRows+1);

        assertEquals(booking, null );
        assertEquals(numOfRows * numPerRows, cinemaManager.getEmptySeatsLeft());;

    }


    //Test Update Booking
    @Test
    public void testUpdateBooking(){
        Booking booking = cinemaManager.selectByDefault(5);
        List<String> seat = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        cinemaManager.updateBooking(booking, "B02");
        List<String> seat1 = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        int totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );

        assertEquals("[A01, A02, A03, A04, A05]", seat.toString());
        assertEquals("[B02, B03, B04, B05, B06]", seat1.toString());


        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);

        booking = cinemaManager.selectByDefault(5);
        seat = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());
        cinemaManager.updateBooking(booking, "B02");
        seat1 = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        assertNotEquals(seat, seat1);


        totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("[A02, A03, A04, A05, A06]", seat.toString());
        assertEquals("[B02, B03, B04, B05, B06]", seat1.toString());
    }

    //Test Update Booking
    @Test
    public void testUpdateBookingFlowIntoNextRow(){
        Booking booking = cinemaManager.selectByDefault(5);
        List<String> seat = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        cinemaManager.updateBooking(booking, "B05");
        List<String> seat1 = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        int totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );

        assertEquals("[A01, A02, A03, A04, A05]", seat.toString());
        assertEquals("[B05, B06, C02, C03, C04]", seat1.toString());


        //Odd number of rows
        numOfRows = 5;
        numPerRows = 7;
        cinemaManager = new CinemaManager("TESTMOVIE",numOfRows, numPerRows);

        booking = cinemaManager.selectByDefault(5);
        seat = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());
        cinemaManager.updateBooking(booking, "B05");
        seat1 = booking.getSeats().stream().map(Seat::getSeatName).collect(Collectors.toList());

        assertNotEquals(seat, seat1);


        totalSeats = cinemaManager.getEmptySeatsLeft()+ booking.getSeats().size();
        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("[A02, A03, A04, A05, A06]", seat.toString());
        assertEquals("[B05, B06, B07, C03, C04]", seat1.toString());
    }

    @Test
    public void testUpdateBookingMultipleBooking(){
        //First Booking
        Booking booking = cinemaManager.selectByDefault(3);

        //Second Booking
        Booking booking1 = cinemaManager.selectByDefault(3);

        //Update First Booking
        cinemaManager.updateBooking(booking, "B02");

        //Update Second Booking
        cinemaManager.updateBooking(booking1, "D02");

        List<String> seatsForBooking = booking.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());

        List<String> seatsForBooking1 = booking1.getSeats()
                .stream()
                .map(Seat::getSeatName)
                .collect(Collectors.toList());

        int totalSeats = booking.getSeats().size() + booking1.getSeats().size() + cinemaManager.getEmptySeatsLeft();
        assertEquals(numOfRows*numPerRows, totalSeats );
        assertEquals("[B02, B03, B04]", seatsForBooking.toString());
        assertEquals("[D02, D03, D04]", seatsForBooking1.toString());

    }

    @Test
    public void testUpdateBookingWithInvalidInput(){
        Booking booking = cinemaManager.selectByDefault(1);
        assertFalse(cinemaManager.updateBooking(booking, "02"));
    }

    @Test
    public void testGetBookingById(){
        Booking actualBooking = cinemaManager.selectByDefault(1);
        Booking retrievedBooking = cinemaManager.getBookingById(actualBooking.getBookingId()).orElse(null);

        assertEquals(actualBooking, retrievedBooking);
    }
}
