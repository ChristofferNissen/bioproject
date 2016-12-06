
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
    private static CinemaView cinemaView;

    public static void main (String[] args){
        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(getShows());

        //cinemaView = new CinemaView();

        //getShowByID(2);

    }

    //
    private static TreeMap<Integer,String> getShows(){
        getShowings(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer,String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
        }
        // The TreeMap to be returned
        return showings;
    }


    // Get info and create GUI
    public static void getShowByID(int selectedID) {
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationID(selectedID); // Return the reservationID for the chosen showing

        //husk at lave exceptions på null
        ArrayList<Integer> reserved_seats = new ArrayList<>();

        for(int id : reservation_ids) {
            // Get seats reserved for this show
            reserved_seats = MySqlConnection.getReservedSeats(id);
        }
        // get show info
        Showing show = MySqlConnection.getShowByID(selectedID);
        //get info about the hall
        Hall hall = MySqlConnection.getHallByID(show.getHall_id());

        // Create cinemaView gui based on data from DB
        CinemaView c = new CinemaView(hall.getRows(), hall.getSeats(),
                show.getTitle(), show.getShow_id(), reserved_seats);

    }

    public static boolean makeReservation(int tlf, int showID, String seats){
        Reservation reservation = new Reservation(tlf, showID,splitSeatString(seats));
        if(MySqlConnection.makeReservation(reservation)){
            return true;
        }
        System.out.println("make reservation: " + tlf + showID);
        return false;
    }

    private static int[] splitSeatString(String seats){
        String[] arr = seats.split(",");
        int[] seat = new int[arr.length];


        for(int i = 1; i < arr.length;i++){
            seat[i-1] = Integer.parseInt(arr[i]);
        }

        System.out.println(seat.toString());

        return seat;
    }

    //Load all shows from DB
    private static void getShowings(){
        showingList = MySqlConnection.getShowingQuery("*");
    }

    //
    private static void getReservations(){
        reservationList = MySqlConnection.getReservationQuery("*");
    }

    //
    public static void storeSelectedID(int a) {
        selectID = a;
    }
}

