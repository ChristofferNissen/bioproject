import Models.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private static int selectID;                            // Used to store show_ID from view
    private static int reservationID;                       // stores id from view
    private static ArrayList<Showing> showingList;          // stores showings from db
    private static ArrayList<Reservation> reservationList;  // stores reservations from db

    // Program starts here
    public static void main(String[] args) {
        BookingGUI gui = new BookingGUI();  // get creates initial UI
        gui.makeFrame(getShows()); // get Creaetes the frame showing showings

        //initializes variable
        reservationID = 0;
    }

    //creates view of reservations
    static void makeReservationView(){
        getReservationsFromDB();                          //gets all reservations
        ReservationView r = new ReservationView();  //initializes view
        r.makeFrame(getReserv());                    //makes frame with reservations
    }

    //Convert from db to treeMap
    static TreeMap<Integer, String> getShows() {
        getShowingsFromDB(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    //Converts reservations from db to treemap
    static TreeMap<Integer, String> getReserv() {
        getReservationsFromDB(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer, String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            reservations.put(r.getReservation_id(), r.toString());
        }
        return reservations;
    }

    // Create a cinemaView preloaded with selected seats
    static void displayReservation(int selectedID) {
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
        CreateShowViewByID(show, input, true);
    }

    // Get info and create GUI
    static void CreateShowViewByID(int selectedID, String input, boolean changeReservation) {
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
             new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, true);
        } else {
             new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, false);
        }
    }

    //create reservation in db
    static boolean makeReservation(int tlf, int showID, String seats){
        if(tlf > 0) {                                                                           //if tlf number isn't empty
            // splitstring splits a string into an array of ints
            Reservation reservation = new Reservation(tlf, showID, splitSeatString(seats));     //create a reservation object
            if (MySqlConnection.makeReservation(reservation)) {                                 //make a reservation
                return true;                                                                    //return true if succesfull
            }
        }
        System.out.println("Booking failed");
        return false;   //else return false
    }

    //update a reservation
    static boolean updateReservation(String input, Boolean changeUpdate){
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
    static int[] splitSeatString(String seats) {
        //seperates at ","
        String[] arr = seats.split(",");

        //create an int array same length as string array
        int[] seat = new int[arr.length];

        //loop through the array, until length
        for(int i = 1; i < arr.length;i++){
            seat[i-1] = Integer.parseInt(arr[i]);
        }

        //return array
        return seat;
    }

    //Load all shows from DB
    private static void getShowingsFromDB() {
        showingList = MySqlConnection.getShowings("SELECT * FROM shows");
    }

    //Load all reservations from DB
    private static void getReservationsFromDB() {
        reservationList = MySqlConnection.getFromReservation("*");
    }

    //Returns a TreeMap over reservations made by specified ID
    static TreeMap<Integer, String> getReservationByID(String tlf_nr) {
        reservationList= MySqlConnection.getReservationsByPhone(tlf_nr);

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            reservations.put(r.getReservation_id(), r.toString());
        }

        return reservations;
    }

    //Delete reservation by ID
    static void deleteReservation(int a) {
        MySqlConnection.deleteReservation(a);
    }

    // a selectedID from a view
    static void storeSelectedID(int a) {
        selectID = a;
    }

    // Stores a reservationID from a view
    static void storeReservationID(int a) {
        reservationID = a;
    }

    // Returns a treeMap over showings which title contained search word
    static TreeMap<Integer, String> makeSearchTitle(String title) {
        showingList = MySqlConnection.getShowsByTitle(title);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    // Returns a treeMap over showings which date matched the specified date
    static TreeMap<Integer, String> makeSearchTime(String date) {
        showingList = MySqlConnection.getShowsByDate(date);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    // Converts ArrayList to string[]
    static String[] arrayListToStringArray(ArrayList<String> temp) {
        int i = 0;
        String[] var = new String[temp.size()];
        for (String r : temp) {
            var[i] = r;
            i++;
        }
        return var;
    }

    // Converts treeMap into datavariables for listView
    static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<String> stringModel, DefaultListModel<Map.Entry> listModel) {
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            stringModel.addElement(value);
            listModel.addElement(entry);
        }
    }
    // Converts treeMap into datavariables for listView
    static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<Map.Entry> listModel){

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            listModel.addElement(entry);
        }
    }
}