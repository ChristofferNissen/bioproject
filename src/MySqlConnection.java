import Models.Seat;
import java.sql.

/**
 * Created by cn on 30/11/2016.
 */
public class MySqlConnection {
    private static final String MYDB = "ccpbioDB";
    private static final String USER = "ccpbio";
    private static final String PASS = "password";

    static final String DB_URL = "jdbc:mysql://mydb.itu.dk/" + MYDB;

    public static void main(String[] args)
    {
        Connection connection = null;
        Statement statement = null;
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            statement = connection.createStatement();

            String sql = "SELECT * FROM table_name";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()) {
                int id = getInt("id");
                String email = rs.getString("email");
                String name = rs.getString("name");

                System.out.println("Name: " + name + ", email: " + email);
            }
            rs.close();
            connection.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
