import Models.MySqlConnection;
import Models.Reservation;
import Models.Showing;
import java.util.ArrayList;
import java.sql.*;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private String name;
    private int number;
    private static ArrayList<Showing> showingList;
    private static ArrayList<Reservation> reservationList;
    private static Cinema cinema;

    public static void main (String[] args){

        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(showingList);

        cinema = new Cinema();
        //MySqlConnection.getReservationQuery("*");

        //getReservations();
        //getShowings();


    }

    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");

        // Extract data

            for (Showing s : showingList) {

                System.out.println(s.toString());
            }

    }

    private static void getReservations(){
        reservationList = MySqlConnection.getReservationQuery("*");

        // Extract data
            for (Reservation r : reservationList) {

                System.out.println(r.toString());

            }



        Reservation k = reservationList.get(0);
        System.out.println(k.getReservation_id());

    }
}

