package com.gic.cinema.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingMenuTest {


    @ParameterizedTest
    @CsvSource({
            "null,Invalid Cinema Config Input",
            "MovieNoConfig,Invalid Cinema Config Input",
            "Movie NoConfig,Invalid Cinema Config Input",
            "Movie NoConfig NoConfig,Invalid Cinema Config Input",
            "Movie 4 4 4,Invalid Cinema Config Input",
            "Movie 5,Invalid Cinema Config Input",
            "Movie 5 ,Invalid Cinema Config Input",
            " 9 10,Invalid Cinema Config Input",
            "Movie 0 5,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 5 0,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 0 51,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 27 0,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 5 51,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 27 5,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 27 51,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 0 0,Invalid [Row] or/and [SeatsPerRow]",
            "Movie 27 0,Invalid [Row] or/and [SeatsPerRow]",
    })
    public void testDisplayCinemaMenu_InvalidConfigInputs(String input, String exceptedExceptionMsg) throws Exception {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine()).thenReturn(input.equals("null") ? null : input);
        Exception exception = assertThrows(Exception.class, () -> {
            BookingMenu bookingMenu = new BookingMenu(sc);
        });
        assertEquals(exceptedExceptionMsg, exception.getMessage());
        String[] actualOutput = outputStreamCaptor.toString().split("\n");
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0].trim());
    }

    @Test
    public void testBookingMenu_exit() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 2 2")
                .thenReturn("1")
                .thenReturn("1")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (4 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("> ", actualOutput[7]);
        assertEquals("Thank you for using GIC Cinema System. Bye!", actualOutput[actualOutput.length-1]);

    }

    @Test
    public void testBookingMenu_booking() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[11]);
        assertEquals("S C R E E N", actualOutput[14]);
        assertEquals("--------", actualOutput[15]);
        assertEquals("B . o . ", actualOutput[17]);
        assertEquals("A o o o ", actualOutput[18]);
        assertEquals("Booking id: GIC0001 confirmed.", actualOutput[23]);
        assertEquals("[1] Book tickets for Movie (5 seats available)", actualOutput[26]);

    }

    @Test
    public void testBookingMenu_bookingInvalidNumber() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("0")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Invalid input! Please enter a number larger than 0", actualOutput[10]);
        assertEquals("Enter number of tickets to book, or enter blank to go back to main menu:", actualOutput[11]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[13]);
    }

    @Test
    public void testBookingMenu_checkBookings() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("2") //Check Booking
                .thenReturn("GIC0001")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Booking id: GIC0001", actualOutput[33]);
        assertEquals("S C R E E N", actualOutput[36]);
        assertEquals("--------", actualOutput[37]);
        assertEquals("B . o . ", actualOutput[39]);
        assertEquals("A o o o ", actualOutput[40]);

    }

    @Test
    public void testBookingMenu_bookingwithUpdate() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("B02")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[11]);
        assertEquals("S C R E E N", actualOutput[26]);
        assertEquals("--------", actualOutput[27]);
        assertEquals("C o o . ", actualOutput[28]);
        assertEquals("B . o o ", actualOutput[29]);

    }

    @Test
    public void testBookingMenu_bookingwithInvalidUpdate() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("B00")
                .thenReturn("B02")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[11]);
        assertEquals("Invalid seat number! Please try again.", actualOutput[23]);
        assertEquals("Enter blank to accept seat selection, or enter new seating position:", actualOutput[34]);
        assertEquals("C o o . ", actualOutput[41]);
        assertEquals("B . o o ", actualOutput[42]);
        assertEquals("Booking id: GIC0001 confirmed.", actualOutput[48]);

    }

    @Test
    public void testBookingMenu_bookingwithExceeding() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("10")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Sorry, there are only 9 seats available.", actualOutput[10]);
        assertEquals("Invalid input! Please try again.", actualOutput[12]);
    }

    @Test
    public void testBookingMenu_checkworkflow() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("2")
                .thenReturn("GIC0001")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[33]);
        assertEquals("B . o . ", actualOutput[39]);
        assertEquals("A o o o ", actualOutput[40]);

    }

    @Test
    public void testBookingMenu_checkTwoBookingworkflow() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("2")
                .thenReturn("GIC0001")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[11]);
        assertEquals("C . . . ", actualOutput[16]);
        assertEquals("B . o . ", actualOutput[17]);
        assertEquals("A o o o ", actualOutput[18]);
        assertEquals("Booking id: GIC0002", actualOutput[34]);
        assertEquals("C o o . ", actualOutput[39]);
        assertEquals("B o # o ", actualOutput[40]);
        assertEquals("A # # # ", actualOutput[41]);
        assertEquals("Booking id: GIC0001", actualOutput[56]);
        assertEquals("C # # . ", actualOutput[61]);
        assertEquals("B # o # ", actualOutput[62]);
        assertEquals("A o o o ", actualOutput[63]);

    }

    @Test
    public void testBookingMenu_checkInvalidBookingID() throws Exception {
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        Scanner sc = mock(Scanner.class);
        when(sc.nextLine())
                .thenReturn("Movie 3 3")
                .thenReturn("1")
                .thenReturn("4")
                .thenReturn("")
                .thenReturn("2")
                .thenReturn("GIC0000")
                .thenReturn("")
                .thenReturn("3");
        new BookingMenu(sc);
        String[] actualOutput = cleanActualOutput(outputStreamCaptor.toString().split("\n"));
        assertEquals("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format", actualOutput[0]);
        assertEquals("> ", actualOutput[1]);
        assertEquals("Welcome to GIC Cinemas", actualOutput[2]);
        assertEquals("[1] Book tickets for Movie (9 seats available)",actualOutput[3]);
        assertEquals("[2] Check bookings", actualOutput[4]);
        assertEquals("[3] Exit", actualOutput[5]);
        assertEquals("Please enter your selection:", actualOutput[6]);
        assertEquals("Successfully reserved 4 Movie tickets.", actualOutput[10]);
        assertEquals("Booking id: GIC0001", actualOutput[11]);
        assertEquals("C . . . ", actualOutput[16]);
        assertEquals("B . o . ", actualOutput[17]);
        assertEquals("A o o o ", actualOutput[18]);
        assertEquals("Invalid booking id! Please try again.", actualOutput[33]);
    }


    private String[] cleanActualOutput(String[] outputs){

        for(int i= 0; i< outputs.length;i++){
            outputs[i] = outputs[i].replace("\r", "");
        }
        return outputs;
    }
}
