package Models;
import java.util.Date;

/**
 * Created by cn on 02/12/2016.
 */
public class Showing {

    private int show_id;
    private Date date;
    private String time;
    private int hall_id;
    private String title;

    // Constructor
    Showing(int show_id, Date date, String time, int hall_id, String title) {
        this.show_id = show_id;
        this.date = date;
        this.time = time;
        this.hall_id = hall_id;
        this.title = title;
    }

    // Getters
    public int getShow_id() {
        return show_id;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getHall_id() {
        return hall_id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {

        return
                title +
                " on the " + date +
                " at " + time +
                " in hall " + hall_id;

    }
}
