
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

    private static TreeMap<Integer,String> getShows(){
        getShowings(); // update showings from DB

        // Convert from arraylist to TreeMap, return the TreeMap.
        TreeMap<Integer,String> showings = new TreeMap();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
            //System.out.println(s.toString());
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

