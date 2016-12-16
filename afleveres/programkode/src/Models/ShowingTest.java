package Models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import static org.junit.Assert.assertEquals;


/**
 * Created by Philip Korsholm on 10-12-2016.
 */
public class ShowingTest {

    int show_id;
    Date date;
    String time;
    int hall_id;
    String title;
    Showing s;

    @Before
    public void setUp(){
        show_id = 2;
        date = new Date(2016,8,22);
        time = "1900";
        hall_id = 1;
        title = "James Bond";
        s = new Showing(show_id, date, time, hall_id, title);
    }

    @Test
    public void testShowID(){
        assertEquals(show_id,s.getShow_id());
    }

    @Test
    public void testGetDate(){
        assertEquals(date, s.getDate());
    }

    @Test
    public void testGetTime(){
        assertEquals(time,s.getTime());
    }

    @Test
    public void getHallID(){
        assertEquals(hall_id,s.getHall_id());
    }

    @Test
    public void getTitle(){
        assertEquals(title, s.getTitle());
    }


    @After
    public void tearDown(){}
}
