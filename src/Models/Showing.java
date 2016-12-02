package Models;
import java.util.Date;

/**
 * Created by cn on 02/12/2016.
 */
public class Showing {

    private int show_id;
    private Date date;
    private int time;
    private int hall_id;
    private String title;

    public Showing(int show_id, String date, int time, int hall_id, String title) {
        this.show_id = show_id;
        this.date = setDate(date);
        this.time = time;
        this.hall_id = hall_id;
        this.title = title;
    }

    // Methods
    public Date setDate(String date){
        String myDate = date;
        return new Date(myDate);
    }

    @Override
    public String toString() {
        return "Showing{" +
                "show_id=" + show_id +
                ", date=" + date +
                ", time=" + time +
                ", hall_id=" + hall_id +
                ", title='" + title + '\'' +
                '}';
    }
}
