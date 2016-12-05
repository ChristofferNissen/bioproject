package Models;

import java.util.Collection;

/**
 * Created by phili on 11/30/2016.
 */
public class Hall {
    private int hall_id;
    private int seats;
    private int rows;


    public Hall(int hall_id, int seats, int rows) {
        this.hall_id = hall_id;
        this.seats = seats;
        this.rows = rows;
    }

    public int getHall_id() {
        return hall_id;
    }

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
