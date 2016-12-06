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
        //JList<Map.Entry> showList;
        TreeMap<Integer,String> showings;
        DefaultListModel<Map.Entry> listModel;

        public BookingGUI()
        {
            frame = new JFrame("CinemaView: Book Tickets");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //makeFrame(arrayList);
            frame.setVisible(true);
        }

        public void makeFrame(TreeMap treemap)
        {
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
            searchBar.add(search);

            contentPane.add(searchBar, BorderLayout.NORTH);
            contentPane.add(makeList(treemap), BorderLayout.CENTER); // makeList

            JPanel actionBar = new JPanel();
            actionBar.setLayout(new FlowLayout());
            JButton book = new JButton("Book");
            book.addActionListener(
                    (ActionEvent e) -> {
                        if(showList.getSelectedIndex() == -1) { } else {
                            int i = showList.getSelectedIndex();
                            int a = (Integer) listModel.get(i).getKey();
                            Controller.storeSelectedID(a);
                            Controller.getShowByID(a);
                        }
                    }
            );
            actionBar.add(book);
            contentPane.add(actionBar, BorderLayout.EAST);
            frame.setVisible(true);

        }

        public JPanel makeList(TreeMap<Integer,String> arrayList) {
            JPanel list = new JPanel();
            listModel = new DefaultListModel();
            ArrayList<String> temp = new ArrayList<>();

            for(Map.Entry<Integer,String> entry : arrayList.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue();
                temp.add(value);
                temp.add("");
                listModel.addElement(entry);
            }

            showList = new JList<>(temp.toArray(new String[temp.size()]));
            showList.setFont(new Font("Cambria", Font.BOLD, 14));
            // DefaultListCellRenderer renderer =  (DefaultListCellRenderer) showList.getCellRenderer();
            // renderer.setHorizontalAlignment(JLabel.CENTER);

            list.add(showList);
            return list;
        }
    }