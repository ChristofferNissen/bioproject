package Models;

/**
 * Describes a hall
 * Stores appropriate data
 */
public class Hall {
    private int hall_id;
    private int seats;
    private int rows;

/**
 *
 * Constructor takes param
 * @param hall_id stores unique id of the hall
 * @param seats stores the number of seats pr row
 * @param rows  stores the number of rows
 */
    Hall(int hall_id, int seats, int rows) {
        this.hall_id = hall_id;
        this.seats = seats;
        this.rows = rows;
    }

    // getters
    public int getSeats() {
        return seats;
    }
    public int getRows() {
        return rows;
    }
    public int getHall_id() {
        return hall_id;
    }

    @Override
    public String toString() {
        return "Hall{" +
                "hall_id=" + hall_id +
                ", seats=" + seats +
                ", rows=" + rows +
                '}';
    }
}
