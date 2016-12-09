
import Models.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private String name;
    private static int selectID;
    private static int reservationID;
    private static ArrayList<Showing> showingList;
    private static ArrayList<Reservation> reservationList;
    private static ArrayList<Reservation> reservList;
    private static CinemaView cinemaView;

    public static void main (String[] args){
        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(getShows());

        reservationID = 0;

    }

    public static void makeReservationView(){
        getReservations();
        ReservationView r = new ReservationView();
        r.makeFrame(getRervs());
    }


    /*public static void showReservation(String id) {
=======
    /*
    public static void showReservation(String id) {
>>>>>>> fd284e78bc69fd5999d7e6277fb79c6fd91e50ea

        // get reservation specific reserved seats in from of ",12,13,14" strings
        // pass this to getREservationID

        getReservationByID(id);
<<<<<<< HEAD
    }*/


    //Convert arrayList to treemap
    public static TreeMap<Integer,String> getShows(){
        getShowings(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer,String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }
    public static TreeMap<Integer,String> getRervs() {
        getReservations(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            System.out.println(r.toString());
            reservations.put(r.getReservation_id(),r.toString());
        }

        return reservations;

    }

   /* private static TreeMap<Integer,String> getRervsByID() {
        getReservations(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            System.out.println(r.toString());
            reservations.put(r.getReservation_id(),r.toString());
        }

        return reservations;

    }*/

    public static void displayReservation(int selectedID) {
        //husk at lave exceptions på null
        ArrayList<Integer> selected_seats;
        // Get seats reserved for this reservation
        selected_seats = MySqlConnection.getReservedSeats(selectedID);
        String input ="";

        for(int i : selected_seats) {
            input = input + "," + i;
        }

        ArrayList<Integer> show_id = MySqlConnection.getShowID(selectedID);

        int show = 0;
        // save id as integer
        for(int i : show_id) {
            show = i;
        }

         // show = show_id, input = reserved seats as string
        getShowByID(show,input,true);
    }

    // Get info and create GUI
    public static void getShowByID(int selectedID, String input, boolean changeReservation) {
        // Return the reservationID for the chosen showing
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationID(selectedID);
        ArrayList<Integer> reserved_seats = new ArrayList<>();

        for(int id : reservation_ids) {
            // Get seats reserved for this show
            ArrayList<Integer> temp = new ArrayList<>();

            temp = MySqlConnection.getReservedSeats(id);
            for(int i : temp) {
                reserved_seats.add(i);
            }
        }

        // get show info
        Showing show = MySqlConnection.getShowByID(selectedID);
        //get info about the hall
        Hall hall = MySqlConnection.getHallByID(show.getHall_id());

        // Create cinemaView gui based on data from DB


        if(changeReservation) {
            CinemaView c = new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, true);
        } else {
            CinemaView c = new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, false);
        }
    }

    public static boolean makeReservation(int tlf, int showID, String seats){
        if(tlf > 0) {
            Reservation reservation = new Reservation(tlf, showID, splitSeatString(seats));
            if (MySqlConnection.makeReservation(reservation)) {
                return true;
            }
        }
        System.out.println("make reservation: " + tlf + showID);
        return false;
    }

    public static boolean updateReservation(String input, Boolean changeUpdate){
        int[] inputSplit = splitSeatString(input);

        if(changeUpdate){
            MySqlConnection.updateReservation(reservationID,inputSplit);
            return true;
        } else {
            return false;
        }
    }


    //splits seats to an array
    public static int[] splitSeatString(String seats){
        //seperates at ","
        String[] arr = seats.split(",");

        int[] seat = new int[arr.length];

        for(int i = 1; i < arr.length;i++){
            seat[i-1] = Integer.parseInt(arr[i]);
        }

        System.out.println(seat.toString());

        return seat;
    }


    /*public static void updateReserv(String phone){
        ArrayList<Reservation> res = new ArrayList<>();
        res = Models.MySqlConnection.getReservationsByID(phone);




        //String reservedSeats;

        //getShowByID();

    }*/
    //Load all shows from DB
    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("SELECT * FROM shows");
    }

    //Load all reservations from DB
    private static void getReservations(){
        reservationList = MySqlConnection.getFromReservation("*");
    }

    public static TreeMap<Integer, String> getReservationByID(String tlf_nr) {
        reservList= MySqlConnection.getReservationsByPhone(tlf_nr);

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservList) {
            //System.out.println(r.toString());
            reservations.put(r.getReservation_id(),r.toString());
        }

        return reservations;
    }

    //
    public static void storeSelectedID(int a) {
        selectID = a;
    }

    public static void storeReservationID(int a) {
        reservationID = a;
    }

    /*public static void deleteReservation(String tlf_nr) {
        MySqlConnection.deleteAllReservations(tlf_nr);
    }*/

    public static TreeMap<Integer, String> makeSearchTitle(String title) {
        showingList = MySqlConnection.getShowsByTitle(title);

        TreeMap<Integer,String> showings = new TreeMap();
        for (Showing s : showingList) {
            //System.out.println(s.getShow_id() + "" + s.toString());
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;

    }

    public static TreeMap<Integer,String> makeSearchTime(String date) {
        showingList = MySqlConnection.getShowsByDate(date);

        TreeMap<Integer,String> showings = new TreeMap();
        for (Showing s : showingList) {
            //System.out.println(s.getShow_id() + "" + s.toString());
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

}