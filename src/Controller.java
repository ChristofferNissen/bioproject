import Models.MySqlConnection;
import Models.Reservation;
import Models.Showing;
import java.util.ArrayList;
import java.sql.*;
import java.util.TreeMap;

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



    private static TreeMap getShows(){
        getShowings();
        TreeMap showings = new TreeMap();
        for (Showing s : showingList)
            showings.put(s.getShow_id(),s.toString());
        return showings;
    }

    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");
    }

    public static ArrayList<Integer> getReservedSeats(int show_id){
        getReservations();
        ArrayList<Integer> reserv = new ArrayList<>();
        for (Reservation r : reservationList)
            reserv.add(r.getReserved_seat());
        return reserv;
    }

    private static void getReservations(){

        reservationList = MySqlConnection.getReservationQuery("*");

    }
}

