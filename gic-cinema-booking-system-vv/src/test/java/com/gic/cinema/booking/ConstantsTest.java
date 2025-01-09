package com.gic.cinema.booking;

import com.gic.cinema.booking.Utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsTest {
    @Test
    public void testConstructor(){
        assertEquals(26, MOVIE_MAX_NUMBER_OF_ROW);
        assertEquals(50, MOVIE_MAX_NUMBER_OF_SEATS_PER_ROW);
        assertEquals("GIC", BOOKING_ID_PREFIX);
        assertEquals("S C R E E N", DISPLAY_SCREEN);
        assertEquals(" ", DISPLAY_SEAT_SPACE);
        assertEquals("-", DISPLAY_SCREEN_DIVIDER);
        assertEquals(".", DISPLAY_SEAT_EMPTY);
        assertEquals("#", DISPLAY_SEAT_OTHERS);
        assertEquals("o", DISPLAY_SEAT_SELECTED);

    }



    public static final int MOVIE_MAX_NUMBER_OF_ROW = 26;
    public static final int MOVIE_MAX_NUMBER_OF_SEATS_PER_ROW = 50;
    public static final String BOOKING_ID_PREFIX = "GIC";
    public static final String DISPLAY_SCREEN = "S C R E E N";
    public static final String DISPLAY_SEAT_SPACE = " ";
    public static final String DISPLAY_SCREEN_DIVIDER = "-";
    public static final String DISPLAY_SEAT_EMPTY = ".";
    public static final String DISPLAY_SEAT_OTHERS = "#";
    public static final String DISPLAY_SEAT_SELECTED = "o";
}
