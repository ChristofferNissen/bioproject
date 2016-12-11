import Models.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Controls flow of data through the program and handles input from gui
 */
public class Controller {

    private static int selectID;                            // Used to store show_ID from view
    private static int reservationID;                       // stores id from view
    private static ArrayList<Showing> showingList;          // stores showings from db
    private static ArrayList<Reservation> reservationList;  // stores reservations from db

    /**
     * Start of the program
     */
    public static void main(String[] args) {
        BookingGUI gui = new BookingGUI();  // get creates initial UI
        gui.makeFrame(getShows()); // get Creates the frame showing showings

        //initializes variable
        reservationID = 0;
    }

    //creates view of reservations
    static void makeReservationView(){
        getReservationsFromDB();                          //gets all reservations
        ReservationView r = new ReservationView();  //initializes view
        r.makeFrame(getReserv());                    //makes frame with reservations
    }

    /**
     * Convert showings from db to treeMap
     * @return treemap of shows
     */
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

    /**
     * Converts reservations from db to treemap
     * @return treemap of reservations
     */
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

    /**
     * create reservation in db
     * @param tlf       tlf as customer id
     * @param showID    showID
     * @param seats     string of seats
     * @return  true if reservation successful
     * @return  false if reservation failed
     */
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

    /**
     * update a reservation
     * @param input         String with seats
     * @param changeUpdate  if seats should be updated this must be true
     * @return true if successful
     * @return false if unsuccessful
     */
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

    /**
     * splits string seats to an array
     * @param seats     String of seats seperated by ","
     * @return  return an array of ints being seats
     */
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

    /**
     * Load all shows from DB into showinglist
     */
    private static void getShowingsFromDB() {
        showingList = MySqlConnection.getShowingQuery("SELECT * FROM shows");
    }

    /**
     * Load all reservations from DB into reservationlist
     */
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

    /**
     * Delete reservation by ID
     * @param a id of reservation to delete
     */
    static void deleteReservation(int a) {
        MySqlConnection.deleteReservation(a);
    }

    /**
     * Stores an id in class variable
     * @param a id to store
     */
    static void storeSelectedID(int a) {
        selectID = a;
    }

    /**
     * Stores a reservationID from view
     * @param a id to store
     */
    static void storeReservationID(int a) {
        reservationID = a;
    }

    /**
     * Returns a treeMap of showings which title contained search word
     * @param title title searched
     * @return Treemap of showings
     */
    static TreeMap<Integer, String> makeSearchTitle(String title) {
        showingList = MySqlConnection.getShowsByTitle(title);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    /**
     * Returns a treeMap over showings which date matched the specified date
     * @param date String describing date searched
     * @return  Treemap of showings
     */
    static TreeMap<Integer, String> makeSearchTime(String date) {
        showingList = MySqlConnection.getShowsByDate(date);

        TreeMap<Integer, String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    /**
     * Converts ArrayList to string[]
     * @param temp Arraylist to be converted
     * @return  String array
     */
    static String[] arrayListToStringArray(ArrayList<String> temp) {
        int i = 0;
        String[] var = new String[temp.size()];
        for (String r : temp) {
            var[i] = r;
            i++;
        }
        return var;
    }

    /**
     * Converts treeMap into datavariables for listView
     * @param temp          temporary arraylist
     * @param treeMap       Treemap of values
     * @param stringModel   ListModel
     * @param listModel     listmodel
     */
    static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<String> stringModel, DefaultListModel<Map.Entry> listModel) {
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            stringModel.addElement(value);
            listModel.addElement(entry);
        }
    }

    /**
     * Converts treeMap into datavariables for listView
     * @param temp
     * @param treeMap
     * @param listModel
     */
    static void getDataFromTreeMap(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                          DefaultListModel<Map.Entry> listModel){

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            listModel.addElement(entry);
        }
    }
}