package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by cn on 11/12/2016.
 */
public class MySqlConnectionTest {

    @Before
    public void setUp(){

    }

    @After
    public void tearDown(){}



    // tests if the object returned has the same hall_id as requested
    @Test
    public void getHallByIDTest() {
        for(int i = 1; i < 3; i++) {
            assertEquals(MySqlConnection.getHallByHallID(i).getHall_id(),i);
        }

    }

    // tests if the object returned has the same show_id as requested
    @Test
    public void getShowByIDTest() {
        for(int i = 10; i < 16; i++) {
            int show_id = i;
            assertEquals(MySqlConnection.getShowByShowID(show_id).getShow_id(),show_id);
        }
    }

    // tests if the arrayList returned contains all the reservation ids for a particular show
    @Test
    public void getReservationIDTest() {
        int show_id = 10;
        int reservation_id = 148;
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(reservation_id);

        assertEquals(arrayList,MySqlConnection.getReservationByShowID(show_id));
    }

    // tests if the arrayList contains the show_id for the specified reservation
    @Test
    public void getShowIDTest() {
        int reservation_id = 148;
        ArrayList<Integer> id = new ArrayList<>();
        id.add(10);
        assertEquals(id,MySqlConnection.GetShowByReservationId(reservation_id));

    }

    // In this testmethod we test the fuctionality of MySqlConnection.deleteReservation()
    // , MySqlConnection.createReservation,
    // and MySqlConnection.updateReservation
    @Test
    public void createUpdateDeleteReservationTest() {
        // initialise b and populate with seats we want to reserve
        int[] b = new int [3];
        b[0] = 50;
        b[1] = 51;
        b[2] = 52;
        // convert to arrayList
        ArrayList<Integer> bArrayList = new ArrayList<>();
        bArrayList.add(50);
        bArrayList.add(51);
        bArrayList.add(52);

        // create new reservation
        Reservation r = new Reservation(666,10,b);

        // send reservation to DB
        assertEquals(true,MySqlConnection.createReservation(r));

        // retrieve reservation from DB
        ArrayList<Reservation> resMake = MySqlConnection.getReservationsByPhone("666");

        // check if phone number is the same
        for(Reservation result : resMake){
            assertEquals(r.getTlf_nr(),result.getTlf_nr());
        }

        // retrieve reservation id from DB
        int id = resMake.get(0).getReservation_id();

        // Check if the seats are the one we specified
        assertEquals(MySqlConnection.getReservedSeats(resMake.get(0).getReservation_id()),bArrayList);

        // initialise c and populate with new seat numbers for update
        int[] c = new int[3];
        c[0] = 40;
        c[1] = 41;
        c[2] = 42;

        // conver to arrayList
        ArrayList<Integer> cArrayList = new ArrayList<>();
        cArrayList.add(40);
        cArrayList.add(41);
        cArrayList.add(42);

        // update the reservation
        assertEquals(true,MySqlConnection.updateReservation(id,c));

        // retrieve the updated reservation
        ArrayList<Reservation> resUpdate = MySqlConnection.getReservationsByPhone("666");

        // check if they are the same
        assertFalse(resMake == resUpdate);

        // Check if the reserved seats match the new specified.
        assertEquals(MySqlConnection.getReservedSeats(resUpdate.get(0).getReservation_id()), cArrayList);

        // delete reservation
        assertEquals(true,MySqlConnection.deleteReservation(id));

    }

    @Test
    public void createReservedSeatsTest() {

        int[] a = {95,96,97};
        int reservation_id = 149;

        assertEquals(true,MySqlConnection.createReservedSeats(a,reservation_id));
        MySqlConnection.deleteReservedSeats(153);
    }

    // Returns an arraylist of all reservations made by a given phone number

    @Test
    public void getReservationByPhoneTest() {
        //we know the reservation_ids for the reservations made by 123

        String phone = "123";
        ArrayList<Integer> r_ids = new ArrayList<>();
        r_ids.add(111);
        r_ids.add(142);
        r_ids.add(148);
        r_ids.add(202);

        ArrayList<Reservation> res = MySqlConnection.getReservationsByPhone(phone);

        ArrayList<Integer> test_ids = new ArrayList<>();

        for(Reservation r : res) {
            test_ids.add(r.getReservation_id());

        }

        assertEquals(r_ids,test_ids);

    }


    // returns the seats for a particular ID
    @Test
    public void getReservedSeatsTest() {
        // we know that the reserved seats for reservation_id 146 is 95 and 96.
        // These are the numbers we will test.
        ArrayList<Integer> arrayList;
        Integer seatNumber = 95;

        for(Integer i = 0; i<2 - 1; i++){
            arrayList = MySqlConnection.getReservedSeats(146);
            assertEquals(seatNumber,arrayList.get(i));
            seatNumber++;
        }

    }


    // tests if getShowings returns all shows
    @Test
    public void getShowingsTest() {
        String sql = "SELECT * FROM shows";
        ArrayList<Showing> shows = MySqlConnection.getShowings(sql);

        for(int i = 10; i <= 9 + shows.size(); i++) {
            assertEquals(shows.get(i-10).getShow_id(),i);
        }
    }


    @Test
    public void getShowsByDateTest() {
        ArrayList<Showing> show_ids = new ArrayList<>();
        Showing one = (MySqlConnection.getShowByShowID(14));
        show_ids.add(one);
        Showing two = (MySqlConnection.getShowByShowID(15));
        show_ids.add(two);

        ArrayList<Showing> result_ids = MySqlConnection.getShowsByDate("01-01");

        // Check if its the same show_id. If it is, the test will pass
        assertEquals(show_ids.get(0).getShow_id(),result_ids.get(0).getShow_id());
        assertEquals(show_ids.get(1).getShow_id(),result_ids.get(1).getShow_id());

    }

    @Test
    public void getShowsByTitleTest() {
        // we know that the shows in the DB that will be returned when the string "casino" is passed as an argument is 10,11
        ArrayList<Showing> show_ids = new ArrayList<>();
        Showing one = (MySqlConnection.getShowByShowID(10));
        show_ids.add(one);
        Showing two = (MySqlConnection.getShowByShowID(11));
        show_ids.add(two);

        ArrayList<Showing> result_ids = MySqlConnection.getShowsByTitle("casino");

        assertEquals(show_ids.get(0).getShow_id(),result_ids.get(0).getShow_id());
        assertEquals(show_ids.get(1).getShow_id(),result_ids.get(1).getShow_id());

    }
}
