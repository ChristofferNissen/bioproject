import Models.MySqlConnection;

import javax.swing.*;
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

    // Field variables
    private JFrame frame;
    private JPanel list;
    private JList<String> showList;
    private DefaultListModel<Map.Entry> listModel;
    private DefaultListModel<String> stringModel;

    public ReservationView() {
        frame = new JFrame("Reservations");
    }

    // Creates the gui with elements for displaying the reservation frame
    public void makeFrame(TreeMap treeMap){
        JPanel contentPane = (JPanel)frame.getContentPane();
        frame.setPreferredSize(new Dimension(400, 800));
        contentPane.setLayout(new BorderLayout());

        JPanel search = new JPanel();
        search.setLayout(new GridLayout(2,1));

        JTextField text = new JTextField();
        text.setToolTipText("Enter a phone number");
        search.add(text);

        JButton searchReservations = new JButton("Search reservations");
        frame.getRootPane().setDefaultButton(searchReservations);
        searchReservations.addActionListener(
                (ActionEvent e ) -> {
                    String tlf_nr = text.getText();
                    // When search is pressed, look up the phoneNumber and if it exists in DB, get a result back
                    updateList(Controller.getReservationByID(tlf_nr));
                }
        );
        search.add(searchReservations);

        JButton changeReservations = new JButton("Change reservation");
        changeReservations.addActionListener(
                (ActionEvent e ) -> {
                    if(showList.getSelectedIndex() == -1) { } else {
                        int i = showList.getSelectedIndex();
                        System.out.println(i);
                        int a = (Integer) listModel.get(i).getKey();
                        System.out.println(a);

                        // store reservation_ID for this reservation
                        Controller.storeReservationID(a);

                        // Display the reselected reservation
                        Controller.displayReservation(a);
                        frame.setVisible(true);
                    }
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

                        // put into controller, then call from here
                        MySqlConnection.deleteReservation(a);
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

        buttonPane2.add(search,BorderLayout.NORTH);
        buttonPane2.add(changeReservations,BorderLayout.CENTER);
        buttonPane2.add(deleteReservations,BorderLayout.SOUTH);

        contentPane.add(buttonPane,BorderLayout.NORTH);
        makeReservationList(treeMap);
        contentPane.add(list,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);

    }

    // Creates a visual list of reservations
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

    // Updates the visual list of reservations due to search criterias
    public void updateList(TreeMap<Integer,String> treeMap) {
        ArrayList<String> temp = new ArrayList<>();
        stringModel = new DefaultListModel<>();

        // Get data from treeMap into arrayList
        Controller.getDataFromTreeMap(temp,treeMap,stringModel,listModel);

        showList.setModel(stringModel);
        frame.setVisible(true);

    }
}
