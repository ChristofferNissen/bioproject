/**
 * Created by caecilieiversen on 30/11/2016.
 */
import Models.Showing;
import apple.laf.JRSUIUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.*;

    public class BookingGUI
    {
        private JFrame frame;
        JList<Showing> showList;
        TreeMap showings;
        DefaultListModel<Showing> listModel;

        public BookingGUI()
        {
            frame = new JFrame("Cinema: Book Tickets");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //makeFrame(arrayList);
            frame.setVisible(true);
        }

        public void makeFrame(ArrayList<Models.Showing> arrayList)
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
            time.setBorder(new TitledBorder("Time"));
            searchBar.add(time);

            JButton search = new JButton("Search");
            searchBar.add(search);

            contentPane.add(searchBar, BorderLayout.NORTH);
            contentPane.add(makeList(arrayList), BorderLayout.CENTER); // makeList

            JPanel actionBar = new JPanel();
            actionBar.setLayout(new FlowLayout());
            JButton book = new JButton("Book");
            book.addActionListener(
                    (ActionEvent e) -> {
                        int i = showList.getSelectedIndex();
                        System.out.println(listModel.get(i).getShow_id());
                    }

            );
            actionBar.add(book);

            contentPane.add(actionBar, BorderLayout.EAST);

            frame.setVisible(true);

        }

        public JPanel makeList(ArrayList<Models.Showing> arrayList) {
            JPanel list = new JPanel();

            listModel = new DefaultListModel();

                for (Showing s : arrayList) {

                    listModel.addElement(s);


                }


            showList = new JList<Showing>(listModel);

            list.add(showList);



            return list;

        }
    }




