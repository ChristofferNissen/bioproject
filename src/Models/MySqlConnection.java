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
        ArrayList<Models.Reservation> reservations;
        reservations = new ArrayList<Reservation>();

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

    public static ArrayList<Showing> getShowingQuery(String sql) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Models.Showing> shows;
        shows = new ArrayList<Showing>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT " + sql + " FROM shows");

            // Process data
            while(rs.next()) {
                int show_id = rs.getInt("show_id");
                Date date = rs.getDate("date");
                int time = rs.getInt("time");
                int hall_id = rs.getInt("hall_id");
                String title = rs.getString("title");
                Showing show = new Showing(show_id,date,time,hall_id,title);
                shows.add(show);

            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return shows;
    }

    public static ArrayList<Integer> getReservedSeatsShowing(int show_id){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Integer> RSeats;
        RSeats = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM reservations WHERE reservations.show_id = " + show_id);

            // Process data
            while(rs.next()) {
                int reserved_seat = rs.getInt("reserved_seats");
                RSeats.add(reserved_seat);

            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return RSeats;


    }

}