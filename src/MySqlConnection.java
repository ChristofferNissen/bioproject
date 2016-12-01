import Models.Seat;
import java.sql.*;


/**
 * Created by cn on 30/11/2016.
 */
public class MySqlConnection {
    private static final String MYDB = "ccpbioDB";
    private static final String USER = "ccpbio";
    private static final String PASS = "password";


    static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;

    public static void main(String[] args) {

        String sql = "SELECT * FROM reservations";
        getReservationQuery(sql);

    }

    public static void getReservationQuery(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();


            // build and execute string
            ResultSet rs = statement.executeQuery(sql);
            //
            while(rs.next()) {
                //int id = getInt("id");
                String reservation_id = rs.getString("reservation_id");
                String tlf_nr = rs.getString("tlf_nr");

                System.out.println("Tlf_nr: " + tlf_nr + ", Reservation_id: " + reservation_id);
            }
            // close connection
            rs.close();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    }


