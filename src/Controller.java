import Models.MySqlConnection;
import Models.Reservation;
import java.util.ArrayList;
import java.sql.*;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private String name;
    private int number;
    private static ArrayList<Models.Showing> showingList;
    private static ArrayList<Models.Reservation> reservationList;
    private static Cinema cinema;

    public static void main (String[] args){



        //cinema = new Cinema();
        //MySqlConnection.getReservationQuery("*");

        getReservations();

    }

    private void getShowings(){
        showingList = new ArrayList<Models.Showing>();



    }

    private static void getReservations(){
        reservationList = MySqlConnection.getReservationQuery("*");

        // Extract data
        for(Reservation r : reservationList){

            System.out.println(r.toString());

        }
    }
}
