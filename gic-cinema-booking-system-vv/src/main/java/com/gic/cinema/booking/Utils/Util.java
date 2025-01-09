package com.gic.cinema.booking.Utils;

import com.gic.cinema.booking.Model.Booking;
import com.gic.cinema.booking.Model.Movie;
import com.gic.cinema.booking.Model.Seat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Util {

    //Returns Number to Alphabet
    //Adding 65 to include 0
    public static String NumerictoAlphabet(int i) {
        return String.valueOf((char)(i + 65));
    }

    public static int AlphabetToNumeric(char alphabet){
        return (alphabet - 'A' );
    }

    public static char[][] generateSeatDisplay(Booking booking, Movie movie){
        char [][] charToDisplayArray = new char[movie.getNumOfRows() + 3][0];


        //Screen
        int screenStartPos = Math.max(0,(movie.getNumofSeatsPerRow()*2+1)/2-5);
        charToDisplayArray[0] = String.format("%s%s", " ".repeat(screenStartPos), Constants.DISPLAY_SCREEN).toCharArray();
        charToDisplayArray[1] = Constants.DISPLAY_SCREEN_DIVIDER.repeat(movie.getNumofSeatsPerRow()*2+2).toCharArray();

        int behindScreen = 2;

        //Seats
        for(int row = movie.getNumofSeatsPerRow()-1; row >= 0 ; row-- ){
            Seat[] currentSeatRow = movie.getMovieMap().get(NumerictoAlphabet(row));
            String rowOutput = NumerictoAlphabet(row) + Constants.DISPLAY_SEAT_SPACE;
            for(int col = 0; col< movie.getNumofSeatsPerRow(); col++){
                String inputChar = Constants.DISPLAY_SEAT_EMPTY;

                if(currentSeatRow[col].getBookingId() != null){
                    if(booking.getSeats().contains(currentSeatRow[col])){
                        inputChar = Constants.DISPLAY_SEAT_SELECTED;
                    }else{
                        inputChar = Constants.DISPLAY_SEAT_OTHERS;
                    }
                }
                String spacesBetweenSeats = Constants.DISPLAY_SEAT_SPACE;
                if (col > 8)
                    spacesBetweenSeats = spacesBetweenSeats.concat(" ");
                rowOutput = String.format("%s%s%s", rowOutput, inputChar, spacesBetweenSeats);

            }
            charToDisplayArray[behindScreen] = rowOutput.toCharArray();
            behindScreen++;
        }
        String rowOutput = "  ";
        rowOutput += IntStream.range(1,movie.getNumofSeatsPerRow()+1).mapToObj(x->String.valueOf(x)).collect(Collectors.joining(Constants.DISPLAY_SEAT_SPACE));
        charToDisplayArray[behindScreen] = rowOutput.toCharArray();

        return charToDisplayArray;
    }
}
