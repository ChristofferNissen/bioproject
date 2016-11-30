/**
 * Created by caecilieiversen on 30/11/2016.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Cinema extends JComponent implements MouseListener {

    private int[] rows;
    private int[] seats;
    private Rectangle rect;

    public Cinema()
    {
        rows = new int[] {0,1,2,3,4,5,6,7,8,9};
        seats = new int[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14};

        makeFrame(this);
    }

    public void makeFrame(Cinema c){
        JFrame frame = new JFrame("Cinema: Choose Seats");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(c);
        frame.setVisible(true);
    }

    public void drawSeat()
    {
        
    }

    public void paint(Graphics g)
    {
        int width = 20;
        int height = 20;
        int xValue = 30;
        int yValue = 30;

        for (int i = 0; i <= seats.length; i++) {
            for (int j = 0; j <= rows.length; j++) {
                drawSeat();
                this.addMouseListener(this);
            }
        }

        /*
        int width = 20;
        int height = 20;
        int xValue = 30;
        int yValue = 30;

        for (int i = 1; i <= seats.length; i++) {
            for (int j = 1; j <= rows.length; j++) {
                g.drawRect(i * xValue, j * yValue, width, height);
                this.addMouseListener(this);
            }
        }
        */
    }



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
}

