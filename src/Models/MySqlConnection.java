package Models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cn on 30/11/2016.
 */
public class MySqlConnection {
    private static final String MYDB = "ccpbioDB";
    private static final String USER = "ccpbio";
    private static final String PASS = "password";
    static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;


    public static ArrayList<Reservation> getReservationQuery(String sql) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Models.Reservation> reservations = new ArrayList<Reservation>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT " + sql + " FROM reservations");

            // Process data
            while(rs.next()) {
                int reservation_id = rs.getInt("reservation_id");
                int tlf_nr = rs.getInt("tlf_nr");
                int reserved_seat = rs.getInt("reserved_seats");
                Reservation reservation = new Reservation(reservation_id,tlf_nr,reserved_seat);
                reservations.add(reservation);

            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return reservations;
    }


}


