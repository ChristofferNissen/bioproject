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
    public Reservation(int tlf_nr, int show_id, int[] reserved_seats) {
        this.show_id = show_id;
        this.tlf_nr = tlf_nr;
        this.reserved_seats = new int[reserved_seats.length];
        getReservedSeats(reserved_seats);
    }

    // Constructor
    public Reservation(int reservation_id, int tlf_nr, int show_id) {
        this.reservation_id = reservation_id;
        this.tlf_nr = tlf_nr;
        this.show_id = show_id;
    }

    // methods
    @Override
    public String toString() {
        return "Reservation: " +
                "Show id: " + show_id +
                ", Tlf_nr: " + tlf_nr;

    }

    //getters
    public int getReservation_id() {
        return reservation_id;
    }

    int getTlf_nr() {
        return tlf_nr;
    }

    int[] getReserved_seats() {
        return reserved_seats;
    }

    private void getReservedSeats(int[] reserved_seats) {
        for(int i = 0; i <=reserved_seats.length-1; i++){
            this.reserved_seats[i] = reserved_seats[i];

        }


    }

    int getShow_id() {
        return show_id;
    }
}
