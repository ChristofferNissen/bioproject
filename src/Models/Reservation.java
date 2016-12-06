package Models;

/**
 * Created by cn on 02/12/2016.
 */
public class Reservation {
    // Fields
    private int reservation_id;
    private int tlf_nr;
    private int show_id;
    private int[] reserved_seats;

    // Constructor

    public Reservation(int reservation_id, int tlf_nr, int[] reserved_seats) {
        this.reservation_id = reservation_id;
        this.tlf_nr = tlf_nr;
        this.reserved_seats = reserved_seats;
    }

    public Reservation(int reservation_id, int tlf_nr) {
        this.reservation_id = reservation_id;
        this.tlf_nr = tlf_nr;
    }

    // methods
    @Override
    public String toString() {
        return "Reservation{" +
                "reservation_id=" + reservation_id +
                ", tlf_nr=" + tlf_nr +
                ", reserved_seats = " + reserved_seats.toString() + "";

    }

    //getters


    public int getReservation_id() {
        return reservation_id;
    }

    public int getTlf_nr() {
        return tlf_nr;
    }

    public int[] getReserved_seats() {
        return reserved_seats;
    }

    public int getShow_id() {
        return show_id;
    }
}
