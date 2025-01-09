package com.gic.cinema.booking;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Utils.Constants;

import java.util.Scanner;

public class BookingMenu {
    private Scanner sc;

    private CinemaManager cinemaManager;

    public BookingMenu(Scanner sc) throws Exception {
        this.sc = sc;
        this.ConfigurationInput();
    }

    public void ConfigurationInput() throws Exception{
        System.out.println("Please define movie title and seating map in [Title] [Row] [SeatsPerRow] format");
        System.out.print("> ");
        String cinemaConfigInput = sc.nextLine();
        System.out.println();
        if(cinemaConfigInput != null && cinemaConfigInput.matches("^[a-zA-Z0-9]* [0-9]?[0-9]( [0-9]?[0-9]$)") ) {
            cinemaConfigInput = cinemaConfigInput.trim();
            String[] cinemaConfigArray = cinemaConfigInput.split(" ");
            int numOfRow = Integer.parseInt(cinemaConfigArray[1]);
            int numOfSeatsPerRow = Integer.parseInt(cinemaConfigArray[2]);
            if(numOfRow < Constants.MOVIE_MAX_NUMBER_OF_ROW && numOfRow > 0 &&
                    numOfSeatsPerRow < Constants.MOVIE_MAX_NUMBER_OF_SEATS_PER_ROW && numOfSeatsPerRow > 0) {
                cinemaManager = new CinemaManager(cinemaConfigArray[0], numOfRow, numOfSeatsPerRow);
            } else {
                throw new Exception("Invalid [Row] or/and [SeatsPerRow]");
            }
        } else {
            throw new Exception("Invalid Cinema Config Input");
        }

        String menuInput = null;
        do {
            menuInput= getBookingRequest();

            switch (menuInput) {
                case "1":
                    displayBookingWorkflow();
                    break;
                case "2":
                    displayCheckBookingWorkflow();
                    break;
                case "3":
                    System.out.println("Thank you for using GIC Cinema System. Bye!");
                    return;
                default:
                    System.out.println("Invalid input, please select a valid option! ");
                    System.out.println();
            }
        }while (menuInput!= "3");

    }
    private void displayBookingWorkflow() {
        boolean validRequest = false;
        int userInputInt = 0;
        String userInput = "";
        while (!validRequest){
            System.out.println("Enter number of tickets to book, or enter blank to go back to main menu:");
            System.out.print("> ");
            userInput = sc.nextLine();
            System.out.println();

            if (userInput.isEmpty())
                return;
            try{
                userInputInt = Integer.parseInt(userInput);
            }catch (NumberFormatException e){
                System.out.println("Invalid input! Please try again.");
                System.out.println();
                continue;
            }

            if (userInputInt < 1 || userInputInt > cinemaManager.getEmptySeatsLeft()) {
                if(userInputInt < 1) System.out.println("Invalid input! Please enter a number larger than 0");

                if(userInputInt > cinemaManager.getEmptySeatsLeft()) {
                    System.out.printf("Sorry, there are only %d seats available.%n", cinemaManager.getEmptySeatsLeft());
                    System.out.println();
                    System.out.println("Invalid input! Please try again.");
                    System.out.println();
                }
            } else {
                validRequest = true;
            }
        }

        Booking booking = cinemaManager.selectByDefault(userInputInt);
        System.out.printf("Successfully reserved %d %s tickets.%n", userInputInt, cinemaManager.getMovieTitle());

        //reset valid request
        validRequest = false;
        while(!validRequest){
            cinemaManager.displayBookingSeats(booking);
            System.out.println("Enter blank to accept seat selection, or enter new seating position:");
            System.out.print("> ");
            userInput = sc.nextLine();
            System.out.println();
            if(userInput.isEmpty()) {
                System.out.printf("Booking id: %s confirmed.%n", booking.getBookingId());
                System.out.println();
                validRequest = true;
            }

            if(validRequest != true && !cinemaManager.updateBooking(booking, userInput)){
                System.out.println("Invalid seat number! Please try again.");
            }
        }
    }
    private void displayCheckBookingWorkflow() {
        boolean validRequest = false;
        while(!validRequest){
            System.out.println("Enter booking id, or blank to go back to main menu");
            System.out.print("> ");
            String userInput = sc.nextLine();
            System.out.println();
            if(userInput.isEmpty()) {
                return;
            }
            Booking bookingFound = cinemaManager.getBookingById(userInput).orElse(null);
            if(bookingFound != null){
                cinemaManager.displayBookingSeats(bookingFound);
            }else{
                System.out.println("Invalid booking id! Please try again.");
            }
        }
    }
    private void displayBookingMenu(){
        System.out.println("Welcome to GIC Cinemas");
        System.out.printf("%s %s (%d seats available)%n", "[1] Book tickets for",
                cinemaManager.getMovieTitle(),
                cinemaManager.getEmptySeatsLeft());
        System.out.println("[2] Check bookings");
        System.out.println("[3] Exit");
        System.out.println("Please enter your selection:");
        System.out.print("> ");

    }
    private String getBookingRequest(){
        String input = null;
        do {
            displayBookingMenu();
            input = sc.nextLine().trim();
            System.out.println();
        }while (!input.matches("^[0-3]*$"));

        return input;
    }




}
