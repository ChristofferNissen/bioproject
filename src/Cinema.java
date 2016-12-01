/**
 * Created by caecilieiversen on 30/11/2016.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Cinema extends JComponent {

    private int[] rows;
    private int[] seats;
    private Rectangle rect;
    private JFrame frame;
    private JPanel cinema;

    public Cinema()
    {
        rows = new int[] {0,1,2,3,4,5,6,7,8,9};
        seats = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};

        frame = new JFrame("Cinema: Choose Seats");
        cinema = new JPanel();
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
        frame.add(cinema, BorderLayout.CENTER);

        makeCinema();

        frame.setVisible(true);
    }

    public void makeCinema()
    {
        for (int i = 0; i <= seats.length; i++) {
            for (int j = 0; j <= rows.length; j++) {
                JButton seat = new JButton();
                seat.setPreferredSize(new Dimension(20, 20));
                cinema.add(seat);
            }
        }
    }



    /*
    public void mouseClicked(MouseEvent e) {
        if (rect.contains(e.getX(), e.getY())) {
            System.out.print("HEJ");
            repaint();
        }
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }
    */
}

