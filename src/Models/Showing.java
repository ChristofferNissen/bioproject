package Models;
import java.util.Date;

/**
 * Describes a showing or screening of a movie
 * has one constructor and getter methods for variables
 *
 * show_id
 * date
 * time
 * hall_id
 * title
 */
public class Showing {

    private int show_id;
    private Date date;
    private String time;
    private int hall_id;
    private String title;

    /**
     * Constructor
     * @param show_id   stores id of the showing
     * @param date      Date of the showing
     * @param time      Time of day
     * @param hall_id   which hall its showing in
     * @param title     Title of the movie
     */
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
                "  |  " + date.toString().replace("-","/") +
                "  |  " + time +
                "  |  Hall: " + hall_id;

    }
}
