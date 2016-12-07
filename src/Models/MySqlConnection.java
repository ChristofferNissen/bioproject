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
                int showID = rs.getInt("show_id");
                Reservation reservation = new Reservation(reservation_id,tlf_nr,showID);
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
                String time = rs.getString("time");
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

    public static Hall getHallByID(int hall_id) {
        Connection connection = null;
        Statement statement = null;
        Hall h = null;

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM theaters WHERE theaters.hall_id = " + hall_id);

            // Process data
            while(rs.next()) {
                int id = rs.getInt("hall_id");
                int seats = rs.getInt("seats");
                int rows = rs.getInt("rows");
                h = new Hall(id,seats,rows);

            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return h;
    }

    public static Showing getShowByID(int show_id) {
            Connection connection = null;
            Statement statement = null;
            Showing show = null;

            try {
                // Connect to server
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                connection = DriverManager.getConnection(DB_URL, USER, PASS);
                statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM shows WHERE shows.show_id = " + show_id);

                // Process data
                while(rs.next()) {
                    int id = rs.getInt("show_id");
                    Date date = rs.getDate("date");
                    String time = rs.getString("time");
                    int hall_id = rs.getInt("hall_id");
                    String title = rs.getString("title");
                    show = new Showing(id,date,time,hall_id,title);

                }
                // When done processing, close connection
                rs.close();
                connection.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
            // return show
            return show;

        }

    public static ArrayList<Integer> getReservationID(int show_id) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Integer> reservationID;
        reservationID = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM reservations" +
                    " WHERE reservations.show_id = " + show_id);

            // Process data
            while(rs.next()) {
                int reservation_ID = rs.getInt("reservation_id");
                reservationID.add(reservation_ID);
            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return reservationID;
    }

    public static ArrayList<Integer> getReservedSeats(int reservation_id){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Integer> RSeats;
        RSeats = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM reserved_seats" +
                    " WHERE reserved_seats.reservation_id = " + reservation_id);

            // Process data
            while(rs.next()) {
                int reserved_seat = rs.getInt("seat_id");
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

    public static boolean updateReservation(){


        return false;
    }

    //make reservation
    public static boolean makeReservation(Reservation r) {
        Connection connection = null;
        Statement statement = null;

        //get tlf, and showID from reservation
        int tlfNr = r.getTlf_nr();
        int showID = r.getShow_id();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            //checks if a customer exist in system
            String customerCheck = "SELECT * FROM customers WHERE tlf_nr = " + tlfNr;
            //returns nothing if the query returns nothing

            if(!statement.executeQuery(customerCheck).next()){ //if no next(empty query)
                //makes the customer
                String makeCustomer = "INSERT INTO customers (tlf_nr) VALUES (" + tlfNr + ")";
                statement.executeUpdate(makeCustomer);
            }

            //make a reservation in database
            String createReservation ="INSERT INTO reservations (tlf_nr, show_id) VALUES (" + tlfNr + "," + showID + ")";
            statement.executeUpdate(createReservation);

            //get reservation id, for reserving seats
            ResultSet rs = statement.executeQuery("SELECT * FROM reservations WHERE tlf_nr = " + tlfNr + " AND show_id = " + showID);
            int reservation_id = 0;
            while(rs.next()) { //should return, only 1 entry
                //save id
                reservation_id = rs.getInt("reservation_id");
                System.out.println(reservation_id +"");
            }

            //reserve a number of seats
            if(createReservedSeats(r.getReserved_seats(), reservation_id)){
                //returns true
                connection.close();
                return true;
            }
            //close connection
            connection.close();
            return false;

        } catch(Exception e) {
            //print stack trace if exceptions are thrown
            e.printStackTrace();
        }
        // return collection
        return false;
    }

    //create reservation for seats
    private static boolean createReservedSeats(int[] seats, int reservation_id) {
        Connection connection = null;
        Statement statement = null;

        //check variable
        int lines = 1;
        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            //creates an entry for each seat selected
            for (int i = 0; i < seats.length-1; i++) {
                String reserveSeat = "INSERT INTO reserved_seats (reservation_id, seat_id) VALUES (" + reservation_id + "," + seats[i]+ ")";
                lines += statement.executeUpdate(reserveSeat);
            }

            //catches exceptions and prints
        } catch (Exception e) {
            e.printStackTrace();
        }
        //if the number of entried is the same as number of seats
        if(lines == seats.length)
            return true;
        else
            return false;
    }

    public static ArrayList<Reservation> getReservationsByID(String phone) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Models.Reservation> res;
        res = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM reservations WHERE tlf_nr = " + phone);

            // Process data
            while(rs.next()) {
                int show_id = rs.getInt("show_id");
                int reservation_id = rs.getInt("reservation_id");
                int tlf_nr = rs.getInt("tlf_nr");
                Reservation r = new Reservation(reservation_id, tlf_nr, show_id);
                res.add(r);

            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return res;
    }

    public static boolean updateReservation(Reservation r){



        return false;
    }

    // Delete reservation from reservations && delete reservated seats
    public static void deleteAllReservations(String tlf_nr){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Integer> r_id;
        r_id = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT reservation_id  FROM reservations where tlf_nr=" + tlf_nr);
            // Process data
            while(rs.next()) {
                int reservation_id = rs.getInt("reservation_id");
                r_id.add(reservation_id);
            }


            // Delete seats by ID
            for(int a : r_id) {
                statement.executeUpdate("DELETE FROM reserved_seats where reservation_id=" + a);
            }
            statement.executeUpdate("DELETE FROM reservations where tlf_nr="+tlf_nr);

            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void deleteRerv(int id) {
        Connection connection = null;
        Statement statement = null;
        int r_id = id;

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            /*
            ResultSet rs = statement.executeQuery("SELECT reservation_id  FROM reservations where tlf_nr=" + tlf_nr);
            // Process data
            while(rs.next()) {
                int reservation_id = rs.getInt("reservation_id");
                r_id.add(reservation_id);
            }
            */


            // Delete seats by ID

            statement.executeUpdate("DELETE FROM reserved_seats where reservation_id=" + id);

            statement.executeUpdate("DELETE FROM reservations where reservation_id="+id);

            // When done processing, close connection
            /*
            rs.close();
            */
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}