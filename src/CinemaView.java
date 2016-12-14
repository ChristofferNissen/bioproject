import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Class CinemaView contains the code for creating the GUI for showing reservations and showings
 */

class CinemaView extends JComponent {

    //Field variables
    private int rows;
    private int seats;
    private String title;
    private String time;
    private java.util.Date date;
    private int hall;
    private int showID;

    //Private int seatNumber;
    private boolean changeReservation;
    private String input;
    private ArrayList<Integer> reservedSeats;

    //Icons
    private ImageIcon vacantSeat;
    private ImageIcon occupiedSeat;
    private ImageIcon selectedSeat;

    //GUI
    private JFrame frame;
    private JPanel cinema;
    private JButton seat;

    /**
     * Creates a new CinemaView with all the data to be displayed
     * @param rows  Number of rows in hall
     * @param seats Number of seats per row
     * @param title Movietitle thats being shown
     * @param time  Time of the showing
     * @param date  Date of the showing
     * @param hall  In which hall the showing is shown (hall_id)
     * @param showID The ID of the show in DB
     * @param reservedSeats ArrayList<Integer> of reserved seats
     * @param input reservedSeats as String of from ",1,2" (For when preloading a reservation with seats allready selected)
     * @param changeReservation Boolean true if its a reservation being changed rather than showing available seats
     */
    CinemaView(int rows, int seats, String title, String time, java.util.Date date, int hall,
               int showID, ArrayList<Integer> reservedSeats, String input, Boolean changeReservation) {
        this.rows = rows;
        this.seats = seats;
        this.title = title;
        this.time = time;
        this.date = date;
        this.hall = hall;
        this.showID = showID;
        this.input = input;
        this.changeReservation = changeReservation;
        this.reservedSeats = reservedSeats;

        //Icons
        vacantSeat = new ImageIcon("VacantSeat.png");
        occupiedSeat = new ImageIcon("occupiedSeat.png");
        selectedSeat = new ImageIcon("selectedSeat.png");

        //Build GUI
        frame = new JFrame("CinemaView: Choose Seats");
        cinema = new JPanel();
        makeFrame(this);
    }

    /**
     * Creates the gui elements and makes the frame visible
     * @param c a CinemaView object containing all information needed for building the gui
     */
    private void makeFrame(CinemaView c){
        frame.setMinimumSize(new Dimension(900, 750));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(c);;
        frame.setLayout(new BorderLayout());

        cinema.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Create show-descriptive label
        frame.add(makeShowLabel(), BorderLayout.NORTH);

        //Create visual representation of seats
        JPanel seats = makeGrid();
        frame.add(seats, BorderLayout.CENTER);

        //Create filler panel
        JPanel fillerPanel = new JPanel();
        fillerPanel.setBackground(Color.darkGray);
        fillerPanel.setBorder(new EmptyBorder(245, 100, 245, 70));
        frame.add(fillerPanel, BorderLayout.WEST);

        //Add screen to frame
        frame.add(makeScreen(), BorderLayout.SOUTH);

        //Decide whether to display "Book Now" or "Update reservation"-button
        if(changeReservation) {
            frame.add(makeUpdateButton(), BorderLayout.EAST);
        } else {
            frame.add(makeBookingButton(), BorderLayout.EAST);
        }

        frame.setVisible(true);
    }

    /**
     * Creates labels for describing the show
     * @return JPanel including JLabels with show descriptions
     */
    private JPanel makeShowLabel() {
        JLabel label = new JLabel("You have chosen " + title, SwingConstants.CENTER);
        JLabel dateLabel = new JLabel("   Date: " + date + ".\n" + "Time: " + time, SwingConstants.LEFT);
        JLabel hallLabel = new JLabel("   Hall: " + hall, SwingConstants.LEFT);

        label.setFont(new Font("Cambria", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(10,10,10,10));
        hallLabel.setFont(new Font("Cambria", Font.PLAIN, 12));
        hallLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Cambria", Font.PLAIN, 12));
        dateLabel.setForeground(Color.WHITE);

        //Adds labels to JPanel
        JPanel labelCollection = new JPanel();
        labelCollection.setBackground(Color.darkGray);
        labelCollection.setLayout(new BorderLayout());
        labelCollection.add(label, BorderLayout.NORTH);

        JPanel labelCollectionTwo = new JPanel();
        labelCollectionTwo.setBackground(Color.darkGray);
        labelCollectionTwo.setLayout(new BorderLayout());
        labelCollectionTwo.add(dateLabel, BorderLayout.CENTER);
        labelCollectionTwo.add(hallLabel, BorderLayout.SOUTH);

        labelCollection.add(labelCollectionTwo, BorderLayout.CENTER);

        return labelCollection;
    }

    /**
     * Creates a visual representation of the screen location
     * @return JPanel with cinema screen
     */
    private JPanel makeScreen() {
        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(Color.darkGray);
        screenPanel.setBorder(new EmptyBorder(10,10,10,20));

        JLabel screen = new JLabel("Screen", SwingConstants.CENTER);
        screen.setFont(new Font("Cambria", Font.BOLD, 14));
        screen.setBorder(new LineBorder(Color.BLACK, 2));
        screen.setPreferredSize(new Dimension(500, 20));
        screenPanel.add(screen);

        return screenPanel;
    }

    /**
     * Creates "Update reservation"-button
     * @return JPanel with "Update reservation"-button
     */
    private JPanel makeUpdateButton() {
        JPanel bookingButton = new JPanel();
        bookingButton.setBackground(Color.darkGray);
        bookingButton.setLayout(new BorderLayout());
        bookingButton.setBorder(new EmptyBorder(260, 30, 260, 70));

        JButton updateReservation= new JButton("Update");
        updateReservation.setBackground(Color.lightGray);
        updateReservation.addActionListener(
                (ActionEvent e) -> {
                    if (Controller.updateReservation(input, true)) {
                        JOptionPane.showMessageDialog(null, "Update Succesfull");
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Update Failed");
                    }
                }
        );

        bookingButton.add(updateReservation, BorderLayout.CENTER);

        return bookingButton;
    }

    /**
     * Creates "Book now"-button
     * @return JPanel with "Book now"-button
     */
    private JPanel makeBookingButton() {
        JPanel bookingButton = new JPanel();
        bookingButton.setBackground(Color.darkGray);
        bookingButton.setLayout(new BorderLayout());
        bookingButton.setBorder(new EmptyBorder(260, 10, 260, 70));

        JButton bookNow = new JButton("Book now!");
        bookNow.setBackground(Color.lightGray);
        bookNow.setBorderPainted(true);
        bookNow.addActionListener(
                (ActionEvent e) ->{
                    int tlf = 0;
                    try{
                        tlf = getCustomerNumber();
                    }
                    catch(IllegalArgumentException iae) {
                        if (iae.getMessage().equals("Missing phone number"))
                            JOptionPane.showMessageDialog(null, "Please enter a PhoneNumber");
                    }
                    // Report back if the reservation was succesfull
                    if (Controller.createReservation(tlf, showID, input) && input.length() > 0) {
                        JOptionPane.showMessageDialog(null, "Booking Succes");
                        frame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(null, "Booking failed");
                    }
                }
        );

        bookingButton.add(bookNow, BorderLayout.CENTER);

        return bookingButton;
    }

    /**
     * Creates a grid of seats
     * @return Returns a JPanel containing the seat grid
     */
    private JPanel makeGrid() {
        JPanel seatArrangement = new JPanel();
        seatArrangement.setLayout(new GridBagLayout());
        seatArrangement.setBorder(BorderFactory.createLineBorder(Color.black));
        seatArrangement.setBackground(Color.DARK_GRAY);
        GridBagConstraints c = new GridBagConstraints();
        int seatNumber = 1;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                 boolean buttonSet = false;
                // If reservedSeats doesnt contain the seatnumber, set the seat to free

                // Check if it is reserved seats, and if it is also in the input string, set it to selected, otherwise to occupied

                // If it is a reservation and not a new booking
                if(!input.matches("")) {
                    // Turn string into int[], to check if a seat is both in input and is a reserved seat
                    int[] var = Controller.splitSeatString(input);

                    // For every variable in var, check if it matches seatNumber
                    for (int k : var) {
                        // If k, a seatNumber in input, set it to selected seat, set buttonSet to true
                        if (k == seatNumber) {
                            seat = new JButton("" + seatNumber, occupiedSeat);
                            seat.addActionListener(
                                    (ActionEvent e) -> {
                                        JButton clicked = (JButton) e.getSource();
                                        setIcon(clicked, e);
                                    });
                            seat.setIcon(selectedSeat);
                            buttonSet = true;
                        }

                        // If buttonSet is still false, and the seatNumber is in reservedSeasts, set it to occupied, and buttonSet to true
                        if(!buttonSet && reservedSeats.contains(seatNumber)) {
                            seat = new JButton("occupied", occupiedSeat);
                            buttonSet = true;
                        }

                        // If buttonSet is still false, set the seat to be a free seat
                        if (!buttonSet) {
                            seat = new JButton("" + seatNumber, vacantSeat);
                            seat.addActionListener(
                                    (ActionEvent e) -> {
                                        JButton clicked = (JButton) e.getSource();
                                        setIcon(clicked, e);
                                    });
                            buttonSet = true;
                        }
                    }

                } else { // If it is a showing
                    // if seatNumber is not in reservedSeats, set it to free, set buttonSet to true
                    if (!reservedSeats.contains(seatNumber)) {
                        seat = new JButton("" + seatNumber, vacantSeat);
                        seat.addActionListener(
                                (ActionEvent e) -> {
                                    JButton clicked = (JButton) e.getSource();
                                    setIcon(clicked, e);
                                });
                        buttonSet = true;
                    }

                }
                    // if buttonSet is false, set it to occupied
                    if(!buttonSet) {
                    seat = new JButton("occupied", occupiedSeat);
                }


                seat.setPreferredSize(new Dimension(46, 38));
                seat.setBorder(BorderFactory.createLineBorder(Color.black));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(6, 2, 6, 2);      // External padding around each button
                c.gridx = j;                            // Position in grid
                c.gridy = i;
                seatArrangement.add(seat, c);
                seatNumber++;

            }
        }

        return seatArrangement;                         // Return the JPanel containing the grid
    }

    /**
     * Determines wether to set the icon to free or to selectedSeat, and it will record the actionCommands so that the seats marked selected will get saved
     * @param clicked JButton to setIcon
     * @param e ActionEvent
     */
    private void setIcon(JButton clicked, ActionEvent e) {
        if (!clicked.getIcon().equals(selectedSeat)) {
            clicked.setIcon(selectedSeat);
            input = input + "," + e.getActionCommand();
        } else {
            clicked.setIcon(vacantSeat);
            if (input.contains(e.getActionCommand())) {
                input = input.replace("," + e.getActionCommand(), "");
            }
        }
    }

    /**
     * Requests the number to make a reservation
     * @return int Customer's number
     */
    private int getCustomerNumber(){
        JPanel myPanel = new JPanel();
        JTextField phoneField = new JTextField(12);
        myPanel.add(new JLabel("Input Phone:"));
        myPanel.add(phoneField);

        int pressed = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Customers PhoneNumber", JOptionPane.OK_CANCEL_OPTION);
        if (pressed == JOptionPane.OK_OPTION) {
            if(phoneField.getText() != null){
                return Integer.parseInt(phoneField.getText());
            }else{
                throw new IllegalArgumentException("Missing phone number");
            }
        }

        if (pressed == JOptionPane.CANCEL_OPTION){
            throw new IllegalArgumentException("cancel pressed");
        }
        return -1;
    }
}
