package com.gic.cinema.booking.Model;

import com.gic.cinema.booking.Utils.Util;

import java.util.*;

public class Movie{
    private int numOfRows;
    private int numofSeatsPerRow;
    private String movieTitle;
    private int emptySeatsLeft;

    private Map<String, Seat[]> movieMap = new HashMap<String, Seat[]>();

    public Movie(String movieTitle, int numOfRows, int numofSeatsPerRow) {
        this.numOfRows = numOfRows;
        this.numofSeatsPerRow = numofSeatsPerRow;
        this.movieTitle = movieTitle;
        this.emptySeatsLeft = numofSeatsPerRow * numOfRows;
        populateSeatMap();
    }

    private void populateSeatMap(){
        for (int row=0; row < numOfRows; row ++){
            String rowAlphabet = Util.NumerictoAlphabet(row);
            movieMap.put(rowAlphabet, new Seat[numofSeatsPerRow]);
            for(int col=0; col< numofSeatsPerRow; col++){
                Seat newSeat = new Seat(row,col);
                movieMap.get(rowAlphabet)[col] = newSeat;
            }
        }
    }

    public String getMovieTitle() {
        return movieTitle;
    }
    public int getEmptySeatsLeft() {
        return emptySeatsLeft;
    }

    public int getNumOfRows() {
        return numOfRows;
    }

    public int getNumofSeatsPerRow() {
        return numofSeatsPerRow;
    }

    public Map<String, Seat[]> getMovieMap() {
        return Map.copyOf(movieMap);
    }

    public int checkAndSelectSeat(int numOfRequestedSeats, Seat suggestedSeat, Booking booking){
        if(numOfRequestedSeats > 0 && suggestedSeat.getBookingId() == null){
            booking.addSeats(suggestedSeat);
            suggestedSeat.setBookingId(booking.getBookingId());
            numOfRequestedSeats--;
            emptySeatsLeft--;
        }
        return numOfRequestedSeats;
    }

    public void revertBooking(Seat seat){
        seat.setBookingId(null);
        emptySeatsLeft++;
    }
}
