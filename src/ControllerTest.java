

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Philip Korsholm on 10-12-2016.
 */
public class ControllerTest {

    Controller c;
    String split;
    int[] arr;

    @Before
    public void setUp(){
        split = ",1,2";
        arr = new int[]{1,2};
        c = new Controller();
    }

    @Test
    public void testSplitString(){
        assertEquals(arr[0],Controller.splitSeatString(split)[0]);
        assertEquals(arr[1],Controller.splitSeatString(split)[1]);
    }


    @After
    public void tearDown(){}

}
