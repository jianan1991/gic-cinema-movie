package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Movie;
import com.gic.cinema.booking.Model.Seat;
import com.gic.cinema.booking.Utils.Constants;
import com.gic.cinema.booking.Utils.Util;

import java.util.*;
import java.util.stream.Collectors;

public class CinemaManager {

    private List<Booking> bookingIDs = new ArrayList<>();
    private Movie movie;
    private SortedSet<Seat> bookedSeats = new TreeSet<>();


    public CinemaManager(String movieTitle, int numOfRows, int numofSeatsPerRow){
        this.movie = new Movie(movieTitle,numOfRows,numofSeatsPerRow);
    }

    public String getMovieTitle() {
        return movie.getMovieTitle();
    }
    public int getEmptySeatsLeft() {
        return movie.getEmptySeatsLeft();
    }

    public Optional<Booking> getBookingById(String bookingID){
        return bookingIDs.stream().filter(booking -> booking.getBookingId().equals(bookingID)).findFirst();

    }
    public void displayBookingSeats(Booking booking){
        System.out.printf("Booking id: %s%n", booking.getBookingId());
        System.out.println("Selected seats:");
        System.out.println();
        char[][] cinemaSeats2dArray = Util.generateSeatDisplay(booking, movie);
        for(char[] cinemaSeatsRowArray: cinemaSeats2dArray)
            System.out.println(String.valueOf(cinemaSeatsRowArray));
        System.out.println();
    }


    public Booking selectByDefault(int numOfRequestedSeats){
        String bookingId = String.format("%s%04d", Constants.BOOKING_ID_PREFIX, bookingIDs.size()+1);
        Booking newBooking = new Booking(bookingId);
        int row = 0;

        if(numOfRequestedSeats <= movie.getEmptySeatsLeft()) {
            while (numOfRequestedSeats > 0) {
                Seat[] furthestRow = movie.getMovieMap().get(Util.NumerictoAlphabet(row));
                if (Arrays.stream(furthestRow).filter(x -> x.getBookingId() == null).count() > 0) {
                    numOfRequestedSeats = selectDefaultSeatsPerRow(numOfRequestedSeats, furthestRow, newBooking);
                }
                row++;
                if (row > movie.getNumOfRows() - 1) {
                    break;
                }
            }
        }else{
            return null;
        }
        bookedSeats.addAll(newBooking.getSeats());
        bookingIDs.add(newBooking);
        return newBooking;
    }

    public boolean updateBooking(Booking booking, String position){
        int row;
        int col;
        //Check if input is at least minimum length if not return e.g A01, A11
        if(position.length() < 3){
            return false;
        }
        try {
            row = Util.AlphabetToNumeric(position.charAt(0));
            col = Integer.valueOf(position.substring(1)) - 1;
        }catch (NumberFormatException e){
            return false;
        }

        List<String> availableSeatNames = Arrays.stream(movie.getMovieMap().get(String.valueOf(position.charAt(0)))).map(x->x.getSeatName()).collect(Collectors.toList());

        if(availableSeatNames.contains(position)){
            //remove previous booking
            int numOfRequestedSeats = booking.getSeats().size();
            for (Seat seat: booking.getSeats()) {
                movie.revertBooking(seat);
                bookedSeats.remove(seat);
            }
            booking.clearSeats();
            //rebook based
            Seat[] currentRow = movie.getMovieMap().get(Util.NumerictoAlphabet(row));
            if(Arrays.stream(currentRow).filter(x->x.getBookingId() == null).count() > 0) {
                numOfRequestedSeats = selectFromSelectionPerRow(numOfRequestedSeats, currentRow, col, booking);
            }
            while(numOfRequestedSeats > 0){
                row = (row + 1) % (movie.getNumOfRows());

                currentRow = movie.getMovieMap().get(Util.NumerictoAlphabet(row));
                if(Arrays.stream(currentRow).filter(x->x.getBookingId() == null).count() > 0) {
                    numOfRequestedSeats = selectDefaultSeatsPerRow(numOfRequestedSeats, currentRow, booking);
                }

            }
            return true;
        }
        return false;
    }

    private int selectFromSelectionPerRow(int numOfRequestedSeats, Seat[] currentRow, int position, Booking booking){

        while (numOfRequestedSeats > 0){
            if(position > movie.getNumofSeatsPerRow()-1){
                break;
            }
            numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, currentRow[position], booking);
            position++;
        }
        return numOfRequestedSeats;
    }

    private int selectDefaultSeatsPerRow(int numOfRequestedSeats, Seat[] currentRow, Booking booking){
        int middleCol = movie.getNumofSeatsPerRow()/2;
        int leftPointer = middleCol;
        int rightPointer = middleCol;

        if(movie.getNumofSeatsPerRow() % 2 == 0){
            leftPointer--;
        }else{
            leftPointer--;
            rightPointer++;
        }
        while(numOfRequestedSeats > 0) {

            if(leftPointer<0 || rightPointer >= movie.getNumofSeatsPerRow()){
                break;
            }
            numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, currentRow[middleCol], booking);
            numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, currentRow[leftPointer], booking);
            numOfRequestedSeats = movie.checkAndSelectSeat(numOfRequestedSeats, currentRow[rightPointer], booking);

            leftPointer--;
            rightPointer++;

        }
        return numOfRequestedSeats;
    }

}
