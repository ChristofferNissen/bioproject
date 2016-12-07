import Models.MySqlConnection;
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
    private JPanel list;
    private JPanel contentPane;
    private JList<String> showList;
    private DefaultListModel<Map.Entry> listModel;
    private DefaultListModel<String> stringModel;

    private int reservation_id;

    private ArrayList<String> kda;

    public ReservationView() {
        frame = new JFrame("Reservations");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void makeFrame(TreeMap treeMap){
        JPanel contentPane = (JPanel)frame.getContentPane();
        frame.setPreferredSize(new Dimension(400, 800));
        contentPane.setLayout(new BorderLayout());

        JTextField text = new JTextField();
        //text.setText("Enter a phone number");
        text.setToolTipText("Enter a phone number");
        contentPane.add(text,BorderLayout.SOUTH);

        JButton searchReservations = new JButton("Search reservations");
        searchReservations.addActionListener(
                (ActionEvent e ) -> {
                    String tlf_nr = text.getText();
                    updateList(Controller.getReservationByID(tlf_nr));
                }
        );
        JButton changeReservations = new JButton("Change reservation");
        changeReservations.addActionListener(
                (ActionEvent e ) -> {


                    if(showList.getSelectedIndex() == -1) { } else {
                        int i = showList.getSelectedIndex();
                        System.out.println(i);
                        int a = (Integer) listModel.get(i).getKey();
                        System.out.println(a);
                        // a = reservations_id

                        // rename
                        Controller.reservedSeatsToString(a);

                        // put into controller, then call from here
                        //MySqlConnection.deleteRerv(a);
                        //updateList(Controller.getRervs());
                        frame.setVisible(true);
                    }

                    /*
                    JPanel myPanel = new JPanel();
                    JTextField phoneField = new JTextField(12);

                    myPanel.add(new JLabel("Input Phone:"));
                    myPanel.add(phoneField);

                    int pressed = JOptionPane.showConfirmDialog(null, myPanel,
                            "Please Enter Customers PhoneNumber", JOptionPane.OK_CANCEL_OPTION);
                    if (pressed == JOptionPane.OK_OPTION) {
                        if(phoneField != null){
                            try{
                                //Controller.updateReservation(Integer.parseInt(phoneField.getText()));
                            }catch (IllegalArgumentException iae){
                                JOptionPane.showMessageDialog(null, "please enter a PhoneNumber");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "please enter a PhoneNumber");
                        }
                    }
                    )
                    */

                }
        );
        JButton deleteReservations = new JButton("Delete reservation");
        deleteReservations.addActionListener(
                (ActionEvent e ) -> {

                    if(showList.getSelectedIndex() == -1) { } else {
                        int i = showList.getSelectedIndex();
                        System.out.println(i);
                        int a = (Integer) listModel.get(i).getKey();
                        System.out.println(a);
                        //Controller.storeSelectedID(a);

                        // put into controller, then call from here
                        MySqlConnection.deleteRerv(a);
                        updateList(Controller.getRervs());
                        frame.setVisible(true);
                    }



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
        makeReservationList(treeMap);
        contentPane.add(list,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }
// change returntype to JPanel
    public void makeReservationList(TreeMap<Integer,String> treeMap) {
        list = new JPanel();
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

    }

    public void updateList(TreeMap<Integer,String> treeMap) {
        ArrayList<String> temp = new ArrayList<>();
        listModel = new DefaultListModel<>();
        stringModel = new DefaultListModel<>();
        for(Map.Entry<Integer,String> entry : treeMap.entrySet()) {
            String value = entry.getValue();
            temp.add(value);
            stringModel.addElement(value);
            listModel.addElement(entry);

        }

        // convert to String[] from arrayList
        int i = 0;
        String[] var = new String[temp.size()];
        for(String r : temp) {
            System.out.println(r);
            var[i] = r;
            i++;
        }

        JList updatedList = new JList<>(var);

        showList.setModel(stringModel);

        //showList.setPreferredSize(new Dimension(400,400));
        System.out.println(showList.size());

        frame.setVisible(true);


    }

}
