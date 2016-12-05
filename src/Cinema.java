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

public class Cinema extends JComponent { //implements ActionListener {

    private int rows;
    private int seats;
    private String title;
    private int showID;
    private ArrayList reservedSeats;
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
        this.reservedSeats = new ArrayList<Integer>();

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

        //makeCinema();

        frame.setVisible(true);
    }



    /*
    public void makeCinema()
    {
        System.out.print(rows.length + " " + seats.length);

        for (int i = 0; i <= seats.length; i++) {
            for (int j = 0; j <= rows.length; j++) {
                seat = new JButton();
                seat.setPreferredSize(new Dimension(20, 20));
                // seat.addActionListener();

                cinema.add(seat);
            }
        }
    }
    */

    public JPanel makeGrid() {
        JPanel seatArrangement = new JPanel();
        seatArrangement.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ImageIcon vacantSeat = new ImageIcon("VacantSeat.png");
        ImageIcon occupiedSeat = new ImageIcon("occupiedSeat.png");
        ImageIcon selectedSeat = new ImageIcon("selectedSeat.png");

        int seatNumber = 1;

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= seats; j++) {
                if(!reservedSeats.contains(seatNumber)){
                    seat = new JButton("Free",vacantSeat);
                }else{
                    seat = new JButton("Free",occupiedSeat);
                }

                seat.setPreferredSize(new Dimension(46, 38));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(2,2,2,2); // External padding around each button
                c.gridx = j;                    // Position in grid
                c.gridy = i;

                /*
                seat.setBackground(Color.green);
                seat.setOpaque(true);
                seat.setBorderPainted(false);
                */

                seatArrangement.add(seat, c);
                seatNumber++;
            }
        }

        return seatArrangement;

    }

}

