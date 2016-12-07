/**
 * Created by caecilieiversen on 30/11/2016.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class CinemaView extends JComponent implements ActionListener {

    //fields
    private int rows;
    private int seats;
    private String title;
    private String time;
    private java.util.Date date;
    private int hall;
    private int showID;
    private int seatNumber;
    private String input;
    private ArrayList reservedSeats;
    private ArrayList selectedSeats;

    private ImageIcon vacantSeat;
    private ImageIcon occupiedSeat;
    private ImageIcon selectedSeat;


    private JFrame frame;
    private JPanel cinema;
    private JButton seat;

    public CinemaView(int rows, int seats, String title, String time, java.util.Date date, int hall, int showID, ArrayList<Integer> reservedSeats, String input)
    {
        this.rows = rows;
        this.seats = seats;
        this.title = title;
        this.time = time;
        this.date = date;
        this.hall = hall;
        this.showID = showID;

        this.reservedSeats = reservedSeats;

        for(int a : reservedSeats) {
            System.out.println(a + "WUWU");
        }

        this.input = input;

        // Icons
        vacantSeat = new ImageIcon("VacantSeat.png");
        occupiedSeat = new ImageIcon("occupiedSeat.png");
        selectedSeat = new ImageIcon("selectedSeat.png");

        frame = new JFrame("CinemaView: Choose Seats");
        cinema = new JPanel();
        makeFrame(this);
    }

    public void makeFrame(CinemaView c){
        frame.setSize(900, 650);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(c);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("You have chosen " + title + " on the " + date + " at " + time + " in hall " + hall, SwingConstants.CENTER);
        frame.add(label, BorderLayout.NORTH);
        label.setFont(new Font("Cambria", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(10,10,10,10));

        // cinema.setPreferredSize(new Dimension(100, 100));
        cinema.setBorder(new EmptyBorder(10, 10, 10, 10));
        // cinema.setLayout(new GridLayout(rows.length, seats.length));

        JPanel seats = makeGrid();
        frame.add(seats, BorderLayout.CENTER);

        JPanel fillerPanel = new JPanel();
        fillerPanel.setBorder(new EmptyBorder(245, 100, 245, 70));
        frame.add(fillerPanel, BorderLayout.WEST);

        JPanel screenPanel = new JPanel();
        screenPanel.setBorder(new EmptyBorder(10,10,10,20));
        JLabel screen = new JLabel("Screen", SwingConstants.CENTER);
        screen.setFont(new Font("Cambria", Font.BOLD, 14));
        screen.setBorder(new LineBorder(Color.BLACK, 2));
        screen.setPreferredSize(new Dimension(500, 20));

        screenPanel.add(screen);
        frame.add(screenPanel, BorderLayout.SOUTH);

        JPanel bookingButton = new JPanel();
        bookingButton.setLayout(new BorderLayout());
        bookingButton.setBorder(new EmptyBorder(245, 10, 245, 70));
        JButton bookNow = new JButton("Book now!");
        bookNow.addActionListener(
                (ActionEvent e) ->{
                    int tlf = book();
                    //if(String.valueOf(tlf).length() == 8) {
                        if (Controller.makeReservation(tlf, showID, input) && input.length() > 0) {
                            JOptionPane.showMessageDialog(null, "Booking Succes");
                            input = "";

                        } else if (input.length() <= 0) {
                            JOptionPane.showMessageDialog(null, "Please enter a number");

                        } else {
                            JOptionPane.showMessageDialog(null, "Booking failed");
                        }
                        System.out.println("booking succes");

                }
        );
        bookingButton.add(bookNow, BorderLayout.CENTER);

        frame.add(bookingButton, BorderLayout.EAST);
        //frame.pack();
        frame.setVisible(true);
    }

    public JPanel makeGrid() {
        JPanel seatArrangement = new JPanel();
        seatArrangement.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        seatNumber = 1;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                // If it doesnt contain the seatnumber, set the seat to free
                if (!reservedSeats.contains(seatNumber)) {
                    seat = new JButton(""+seatNumber, vacantSeat);
                    seat.addActionListener(
                            (ActionEvent e) -> {
                                JButton clicked = (JButton) e.getSource();
                                if (!clicked.getIcon().equals(selectedSeat)) {
                                    clicked.setIcon(selectedSeat);
                                    input = input + "," + e.getActionCommand();
                                    System.out.println(input);
                                } else {
                                    clicked.setIcon(vacantSeat);
                                    if (input.contains(e.getActionCommand())) {
                                        input = input.replace("," + e.getActionCommand(), "");
                                        System.out.println(input);
                                    }
                                }
                            });
                }


                if (reservedSeats.contains(seatNumber)) {
                    String s = "," + seatNumber;

                        if(input.contains(s)){
                            seat = new JButton(""+seatNumber, occupiedSeat);
                            seat.addActionListener(
                                    (ActionEvent e) -> {
                                        JButton clicked = (JButton) e.getSource();
                                        if (!clicked.getIcon().equals(selectedSeat)) {
                                            clicked.setIcon(selectedSeat);
                                            input = input + "," + e.getActionCommand();
                                            System.out.println(input);
                                        } else {
                                            clicked.setIcon(vacantSeat);
                                            if (input.contains(e.getActionCommand())) {
                                                input = input.replace("," + e.getActionCommand(), "");
                                                System.out.println(input);
                                            }
                                        }
                                    });
                            seat.setIcon(selectedSeat);
                    } else {
                            seat = new JButton("occupied", occupiedSeat);
                        }

                }



                seat.setPreferredSize(new Dimension(46, 38));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(6, 2, 6, 2); // External padding around each button
                c.gridx = j;                    // Position in grid
                c.gridy = i;
                seatArrangement.add(seat, c);
                seatNumber++;
            }

        }
        return seatArrangement;

    }

    public int book(){
        int i = 0;
        try{
            i = getReservationNumber();
        }
        catch(IllegalArgumentException e) {
            if (e.getMessage().equals("Missing phone number"))
                JOptionPane.showMessageDialog(null, "Please enter a PhoneNumber");
        }
        return i;

    }

    private int getReservationNumber(){
        JPanel myPanel = new JPanel();
        JTextField phoneField = new JTextField(12);

        myPanel.add(new JLabel("Input Phone:"));
        myPanel.add(phoneField);

        int pressed = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter Customers PhoneNumber", JOptionPane.OK_CANCEL_OPTION);
        if (pressed == JOptionPane.OK_OPTION) {
            if(phoneField != null){
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


    public void storeSelectedSeat(JButton s) {
        selectedSeats.add(s);

    }

    public void setInput(String input){


    }

    public void actionPerformed(ActionEvent e ) {

    }

}

