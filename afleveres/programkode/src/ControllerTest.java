import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Philip Korsholm on 10-12-2016.
 */
public class ControllerTest {

    private String split;
    private int[] arr;

    @Before
    public void setUp(){
        split = ",1,2";
        arr = new int[]{1,2};
    }

    @Test
    public void testSplitString(){
        assertEquals(arr[0],Controller.splitSeatString(split)[0]);
        assertEquals(arr[1],Controller.splitSeatString(split)[1]);
    }

    // tests if the displayReservationView creates a view
    @Test
    public void displayReservationTest(){
        assertEquals(true,Controller.displayReservation(144));
    }

    // tests if it can load a view both with and without selected seats
    @Test
    public void CreateShowViewByIDTest(){
        assertEquals(true,Controller.CreateCinemaViewByShowID(10,"",false));
        assertEquals(true,Controller.CreateCinemaViewByShowID(10,",30,31,32",true));
    }

    // tests if it manages to create a reservation
    @Test
    public void createReservationTest() {
        assertFalse(Controller.createReservation(-1,10,"10,11"));
        assertEquals(true,Controller.createReservation(99,11,",10,11"));

        // delete reservation after use
        TreeMap k = Controller.getReservationsByPhone("99");
        Integer i = (Integer) k.firstKey();
        assertEquals(true,Controller.deleteReservation(i));
    }

    // test if it manages to create a reservationView
    @Test
    public void makeReservationViewTest() {
        assertEquals(true,Controller.createReservationView());
    }

    // tests if it manages to update a reservation
    @Test
    public void updateReservationTest() {
        assertEquals(true,Controller.updateReservation("47,48,49",true,202));
    }

    @After
    public void tearDown(){}

}
