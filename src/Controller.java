import Models.Seat;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private String name;
    private  int tal;
    private static BookingGUI bookingGUI;
    private static Cinema cinema;

    public static void main (String[] args){

        Seat seat = new Seat(0);

        /* bookingGUI = new BookingGUI();

         */

        cinema = new Cinema();


    }


}
