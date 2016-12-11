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
    public void getGetHall() {
        int hall_id = 1;
        assertEquals(MySqlConnection.getHallByID(hall_id).getHall_id(),hall_id);

    }

    // tests if the object returned has the same show_id as requested
    @Test
    public void getShowByIDTest() {
        int show_id = 10;
        assertEquals(MySqlConnection.getShowByID(show_id).getShow_id(),show_id);
    }

    // tests if the arrayList returned contains all the reservation ids for a particular show
    @Test
    public void getReservationIDTest() {
        int show_id = 10;
        int reservation_id = 148;
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(reservation_id);

        assertEquals(arrayList,MySqlConnection.getReservationID(show_id));
    }

    // tests if the arrayList contains the show_id for the specified reservation
    @Test
    public void getShowIDTest() {
        int reservation_id = 148;
        ArrayList<Integer> id = new ArrayList<>();
        id.add(10);
        assertEquals(id,MySqlConnection.getShowID(reservation_id));

    }

    


}
