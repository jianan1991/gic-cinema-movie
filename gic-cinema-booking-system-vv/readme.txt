Hi, Thanks for reviewing my code.

In the Models package are objects/resources that is created

CinemaManager orchestrate between booking, movie and seats to make the booking. It also ensures that other classes do not have
access to the functions from Booking, Movie and Seat. They are only allowed to access what they are supposed to have access.

In the Utils package are Util and Constants, which are common function that we use, so that we do not need to initiate it multiple times.

BookingMenu is like the front-end of this application. Most of the Input Validation is done there.

To run the app please use the following instructions:
- Open the command terminal in the directory of the folder
- java -cp target/classes com.gic.cinema.booking.Main

Assumptions
- When seats are filled to the screen, the application will try to fill the seats by starting from the back from the cinema.
- If user requested seat has spilled over to the next row, it will start from the middle.

Test Coverage: 94%
