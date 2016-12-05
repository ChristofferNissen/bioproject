
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
    private static Cinema cinema;

    public static void main (String[] args){
        getShowings();
        BookingGUI gui = new BookingGUI();
        gui.makeFrame(getShows());

        //cinema = new Cinema();

        //getShowByID(2);

    }

    private static TreeMap<Integer,String> getShows(){
        getShowings();
        TreeMap<Integer,String> showings = new TreeMap<Integer,String>();
        for (Showing s : showingList) {
            showings.put(s.getShow_id(), s.toString());
            //System.out.println(s.toString());
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

    public static void getShowByID(int a) {
        ArrayList<Integer> reservation_ids = MySqlConnection.getReservationID(a);

        //husk at lave exceptions på null
        ArrayList<Integer> reserved_seats = null;

        for(int l : reservation_ids) {
            reserved_seats = MySqlConnection.getReservedSeats(l);
        }

        Showing show = MySqlConnection.getShowByID(a);
        Hall hall = MySqlConnection.getHallByID(show.getHall_id());

        Cinema c = new Cinema(hall.getRows(), hall.getSeats(),
                show.getTitle(), show.getShow_id(), reserved_seats);

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

