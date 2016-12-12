package Models;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * MySqlConnection contains all the methods being used to pull data from the database
 */
public class MySqlConnection {
    private static final String MYDB = "ccpbioDB";
    private static final String USER = "ccpbio";
    private static final String PASS = "password";
    private static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;

    //
    // Getters
    //

    /**
     * Retried all reservations from the database
     * @return An arrayylist<Rerservation> containing all reservations currenly in the db
     */
    public static ArrayList<Reservation> getReservations() {
        Connection connection;
        Statement statement;
        ArrayList<Models.Reservation> reservations = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM reservations");

            // Process result set from database
            while (rs.next()) {
                int reservation_id = rs.getInt("reservation_id");
                int tlf_nr = rs.getInt("tlf_nr");
                int showID = rs.getInt("show_id");
                Reservation reservation = new Reservation(reservation_id, tlf_nr, showID);
                reservations.add(reservation);
            }
            // When done processing, close resultset and close connection
            rs.close();
            connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        // Return arraylist of Reservations
        return reservations;
    }

    /**
     * Retrieves all showings maching the sql command
     * @param sql SQL-command as string
     * @return An arraylist containing showings macbing the sql-command
     */
    public static ArrayList<Showing> getShowings(String sql) {
        Connection connection;
        Statement statement;
        ArrayList<Models.Showing> shows = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

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

    /**
     * Gets the hall object specied by hall_id from the db
     * @param hall_id hall_ID
     * @return Hall object
     */
    public static Hall getHallByHallID(int hall_id) {
        Connection connection ;
        Statement statement;
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

    /**
     * Retired information about a show specified by show_id
     * @param show_id Show_ID
     * @return a showing about corresponsing to the show_id given as a parameter
     */
    public static Showing getShowByShowID(int show_id) {
            Connection connection;
            Statement statement;
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

    /**
     * Retrieves all reservations for a given show
     * @param show_id Show_ID
     * @return returns an arrayList<Integer> containing reservation_ids
     */
    public static ArrayList<Integer> getReservationByShowID(int show_id) {
        Connection connection;
        Statement statement;
        ArrayList<Integer> reservationID = new ArrayList<>();

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

    /**
     * Retrives the show_id for a particular reservation_id
     * @param reservation_id Reservation_ID
     * @return an arrayList containing a show_id
     */
    public static ArrayList<Integer> GetShowByReservationId(int reservation_id) {
        Connection connection;
        Statement statement;
        ArrayList<Integer> showID;
        showID = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT Show_id FROM reservations" +
                    " WHERE reservations.reservation_id = " + reservation_id);

            // Process data
            while(rs.next()) {
                int ID = rs.getInt("show_id");
                showID.add(ID);
            }
            // When done processing, close connection
            rs.close();
            connection.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
        // return collection
        return showID;
    }

    /**
     * Retrieves the seats reserved by a particular ID
     * @param reservation_id Reservation_ID
     * @return An arrayList<Integer> containing the reserved seats
     */
    public static ArrayList<Integer> getReservedSeats(int reservation_id){
        Connection connection;
        Statement statement;
        ArrayList<Integer> RSeats = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT seat_id FROM reserved_seats" +
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

    /**
     * Retrieves the reservation made under a particular phonenumber
     * @param phoneNumber PhoneNumber of the reservation
     * @return  An arrayList<Reservations>
     */
    public static ArrayList<Reservation> getReservationsByPhone(String phoneNumber) {
        Connection connection;
        Statement statement;
        ArrayList<Models.Reservation> res;
        res = new ArrayList<>();

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM reservations WHERE tlf_nr = " + phoneNumber);

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

    /**
     * Retrieves showings maching a given title
     * @param Title Title to search for
     * @return An ArrayList<Showing>
     */
    public static ArrayList<Showing> getShowsByTitle(String Title) {
        String sql = "SELECT * FROM shows WHERE title LIKE '%"+ Title + "%'";
        return getShowings(sql);
    }

    /**
     * Retrives showings on a particular date
     * @param Date Date for which to find showings
     * @return an ArrayList<Showing>
     */
    public static ArrayList<Showing> getShowsByDate(String Date) {
        String sql = "SELECT * FROM shows WHERE DATE LIKE '%"+ Date + "%'";
        return getShowings(sql);
    }

    //
    // Inserts
    //

    /**
     * creates entities in the database for the seats reserved by the particular id
     * @param seats int[] containing seats
     * @param reservation_id Reservation ID
     * @return boolean
     */
    public static boolean createReservedSeats(int[] seats, int reservation_id) {
        Connection connection;
        Statement statement;

        //check variable
        int lines = 0;
        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            //creates an entry for each seat selected
            for (int i = 0; i < seats.length; i++) {
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

    /**
     * creates the reservation in the database
     * @param r Reservation Object
     * @return boolean
     */
    public static boolean createReservation(Reservation r) {
        Connection connection;
        Statement statement;

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

            // Create customer if not in system
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

            // Get the reservation ID for this reservation
            int reservation_id = 0;
            while(rs.next()) { //should return, only 1 entry
                //save id
                reservation_id = rs.getInt("reservation_id");
            }

            //reserved the seats related to this reservation in datebase
            if(createReservedSeats(r.getReserved_seats(), reservation_id)){
                //returns true
                connection.close();
                return true;
            }
            //close connection
            connection.close();

        } catch(Exception e) {
            //print stack trace if exceptions are thrown
            e.printStackTrace();
        }
        // return collection
        return false;
    }

    /**
     * update reservation with new seats
     * @param reservationID The reservationID for the reservation to update
     * @param input The new seats as a string of from ",1,2" for seat 1 and 2
     * @return boolean
     */
    public static boolean updateReservation(int reservationID, int[] input){
        if(deleteReservedSeats(reservationID)){
            createReservedSeats(input,reservationID);
            return true;
        }
        return false;
    }

    //
    // Deletes
    //

    /**
     * Removes a reservation and its seats from the database
     * @param id ReservationID of the reservation to remove
     * @return boolean
     */
    public static boolean deleteReservation(int id) {
        Connection connection;
        Statement statement;

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            int entitiesDeleted = 0;

            // Delete seats by ID
            entitiesDeleted = entitiesDeleted + statement.executeUpdate("DELETE FROM reserved_seats where reservation_id=" + id);
            entitiesDeleted = entitiesDeleted + statement.executeUpdate("DELETE FROM reservations where reservation_id=" + id);

            // When done processing, close connection
            connection.close();

            if(entitiesDeleted > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes the entities in reserved_seats in DB for the specified ID
     * @param id
     * @return boolean
     */
    public static boolean deleteReservedSeats(int id){
        Connection connection;
        Statement statement;

        try {
            // Connect to server
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            if(statement.executeUpdate("DELETE FROM reserved_seats WHERE reservation_id = " + id) > 0) {
                connection.close();
                return true;
            }

            //close connection
            connection.close();

        } catch(Exception e) {
            //print stack trace if exceptions are thrown
            e.printStackTrace();
        }
        return false;
    }

}