package Models;

/**
 *  Describes a reservation
 *
 *  Has two constructors
 *  1 for creating with a number of selected seats with parameters
 *  1 for creating one without seats, but with a specific reservaiton number
 */
public class Reservation {
    // Fields
    private int reservation_id;
    private int tlf_nr;
    private int show_id;
    private int[] reserved_seats;

    /**
     * Constructor for creating a reservation with a number of seats
     * @param tlf_nr used as customer id
     * @param show_id show id for recognizing reservation
     * @param reserved_seats determine which seats
     */
    public Reservation(int tlf_nr, int show_id, int[] reserved_seats) {
        this.show_id = show_id;
        this.tlf_nr = tlf_nr;
        this.reserved_seats = new int[reserved_seats.length];
        getReservedSeats(reserved_seats);
    }

    /**
     * constructor for creating a reservation with an id
     * @param reservation_id stores id of reservation assigned by database
     * @param tlf_nr customer id number
     * @param show_id stores id of showing
     */
    public Reservation(int reservation_id, int tlf_nr, int show_id) {
        this.reservation_id = reservation_id;
        this.tlf_nr = tlf_nr;
        this.show_id = show_id;
    }

    // methods
    @Override
    public String toString() {
        return "Reservation: " + reservation_id +
                "  |  Show id: " + show_id +
                "  |  tlf: " + tlf_nr;

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
