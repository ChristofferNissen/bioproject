/**
 * Created by caecilieiversen on 30/11/2016.
 */
import com.sun.scenario.effect.impl.ImagePool;
import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class Cinema extends JComponent implements ActionListener {

    private int rows;
    private int seats;
    private String title;
    private int showID;
    private ArrayList reservedSeats;
    private ArrayList selectedSeats;

    private ImageIcon vacantSeat;
    private ImageIcon occupiedSeat;
    private ImageIcon selectedSeat;


    private JFrame frame;
    private JPanel cinema;
    private JButton seat;


    public Cinema()
    {
        //rows = new int[] {0,1,2,3,4,5};
        //seats = new int[] {0,1,2,3,4,5,6,7};
        rows = 5;
        seats = 5;
        showID = 5;
        reservedSeats = new ArrayList<Integer>();
        reservedSeats.add(2);
        reservedSeats.add(10);
        reservedSeats.add(12);
        reservedSeats.add(20);
        title = "<title goes here>";
        vacantSeat = new ImageIcon("VacantSeat.png");
        occupiedSeat = new ImageIcon("occupiedSeat.png");
        selectedSeat = new ImageIcon("selectedSeat.png");

        frame = new JFrame("Cinema: Choose Seats");
        cinema = new JPanel();
        //seat = new JButton();
        makeFrame(this);
    }

    public Cinema(int rows, int seats, String title, int showID, ArrayList<Integer> reservedSeats)
    {
        this.rows = rows;
        this.seats = seats;
        this.title = title;
        this.showID = showID;

        this.reservedSeats = reservedSeats;

        vacantSeat = new ImageIcon("VacantSeat.png");
        occupiedSeat = new ImageIcon("occupiedSeat.png");
        selectedSeat = new ImageIcon("selectedSeat.png");


        frame = new JFrame("Cinema: Choose Seats");
        cinema = new JPanel();
        //seat = new JButton();
        makeFrame(this);
    }

    public void makeFrame(Cinema c){
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(c);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(title);
        frame.add(label, BorderLayout.NORTH);
        label.setFont(new Font("Comic Sans MS", Font.BOLD, 22));

        // cinema.setPreferredSize(new Dimension(100, 100));
        cinema.setBorder(new EmptyBorder(10, 10, 10, 10));
        // cinema.setLayout(new GridLayout(rows.length, seats.length));

        JPanel seats = makeGrid();

        frame.add(seats, BorderLayout.CENTER);

        JPanel bookingButton = new JPanel();
        JButton bookNow = new JButton("Book now!");
        bookNow.addActionListener(
                (ActionEvent e) ->{
                    System.out.println("booking succes");
                }
        );
        bookingButton.add(bookNow);

        frame.add(bookingButton, BorderLayout.EAST);



        //makeCinema();

        frame.setVisible(true);
    }

    public JPanel makeGrid() {
        JPanel seatArrangement = new JPanel();
        seatArrangement.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        int seatNumber = 1;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                // If it doesnt contain the seatnumber, set the seat to free
                if(!reservedSeats.contains(seatNumber)){
                    seat = new JButton(""+seatNumber,vacantSeat);
                    seat.addActionListener(this);
                }else
                    seat = new JButton("occupied",occupiedSeat);


                seat.setPreferredSize(new Dimension(46, 38));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(6,2,6,2); // External padding around each button
                c.gridx = j;                    // Position in grid
                c.gridy = i;

                seatArrangement.add(seat, c);
                seatNumber++;
            }
        }

        return seatArrangement;

    }
    public void actionPerformed(ActionEvent e){
        selectedSeats = new ArrayList<Integer>();
        System.out.println(e.getActionCommand());

        String a  = e.getActionCommand();


        System.out.println(a);

        JButton clicked = (JButton) e.getSource();
        if(!clicked.getIcon().equals(selectedSeat)) {
            clicked.setIcon(selectedSeat);
            System.out.println(""+clicked.toString());
        }
        else{
            clicked.setIcon(vacantSeat);
        }
    }

}

