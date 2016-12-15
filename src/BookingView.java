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
class BookingView {

    //Fields
    private JFrame frame;                                                   //frame
    private JList<String> showList;                                         //list of shows
    private DefaultListModel<Map.Entry> listModel;                          //list for storing shows

    //Constructor
    BookingView() {
        frame = new JFrame("CinemaView: Book Tickets");                //initialize frame
        frame.setMinimumSize(new Dimension(700, 500));        //set size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);               //and close
        listModel = new DefaultListModel<>();                               //make listmodel
    }

    /**
     * Makes frame setup with all panels
     * Includes: Search bar,
     * Panel for list of showings,
     * Buttons for opening booking screen and change of reservation
     * @param treeMap takes a treemap of showings
     */
    public void makeFrame(TreeMap<Integer, String> treeMap) {
        JPanel contentPane = (JPanel) frame.getContentPane();               //get contentPane
        contentPane.setBackground(Color.darkGray);
        contentPane.setLayout(new BorderLayout());                          //set Layout
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));       //set Border

        contentPane.add(makeSearchBar(), BorderLayout.NORTH);               //add search bar to contentpane
        contentPane.add(makeList(treeMap), BorderLayout.CENTER);            //add list of shows by makeList method

        //JPanels for buttons: book and change reservation
        JPanel actionBar = new JPanel();
        actionBar.setBackground(Color.darkGray);
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.darkGray);
        actionBar.setLayout(new BorderLayout());
        actionBar.setBorder(new EmptyBorder(50, 10, 350, 10));

        //Make reservation
        actionBar.add(makeReserveButton(), BorderLayout.CENTER);            //add reserve button to layout
        actionBar.setMinimumSize(new Dimension(50,20));

        //Change reservation
        bottomBar.add(makeChangeButton());                                  //add change button
        contentPane.add(actionBar, BorderLayout.EAST);                      //add buttons to contentpane
        contentPane.add(bottomBar, BorderLayout.SOUTH);

        //Pack and set visible
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates search bar
     * @return JPanel containing search bar
     */
    private JPanel makeSearchBar() {
        JPanel searchBar = new JPanel();                                        //create new JPanel
        searchBar.setBackground(Color.darkGray);
        searchBar.setLayout(new FlowLayout());                                  //set Layout
        searchBar.setBorder(new EtchedBorder(EtchedBorder.LOWERED));            //set Border

        JTextField movie = new JTextField(20);                         //create textField
        movie.setBackground(Color.LIGHT_GRAY);
        movie.setBorder(new TitledBorder("Movie"));                       //set border with title "Movie"
        searchBar.add(movie);                                                  //add textField to JPanel searchBar

        JTextField time = new JTextField(20);                          //create textField
        time.setBackground(Color.LIGHT_GRAY);
        time.setBorder(new TitledBorder("Date"));                         //set border with title "Date"
        searchBar.add(time);                                                   //add textField to JPanel searchBar

        JButton search = new JButton("Search");                           //create new Button "Search"
        search.addActionListener(                                              //add actionListener
                (ActionEvent e) -> {                                           //lambda for functionality
                    String title = movie.getText();                            //get text from movie field
                    String date = time.getText();                              //and get date field
                    if (!title.isEmpty() && date.isEmpty()) {                  //if there is movie text, but no data text
                        //update list searching for title
                        updateList(Controller.showsMatchingSearchTitle(title));      //update list with titles maching input in textField
                    }
                    if(!date.isEmpty() && title.isEmpty()){                    //if only the date field is nonempty
                        //update list searching for date
                        updateList(Controller.showsMatchingSearchDate(date));  //update list with dates maching input in textField
                    }
                    if(date.isEmpty() && title.isEmpty()){                     //if both are empty, get complete list of shows
                        updateList(Controller.updateShowingList());
                    }
                }
        );

        searchBar.add(search);                                                 //add search button to searchBar
        frame.getRootPane().setDefaultButton(search);                          // Set defaultButton to "Search"

        return searchBar;
    }

    /**
     * Creates button for ticket reservation
     * @return JButton for reservation
     */
    private JButton makeReserveButton() {
        JButton book = new JButton("Reserve seats");
        book.setMinimumSize(new Dimension(50,20));
        book.addActionListener(
                (ActionEvent e) -> {
                    if (showList.getSelectedIndex() == -1) {
                        return;
                    }
                    int i = showList.getSelectedIndex();                      //get index of selection
                    int a = (Integer) listModel.get(i).getKey();              //get key for showing
                    Controller.storeShowID(a);                                //store key in Controller
                    Controller.CreateCinemaViewByShowID(a, "",false);         //get the show with chosen id
                }
        );

        return book;
    }

    /**
     * Creates button for changing reservations
     * @return JButton for reservation changes
     */
    private JButton makeChangeButton(){
        JButton changeReservation = new JButton("Change Reservation");
        changeReservation.addActionListener(
                (ActionEvent e) -> {
                    Controller.createReservationView();                       //switch view to reservationview
                }
        );

        return changeReservation;
    }

    /**
     * Makes a JPanel with a list of showings
     * @param treeMap takes treeMap of showings
     * @return returns a list of showing
     */
    private JPanel makeList(TreeMap<Integer, String> treeMap) {
            JPanel list = new JPanel();                                       //make panel for showing list
            list.setBackground(Color.darkGray);
            ArrayList<String> temp = new ArrayList<>();                       //temporary arraylist

            //for each løkke over det givne treemap
            Controller.processTreeMapForView(temp,treeMap,listModel);

            // convert to String[] from arrayList
            String[] var = Controller.arrayListToStringArray(temp);

            showList = new JList<>(var);                                      //new list of String array
            showList.setBackground(Color.lightGray);
            showList.setFont(new Font("Cambria", Font.BOLD, 14));         //set cont
            showList.setBorder(new EmptyBorder(10, 10, 10, 10));

            //add showlist to jpanel
            list.add(showList);
            return list;
    }

    /**
     * Updates the list in JPanel from makeList
     * @param treeMap takes a treeMap of showings
     */
    private void updateList(TreeMap<Integer,String> treeMap) {
            ArrayList<String> temp = new ArrayList<>();                       //creates a temp arraylist
            listModel = new DefaultListModel<>();                             //cleans listmodel and stringmodel
            DefaultListModel<String> stringModel = new DefaultListModel<>();

            //Convert treeMap to usefull data
            Controller.processTreeMapForView(temp,treeMap,stringModel,listModel);

            showList.setModel(stringModel);                                   //sets list of model
            frame.setVisible(true);
    }
}