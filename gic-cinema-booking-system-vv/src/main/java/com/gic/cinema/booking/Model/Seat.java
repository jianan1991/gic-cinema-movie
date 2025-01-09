package com.gic.cinema.booking.Model;

import com.gic.cinema.booking.Utils.Util;

import java.util.Objects;

public class Seat implements Comparable<Seat> {
    private String seatName;
    private String bookingId;
    private int row;
    private int col;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.bookingId = null;
        this.seatName = Util.NumerictoAlphabet(row) + String.format("%02d", col+1);
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatName() {
        return seatName;
    }

    @Override
    public int compareTo(Seat o) {
        if(o.seatName==this.seatName) return 0;

        if(o.row<this.row) { return 1; }
        else {
            if(o.row==this.row){
                if(o.col==this.col) return 0;
                if(o.col<this.col) {return 1;}
                else { return -1;}
            }
            else { return -1;}
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatName.equals(seat.seatName) && Objects.equals(bookingId, seat.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatName, bookingId);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatName='" + seatName + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
