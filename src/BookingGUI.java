/**
 * Created by caecilieiversen on 30/11/2016.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Map;

    public class BookingGUI {

        //fields
        private JFrame frame;                                       //frame
        private JList<String> showList;                             //list of shows

        //private TreeMap<Integer, String> showings;
        private DefaultListModel<Map.Entry> listModel;              //list for storing shows
        private DefaultListModel<String> stringModel;               //list for storing indicies

        //Constructor
        public BookingGUI() {
            frame = new JFrame("CinemaView: Book Tickets");         //initialize frame
            frame.setSize(800, 600);                                //set size
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //and close
            listModel = new DefaultListModel();                     //make listmodel
        }

        //Create frame
        public void makeFrame(TreeMap treemap) {
            //get contentpane
            JPanel contentPane = (JPanel) frame.getContentPane();

            //set Layout
            contentPane.setLayout(new BorderLayout());
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

            //jpanel for searchbar
            JPanel searchBar = new JPanel();
            //set layouts
            searchBar.setLayout(new FlowLayout());
            searchBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

            //textfield for movie search
            JTextField movie = new JTextField(20);
            movie.setBorder(new TitledBorder("Movie"));
            searchBar.add(movie);

            //textfield for time search
            JTextField time = new JTextField(20);
            time.setBorder(new TitledBorder("Date"));
            searchBar.add(time);

            //search button
            JButton search = new JButton("Search");
            search.addActionListener(
                    (ActionEvent e) -> {                                    //lambda for functionality
                        String title = movie.getText();                     //get text from movie field
                        //System.out.println(title);
                        String date = time.getText();                       //and get date field
                        if (!title.isEmpty() && date.isEmpty()) {           //if there is movie text, but no data text
                            //update list searching for title
                            updateList(Controller.makeSearchTitle(title));
                        }
                        if(!date.isEmpty() && title.isEmpty()){             //if only the date field is nonempty
                            //update list searching for date
                            updateList(Controller.makeSearchTime(date));
                        }
                        if(date.isEmpty() && title.isEmpty()){              //if both are empty, get complete list of shows
                            updateList(Controller.getShows());
                        }
                        System.out.println(title);
                    }
            );
            searchBar.add(search);  //add search button
            frame.getRootPane().setDefaultButton(search);
            contentPane.add(searchBar, BorderLayout.NORTH);                 //add all to contentpane
            contentPane.add(makeList(treemap), BorderLayout.CENTER);        //add list of shows by makeList method

            //Jpanels for buttons: book and change reservation
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
                                int i = showList.getSelectedIndex();            //get index of selection
                                int a = (Integer) listModel.get(i).getKey();    //get key for showing
                                Controller.storeSelectedID(a);                  //store key in Controller
                                Controller.getShowByID(a, "",false);            //get the show with chosen id
                            }
                        }
                );

            actionBar.add(book, BorderLayout.CENTER);                       //add button to layout

            // Change reservation
            JButton changeReservation = new JButton("Change Reservation");
                changeReservation.addActionListener(
                        (ActionEvent e) -> {
                            Controller.makeReservationView();                //switch view to reservationview
                        }
                );

            buttomBar.add(changeReservation);                                //add button
            contentPane.add(actionBar, BorderLayout.EAST);                   //add buttons to contentpane
            contentPane.add(buttomBar, BorderLayout.SOUTH);

            //pack and set visible
            frame.pack();
            frame.setVisible(true);
        }

        // Create list for displaying shows
        public JPanel makeList(TreeMap<Integer, String> treeMap) {
            JPanel list = new JPanel();                 //make panel for showing list
            ArrayList<String> temp = new ArrayList<>(); //temporary arraylist

            //for each løkke over det givne treemap
            Controller.getDataFromTreeMap(temp,treeMap,listModel);

            // convert to String[] from arrayList
            String[] var = Controller.arrayListToStringArray(temp);

            showList = new JList<>(var);        //new list of String array
            showList.setFont(new Font("Cambria", Font.BOLD, 14));   //set cont
            showList.setBorder(new EmptyBorder(10, 10, 10, 10));

            //add showlist to jpanel
            list.add(showList);
            return list;
        }

        // Update list
        public void updateList(TreeMap<Integer,String> treeMap) {
            ArrayList<String> temp = new ArrayList<>();         //creates a temp arraylist
            listModel = new DefaultListModel<>();               //cleans listmodel and stringmodel
            stringModel = new DefaultListModel<>();

            //Convert treeMap to usefull data
            Controller.getDataFromTreeMap(temp,treeMap,stringModel,listModel);

            showList.setModel(stringModel); //sets list of model

            frame.setVisible(true);


        }
    }