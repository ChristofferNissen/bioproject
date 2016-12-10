package Models;

/**
 * Created by phili on 11/30/2016.
 */
public class Hall {
    private int hall_id;
    private int seats;
    private int rows;

    // Constructor for a Hall object
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

    @Override
    public String toString() {
        return "Hall{" +
                "hall_id=" + hall_id +
                ", seats=" + seats +
                ", rows=" + rows +
                '}';
    }
}
