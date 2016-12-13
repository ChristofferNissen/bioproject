
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.border.*;
import java.util.Map;


/**
 * Initial user interface
 * loaded on start
 */
class BookingGUI {

        //fields
        private JFrame frame;                                                   //frame
        private JList<String> showList;                                         //list of shows
        private DefaultListModel<Map.Entry> listModel;                          //list for storing shows
        //private DefaultListModel<String> stringModel;                           //list for storing indicies

        //Constructor
        BookingGUI() {
            frame = new JFrame("CinemaView: Book Tickets");                     //initialize frame
            frame.setSize(800, 600);                                            //set size
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               //and close
            listModel = new DefaultListModel<>();                               //make listmodel
        }

    /**
     * Makes frame setup with all panels
     * Search bar
     * panel for list of showings
     * buttons for opening booking screen and change of reservation
     * @param treeMap takes a treemap of showings
     */
    public void makeFrame(TreeMap<Integer, String> treeMap) {
            JPanel contentPane = (JPanel) frame.getContentPane();               // Get contentPane
            contentPane.setLayout(new BorderLayout());                          // Set Layout
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));             // Set Border

            JPanel searchBar = new JPanel();                                    // Create new JPanel
            searchBar.setLayout(new FlowLayout());                              // Set Layout
            searchBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));        // Set Border

            JTextField movie = new JTextField(20);                              // Create textField
            movie.setBorder(new TitledBorder("Movie"));                         // Set border with title "Movie"
            searchBar.add(movie);                                               // add textField to JPanel searchBar

            JTextField time = new JTextField(20);                               // Create textField
            time.setBorder(new TitledBorder("Date"));                           // Set border with title "Date"
            searchBar.add(time);                                                // Add textField to JPanel searchBar

            JButton search = new JButton("Search");                             // Create new Button "Search"
            search.addActionListener(                                           // Add actionListener
                    (ActionEvent e) -> {                                        //lambda for functionality
                        String title = movie.getText();                         //get text from movie field
                        String date = time.getText();                           //and get date field
                        if (!title.isEmpty() && date.isEmpty()) {               //if there is movie text, but no data text
                            //update list searching for title
                            updateList(Controller.showsMatchingSearchTitle(title));      // Update list with titles maching input in textField
                        }
                        if(!date.isEmpty() && title.isEmpty()){                 // If only the date field is nonempty
                            //update list searching for date
                            updateList(Controller.showsMatchingSearchDate(date));        // Update list with dates maching input in textField
                        }
                        if(date.isEmpty() && title.isEmpty()){                  // If both are empty, get complete list of shows
                            updateList(Controller.updateShowingList());
                        }
                    }
            );

            searchBar.add(search);                                              //add search button to searchBar
            frame.getRootPane().setDefaultButton(search);                       // Set defaultButton to "Search"
            contentPane.add(searchBar, BorderLayout.NORTH);                     //add all to contentpane
            contentPane.add(makeList(treeMap), BorderLayout.CENTER);            //add list of shows by makeList method

            //Jpanels for buttons: book and change reservation
            JPanel actionBar = new JPanel();
            JPanel buttomBar = new JPanel();
            actionBar.setLayout(new BorderLayout());
            actionBar.setBorder(new EmptyBorder(50, 10, 350, 10));

            // Make reservation
            JButton book = new JButton("Reserve seats");
            book.setMinimumSize(new Dimension(50,20));
                book.addActionListener(
                        (ActionEvent e) -> {
                            if (showList.getSelectedIndex() == -1) {
                                return;
                            }
                            int i = showList.getSelectedIndex();            //get index of selection
                            int a = (Integer) listModel.get(i).getKey();    //get key for showing
                            Controller.storeShowID(a);                  //store key in Controller
                            Controller.CreateCinemaViewByShowID(a, "",false);     //get the show with chosen id
                        }
                );

            actionBar.add(book, BorderLayout.CENTER);                           //add button to layout
            actionBar.setMinimumSize(new Dimension(50,20));

            // Change reservation
            JButton changeReservation = new JButton("Change Reservation");
                changeReservation.addActionListener(
                        (ActionEvent e) -> {
                            Controller.createReservationView();                   //switch view to reservationview
                        }
                );

            buttomBar.add(changeReservation);                                   //add button
            contentPane.add(actionBar, BorderLayout.EAST);                      //add buttons to contentpane
            contentPane.add(buttomBar, BorderLayout.SOUTH);

            //pack and set visible
            frame.pack();
            frame.setVisible(true);
        }

    /**
     * makes a jpanel with a list of showings
     * @param treeMap takes treemap of showings
     * @return returns a list of showing
     */
    private JPanel makeList(TreeMap<Integer, String> treeMap) {
            JPanel list = new JPanel();                                         //make panel for showing list
            ArrayList<String> temp = new ArrayList<>();                         //temporary arraylist

            //for each løkke over det givne treemap
            Controller.processTreeMapForView(temp,treeMap,listModel);

            // convert to String[] from arrayList
            String[] var = Controller.arrayListToStringArray(temp);

            showList = new JList<>(var);                                        //new list of String array
            showList.setFont(new Font("Cambria", Font.BOLD, 14));               //set cont
            showList.setBorder(new EmptyBorder(10, 10, 10, 10));

            //add showlist to jpanel
            list.add(showList);
            return list;
        }

    /**
     * updates the list in jpanel from makelist
     * @param treeMap takes a treemap of showings
     */
    private void updateList(TreeMap<Integer,String> treeMap) {
            ArrayList<String> temp = new ArrayList<>();                         //creates a temp arraylist
            listModel = new DefaultListModel<>();                               //cleans listmodel and stringmodel
            DefaultListModel<String> stringModel = new DefaultListModel<>();

            //Convert treeMap to usefull data
            Controller.processTreeMapForView(temp,treeMap,stringModel,listModel);

            showList.setModel(stringModel);                                     //sets list of model
            frame.setVisible(true);
        }
}