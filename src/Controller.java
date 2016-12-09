
import Models.*;
import apple.laf.JRSUIUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private static int selectID;
    private static int reservationID;                       //stores id from view
    private static ArrayList<Showing> showingList; //       // stores showings from db
    private static ArrayList<Reservation> reservationList; //

    // Program starts here
    public static void main(String[] args) {
        BookingGUI gui = new BookingGUI();  // get creates initial UI
        gui.makeFrame(getShows()); // get Creaetes the frame showing showings

        //initializes variable
        reservationID = 0;
    }


    //creates view of reservations
    public static void makeReservationView(){
        getReservations();                          //gets all reservations
        ReservationView r = new ReservationView();  //initializes view
        r.makeFrame(getRervs());                    //makes frame with reservations
    }

    //Convert arrayList to treemap
    public static TreeMap<Integer, String> getShows() {
        getShowings(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    public static TreeMap<Integer, String> getRervs() {
        getReservations(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer, String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            System.out.println(r.toString());
            reservations.put(r.getReservation_id(), r.toString());
        }
        return reservations;
    }

    public static void displayReservation(int selectedID) {



        //
        //
        //husk at lave exceptions på null
        //
        //
        ArrayList<Integer> selected_seats;
        // Get seats reserved for this reservation
        selected_seats = MySqlConnection.getReservedSeats(selectedID);
        String input = "";

        //convert int array to string
        for(int i : selected_seats) {
            input = input + "," + i;
        }

        //get show id
        ArrayList<Integer> show_id = MySqlConnection.getShowID(selectedID);

        int show = 0;
        // save id as integer
        for (int i : show_id) {
            show = i;
        }

        // show = show_id, input = reserved seats as string
        getShowByID(show, input, true);
    }

    // Get info and create GUI
    public static void getShowByID(int selectedID, String input, boolean changeReservation) {
        // Return the reservationID for the chosen showing
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationID(selectedID);
        ArrayList<Integer> reserved_seats = new ArrayList<>();

        for (int id : reservation_ids) {
            // Get seats reserved for this show
            ArrayList<Integer> temp = new ArrayList<>();
            temp = MySqlConnection.getReservedSeats(id);
            for (int i : temp) {
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

    //create reservation in db
    public static boolean makeReservation(int tlf, int showID, String seats){
        if(tlf > 0) {       //if tlf number isn't empty
            //splitstring splits a string into an array of ints
            Reservation reservation = new Reservation(tlf, showID, splitSeatString(seats)); //create a reservation object
            if (MySqlConnection.makeReservation(reservation)) { //make a reservation
                return true;    //return true if succesfull
            }
        }
        System.out.println("make reservation: " + tlf + showID);
        return false;   //else return false
    }

    //update a reservation
    public static boolean updateReservation(String input, Boolean changeUpdate){
        //split string of selected seats to int array
        int[] inputSplit = splitSeatString(input);

        //update reservation using reservation id
        if(changeUpdate){
            MySqlConnection.updateReservation(reservationID,inputSplit);
            return true;    //return true if succesful
        } else {
            return false;   //return false if fail
        }
    }


    //splits seats to an array
    public static int[] splitSeatString(String seats) {
        //seperates at ","
        String[] arr = seats.split(",");

        //create an int array same length as string array
        int[] seat = new int[arr.length];





        //loop through the array, until length
        for(int i = 1; i < arr.length;i++){
            seat[i-1] = Integer.parseInt(arr[i]);
        }

        System.out.println(seat.toString());

        //return array
        return seat;
    }

    //Load all shows from DB
    private static void getShowings() {
        showingList = MySqlConnection.getShowingQuery("SELECT * FROM shows");
    }

    //Load all reservations from DB
    private static void getReservations() {
        reservationList = MySqlConnection.getFromReservation("*");
    }

    public static TreeMap<Integer, String> getReservationByID(String tlf_nr) {
        reservationList= MySqlConnection.getReservationsByPhone(tlf_nr);

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            //System.out.println(r.toString());
            reservations.put(r.getReservation_id(), r.toString());
        }

        return reservations;
    }

    // a selectedID from a view
    public static void storeSelectedID(int a) {
        selectID = a;
    }

    // Stores a reservationID from a view
    public static void storeReservationID(int a) {
        reservationID = a;
    }

    public static TreeMap<Integer, String> makeSearchTitle(String title) {
        showingList = MySqlConnection.getShowsByTitle(title);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            //System.out.println(s.getShow_id() + "" + s.toString());
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;

    }

    public static TreeMap<Integer, String> makeSearchTime(String date) {
        showingList = MySqlConnection.getShowsByDate(date);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            //System.out.println(s.getShow_id() + "" + s.toString());
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    // Converts ArrayList to string[]
    public static String[] arrayListToStringArray(ArrayList<String> temp) {
        int i = 0;
        String[] var = new String[temp.size()];
        for (String r : temp) {

            System.out.println(r);
            var[i] = r;
            i++;
        }
        return var;
    }

    // Converts treeMap into datavariables for listView
    public static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<String> stringModel, DefaultListModel<Map.Entry> listModel) {
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            stringModel.addElement(value);
            listModel.addElement(entry);
        }
    }

    // Converts treeMap into datavariables for listView
    public static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<Map.Entry> listModel){

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            listModel.addElement(entry);
        }
    }


}