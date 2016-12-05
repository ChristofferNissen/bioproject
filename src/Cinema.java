/**
 * Created by caecilieiversen on 30/11/2016.
 */
import com.sun.scenario.effect.impl.sw.java.JSWBlend_BLUEPeer;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class Cinema extends JComponent { //implements ActionListener {

    private int rows;
    private int seats;
    private Rectangle rect;
    private JFrame frame;
    private JPanel cinema;
    private JButton seat;

    public Cinema()
    {
        //rows = new int[] {0,1,2,3,4,5};
        //seats = new int[] {0,1,2,3,4,5,6,7};
        rows = 5;
        seats = 5;

        frame = new JFrame("Cinema: Choose Seats");
        cinema = new JPanel();
        //seat = new JButton();
        makeFrame(this);
    }

    public Cinema(int rows)
    {
        rows = 5;
        seats = 5;

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

        JLabel label = new JLabel("    Movie Title");
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

        ImageIcon greenIcon = new ImageIcon("VacantSeat.png");

        for (int i = 0; i <= seats; i++) {
            for (int j = 0; j <= rows; j++) {
                seat = new JButton("Free",greenIcon);
                seat.setPreferredSize(new Dimension(46, 38));
                c.fill = GridBagConstraints.HORIZONTAL;
                c.insets = new Insets(2,2,2,2); // External padding around each button
                c.gridx = i;                    // Position in grid
                c.gridy = j;

                /*
                seat.setBackground(Color.green);
                seat.setOpaque(true);
                seat.setBorderPainted(false);
                */

                seatArrangement.add(seat, c);
            }
        }

        return seatArrangement;

    }

}

