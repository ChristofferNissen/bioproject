import Models.Reservation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by cn on 06/12/2016.
 */
public class ReservationView{

    private JFrame frame;
    private JList<String> showList;
    private DefaultListModel<Map.Entry> listModel;

    private int reservation_id;

    private ArrayList<String> kda;

    public ReservationView() {
        frame = new JFrame("Reservations");
        frame.setPreferredSize(new Dimension(400, 800));
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void makeFrame(TreeMap treeMap){
        JPanel contentPane = (JPanel)frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JTextField text = new JTextField();
        //text.setText("Enter a phone number");
        text.setToolTipText("Enter a phone number");
        contentPane.add(text,BorderLayout.SOUTH);

        JButton searchReservations = new JButton("Search reservations");
        searchReservations.addActionListener(
                (ActionEvent e ) -> {
                    String tlf_nr = text.getText();
                    makeFrame(Controller.getReservationByID(tlf_nr));
                }
        );
        JButton changeReservations = new JButton("Change reservation");
        changeReservations.addActionListener(
                (ActionEvent e ) -> {

                }
        );
        JButton deleteReservations = new JButton("Delete reservation");
        deleteReservations.addActionListener(
                (ActionEvent e ) -> {

                }
        );

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BorderLayout());
        JPanel buttonPane2 = new JPanel();
        buttonPane2.setLayout(new BorderLayout());
        buttonPane.add(buttonPane2,BorderLayout.CENTER);

        buttonPane2.add(searchReservations,BorderLayout.NORTH);
        buttonPane2.add(changeReservations,BorderLayout.CENTER);
        buttonPane2.add(deleteReservations,BorderLayout.SOUTH);

        contentPane.add(buttonPane,BorderLayout.NORTH);
        contentPane.add(makeReservationList(treeMap));

        frame.pack();
        frame.setVisible(true);

    }
// change returntype to JPanel
    private JPanel makeReservationList(TreeMap<Integer,String> treeMap) {
        JPanel list = new JPanel();
        list.setPreferredSize(new Dimension(400,400));
        listModel = new DefaultListModel<>();
        ArrayList<String> temp = new ArrayList<>();

        for(Map.Entry<Integer,String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            listModel.addElement(entry);

        }

        // convert to String[] from arrayList
        int i = 0;
        String[] var = new String[temp.size()];
        for(String r : temp) {
            var[i] = r;
            i++;
        }

        showList = new JList<>(var);
        showList.setFont(new Font("Cambria", Font.BOLD, 14));
        showList.setBorder(new EmptyBorder(10,10,10,10));

        list.add(showList);
        return list;

    }

}
