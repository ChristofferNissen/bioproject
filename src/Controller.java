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
    private static int selectID;
    private static ArrayList<Showing> showingList;
    private static ArrayList<Reservation> reservationList;
    private static Cinema cinema;

    public static void main (String[] args){
        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(getShows());

        cinema = new Cinema();
        //MySqlConnection.getReservationQuery("*");

        //getReservations();
        //getShowings();

    }

    private static TreeMap<Integer,String> getShows(){
        getShowings();
        TreeMap<Integer,String> showings = new TreeMap<Integer,String>();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
            System.out.println(s.toString());
        }
        return showings;
    }

    public static ArrayList<Integer> getReservedSeats(int show_id){
        getReservations();
        ArrayList<Integer> reserv = new ArrayList<>();
        for (Reservation r : reservationList)
            reserv.add(r.getReserved_seat());
        return reserv;
    }

    private static void getShowByID(int a) {
        ArrayList<Integer> reserved_seats = MySqlConnection.getReservedSeatsShowing(a);


    }

    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");
    }

    private static void getReservations(){

        reservationList = MySqlConnection.getReservationQuery("*");

    }

    public static void storeSelectedID(int a) {
        selectID = a;

    }
}

