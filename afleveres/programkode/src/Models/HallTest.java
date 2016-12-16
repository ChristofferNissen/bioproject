package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Philip Korsholm on 10-12-2016.
 */
public class HallTest{

    private int hall_id;
    private int seats;
    private int rows;
    private Hall h;

    @Before
    public void setUp(){
        hall_id = 1;
        seats = 5;
        rows = 10;
        h = new Hall(hall_id,seats,rows);
    }

    @Test
    public void testGetRows(){
        assertEquals(rows,h.getRows());
    }

    @Test
    public void testGetSeats(){
        assertEquals(seats, h.getSeats());
    }


    @After
    public void tearDown(){}

}
