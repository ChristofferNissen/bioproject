import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * ReservationView creates the GUI element "Reservations" which will be created when "Change reservation" is pressed in BookingView
 */
class ReservationView{

    // Field variables
    private JFrame frame;
    private JPanel list;
    private JList<String> reservationList;
    private DefaultListModel<Map.Entry> listModel;

    ReservationView() {
        frame = new JFrame("Reservations");
    }

    /**
     * Constructs the GUI elements of ReservationView
     * @param treeMap A treeMap containing Reservations
     * @return boolean on condition if frame was created
     */
    boolean makeFrame(TreeMap<Integer,String> treeMap){
        JPanel contentPane = (JPanel)frame.getContentPane();
        frame.setPreferredSize(new Dimension(400, 800));
        contentPane.setLayout(new BorderLayout());

        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(Color.darkGray);
        buttonPane.setLayout(new BorderLayout());

        JPanel buttonPane2 = new JPanel();
        buttonPane2.setBackground(Color.darkGray);
        buttonPane2.setLayout(new BorderLayout());
        buttonPane2.add(makeSearch(),BorderLayout.NORTH);
        buttonPane2.add(makeChangeButton(),BorderLayout.CENTER);
        buttonPane2.add(makeDeleteButton(),BorderLayout.SOUTH);

        buttonPane.add(buttonPane2,BorderLayout.CENTER);
        contentPane.add(buttonPane,BorderLayout.NORTH);

        makeReservationList(treeMap);
        contentPane.add(list,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
        return true;
    }

    /**
     * Creates search bar and search button for reservation change
     * @return JPanel with search bar and search button
     */
    private JPanel makeSearch() {
        JPanel search = new JPanel();
        search.setLayout(new GridLayout(2,1));
        search.setBackground(Color.darkGray);

        JTextField text = new JTextField();
        text.setBackground(Color.lightGray);
        text.setToolTipText("Enter a phone number");
        search.add(text);

        JButton searchReservations = new JButton("Search reservations");
        frame.getRootPane().setDefaultButton(searchReservations);
        searchReservations.addActionListener(
                (ActionEvent e ) -> {
                    String tlf_nr = text.getText();
                    //when search is pressed, look up the phoneNumber and if it exists in DB, get a result back
                    updateList(Controller.getReservationsByPhone(tlf_nr));
                }
        );
        search.add(searchReservations);

        return search;
    }

    /**
     * Creates "Change reservation"-button
     * @return JButton for changing reservations
     */
    private JButton makeChangeButton() {
        JButton changeReservations = new JButton("Change reservation");
        changeReservations.addActionListener(
                (ActionEvent e ) -> {
                    if (reservationList.getSelectedIndex() != -1) {
                        int i = reservationList.getSelectedIndex();
                        int a = (Integer) listModel.get(i).getKey();

                        //store reservation_ID for this reservation
                        Controller.storeReservationID(a);

                        //display the reselected reservation
                        Controller.displayReservation(a);
                    }
                }
        );

        return changeReservations;
    }

    /**
     * Creates "Delete reservation"-button
     * @return JButton for deleting a reservation
     */
    private JButton makeDeleteButton() {
        JButton deleteReservations = new JButton("Delete reservation");
        deleteReservations.addActionListener(
                (ActionEvent e ) -> {
                    if (reservationList.getSelectedIndex() != -1) {
                        int i = reservationList.getSelectedIndex();
                        int a = (Integer) listModel.get(i).getKey();

                        // put into controller, then call from here
                        Controller.deleteReservation(a);
                        updateList(Controller.updateReservationList());
                        frame.setVisible(true);
                    }
                }
        );

        return deleteReservations;
    }

    /**
     * Creates the gui element list which contains displays all reservations
     * @param treeMap Treemap<Integer,String> consisting of the reservation_id as key, and reservation.toString() as value
     */
    private void makeReservationList(TreeMap<Integer,String> treeMap) {
        list = new JPanel();
        list.setBackground(Color.darkGray);
        list.setPreferredSize(new Dimension(400,400));
        listModel = new DefaultListModel<>();
        ArrayList<String> temp = new ArrayList<>();

        Controller.processTreeMapForView(temp,treeMap,listModel);

        // convert to String[] from arrayList
        String[] var = Controller.arrayListToStringArray(temp);

        reservationList = new JList<>(var);
        reservationList.setBackground(Color.lightGray);
        reservationList.setFont(new Font("Cambria", Font.BOLD, 14));
        reservationList.setBorder(new EmptyBorder(10,10,10,10));

        list.add(reservationList);

    }

    // Updates the visual list of reservations due to search criterias

    /**
     * Updates the gui-element list based on a new treeMap containing reservations
     * @param treeMap New treeMap to be displayed
     */
    private void updateList(TreeMap<Integer,String> treeMap) {
        ArrayList<String> temp = new ArrayList<>();
        DefaultListModel<String> stringModel = new DefaultListModel<>();
        listModel = new DefaultListModel<>();

        // Get data from treeMap into arrayList
        Controller.processTreeMapForView(temp,treeMap,stringModel,listModel);

        reservationList.setModel(stringModel);
        frame.setVisible(true);
    }
}
