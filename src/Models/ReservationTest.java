
package Models;
/*import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.After;
import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;*/
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class Reservation.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ReservationTest {
    private int phone;
    private int showID;
    private int[] seats;
    private int resID;
    private Reservation r;
    private Reservation rID;

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        seats = new int[]{1,2,3};
        phone = 12346578;
        showID = 5;
        resID = 3;
        r = new Reservation(phone, showID, seats);
        rID = new Reservation(resID, phone, showID);
    }

    @Test
    public void testGetPhone(){
    assertEquals(phone, r.getTlf_nr());
    }

    @Test
    public void testGetShowID(){
        assertEquals(showID, r.getShow_id());
    }

    @Test
    public void testGetReservedSeats(){
        assertEquals(seats, r.getReserved_seats());
    }

    @Test
    public void testGetReservationID(){
        assertEquals(resID, rID.getReservation_id());
    }

    @Test
    public void testNonEqualResID(){
        assertFalse(resID == r.getReservation_id());
    }
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
