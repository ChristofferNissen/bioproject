
import Models.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by cn on 30/11/2016.
 */
public class Controller {

    private String name;
    private static int selectID;
    private static ArrayList<Showing> showingList;
    private static ArrayList<Reservation> reservationList;
    private static ArrayList<Reservation> reservList;
    private static CinemaView cinemaView;

    public static void main (String[] args){
        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(getShows());


    }

    public static void makeReservationView(){
        getReservations();
        ReservationView r = new ReservationView();
        r.makeFrame(getRervs());
    }

    public static void showReservation(String id) {

        // get reservation specific reserved seats in from of ",12,13,14" strings
        // pass this to getREservationID

        getReservationByID(id);
    }

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

    private static TreeMap<Integer,String> getRervsByID() {
        getReservations(); // update reservations from DB

        // Convert from ArrayList to TreeMap, return the TreeMap
        TreeMap<Integer,String> reservations = new TreeMap<>();
        for (Reservation r : reservationList) {
            System.out.println(r.toString());
            reservations.put(r.getReservation_id(),r.toString());
        }

        return reservations;

    }

    public static void reservedSeatsToString(int selectedID) {
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

        for(int i : show_id) {
            show = i;
        }


        getShowByID(show,input);
    }


    // Get info and create GUI
    public static void getShowByID(int selectedID, String input) {
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationID(selectedID); // Return the reservationID for the chosen showing

        //husk at lave exceptions på null
        ArrayList<Integer> reserved_seats = new ArrayList<>();

        for(int id : reservation_ids) {
            // Get seats reserved for this show
            ArrayList<Integer> temp = new ArrayList<>();

            temp = MySqlConnection.getReservedSeats(id);
            for(int i : temp) {
                reserved_seats.add(i);
            }

            for(int i : reserved_seats) {
                System.out.println(i + "BUBU");
            }


        }
        // get show info
        Showing show = MySqlConnection.getShowByID(selectedID);
        //get info about the hall
        Hall hall = MySqlConnection.getHallByID(show.getHall_id());

        // Create cinemaView gui based on data from DB
        CinemaView c = new CinemaView(hall.getRows(), hall.getSeats(),
                show.getTitle(), show.getTime(), show.getDate(), show.getHall_id(), show.getShow_id(), reserved_seats, input);
    }

    public static boolean makeReservation(int tlf, int showID, String seats){
        Reservation reservation = new Reservation(tlf, showID,splitSeatString(seats));
        if(MySqlConnection.makeReservation(reservation)){
            return true;
        }
        System.out.println("make reservation: " + tlf + showID);
        return false;
    }


    //splits seats to an array
    private static int[] splitSeatString(String seats){
        //seperates at ","
        String[] arr = seats.split(",");

        int[] seat = new int[arr.length];

        for(int i = 1; i < arr.length;i++){
            seat[i-1] = Integer.parseInt(arr[i]);
        }

        System.out.println(seat.toString());

        return seat;
    }

    public static void updateReservation(String phone){
        ArrayList<Reservation> res = new ArrayList<>();
        res = Models.MySqlConnection.getReservationsByID(phone);


        //String reservedSeats;

        //getShowByID();

    }

    //Load all shows from DB
    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");
    }

    //Load all reservations from DB
    private static void getReservations(){
        reservationList = MySqlConnection.getReservationQuery("*");
    }

    public static TreeMap<Integer, String> getReservationByID(String tlf_nr) {
        reservList= MySqlConnection.getReservationsByID(tlf_nr);

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

    public static void deleteReservation(String tlf_nr) {
        MySqlConnection.deleteAllReservations(tlf_nr);
    }

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