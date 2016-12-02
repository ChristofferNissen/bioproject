package Models;

/**
 * Created by cn on 02/12/2016.
 */
public class Reservation {
    // Fields
    private int reservation_id;
    private int tlf_nr;
    private int reserved_seat;

    // Constructor

    public Reservation(int reservation_id, int tlf_nr, int reserved_seat) {
        this.reservation_id = reservation_id;
        this.tlf_nr = tlf_nr;
        this.reserved_seat = reserved_seat;
    }


    // methods
    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", tlf_nr=" + tlf_nr +
                ", reserved_seat=" + reserved_seat +
                '}';
    }


}
