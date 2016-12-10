

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

    @Before
    public void setUp(){
        split = ",1,2";
        c = new Controller();
    }

    @Test
    public void testSplitString(){

    }


    @After
    public void tearDown(){}

}
