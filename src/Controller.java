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
        getShowings();


    }

    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");

        // Extract data
        for(Models.Showing s : showingList) {

            System.out.println(s.toString());

        }


    }

    private static void getReservations(){
        reservationList = MySqlConnection.getReservationQuery("*");

        // Extract data
        for(Models.Reservation r : reservationList){

            System.out.println(r.toString());

        }
    }
}

