/**
 * Created by caecilieiversen on 30/11/2016.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.*;
import java.util.TreeMap;
import java.util.Map;

    public class BookingGUI {
        private JFrame frame;
        private JList<String> showList;
        private TreeMap<Integer, String> showings;
        private DefaultListModel<Map.Entry> listModel;
        private DefaultListModel<String> stringModel;

        public BookingGUI() {
            frame = new JFrame("CinemaView: Book Tickets");
            frame.setSize(800, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            listModel = new DefaultListModel();
            //frame.setVisible(true);
        }

        public void makeFrame(TreeMap treemap) {
            JPanel contentPane = (JPanel) frame.getContentPane();
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
                        String title = movie.getText();
                        System.out.println(title);
                        String date = time.getText();
                        if (!title.isEmpty() && date.isEmpty()) {
                            updateList(Controller.makeSearchTitle(title));
                        }
                        if(!date.isEmpty() && title.isEmpty()){
                            updateList(Controller.makeSearchTime(date));
                        }
                        if(date.isEmpty() && title.isEmpty()){
                            updateList(Controller.getShows());
                        }
                        System.out.println(title);
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
                        if (showList.getSelectedIndex() == -1) {
                        } else {
                            int i = showList.getSelectedIndex();
                            //System.out.println(i);
                            int a = (Integer) listModel.get(i).getKey();
                            //System.out.println(a);
                            Controller.storeSelectedID(a);
                            Controller.getShowByID(a, "",false);
                        }
                    }
            );
            actionBar.add(book, BorderLayout.CENTER);

            // Change reservation
            JButton changeReservation = new JButton("Change Reservation");
            changeReservation.addActionListener(
                    (ActionEvent e) -> {
                        Controller.makeReservationView();
                    }
            );
            buttomBar.add(changeReservation);
            contentPane.add(actionBar, BorderLayout.EAST);
            contentPane.add(buttomBar, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);

        }

        // Create list for displaying shows
        public JPanel makeList(TreeMap<Integer, String> treeMap) {
            JPanel list = new JPanel();
            ArrayList<String> temp = new ArrayList<>();

            for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
                String value = entry.getValue();
                temp.add(value);
                listModel.addElement(entry);
            }

            // convert to String[] from arrayList
            int i = 0;
            String[] var = new String[temp.size()];
            for (String r : temp) {
                var[i] = r;
                i++;
            }

            showList = new JList<>(var);
            showList.setFont(new Font("Cambria", Font.BOLD, 14));
            showList.setBorder(new EmptyBorder(10, 10, 10, 10));

            list.add(showList);
            return list;
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