/**
 * Created by caecilieiversen on 30/11/2016.
 */
import Models.Showing;
//import apple.laf.JRSUIUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.*;
import java.util.TreeMap;
import java.util.Map;

    public class BookingGUI
    {
        private JFrame frame;
        private JList<String> showList;
        private TreeMap<Integer,String> showings;
        private DefaultListModel<Map.Entry> listModel;

        public BookingGUI()
        {
            frame = new JFrame("CinemaView: Book Tickets");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //makeFrame(arrayList);
            listModel = new DefaultListModel();
            frame.setVisible(true);
        }

        public void makeFrame(TreeMap treemap) {
            JPanel contentPane = (JPanel)frame.getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

            JPanel searchBar = new JPanel();
            searchBar.setLayout(new FlowLayout());
            searchBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

            JTextField movie = new JTextField(20);
            movie.setBorder(new TitledBorder("Movie"));
            searchBar.add(movie);

            JTextField time = new JTextField(20);
            time.setBorder(new TitledBorder("Date"));
            searchBar.add(time);

            JButton search = new JButton("Search");
            search.addActionListener(
                    (ActionEvent e) -> {
                        String a = movie.getText();
                        System.out.println(a);
                    }
            );
            searchBar.add(search);

            contentPane.add(searchBar, BorderLayout.NORTH);
            contentPane.add(makeList(treemap), BorderLayout.CENTER); // makeList

            JPanel actionBar = new JPanel();
            JPanel buttomBar = new JPanel();
            actionBar.setLayout(new BorderLayout());
            actionBar.setBorder(new EmptyBorder(50, 10, 350, 10));

            // Make reservation
            JButton book = new JButton("Book");
            book.addActionListener(
                    (ActionEvent e) -> {
                        if(showList.getSelectedIndex() == -1) { } else {
                            int i = showList.getSelectedIndex();
                            //System.out.println(i);
                            int a = (Integer) listModel.get(i).getKey();
                            //System.out.println(a);
                            Controller.storeSelectedID(a);
                            Controller.getShowByID(a,"");
                        }
                    }
            );
            actionBar.add(book, BorderLayout.CENTER);

            // Change reservation
            JButton changeReservation = new JButton("Change Reservation");
            changeReservation.addActionListener(
                    (ActionEvent e) -> {
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
                    }
            );
            buttomBar.add(changeReservation);

            // Delete reservation
            JButton deleteReservation = new JButton("Delete Reservation");
            deleteReservation.addActionListener(
                    (ActionEvent e) -> {
                        int tlf_nr = 20546033;
                        Controller.deleteReservation(tlf_nr);
                    }
            );
            buttomBar.add(deleteReservation);

            contentPane.add(actionBar, BorderLayout.EAST);
            contentPane.add(buttomBar,BorderLayout.SOUTH);
            frame.setVisible(true);

        }

        public void updateList() {
            // Get input from movie-field
            // get the shows containing input
            // remake list, update view.

        }

        public JPanel makeList(TreeMap<Integer,String> treeMap) {
            JPanel list = new JPanel();
            ArrayList<String> temp = new ArrayList<>();

            for(Map.Entry<Integer,String> entry : treeMap.entrySet()) {
                Integer key = entry.getKey();

                String value = entry.getValue();
                temp.add(value);
                listModel.addElement(entry);
            }

            //showList = new JList<>(temp.toArray(new String[temp.size()]));

            // convert to String[] from arrayList
            int i = 0;
            String[] var = new String[temp.size()];
            for(String r : temp) {
                var[i] = r;
                i++;
            }

            showList = new JList<String>(var);
            showList.setFont(new Font("Cambria", Font.BOLD, 14));
            showList.setBorder(new EmptyBorder(10,10,10,10));

            list.add(showList);

            return list;
        }
    }