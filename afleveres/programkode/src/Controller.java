import Models.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Controls flow of data through the program and handles input from gui
 */
public class Controller {

    private static int reservationID;                       // stores id from view
    private static ArrayList<Showing> showingList;          // stores showings from db
    private static ArrayList<Reservation> reservationList;  // stores reservations from db

    /**
     * Start of the program
     */
    public static void main(String[] args) {
        BookingView gui = new BookingView();  // get creates initial UI
        gui.makeFrame(updateShowingList()); // get Creates the frame showing showings

        //initializes variable
        reservationID = 0;
    }

    /**
     * Creates the Reservation Window when "Change reservation" is pressed
     */
    static boolean createReservationView(){
        getReservationsFromDB();                          //gets all reservations
        ReservationView r = new ReservationView();  //initializes view
        return r.makeFrame(updateReservationList());                    //makes frame with reservations
    }

    /**
     * Convert showings from db to treeMap <Integer,String>
     * @return treemap of shows in format <show_id,s.toString()>
     */
    static TreeMap<Integer, String> updateShowingList() {
        getShowingsFromDB(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer, String> showings = new TreeMap<>();
        for (Showing s : showingList) showings.put(s.getShow_id(), s.toString());
        // The TreeMap to be returned
        return showings;
    }

    /**
     * Converts reservations from db to treemap <Integer,String>
     * @return treemap of reservations as <reservation_id,r.toString()>
     */
    static TreeMap<Integer, String> updateReservationList() {
        getReservationsFromDB(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer, String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            reservations.put(r.getReservation_id(), r.toString());
        }
        return reservations;
    }

    /**
     * Loads a cinnemaView with selected_seats accordingly to a reservation_id for when updating a reservation
     * @param reservationID Unique reservation ID
     */
    static boolean displayReservation(int reservationID) {
        ArrayList<Integer> selected_seats;
        // Get seats reserved for this reservation
        selected_seats = MySqlConnection.getReservedSeats(reservationID);
        String input = "";

        //convert int array to string
        for(int i : selected_seats) {
            input = input + "," + i;
        }

        //get show id
        ArrayList<Integer> show_id = MySqlConnection.GetShowByReservationId(reservationID);

        int show = 0;
        // save id as integer
        for (int i : show_id) {
            show = i;
        }

        // show = show_id, input = reserved seats as string

        return CreateCinemaViewByShowID(show, input, true);

    }

    // Get info and create GUI
    static boolean CreateCinemaViewByShowID(int showID, String input, boolean changeReservation) {
        // Return the reservationID for the chosen showing
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationByShowID(showID);
        ArrayList<Integer> reserved_seats = new ArrayList<>();

        for (int id : reservation_ids) {
            // Get seats reserved for this show
            ArrayList<Integer> temp;
            temp = MySqlConnection.getReservedSeats(id);
            reserved_seats.addAll(temp);

        }

        // get show info
        Showing show = MySqlConnection.getShowByShowID(showID);
        //get info about the hall
        Hall hall = MySqlConnection.getHallByHallID(show.getHall_id());

        // Create cinemaView gui based on data from DB
        if(changeReservation) {
             new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, true);
                    return true;
        }
        if (!changeReservation) {
             new CinemaView(hall.getRows(), hall.getSeats(),
                    show.getTitle(), show.getTime(), show.getDate(),
                    show.getHall_id(), show.getShow_id(), reserved_seats, input, false);
                    return true;
        }
        return false;
    }

    /**
     * create reservation in db
     * @param tlf       tlf as customer id
     * @param showID    showID
     * @param seats     string of seats
     * @return  true if reservation successful
     */
    static boolean createReservation(int tlf, int showID, String seats){
        if(tlf > 0) {                                                                           //if tlf number isn't empty
            // splitstring splits a string into an array of ints
            Reservation reservation = new Reservation(tlf, showID, splitSeatString(seats));     //create a reservation object
            if (MySqlConnection.createReservation(reservation)) {                                 //make a reservation
                return true;                                                                    //return true if succesfull
            }
        }
        return false;   //else return false
    }

    /**
     * update a reservation
     * @param input         String with seats
     * @param changeUpdate  if seats should be updated this must be true
     * @return true if successful
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
    // for testing
    static boolean updateReservation(String input, Boolean changeUpdate,int reservationid){
        //split string of selected seats to int array
        int[] inputSplit = splitSeatString(input);

        //update reservation using reservation id
        if(changeUpdate){
            MySqlConnection.updateReservation(reservationid,inputSplit);
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
        showingList = MySqlConnection.getShowings("SELECT * FROM shows");
    }

    /**
     * Load all reservations from DB into reservationlist
     */
    private static void getReservationsFromDB() {
        reservationList = MySqlConnection.getReservations();
    }

    //Returns a TreeMap over reservations made by specified ID
    static TreeMap<Integer, String> getReservationsByPhone(String tlf_nr) {
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
     * @param a : id for a reservation to delete
     */
    static boolean deleteReservation(int a) {
        return MySqlConnection.deleteReservation(a);
    }

    /**
     * Stores a reservationID from view
     * @param a reservation_id to store from a view class
     */
    static void storeReservationID(int a) {
        reservationID = a;
    }

    /**
     * Returns a treeMap of showings which title contained search word
     * @param title         title searched
     * @return Treemap      of showings
     */
    static TreeMap<Integer, String> showsMatchingSearchTitle(String title) {
        showingList = MySqlConnection.getShowsByTitle(title);

        TreeMap<Integer, String> showings = new TreeMap<>();
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
    static TreeMap<Integer, String> showsMatchingSearchDate(String date) {
        showingList = MySqlConnection.getShowsByDate(date);

        TreeMap<Integer, String> showings = new TreeMap<>();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }

    /**
     * Converts ArrayList to string[]
     * @param stringstoConvert Arraylist to be converted
     * @return  String array
     */
    static String[] arrayListToStringArray(ArrayList<String> stringstoConvert) {
        int i = 0;
        String[] var = new String[stringstoConvert.size()];
        for (String r : stringstoConvert) {
            var[i] = r;
            i++;
        }
        return var;
    }

    /**
     * Converts treeMap into datavariables for listView
     * @param temp          temporary arraylist
     * @param treeMap       Treemap of values
     * @param stringModel   listModel (The elements displayed)
     * @param listModel     listmodel
     */
    static void processTreeMapForView(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
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
     * @param temp ArrayList<String>
     * @param treeMap TreeMap<Integer,String>
     * @param listModel DefaultListModel<Map.Entry>
     */
    static void processTreeMapForView(ArrayList<String> temp, TreeMap<Integer, String> treeMap,
                                      DefaultListModel<Map.Entry> listModel){

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            listModel.addElement(entry);
        }
    }
}