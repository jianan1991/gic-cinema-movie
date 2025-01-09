package com.gic.cinema.booking.Model;

import java.util.TreeSet;

public class Booking {
    private String bookingId;
    private TreeSet<Seat> seats = new TreeSet<>();

    public Booking(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }
    public TreeSet<Seat> getSeats() {
        return seats;
    }
    public void addSeats(Seat seats) {
        this.seats.add(seats);
    }
    public void clearSeats(){
        this.seats = new TreeSet<>();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", seats=" + seats +
                '}';
    }
}
