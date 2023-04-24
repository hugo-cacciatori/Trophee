import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class View extends JFrame
{
    //attributes
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final DecimalFormat PERCENT = new DecimalFormat("#.##");
    private static final DecimalFormat WINS = new DecimalFormat("#");
    private static final Color VERTTHOMAS = new Color(59,129,30);
    private Button settingsButton;
    private Button myClubButton;
    private Button listButton;
    private JPanel ongletsPanel;
    private JPanel contentPanel;
    private JPanel settingsPanel;
    private JPanel myClubPanel;
    private JPanel listPanel;
    private Player newTeamCapitain = new Player();

    private Integer thisUserTag;
    private Integer thisClubID;

    private Club thisClub;
    private User thisUser;

    private JLabel myTeams;
    private JLabel myPlayers;
    private ImageIcon fond;
    private ImageIcon listee;
    private ImageIcon trophee;
    private ImageIcon clunkee;
    private ImageIcon placeholderPic;
    private DefaultListModel<Team> tlModel;
    private DefaultListModel<Player> plModel;
    private DefaultListModel<Event> elModel;
    private JList<Team> tl;
    private JList<Player> pl;
    private JList<Event> el;
    private Document displayDocumentLeft;
    private Document displayDocumentRight;
    private Document secondaryListOneDoc;
    private Document secondaryListTwoDoc;
    private Document secondaryListOneTitleDoc;
    private Document secondaryListTwoTitleDoc;
    private JTextPane secondaryListOne;
    private JTextPane secondaryListTwo;
    private JTextArea nomArea;
    private JTextArea ownerArea;
    private JTextArea desArea;
    private JTextArea telArea;
    private JTextArea mailArea;
    private JTextArea sportArea;
    private JTextPane winrateClubPane;
    private JLabel ppLabel;
    private Document winrateClubDoc;
    private Document infoTitleDoc;
    private JPanel displayPanel;
    private JPanel displayNestedPanel;
    private JPanel secondaryListsPanel;
    private JScrollPane secListOneScroll;
    private JScrollPane secListTwoScroll;
    private BackgroundPanel content;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private JTextArea adressArea;
    private JComboBox<Club> clubBox;
    private JTextArea loggedAs;
    private boolean loggedIn;
    private JPanel loginPanel;
    private JTextArea idArea;
    private JPasswordField pwArea;
    private JFileChooser choose;

    private static View instance = null;
    public static View getInstance(){
        if(instance == null){
            instance = new View(WIDTH, HEIGHT);
        }
        return instance;
    }

    public View(int w, int h) 
    {
        this.setSize(w, h);
        this.ongletsPanel = new JPanel();
        this.contentPanel = new JPanel();
        this.myClubPanel = new JPanel();
        this.settingsPanel = new JPanel();
        this.listPanel = new JPanel();
        this.loginPanel = new JPanel();
        loggedIn = false;
        init();
    }

    private void init()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Trophee");
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.fond = new ImageIcon(getClass().getResource("/ressources/fezfezfez.png"));
        this.listee = new ImageIcon(getClass().getResource("/ressources/List.png"));
        this.trophee = new ImageIcon(getClass().getResource("/ressources/Trophee.png"));
        this.clunkee = new ImageIcon(getClass().getResource("/ressources/Engrenage.png"));

        this.clubBox = new JComboBox<Club>();

        this.choose = new JFileChooser
        (
        FileSystemView.getFileSystemView().getHomeDirectory()
        );
    
        choose.setDialogTitle("Selectionnez une image");
        choose.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images JPG et PNG", "jpg", "png");
        choose.addChoosableFileFilter(filter);

        ImageIcon thisFond = resizeImage(this.fond, WIDTH, HEIGHT);
        Image thisFondImage = thisFond.getImage();
        this.content = new BackgroundPanel(thisFondImage);
        this.content.setLayout(new BorderLayout());
        this.add(content);
        this.setLayout(new GridLayout(1,1));

        initLeftSide();
        initRightSide();
        this.setVisible(true);
    }
    
    private void initLeftSide(){

        ImageIcon listeeSideButton  = resizeImage(this.listee, 50, 50);
        ImageIcon tropheeSideButton = resizeImage(this.trophee, 50, 50);
        ImageIcon clunkeeSideButton = resizeImage(this.clunkee, 50, 50);

        this.listButton = new Button();
        this.myClubButton = new Button();
        this.settingsButton = new Button();
        this.listButton.add(new JLabel(listeeSideButton));
        this.myClubButton.add(new JLabel(tropheeSideButton));
        this.settingsButton.add(new JLabel(clunkeeSideButton));
        this.ongletsPanel.add(listButton);
        this.ongletsPanel.add(myClubButton);
        this.ongletsPanel.add(settingsButton);
        this.ongletsPanel.setLayout(new GridLayout(3,0));
        this.ongletsPanel.setOpaque(false);
        this.content.add(this.ongletsPanel, BorderLayout.WEST);
    }

    private void initRightSide(){
        initOnglets();
        this.contentPanel.setLayout(new CardLayout());
        this.contentPanel.setOpaque(false);
        this.content.add(this.contentPanel, BorderLayout.CENTER);
    }

    private void initOnglets(){
        this.myClubPanel.setOpaque(false);
        this.listPanel.setOpaque(false);
        this.settingsPanel.setOpaque(false);
        this.contentPanel.add(this.myClubPanel);
        this.contentPanel.add(this.listPanel);
        this.contentPanel.add(this.settingsPanel);
        this.contentPanel.add(this.loginPanel);

        drawLoginScreen();
    }

    //DRAW

    private void drawLoginScreen(){
        //drawLoginScreen:
        myClubPanel.removeAll();
        listPanel.removeAll();
        settingsPanel.removeAll();
        this.loginPanel.setLayout(new GridBagLayout());
        JPanel centerPanel = new JPanel();
        JPanel labelPanel = new JPanel();
        JPanel idPanel = new JPanel();
        JPanel pwPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        labelPanel.setLayout(new GridBagLayout());
        idPanel.setLayout(new GridBagLayout());
        pwPanel.setLayout(new GridBagLayout());
        buttonsPanel.setLayout(new GridBagLayout());
        centerPanel.setLayout(new GridLayout(4,1));
        labelPanel.setOpaque(false);
        idPanel.setOpaque(false);
        pwPanel.setOpaque(false);
        buttonsPanel.setOpaque(false);
        centerPanel.setOpaque(false);
        loginPanel.setOpaque(false);
        JLabel logInLabel = new JLabel("Veuillez vous identifier");
        JLabel idLabel = new JLabel("Identifiant : ");
        JLabel pwLabel = new JLabel("Mot de passe : ");
        Button confirmButton = new Button("Se connecter");
        Button createAccount = new Button("Créer un compte");
        this.idArea = new JTextArea();
        this.pwArea = new JPasswordField();
        idArea.setLineWrap(true);
        idArea.setWrapStyleWord(true);
        idArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pwArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        labelPanel.add(logInLabel);
        addComponent(idPanel, idLabel, 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 20), 0, 0);
        addComponent(idPanel, idArea, 1, 0, 1, 1, 2.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), -10, 0);
        addComponent(pwPanel, pwLabel, 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0);
        addComponent(pwPanel, pwArea, 1, 0, 1, 1, 2.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 150, 0);
        addComponent(buttonsPanel, confirmButton, 0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 30, 0, 0), 0, 0);
        addComponent(buttonsPanel, createAccount, 1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 10), 0, 0);

        centerPanel.add(labelPanel);
        centerPanel.add(idPanel);
        centerPanel.add(pwPanel);
        centerPanel.add(buttonsPanel);
        centerPanel.setPreferredSize(new Dimension(300,200));
        this.loginPanel.add(centerPanel);
        setLoginScreenVisible();

        confirmButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean isClubListEmpty = false;
                try {
                    String password = "";
                    for(char c:pwArea.getPassword()){
                        password+=c;
                    }
                    ResultSet authResults = DatabaseManager.getInstance().authentificate(idArea.getText(), password);
                    if(authResults.next() && authResults.getBoolean("users.isActive")){

                        loggedIn = true;

                        thisUserTag = authResults.getInt("tag");
                        updateThisUser();
                        ResultSet userClubsRS = DatabaseManager.getInstance().getUserClubs(thisUserTag);
                        if(userClubsRS.next()){
                            thisClubID = userClubsRS.getInt("clubs.id");
                            thisClub = new Club(userClubsRS.getInt("clubs.id"), userClubsRS.getString("clubs.nom"), userClubsRS.getString("clubs.adresse"), userClubsRS.getString("clubs.tel"), userClubsRS.getString("clubs.mail"), userClubsRS.getString("clubs.description"), userClubsRS.getInt("clubs.sportID"), userClubsRS.getInt("clubs.userTag"));
                        }
                        else{
                            isClubListEmpty = true;
                            openNewClubWindow();
                        }
                    }
                    else{
                        ChildWindow denied = new ChildWindow(220,80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Identifiant ou mot de passe erroné.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        } 
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if(loggedIn && !isClubListEmpty)
                {
                    listButton.addSwapToListListener();
                    myClubButton.addSwapToMyClubListener();
                    settingsButton.addSwapToSettingsListener();
                    drawMyClub();
                    drawList();
                    drawSettings();
                    loginPanel.setVisible(false);
                    setMyClubVisible();
                }
            }
        }
        );

        createAccount.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent event) 
            {
                createAccount();
            }
        }
        );
    }

    private void drawList(){
        //drawlist:
        this.listPanel.setLayout(new GridBagLayout());
        JPanel header = new JPanel();
        JPanel listTitles = new JPanel();
        JPanel body = new JPanel();
        JPanel listButtons = new JPanel();
        JPanel selectBand = new JPanel();
        JPanel displayPanel = new JPanel();

        header.setOpaque(false);
        listTitles.setOpaque(false);
        body.setOpaque(false);
        displayPanel.setOpaque(false);
        listButtons.setOpaque(false);
        selectBand.setOpaque(false);

        addComponent(this.listPanel, header, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(this.listPanel, listTitles, 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(this.listPanel, body, 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(this.listPanel, listButtons, 0, 3, 1, 1, 0.1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 10);
        addComponent(this.listPanel, selectBand, 0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        addComponent(this.listPanel, displayPanel, 0, 5, 1, 1, 0.5, 0.5, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);

        drawListContent(header, listTitles, body, listButtons, selectBand, displayPanel);
    }

    private void drawListContent(JPanel header, JPanel listTitles, JPanel body, JPanel listButtons, JPanel selectBand, JPanel displayPanel){
        //drawlistcontent:
        Button refreshButton = new Button("Actualiser");
        Button newEventButton = new Button("Créer un event");
        Button modEventButton = new Button("Modifier un event");
        Button closeEventButton = new Button("Terminer un event");
        Button delEventButton = new Button("Supprimer un event");

        listButtons.setLayout(new GridLayout(1,4));

        listButtons.add(newEventButton);
        listButtons.add(modEventButton);
        listButtons.add(closeEventButton);
        listButtons.add(delEventButton);

        JComboBox<String> displayBox = new JComboBox<String>();
        displayBox.addItem("Compétitions");
        displayBox.addItem("Clubs");
        displayBox.addItem("Equipes");
        displayBox.addItem("Joueurs");

        String[] columnNames = {"#", "Date", "Nom", "Sport", "Club"};
        String[][] leftData = {
            {"1", "15/03/2021", "Les étoiles de l'esport", "League of legends", "Solary"},
            {"2", "20/12/2023", "Golf amateur", "Golf", "userClub" },
            {"3", "18/01/2022", "Foot2rue", "Football", "TAG" },
            {"4", "05/03/2012", "Les rois du gazon", "Rugby", "userClub" },
            {"5", "05/03/2011", "Panpan !", "tir à l'arc", "le club de jo" }
        };
        String[][] rightData = {
            {"2", "20/12/2023", "Golf amateur", "Golf", "userClub"},
            {"4", "05/03/2012", "Les rois du gazon", "Rugby", "userClub"}
        };

        ResultSet eventRS = DatabaseManager.getInstance().getAllEvents();
 
        JTable leftTable = new JTable();
        JTable rightTable = new JTable();

        leftTable.setFont(new Font("Arial",Font.PLAIN,12));
        rightTable.setFont(new Font("Arial",Font.PLAIN,12));

        
        DefaultTableModel leftTableModel = new DefaultTableModel(leftData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };

        DefaultTableModel rightTableModel = new DefaultTableModel(rightData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };

        leftTable.setModel(leftTableModel);
        rightTable.setModel(rightTableModel);

        leftTable.getColumnModel().getColumn(0).setMaxWidth(25);
        leftTable.getColumnModel().getColumn(1).setMaxWidth(80);
        leftTable.getColumnModel().getColumn(3).setMaxWidth(80);
        leftTable.getColumnModel().getColumn(4).setMaxWidth(80);

        rightTable.getColumnModel().getColumn(0).setMaxWidth(25);
        rightTable.getColumnModel().getColumn(1).setMaxWidth(80);
        rightTable.getColumnModel().getColumn(3).setMaxWidth(80);
        rightTable.getColumnModel().getColumn(4).setMaxWidth(80);


        leftTable.setCellSelectionEnabled(true);
        rightTable.setCellSelectionEnabled(true);

        leftTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rightTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        leftTable.setRowSelectionAllowed(true);
        leftTable.setColumnSelectionAllowed(false);
        rightTable.setRowSelectionAllowed(true);
        rightTable.setColumnSelectionAllowed(false);

        leftTable.setAutoCreateRowSorter(true);
        rightTable.setAutoCreateRowSorter(true);
        leftTable.setRowHeight(25);
        rightTable.setRowHeight(25);

        leftTable.setBackground(new Color(0, 0, 0, 0));
        rightTable.setBackground(new Color(0, 0, 0, 0));

        Object[] leftTableColumnData = new Object[leftTable.getColumnCount()];
        Object[] leftTableRowData = new Object [leftTable.getRowCount()];
        Object[] rightTableColumnData = new Object[rightTable.getColumnCount()];
        Object[] rightTableRowData = new Object [rightTable.getRowCount()];
        
        rightTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                String idSearch = "";
                String selectedId ="";
                selectedId = (String)rightTable.getValueAt(rightTable.getSelectedRow(), 0);
                for(int i = 0; i< leftTable.getRowCount(); i++){  
                    idSearch = (String)leftTable.getValueAt(i, 0);
                    if(idSearch.equals(selectedId)){
                        leftTable.changeSelection(i, 0, false, false);
                        break;
                    }
                }
            }
        });

        leftTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                String idSearch = "";
                String selectedId ="";
                selectedId = (String)leftTable.getValueAt(leftTable.getSelectedRow(), 0);
                for(int i = 0; i< rightTable.getRowCount(); i++){  
                    idSearch = (String)rightTable.getValueAt(i, 0);
                    if(idSearch.equals(selectedId)){
                        rightTable.changeSelection(i, 0, false, false);
                        break;
                    }
                }
            }
        });

        leftTable.getTableHeader().setEnabled(false);
        rightTable.getTableHeader().setEnabled(false);
        leftTable.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        rightTable.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        
        leftTable.getRowSorter().toggleSortOrder(1);
        leftTable.getRowSorter().toggleSortOrder(1);
        rightTable.getRowSorter().toggleSortOrder(1);
        rightTable.getRowSorter().toggleSortOrder(1);
        leftTable.setOpaque(false);
        rightTable.setOpaque(false);
        
        JScrollPane leftTableScroll = new JScrollPane(leftTable);
        JScrollPane rightTableScroll = new JScrollPane(rightTable);

        leftTableScroll.setColumnHeader(new JViewport());
        leftTableScroll.getColumnHeader().setOpaque(false);
        rightTableScroll.setColumnHeader(new JViewport());
        rightTableScroll.getColumnHeader().setOpaque(false);

        leftTableScroll.getViewport().setOpaque(false);
        rightTableScroll.getViewport().setOpaque(false);
        leftTableScroll.setOpaque(false);
        rightTableScroll.setOpaque(false);

        newEventButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                openNewEventWindow();
            }
        }
        );

        JTable innerTable = new JTable();

        String[] innerColumnNames = {"Nom", "Club"};
        String[][] innerData = {
            {"Jean pomme", "Les chads"},
            {"Jerry", "Les joyeuses raquettes"},
            {"Tom", "Equipe de fou"}
        };

        DefaultTableModel innerTableModel = new DefaultTableModel(innerData, innerColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };

        //hop:

        JScrollPane innerTableScroll = new JScrollPane(innerTable);

        JLabel displayOuterLeftLabel = new JLabel("Events programmés");
        JLabel displayOuterRightLabel = new JLabel("Vos events programmés");
        
        JLabel displayInnerLeftLabel = new JLabel("nomEvent le dateDebut");
        JLabel displayInnerRightLabel = new JLabel("Participants");

        displayOuterLeftLabel.setFont(new Font("Arial",Font.BOLD,22));
        displayOuterRightLabel.setFont(new Font("Arial",Font.BOLD,22));
        displayInnerLeftLabel.setFont(new Font("Arial",Font.BOLD,22));
        displayInnerRightLabel.setFont(new Font("Arial",Font.BOLD,22));
        
        selectBand.setLayout(new GridLayout(1,2));

        selectBand.add(displayInnerLeftLabel);
        selectBand.add(displayInnerRightLabel);

        
        JTextPane displayArea = new JTextPane();
        
        displayPanel.setLayout(new GridLayout(1,2));

        displayPanel.add(displayArea);
        displayPanel.add(innerTableScroll);

        header.setLayout(new GridBagLayout());
        listTitles.setLayout(new GridBagLayout());
        body.setLayout(new GridLayout(1,2));

        addComponent(header, displayBox, 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
        addComponent(header, refreshButton, 3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);

        addComponent(listTitles, displayOuterLeftLabel, 0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
        addComponent(listTitles, displayOuterRightLabel, 1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);

        body.add(leftTableScroll);
        body.add(rightTableScroll);
    }

    private void drawMyClub(){
        this.myClubPanel.setLayout(new GridBagLayout());
        JPanel header = new JPanel();
        JPanel listTitles = new JPanel();
        JPanel body = new JPanel();
        this.displayPanel = new JPanel();
        JPanel listButtons = new JPanel();
        JPanel footer = new JPanel();
        JPanel selectBand = new JPanel();

        header.setOpaque(false);
        listTitles.setOpaque(false);
        body.setOpaque(false);
        displayPanel.setOpaque(false);
        listButtons.setOpaque(false);
        footer.setOpaque(false);
        selectBand.setOpaque(false);
        
        addComponent(this.myClubPanel, header, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(this.myClubPanel, listTitles, 0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 10);
        addComponent(this.myClubPanel, body, 0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(this.myClubPanel, listButtons, 0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 10);
        addComponent(this.myClubPanel, selectBand, 0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        addComponent(this.myClubPanel, displayPanel, 0, 5, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1,1,1,1), 0, 0);
        addComponent(this.myClubPanel, footer, 0, 6, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 20);
        
        drawMyClubContent(header, listTitles, body, listButtons, selectBand, displayPanel, footer);
    }

    private void drawMyClubContent(JPanel header, JPanel listTitles, JPanel body, JPanel listButtons, JPanel selectBand, JPanel displayPanel, JPanel footer){

        //lists init
        this.tlModel = new DefaultListModel<Team>();
        this.plModel = new DefaultListModel<Player>();
        this.elModel = new DefaultListModel<Event>();
        this.tl = new JList<Team>(tlModel);
        this.pl = new JList<Player>(plModel);
        this.el = new JList<Event>(elModel);

        this.tl.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                if(value instanceof Team){
                    Team t = (Team) value;
                    setText(t.getNom());
                    if(!t.isAvailable()){
                        setForeground(Color.red);
                    }
                }
                return this;
            }
        });
        this.pl.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                if(value instanceof Player){
                    Player p = (Player) value;
                    setText(p.getNom());
                }
                return this;
            }
        });
        this.el.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                
                return this;
            }
        });
        
        this.tl.setOpaque(false);
        this.tl.setBackground(new Color(0, 0, 0, 0));
        this.pl.setOpaque(false);
        this.pl.setBackground(new Color(0, 0, 0, 0));
        this.tl.setFont(new Font("Arial",Font.BOLD,18));
        this.pl.setFont(new Font("Arial",Font.BOLD,18));

        this.addButton = new Button("Nouveau joueur");
        this.modifyButton = new Button("Modifier");
        this.deleteButton = new Button("Supprimer");

        updateLists();

        header.setLayout(new GridBagLayout());
        listTitles.setLayout(new GridLayout(0, 2));
        body.setLayout(new GridLayout(0, 2));
        listButtons.setLayout(new GridLayout(0, 2));
        selectBand.setLayout(new GridLayout(0, 2));
        footer.setLayout(new GridBagLayout());

        this.myTeams = new JLabel("My Teams (" + DatabaseManager.getInstance().countClubTeams(thisClub.getID()) + ")", SwingConstants.CENTER); 
        this.myPlayers = new JLabel("My Players (" + DatabaseManager.getInstance().countClubPlayers(thisClub.getID()) + ")", SwingConstants.CENTER);
        this.myTeams.setFont(new Font("Arial",Font.BOLD,22));
        this.myPlayers.setFont(new Font("Arial",Font.BOLD,22));
        listTitles.add(this.myTeams);
        listTitles.add(this.myPlayers);
        JScrollPane tlScroll = new JScrollPane(this.tl);
        JScrollPane plScroll = new JScrollPane(this.pl);;
        tlScroll.getViewport().setOpaque(false);
        plScroll.getViewport().setOpaque(false);
        tlScroll.setOpaque(false);
        plScroll.setOpaque(false);
        tlScroll.setBackground(new Color(0,0,0,0));
        plScroll.setBackground(new Color(0,0,0,0));
        body.add(tlScroll);
        body.add(plScroll);

        listButtons.setLayout(new GridLayout(0,3));
        listButtons.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.black));
        listButtons.add(this.addButton);
        listButtons.add(this.modifyButton);
        listButtons.add(this.deleteButton);
        addButton.setFont(new Font("Arial",Font.BOLD,12));
        modifyButton.setFont(new Font("Arial",Font.BOLD,12));
        deleteButton.setFont(new Font("Arial",Font.BOLD,12));
        Button newClub = new Button("Créer un nouveau club");
        Button modClub = new Button("Modifier ce club");
        Button deleteClub = new Button("Supprimer ce club");

        newClub.addNewClubWindowListener();

        addComponent(footer, newClub, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 150, 50);
        addComponent(footer, modClub, 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 150, 50);
        addComponent(footer, deleteClub, 2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 150, 50);
        
        this.ppLabel = new JLabel();
        JPanel midPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        midPanel.setOpaque(false);
        rightPanel.setOpaque(false);
        midPanel.setLayout(new GridBagLayout());
        rightPanel.setLayout(new GridBagLayout());
        JTextArea midText = new JTextArea();
        JTextArea rightText = new JTextArea();
        
        //comboboxclubs:

        updateClubBox();
        
        clubBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Club){
                    Club c = (Club) value;
                    setText(c.getNom());
                }
                return this;
            }
        });

        clubBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                if(clubBox.getSelectedItem()!=null){
                    tl.setSelectedIndex(0);
                    pl.setSelectedIndex(0);
                    thisClub = (Club) clubBox.getSelectedItem();
                    paintProfile(); 
                    clearLowerDisplay();
                    updateCounts();
                    updateLists();
                    updateSecondaryLists(); 
                }
            }
        });

        this.nomArea = new JTextArea(0,20);
        this.ownerArea = new JTextArea(0,20);
        this.desArea = new JTextArea(0,27);
        this.telArea = new JTextArea();
        this.mailArea = new JTextArea(0,24);
        this.sportArea = new JTextArea(0,24);
        this.adressArea = new JTextArea(0,24);
        midText.setPreferredSize(new Dimension(85,80));
        rightText.setPreferredSize(new Dimension(64,150));
        desArea.setPreferredSize(new Dimension(120,100));
        midText.setText("Nom du club : \n\nPropriétaire : \n\nDescription :");
        rightText.setText("Tel : \n\nMail : \n\nAdresse : \n\nSport : \n\nWinrate : ");

        this.winrateClubPane = new JTextPane();
        this.winrateClubDoc = winrateClubPane.getStyledDocument();

        midText.setEditable(false);
        rightText.setEditable(false);
        this.nomArea.setEditable(false);
        this.ownerArea.setEditable(false);
        this.desArea.setEditable(false);
        this.telArea.setEditable(false);
        this.mailArea.setEditable(false);
        this.adressArea.setEditable(false);
        this.sportArea.setEditable(false);
        this.winrateClubPane.setEditable(false);
        midText.setOpaque(false);
        rightText.setOpaque(false);
        this.winrateClubPane.setOpaque(false);
        this.nomArea.setOpaque(false);
        this.ownerArea.setOpaque(false);
        this.desArea.setOpaque(false);
        this.telArea.setOpaque(false);
        this.mailArea.setOpaque(false);
        this.adressArea.setOpaque(false);
        this.sportArea.setOpaque(false);
        this.nomArea.setLineWrap(true);
        this.desArea.setLineWrap(true);
        this.telArea.setLineWrap(true);
        this.mailArea.setLineWrap(true);
        this.adressArea.setLineWrap(true);
        this.nomArea.setWrapStyleWord(true);
        this.desArea.setWrapStyleWord(true);
        this.telArea.setWrapStyleWord(true);
        this.mailArea.setWrapStyleWord(true);
        this.adressArea.setWrapStyleWord(true);
        
        midText.setFont(new Font("Arial",Font.BOLD,13));
        rightText.setFont(new Font("Arial",Font.BOLD,13));
        nomArea.setFont(new Font("Arial",Font.PLAIN,13));
        desArea.setFont(new Font("Arial",Font.PLAIN,13));
        telArea.setFont(new Font("Arial",Font.PLAIN,13));
        mailArea.setFont(new Font("Arial",Font.PLAIN,13));
        
        addComponent(header, this.ppLabel, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0);
        addComponent(header, midPanel, 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(1, 0, 1, 1), 0, 0);
        addComponent(header, rightPanel, 2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(1, 0, 1, 1), 0, 0);

        addComponent(midPanel, midText, 0, 0, 1, 8, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        addComponent(midPanel, this.nomArea, 1, 0, 0, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        addComponent(midPanel, this.ownerArea, 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(16, 0, 0, 0), 0, 0);
        addComponent(midPanel, this.desArea, 1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(16, 0, 0, 0), 0, 0);
        addComponent(midPanel, clubBox, 1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);

        addComponent(rightPanel, rightText, 0, 0, 1, 8, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        addComponent(rightPanel, this.telArea, 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
        addComponent(rightPanel, this.mailArea, 1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(16, 0, 0, 0), 0, 0);
        addComponent(rightPanel, this.adressArea, 1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(16, 0, 0, 0), 0, 0);
        addComponent(rightPanel, this.sportArea, 1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(15, 0, 0, 0), 0, 0);
        addComponent(rightPanel, this.winrateClubPane, 1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(13, 0, 0, 0), 0, 0);

        paintProfile();

        JPanel infoTitle = new JPanel();
        JPanel secondaryListsTitles = new JPanel();
        infoTitle.setOpaque(false);
        secondaryListsTitles.setOpaque(false);
        JTextPane infoTitlePane = new JTextPane();
        JTextPane secondaryListOneTitle = new JTextPane();
        JTextPane secondaryListTwoTitle = new JTextPane();
        
        this.infoTitleDoc = infoTitlePane.getStyledDocument();
        this.secondaryListOneTitleDoc = secondaryListOneTitle.getStyledDocument();
        this.secondaryListTwoTitleDoc = secondaryListTwoTitle.getStyledDocument();
        infoTitle.setLayout(new GridLayout(1,0));
        infoTitlePane.setFont(new Font("Arial",Font.BOLD,22));
        secondaryListOneTitle.setFont(new Font("Arial",Font.BOLD,22));
        secondaryListTwoTitle.setFont(new Font("Arial",Font.BOLD,22));
        infoTitlePane.setOpaque(false);
        infoTitlePane.setEditable(false);
        secondaryListOneTitle.setOpaque(false);
        secondaryListTwoTitle.setOpaque(false);
        secondaryListOneTitle.setEditable(false);
        secondaryListTwoTitle.setEditable(false);
        infoTitle.add(infoTitlePane);
        secondaryListsTitles.setLayout(new GridLayout(0,2));
        secondaryListsTitles.add(secondaryListOneTitle);
        secondaryListsTitles.add(secondaryListTwoTitle);
        selectBand.add(infoTitle);
        selectBand.add(secondaryListsTitles);
        this.displayNestedPanel = new JPanel();
        this.secondaryListsPanel = new JPanel();
        displayNestedPanel.setOpaque(false);
        secondaryListsPanel.setOpaque(false);
        displayNestedPanel.setLayout(new GridBagLayout());
        this.secondaryListOne = new JTextPane();
        this.secondaryListTwo = new JTextPane();
        this.secondaryListOne.setOpaque(false);
        this.secondaryListTwo.setOpaque(false);
        secondaryListOne.setEditable(false);
        secondaryListTwo.setEditable(false);
        this.secListOneScroll = new JScrollPane(secondaryListOne);
        this.secListTwoScroll = new JScrollPane(secondaryListTwo);
        this.secListOneScroll.getViewport().setOpaque(false);
        this.secListTwoScroll.getViewport().setOpaque(false);
        this.secListOneScroll.setOpaque(false);
        this.secListTwoScroll.setOpaque(false);
        secListOneScroll.setBackground(new Color(0,0,0,0));
        secListTwoScroll.setBackground(new Color(0,0,0,0));
        secondaryListsPanel.add(secListOneScroll);
        secondaryListsPanel.add(secListTwoScroll);
        this.secondaryListOneDoc = secondaryListOne.getStyledDocument();
        this.secondaryListTwoDoc = secondaryListTwo.getStyledDocument();
        JTextPane displayAreaLeft = new JTextPane();
        JTextPane displayAreaRight = new JTextPane();
        displayAreaLeft.setEditable(false);
        displayAreaLeft.setOpaque(false);
        displayAreaRight.setEditable(false);
        displayAreaRight.setOpaque(false);
        displayPanel.setLayout(new GridLayout(1,2));
        secondaryListsPanel.setLayout(new GridLayout(1,2));
        
        displayPanel.add(displayNestedPanel);
        displayPanel.add(secondaryListsPanel);
        displayPanel.setPreferredSize(new Dimension(600, 55));
        displayAreaLeft.setPreferredSize(new Dimension(200,55));
        displayAreaRight.setPreferredSize(new Dimension(200,55));
        
        addComponent(displayNestedPanel, displayAreaLeft, 0, 0, 1, 0, 1.0, 0.1, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0,0);
        addComponent(displayNestedPanel, displayAreaRight, 2, 0, 1, 0, 1.5, 0.1, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0,0,0,0), 0, 0);

        this.displayDocumentLeft = displayAreaLeft.getStyledDocument();
        this.displayDocumentRight = displayAreaRight.getStyledDocument();
        
        tl.addListSelectionListener(new ListSelectionListener() {
            //affichage team 
            @Override
            public void valueChanged(ListSelectionEvent arg0) 
            {
                if(!tl.isSelectionEmpty())
                {
                    Team thisTeam = tl.getSelectedValue();
                    SimpleAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setBold(attr, true);
                    content.repaint();

                    clearLowerDisplay();
                    updateDisplayArea();
                    updateSecondaryLists();
                    try {
                        infoTitleDoc.insertString(0, thisTeam.getNom(), attr);
                        secondaryListOneTitleDoc.insertString(0, "Joueurs de l'équipe", attr);
                        secondaryListTwoTitleDoc.insertString(0, "Historique de l'équipe", attr);
                        secondaryListOne.setCaretPosition(0);
                    } catch (BadLocationException e) {
                    }
                    if(plModel.getSize()==0 || tlModel.getSize()==0){
                        addButton.setText("Créer une équipe / un joueur");
                    }
                    else{
                        addButton.setText("Créer une équipe");
                    }
                    modifyButton.setText("Modifier " + thisTeam.getNom());
                    deleteButton.setText("Supprimer " + thisTeam.getNom());
                    pl.clearSelection();
                }
                content.repaint();
                updateDisplayArea();
                updateSecondaryLists();
            }
        });

        pl.addListSelectionListener(new ListSelectionListener() {
            //playerdraw:
            @Override
            public void valueChanged(ListSelectionEvent arg0) {
                if(!pl.isSelectionEmpty()){
                    Player thisPlayer = pl.getSelectedValue();
                    SimpleAttributeSet attr = new SimpleAttributeSet();
                    StyleConstants.setBold(attr, true);
                    content.repaint();  
                    clearLowerDisplay();
                    updateDisplayArea();
                    updateSecondaryLists();
                    try {

                        infoTitleDoc.insertString(0, thisPlayer.getNom(), attr);
                        secondaryListOneTitleDoc.insertString(0, "Equipes du joueur", attr);
                        secondaryListTwoTitleDoc.insertString(0, "Historique du joueur", attr);
                        secondaryListTwo.setCaretPosition(0);
                    } catch (BadLocationException e) {
                    }
                    if(plModel.getSize()==0 || tlModel.getSize()==0){
                        addButton.setText("Créer une équipe / un joueur");
                    }
                    else{
                        addButton.setText("Créer un joueur");
                    }
                    modifyButton.setText("Modifier " + thisPlayer.getNom());
                    deleteButton.setText("Supprimer " + thisPlayer.getNom());
                    tl.clearSelection();
                }
                content.repaint();
                updateDisplayArea();
                updateSecondaryLists();
            }
        });

        ResultSet clubPlayersRS = DatabaseManager.getInstance().getClubPlayers(thisClub.getID());
        try {
            if(clubPlayersRS.next()){
                pl.setSelectedIndex(0);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!tl.isSelectionEmpty() || !pl.isSelectionEmpty())
                {
                    ChildWindow confirm = new ChildWindow();
                    confirm.getContentPane().setLayout(new GridLayout(2,0));
                    JTextPane confirmText = new JTextPane();
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new GridLayout(0,2));
                    Button yes = new Button("Oui");
                    Button no = new Button("Non");
                    
                    Document confirmTextDoc = confirmText.getStyledDocument();
                    try {
                        if(!tl.isSelectionEmpty() && pl.isSelectionEmpty()){
                            confirm.setSize(450,90);
                            confirmTextDoc.insertString(0, "Voulez vous vraiment supprimer l'équipe \"" + tlModel.get(tl.getSelectedIndex()).getNom() + "\" ?", null);
                        }
                        if(tl.isSelectionEmpty() && !pl.isSelectionEmpty()){
                            confirm.setSize(380,90);
                            confirmTextDoc.insertString(0, "Voulez vous vraiment supprimer le joueur " + plModel.get(pl.getSelectedIndex()).getNom() + " ?", null);
                        }
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    yes.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            if(!pl.isSelectionEmpty() && tl.isSelectionEmpty()){
                                DatabaseManager.getInstance().deletePlayer(pl.getSelectedValue().getID());
                                if(plModel.getSize()==0){
                                    pl.clearSelection(); 
                                }
                                else{
                                    pl.setSelectedIndex(0);
                                }
                            }
                            if(pl.isSelectionEmpty() && !tl.isSelectionEmpty()){
                                DatabaseManager.getInstance().deleteTeam(tl.getSelectedValue().getID());
                                if(tlModel.getSize()==0){
                                    tl.clearSelection();
                                }
                                else{
                                    tl.setSelectedIndex(0);
                                }
                            }
                            clearLowerDisplay();
                            updateLists();
                            updateCounts();
                            confirm.dispose();
                        }
                    }
                    );
                    no.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            confirm.dispose();
                        }
                    }
                    );
                    confirmText.setEditable(false);
                    confirmText.setOpaque(false);
                    buttonPanel.add(yes);
                    buttonPanel.add(no);
                    confirm.add(confirmText);
                    confirm.add(buttonPanel);
                    confirm.setVisible(true);
                }

            }
        }
        ); 
        
        modClub.addModClubWindowListener();

        deleteClub.addActionListener(new ActionListener()
        {//deleteclub:
            @Override
            public void actionPerformed(ActionEvent event) 
            {
                if(!(DatabaseManager.getInstance().countUserClubs(thisUserTag)<2))
                {
                    Club thisClubCurrent = (Club)clubBox.getSelectedItem();
                    ChildWindow confirm = new ChildWindow(450,90);
                    confirm.getContentPane().setLayout(new GridLayout(2,0));
                    JTextPane confirmText = new JTextPane();
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new GridLayout(0,2));
                    Button yes = new Button("Oui");
                    Button no = new Button("Non");
                    Document confirmTextDoc = confirmText.getStyledDocument();
                    try {
                        confirmTextDoc.insertString(0, "Voulez vous vraiment supprimer le club \"" + thisClubCurrent.getNom() + "\" ?", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    yes.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            DatabaseManager.getInstance().deleteClub(thisClubCurrent.getID());
                            updateClubBox();
                            clearLowerDisplay();
                            updateLists();
                            updateCounts();
                            confirm.dispose();  
                        }
                    });
                    no.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            confirm.dispose();
                        }
                    });
                    confirmText.setEditable(false);
                    confirmText.setOpaque(false);
                    buttonPanel.add(yes);
                    buttonPanel.add(no);
                    confirm.add(confirmText);
                    confirm.add(buttonPanel);
                    confirm.setVisible(true);
                }
                else
                {
                    ChildWindow denied = new ChildWindow(450,90);
                    JTextPane deniedText = new JTextPane();
                    Document deniedTextDoc = deniedText.getStyledDocument();
                    try {
                        deniedTextDoc.insertString(0, "Vous n'avez qu'un seul club, vous ne pouvez pas le supprimer.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    } 
                    deniedText.setEditable(false);
                    deniedText.setOpaque(false);
                    denied.add(deniedText);
                    denied.setVisible(true);
                }  
            }
        }); 

        //modifyButton
        modifyButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(tl.getSelectedIndex()!=-1 || pl.getSelectedIndex()!=-1){
                    if(!tl.isSelectionEmpty() && pl.isSelectionEmpty()){
                        openModTeamWindow();
                    }
                    else if(tl.isSelectionEmpty() && !pl.isSelectionEmpty()){
                        openModPlayerWindow();
                    }
                    else if(tl.isSelectionEmpty() && pl.isSelectionEmpty()){
                        openModPlayerWindow();
                    }
                }
            }
        }
        );

        addButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(tlModel.getSize()==0 || plModel.getSize()==0){
                    ChildWindow creation = new ChildWindow(500, 100);
                    Button createTeam = new Button("Créer une équipe");
                    Button createPlayer = new Button("Créer un joueur");
                    JPanel choicePanel = new JPanel();
                    creation.getContentPane().setLayout(new GridLayout(2,0));
                    choicePanel.setLayout(new GridLayout(0,2));
                    choicePanel.add(createTeam);
                    choicePanel.add(createPlayer);
                    JTextPane creationText = new JTextPane();
                    Document creationTextDoc = creationText.getStyledDocument();
                    try {
                        creationTextDoc.insertString(0, "Que souhaitez vous créer ?", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    createTeam.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            creation.dispose();
                            openNewTeamWindow();
                        }
                    }
                    );
                    createPlayer.addActionListener(new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            creation.dispose();
                            openNewPlayerWindow();
                        }
                    }
                    );
                    creationText.setEditable(false);
                    creationText.setOpaque(false);
                    creation.add(creationText);
                    creation.add(choicePanel);
                    creation.setVisible(true);
                }
                else if(tl.getSelectedIndex()!= -1 || pl.getSelectedIndex()==-1){
                    openNewTeamWindow();
                }
                else if(tl.getSelectedIndex()== -1 || pl.getSelectedIndex()!=-1){
                    openNewPlayerWindow();
                }
            }
        }
        );
    }

    private void updateClubBox() {
        this.clubBox.removeAllItems();
        ResultSet rs = DatabaseManager.getInstance().getUserClubs(thisUserTag);
        try {
            while(rs.next()){
                this.clubBox.addItem(new Club(rs.getInt("id"), rs.getString("nom"), rs.getString("adresse"), rs.getString("tel"), rs.getString("mail"), rs.getString("description"), rs.getInt("sportID"), rs.getInt("userTag")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void drawSettings()
    {
        //drawsettings:
       
        JPanel lowerPanel = new JPanel();
        JPanel upperPanel = new JPanel();
        JPanel loggedAsPanel = new JPanel();
        JPanel clunkeePanel = new JPanel();

        JLabel clunkeeLabel = new JLabel(this.clunkee);
        this.loggedAs = new JTextArea("Vous êtes connectés en tant que : " + thisUser.getNom());
        this.loggedAs.setPreferredSize(new Dimension(280,100));
        this.loggedAs.setFont(new Font("Arial",Font.PLAIN,18));
        this.loggedAs.setLineWrap(true);
        this.loggedAs.setWrapStyleWord(true);
        this.loggedAs.setEditable(false);
        this.loggedAs.setOpaque(false);

        lowerPanel.setOpaque(false);
        upperPanel.setOpaque(false);
        loggedAsPanel.setOpaque(false);
        clunkeePanel.setOpaque(false);

        Button logOff = new Button("Log Off");
        Button changePw = new Button("Change Password");
        Button changeName = new Button("Change Username");
        Button deleteAccount = new Button("Delete Account");

        settingsPanel.setLayout(new GridLayout(2,0));
        lowerPanel.setLayout(new GridBagLayout());
        upperPanel.setLayout(new GridLayout(0,2));
        loggedAsPanel.setLayout(new GridBagLayout());

        settingsPanel.add(upperPanel);
        settingsPanel.add(lowerPanel);

        clunkeePanel.add(clunkeeLabel);
        addComponent(loggedAsPanel, this.loggedAs, 0, 0, 1, 1, 1.0, 0.1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0);
        upperPanel.add(loggedAsPanel);
        upperPanel.add(clunkeePanel);
        
        addComponent(lowerPanel, logOff, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,10), 50, 20);
        addComponent(lowerPanel, changePw, 1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,10,0,10), 50, 20);
        addComponent(lowerPanel, changeName, 2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,10,0,10), 50, 20);
        addComponent(lowerPanel, deleteAccount, 3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,10,0,0), 50, 20);

        logOff.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                logOff();
            }
        }
        );

        changePw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                openChangePwWindow();
            }
        }
        );

        changeName.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                openChangeNameWindow();
            }
        }
        );

        deleteAccount.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                deleteAccount();
            }
        }
        );
    }

    //UTILS

    protected void deleteAccount() {
        ChildWindow confirm = new ChildWindow();
        confirm.getContentPane().setLayout(new GridLayout(2,0));
        JTextPane confirmText = new JTextPane();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,2));
        Button yes = new Button("Oui");
        Button no = new Button("Non");
        
        Document confirmTextDoc = confirmText.getStyledDocument();
        try {
            confirm.setSize(450,90);
            confirmTextDoc.insertString(0, "Voulez vous vraiment supprimer l'utilisateur \"" + thisUser.getNom() + "\" ?", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        yes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                DatabaseManager.getInstance().deleteAccount(thisUserTag);
                confirm.dispose();
                setLoginScreenVisible();
            }
        }
        );
        no.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                confirm.dispose();
            }
        }
        );
        confirmText.setEditable(false);
        confirmText.setOpaque(false);
        buttonPanel.add(yes);
        buttonPanel.add(no);
        confirm.add(confirmText);
        confirm.add(buttonPanel);
        confirm.setVisible(true);
    }

    protected void createAccount() {
        //createaccount:

        ChildWindow newAccountWindow = new ChildWindow(600,480);
        newAccountWindow.setTitle("Créer un nouveau compte");
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        newAccountWindow.add(content);
        content.setLayout(new GridLayout(5,1));
        newAccountWindow.setLayout(new GridLayout(1,1));

        JPanel nomPanel = new JPanel();
        JPanel idPanel = new JPanel();
        JPanel pwPanel = new JPanel();
        JPanel pw2Panel = new JPanel();
        JPanel confirmPanel = new JPanel();
    
        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        idPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        pwPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        pw2Panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());

        JLabel nomLabel = new JLabel("Nom : ");
        JTextArea nomSaisie = new JTextArea(0,22);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel idLabel = new JLabel("Identifiant : ");
        JTextArea idSaisie = new JTextArea(0,22);
        JLabel idWC = new JLabel();
        JLabel idDispo = new JLabel();
        updateWC(idSaisie, idWC, 27);
        
        JLabel pwLabel = new JLabel("Mot de passe : ");
        JPasswordField pwSaisie = new JPasswordField();
        pwSaisie.setColumns(18);
        JLabel pw2Label = new JLabel("Confirmation : ");
        JPasswordField pw2Saisie = new JPasswordField();
        pw2Saisie.setColumns(18);
        TextPrompt nomTp = new TextPrompt("Le nom visible par les autres utilisateurs", nomSaisie);
        TextPrompt idTp = new TextPrompt("L'identifiant vous servira à vous connecter", idSaisie);
        TextPrompt pwTp = new TextPrompt("Votre mot de passe", pwSaisie);
        TextPrompt pw2Tp = new TextPrompt("Confirmez votre mot de passe", pw2Saisie);

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUserNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        idSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUserIdAvailable(idSaisie, idDispo);
            }
        }));

        idSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(idSaisie, idWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(idSaisie, idWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(idSaisie, idWC, 27);
            }
        });

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            //newusersubmit:
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean nameOK = false;
                boolean idOK = false;
                boolean pwOK = false;

                if(!nomSaisie.getText().isBlank())
                {
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre nom de club doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(DatabaseManager.getInstance().isUserNameAvailable(nomSaisie.getText())){
                            nameOK = true;
                        }
                        else{
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Ce nom est déjà utilisé.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                    }
                }

                if(!idSaisie.getText().isBlank())
                {
                    if(idSaisie.getText().length()<2 || idSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre id doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(DatabaseManager.getInstance().isUserIDAvailable(nomSaisie.getText())){
                            idOK = true;
                        }
                        else{
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Cet identifiant est déjà utilisé.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                    }
                }

                if(pwSaisie.getPassword().length!=0)
                {
                    String password = "";
                    for(char c:pwSaisie.getPassword()){
                        password+=c;
                    }
                    String passwordConfirm = "";
                    for(char c:pw2Saisie.getPassword()){
                        passwordConfirm+=c;
                    }
                    if(password.equals(passwordConfirm))
                    {
                        if(pwSaisie.getPassword().length<4){
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Votre mot de passe doit comporter au moins 5 caractères !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            pwOK = true;
                        }
                    }
                    else{
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Les deux mots de passes ne sont pas identiques.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                }
                
                if(nameOK && idOK && pwOK){
                    String password = "";
                    for(char c:pwSaisie.getPassword()){
                        password+=c;
                    }
                    DatabaseManager.getInstance().createUser(nomSaisie.getText(), idSaisie.getText(), password);

                    ChildWindow confirmed = new ChildWindow(400, 80);
                    JTextPane confirmedText = new JTextPane();
                    Document confirmedTextDoc = confirmedText.getStyledDocument();
                    try {
                        confirmedTextDoc.insertString(0, "L'utilisateur " + nomSaisie.getText() + " a bien été créé.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    confirmedText.setEditable(false);
                    confirmedText.setOpaque(false);
                    confirmed.add(confirmedText);
                    confirmed.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        idSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pwSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pw2Saisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        idSaisie.setLineWrap(true);
        idSaisie.setWrapStyleWord(true);
        
        nomPanel.setOpaque(false);
        idPanel.setOpaque(false);
        pwPanel.setOpaque(false);
        pw2Panel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        idPanel.add(idLabel);
        idPanel.add(idSaisie);
        idPanel.add(idWC);
        idPanel.add(idDispo);
        pwPanel.add(pwLabel);
        pwPanel.add(pwSaisie);
        pw2Panel.add(pw2Label);
        pw2Panel.add(pw2Saisie);
        content.add(nomPanel); 
        content.add(idPanel);
        content.add(pwPanel);
        content.add(pw2Panel);
        content.add(confirmPanel);

        newAccountWindow.setVisible(true);
    }

    private void isUserNameAvailable(JTextArea saisie, JLabel dispo) {
        
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isUserNameAvailable(saisie.getText())){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    private void isUserIdAvailable(JTextArea saisie, JLabel dispo) {
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isUserIDAvailable(saisie.getText())){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    private void isClubNameAvailable(JTextArea saisie, JLabel dispo) {
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isClubNameAvailable(saisie.getText()) && saisie.getText() != this.thisClub.getNom()){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    private void isTeamNameAvailable(JTextArea saisie, JLabel dispo) {
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isTeamNameAvailable(saisie.getText()) && saisie.getText() != tl.getSelectedValue().getNom()){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    private void isClubMailAvailable(JTextArea saisie, JLabel dispo) {
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isClubMailAvailable(saisie.getText()) && saisie.getText() != this.thisClub.getMail()){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    private void isPlayerMailAvailable(JTextArea saisie, JLabel dispo) {
        if(saisie.getText().isBlank()){
            dispo.setText(null);
        }
        boolean isDispo = true;
        if(!DatabaseManager.getInstance().isPlayerMailAvailable(saisie.getText()) && saisie.getText() != pl.getSelectedValue().getMail()){
            isDispo = false;
        }
            
        if(isDispo){
            dispo.setText("disponible");
        }
        else{
            dispo.setText("indisponible");
        }
    }

    protected void openChangeNameWindow() {
        //modUsername:
        JPanel newNamePanel = new JPanel();
        JPanel pwPanel = new JPanel();
        JPanel confirmPanel = new JPanel();
        ChildWindow modUsernameWindow = new ChildWindow(600,480);
        modUsernameWindow.setTitle(thisUser.getNom() + " : Modifier le nom d'utilisateur");
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        modUsernameWindow.add(content);
        content.setLayout(new GridLayout(3,0));
        modUsernameWindow.setLayout(new GridLayout(1,1));

        content.add(newNamePanel);
        content.add(pwPanel);
        content.add(confirmPanel);

        pwPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        newNamePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());
        pwPanel.setOpaque(false);
        newNamePanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        JLabel newNameLabel = new JLabel("Nouveau nom d'utilisateur : ");
        JTextArea newNameSaisie = new JTextArea(0,20);
        newNameSaisie.setText(thisUser.getNom());
        JLabel newNameWC = new JLabel();
        updateWC(newNameSaisie, newNameWC, 27);
        JLabel pwLabel = new JLabel("Mot de passe : ");
        JPasswordField pwSaisie = new JPasswordField();
        TextPrompt tpPw = new TextPrompt("Votre mot de passe", pwSaisie);

        newNameSaisie.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateWC(newNameSaisie, newNameWC, 27);
                }
        
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateWC(newNameSaisie, newNameWC, 27);
                }
        
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    updateWC(newNameSaisie, newNameWC, 27);
                }
            });

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                String password = "";
                for(char c:pwSaisie.getPassword()){
                    password+=c;
                }
                if(DatabaseManager.getInstance().checkPw(thisUser.getTag(), password))
                {
                    if(!newNameSaisie.getText().equals(thisUser.getNom()) && !newNameSaisie.getText().isBlank())
                    {
                        if(newNameSaisie.getText().length()<3 || newNameSaisie.getText().length()>27)
                        {
                            ChildWindow denied = new ChildWindow(280, 50);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Votre nom d'utilisateur doit comporter entre 3 et 27 caractères.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            if(!DatabaseManager.getInstance().isUserNameAvailable(newNameSaisie.getText())){

                                ChildWindow denied = new ChildWindow(280, 50);
                                JTextPane deniedText = new JTextPane();
                                Document deniedTextDoc = deniedText.getStyledDocument();
                                try {
                                    deniedTextDoc.insertString(0, "Ce nom d'utilisateur est déjà pris !", null);
                                } catch (BadLocationException e) {
                                    e.printStackTrace();
                                }
                                deniedText.setEditable(false);
                                deniedText.setOpaque(false);
                                denied.add(deniedText);
                                denied.setVisible(true);
                            }
                            else{
                                DatabaseManager.getInstance().setUserName(thisUserTag, newNameSaisie.getText());
                                updateThisUser();
                                paintProfile();
                                loggedAs.setText("Vous êtes connectés en tant que : " + thisUser.getNom());
                                ChildWindow confirmed = new ChildWindow(280, 50);
                                JTextPane confirmedText = new JTextPane();
                                Document confirmedTextDoc = confirmedText.getStyledDocument();
                                try {
                                    confirmedTextDoc.insertString(0, "Votre nom d'utilisateur a bien été modifié !", null);
                                } catch (BadLocationException e) {
                                    e.printStackTrace();
                                }
                                confirmedText.setEditable(false);
                                confirmedText.setOpaque(false);
                                confirmed.add(confirmedText);
                                confirmed.setVisible(true);
                            }
                        }
                    }
                }
                else{
                    ChildWindow denied = new ChildWindow(250, 100);
                    JTextPane deniedText = new JTextPane();
                    Document deniedTextDoc = deniedText.getStyledDocument();
                    try {
                        deniedTextDoc.insertString(0, "Mot de passe erroné.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    deniedText.setEditable(false);
                    deniedText.setOpaque(false);
                    denied.add(deniedText);
                    denied.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        pwSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newNameSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        pwSaisie.setColumns(20);
        newNameSaisie.setLineWrap(true);
        newNameSaisie.setWrapStyleWord(true);
    
        pwPanel.add(pwLabel);
        pwPanel.add(pwSaisie);
        newNamePanel.add(newNameLabel);
        newNamePanel.add(newNameSaisie);
        newNamePanel.add(newNameWC);
        modUsernameWindow.setVisible(true);  
    }

    protected void logOff() {
        setLoginScreenVisible();
    }

    protected void openChangePwWindow() {
        //modPw:
        JPanel pwPanel = new JPanel();
        JPanel pw2Panel = new JPanel();
        JPanel confirmPanel = new JPanel();
        ChildWindow modPwWindow = new ChildWindow(600,480);
        modPwWindow.setTitle(thisUser.getNom() + " : Modifier le mot de passe");
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        modPwWindow.add(content);
        content.setLayout(new GridLayout(3,0));
        modPwWindow.setLayout(new GridLayout(1,1));

        content.add(pwPanel);
        content.add(pw2Panel);
        content.add(confirmPanel);

        pwPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        pw2Panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());
        pwPanel.setOpaque(false);
        pw2Panel.setOpaque(false);
        confirmPanel.setOpaque(false);

        JLabel pwLabel = new JLabel("Nouveau mot de passe : ");
        JPasswordField pwSaisie = new JPasswordField();
        pwSaisie.setColumns(20);
        TextPrompt tpPw = new TextPrompt("Insérer le nouveau mdp", pwSaisie);
        JLabel pw2Label = new JLabel("Confirmer : ");
        JPasswordField pw2Saisie = new JPasswordField();
        pw2Saisie.setColumns(20);
        TextPrompt tpPwConfirm = new TextPrompt("Confirmez le nouveau mdp", pw2Saisie);


        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                String newPw = "";
                for(char c:pwSaisie.getPassword()){
                    newPw+=c;
                }
                String newPwConfirm = "";
                for(char c:pw2Saisie.getPassword()){
                    newPwConfirm+=c;
                }
                
                if(!newPw.equals(DatabaseManager.getInstance().getPW(thisUserTag)) && !newPw.isEmpty())
                {
                    if(newPw.equals(newPwConfirm))
                    {
                        if(newPw.length()<4){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre mot de passe doit comporter au moins 5 caractères !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                        }
                        else{
                            DatabaseManager.getInstance().setPW(thisUserTag, newPw);
                            ChildWindow confirmed = new ChildWindow(250, 100);
                            JTextPane confirmedText = new JTextPane();
                            Document confirmedTextDoc = confirmedText.getStyledDocument();
                            try {
                                confirmedTextDoc.insertString(0, "Votre mot de passe a bien été modifié !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            confirmedText.setEditable(false);
                            confirmedText.setOpaque(false);
                            confirmed.add(confirmedText);
                            confirmed.setVisible(true);
                        }
                    }
                    else{
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Les deux mots de passes ne sont pas identiques.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        pwSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pw2Saisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    
        pwPanel.add(pwLabel);
        pwPanel.add(pwSaisie);
        pw2Panel.add(pw2Label);
        pw2Panel.add(pw2Saisie);
        modPwWindow.setVisible(true);
    }

    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth, int gridheight, Double weightx, Double weighty, int anchor, int fill, Insets insets, int padx, int pady) 
    {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty, anchor, fill, insets, padx, pady);
        container.add(component, gbc);
    }

    public void updateCounts(){
        this.myTeams.setText("My Teams (" + DatabaseManager.getInstance().countClubTeams(thisClub.getID()) + ")"); 
        this.myPlayers.setText("My Players (" + DatabaseManager.getInstance().countClubPlayers(thisClub.getID()) + ")");
    }

    public void setListVisible(){
        this.myClubPanel.setVisible(false);
        this.settingsPanel.setVisible(false); 
        this.listPanel.setVisible(true);
    }
    public void setSettingsVisible(){
        this.myClubPanel.setVisible(false);
        this.settingsPanel.setVisible(true); 
        this.listPanel.setVisible(false);
    }

    public void setMyClubVisible(){
        this.myClubPanel.setVisible(true);
        this.settingsPanel.setVisible(false); 
        this.listPanel.setVisible(false);
    }

    public void setLoginScreenVisible(){
        thisUser = null;
        this.myClubPanel.removeAll();
        this.settingsPanel.removeAll();
        this.listPanel.removeAll();
        this.myClubPanel.setVisible(false);
        this.settingsPanel.setVisible(false); 
        this.listPanel.setVisible(false);
        this.listButton.removeSwapToListListener();
        this.myClubButton.removeSwapToMyClubListener();
        this.settingsButton.removeSwapToSettingsListener();
        this.idArea.setText(null);
        this.pwArea.setText(null);
        this.loginPanel.setVisible(true);
    }

    public ImageIcon resizeImage(ImageIcon beforeResize, int w, int h){
        Image image = beforeResize.getImage(); 
        Image newImg = image.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

    public void paintProfile(){
        this.nomArea.setText(this.thisClub.getNom());
        this.ownerArea.setText(this.thisUser.getNom());
        this.desArea.setText(this.thisClub.getDescription());
        this.telArea.setText(this.thisClub.getTel());
        this.mailArea.setText(this.thisClub.getMail());
        this.sportArea.setText(this.thisClub.getSport().getNom());
        this.adressArea.setText(this.thisClub.getAdresse());
        ResultSet ppRS = DatabaseManager.getInstance().getClubProfilePic(this.thisClub.getID());
        ImageIcon newPP = new ImageIcon();
        try {
            if(ppRS.next()){
                ppRS.getString("clubs.imgPath");
                if(!ppRS.wasNull()){
                    newPP = new ImageIcon(ppRS.getString("clubs.imgPath"));
                }
                else{
                    newPP = new ImageIcon(getClass().getResource("/ressources/Trophee.png"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.ppLabel.setIcon(resizeImage(newPP, 206, 195));
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setForeground(attr, VERTTHOMAS);
        try {
            this.winrateClubDoc.remove(0, winrateClubDoc.getLength());
            winrateClubDoc.insertString(winrateClubDoc.getLength(), WINS.format(thisClub.getWins()), attr);
            winrateClubDoc.insertString(winrateClubDoc.getLength(), " / ", null);
            attr = new SimpleAttributeSet();
            StyleConstants.setForeground(attr, Color.RED);
            winrateClubDoc.insertString(winrateClubDoc.getLength(), thisClub.getLosses() + "", attr);
            winrateClubDoc.insertString(winrateClubDoc.getLength(), " (" + PERCENT.format(thisClub.getWinrate()) + "%)\n", null);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }  
    }

    private void updateThisUser(){
        ResultSet userRS = DatabaseManager.getInstance().getUser(thisUserTag);
        try {
            if(userRS.next())this.thisUser = new User(userRS.getInt("users.tag"), userRS.getString("users.nom"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // updateClubBox();
    }

    public void updateDisplayArea(){
        //updatedisplay:
        if(!this.tl.isSelectionEmpty()){
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setBold(attr, true);
            try {
                this.displayDocumentLeft.remove(0, this.displayDocumentLeft.getLength());
                this.displayDocumentRight.remove(0, this.displayDocumentRight.getLength());
                this.displayDocumentLeft.insertString(0, "Nom : ", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), this.tl.getSelectedValue().getNom() + "\n", null);
                attr = new SimpleAttributeSet();
                StyleConstants.setBold(attr, true);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), "Nombre de joueurs : ", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), DatabaseManager.getInstance().getTeamNbPlayers(tl.getSelectedValue().getID()) + "\n", null);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), "Ratio de victoires : ", attr);
                attr = new SimpleAttributeSet();
                StyleConstants.setForeground(attr, VERTTHOMAS);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), WINS.format(this.tl.getSelectedValue().getWins()), attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), " / ", null);
                attr = new SimpleAttributeSet();
                StyleConstants.setForeground(attr, Color.RED);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), this.tl.getSelectedValue().getLosses() + "", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), " (" + PERCENT.format(this.tl.getSelectedValue().getWinrate()) + "%)", null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        if(!this.pl.isSelectionEmpty()){
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setBold(attr, true);
            try {
                this.displayDocumentLeft.remove(0, this.displayDocumentLeft.getLength());
                this.displayDocumentRight.remove(0, this.displayDocumentRight.getLength());
                this.displayDocumentLeft.insertString(0, "Nom : ", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), this.pl.getSelectedValue().getNom() + "\n", null);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), "Sexe : ", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), this.pl.getSelectedValue().getSex() + "\n", null);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), "Ratio de victoires : ", attr);
                attr = new SimpleAttributeSet();
                StyleConstants.setForeground(attr, VERTTHOMAS);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), WINS.format(this.pl.getSelectedValue().getWins()), attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), " / ", null);
                attr = new SimpleAttributeSet();
                StyleConstants.setForeground(attr, Color.RED);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), this.pl.getSelectedValue().getLosses() + "", attr);
                this.displayDocumentLeft.insertString(this.displayDocumentLeft.getLength(), " (" + PERCENT.format(this.pl.getSelectedValue().getWinrate()) + "%)", null);
                attr = new SimpleAttributeSet();
                StyleConstants.setBold(attr, true);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), "Age : ", attr);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), this.pl.getSelectedValue().getAge() +"\n", null);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), "Tel : ", attr);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), this.pl.getSelectedValue().getTel() + "\n", null);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), "Mail : ", attr);
                this.displayDocumentRight.insertString(this.displayDocumentRight.getLength(), this.pl.getSelectedValue().getMail(), null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearLowerDisplay(){
        try {
            if(tlModel.getSize() ==0 || plModel.getSize()==0){
                addButton.setText("Créer une équipe / un joueur");
            }
            else{
                this.addButton.setText("Créer un joueur");
            }
            this.modifyButton.setText("Modifier");
            this.deleteButton.setText("Supprimer");
            this.displayDocumentLeft.remove(0, this.displayDocumentLeft.getLength());
            this.displayDocumentRight.remove(0, this.displayDocumentRight.getLength());
            this.infoTitleDoc.remove(0, this.infoTitleDoc.getLength());
            this.secondaryListOneTitleDoc.remove(0, this.secondaryListOneTitleDoc.getLength());
            this.secondaryListTwoTitleDoc.remove(0, this.secondaryListTwoTitleDoc.getLength());
            this.secondaryListOneDoc.remove(0, this.secondaryListOneDoc.getLength());
            this.secondaryListTwoDoc.remove(0, this.secondaryListTwoDoc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void updateLists(){
        int plSelect = pl.getSelectedIndex();
        int tlSelect = tl.getSelectedIndex();
        int elSelect = el.getSelectedIndex();
        this.tlModel.clear();
        this.plModel.clear();
        this.elModel.clear();
        ResultSet clubTeamsRS = DatabaseManager.getInstance().getClubTeams(this.thisClub.getID());
        try {
            while(clubTeamsRS.next()){
                ResultSet teamCapitaineRS = DatabaseManager.getInstance().getTeamCapitaine(clubTeamsRS.getInt("teams.id"));
                if(teamCapitaineRS.next()){
                    Team t = new Team(clubTeamsRS.getInt("teams.id"), clubTeamsRS.getString("teams.nom"), clubTeamsRS.getInt("teams.clubID"), teamCapitaineRS.getInt("teams.capitaineID"));
                    t.setAvailability();
                    this.tlModel.addElement(t);
                }
                else{
                    Team t = new Team(clubTeamsRS.getInt("teams.id"), clubTeamsRS.getString("teams.nom"), clubTeamsRS.getInt("teams.clubID"));
                    t.setAvailability();
                    this.tlModel.addElement(t);
                }
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        ResultSet clubPlayersRS = DatabaseManager.getInstance().getClubPlayers(this.thisClub.getID());
        try {
            while(clubPlayersRS.next()){
                this.plModel.addElement(new Player(clubPlayersRS.getInt("players.id"), clubPlayersRS.getString("players.nom"), clubPlayersRS.getString("players.age"), clubPlayersRS.getBoolean("players.sexe"), clubPlayersRS.getString("players.tel"), clubPlayersRS.getString("players.mail")));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        ResultSet clubHistoriqueRS = DatabaseManager.getInstance().getClubHistorique(this.thisClub.getID());
        try {
            while(clubHistoriqueRS.next()){
                Event e = new Event(clubHistoriqueRS.getInt("events.id"), clubHistoriqueRS.getString("events.nom"), clubHistoriqueRS.getDate("events.dateDebut"), clubHistoriqueRS.getDate("events.dateFin"), clubHistoriqueRS.getInt("events.clubID"), clubHistoriqueRS.getInt("events.sportID"), clubHistoriqueRS.getString("events.description"));
                this.elModel.addElement(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.tl.setSelectedIndex(tlSelect);
        this.pl.setSelectedIndex(plSelect);
        this.el.setSelectedIndex(elSelect);

        if(tlModel.getSize() ==0 || plModel.getSize()==0){
            addButton.setText("Créer une équipe / un joueur");
        }
    }

    public void updateSecondaryLists() //omg:
    {
        if(!tl.isSelectionEmpty())
        {
            Team thisTeam = tl.getSelectedValue();
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setBold(attr, true);
            try {
                this.secondaryListOneDoc.remove(0, this.secondaryListOneDoc.getLength());
                this.secondaryListTwoDoc.remove(0, this.secondaryListTwoDoc.getLength());
                int k = 1;
                ResultSet playerCompRS = DatabaseManager.getInstance().getTeamPlayers(thisTeam.getID());
                try {
                    while(playerCompRS.next()){
                        if(playerCompRS.getInt("players.id") == thisTeam.getCapitaineID()){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, Color.BLUE);
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerCompRS.getString("players.nom") +"\n", attr);
                        }
                        else if(DatabaseManager.getInstance().getTeamNbPlayers(thisTeam.getID())==k){
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerCompRS.getString("players.nom"), null);
                        }
                        else{
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerCompRS.getString("players.nom") +"\n", null);
                        }
                        k++;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                this.secondaryListOne.setCaretPosition(0);
                k=1;
                boolean gagnant;
                boolean dernier;
                ResultSet teamHistoriqueRS = DatabaseManager.getInstance().getTeamHistorique(thisTeam.getID());
                try {
                    while(teamHistoriqueRS.next()){
                        gagnant = false;
                        dernier = false;
                        ResultSet teamWinnerRS = DatabaseManager.getInstance().getTeamWinner(teamHistoriqueRS.getInt("events.id"));
                        if(teamWinnerRS.getInt("teams.id") == thisTeam.getID()){
                            gagnant = true;
                        }
                        if(DatabaseManager.getInstance().getTeamHistoriqueSize(thisTeam.getID()) == k){
                            dernier = true;
                        }
                        if(!gagnant && !dernier){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, Color.RED);
                            this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), teamHistoriqueRS.getInt("events.nom") + " " + teamHistoriqueRS.getInt("events.dateDebut") +"\n", attr);
                        }
                        else if(!gagnant && dernier){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, Color.RED);
                            this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), teamHistoriqueRS.getInt("events.nom") + " " + teamHistoriqueRS.getInt("events.dateDebut"), attr);
   
                        }
                        else if(gagnant && !dernier){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, VERTTHOMAS);
                            this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), teamHistoriqueRS.getInt("events.nom") + " " + teamHistoriqueRS.getInt("events.dateDebut") +"\n", attr);
                        }
                        else if(gagnant && dernier){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, VERTTHOMAS);
                            this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), teamHistoriqueRS.getInt("events.nom") + " " + teamHistoriqueRS.getInt("events.dateDebut"), attr);
                        }  
                        k++;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.secondaryListTwo.setCaretPosition(0);
                
            } catch (BadLocationException e) {
            }
            
        }
        if(!pl.isSelectionEmpty()){
            Player thisPlayer = pl.getSelectedValue();
            SimpleAttributeSet attr = new SimpleAttributeSet();
            StyleConstants.setBold(attr, true);
            try {
                this.secondaryListOneDoc.remove(0, this.secondaryListOneDoc.getLength());
                this.secondaryListTwoDoc.remove(0, this.secondaryListTwoDoc.getLength());
                int k = 1;
                ResultSet playerTeamsRS = DatabaseManager.getInstance().getPlayerTeams(thisPlayer.getID());
                try {
                    while(playerTeamsRS.next()){
                        if(thisPlayer.getID() == playerTeamsRS.getInt("teams.capitaineID")){
                            attr = new SimpleAttributeSet();
                            StyleConstants.setForeground(attr, Color.BLUE);
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerTeamsRS.getString("teams.nom") +"\n", attr);
                        }
                        else if(DatabaseManager.getInstance().getPlayerNBTeams(thisPlayer.getID())==k){
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerTeamsRS.getString("teams.nom"), null);
                        }
                        else{
                            this.secondaryListOneDoc.insertString(this.secondaryListOneDoc.getLength(), playerTeamsRS.getString("teams.nom") +"\n", null);
                        }
                        k++;
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                this.secondaryListOne.setCaretPosition(0);
                k=1;
                boolean gagnant;
                boolean dernier;
                for(Event e:thisPlayer.getHistorique()){
                    gagnant = false;
                    dernier = false;
                    if(e.getWinner().getID() == thisPlayer.getID()){
                        gagnant = true;
                    }
                    if(thisPlayer.getHistorique().size()==k)
                    {
                        dernier = true;
                    }
                    if(!gagnant && !dernier){
                        attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.RED);
                        this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), e.getNom() + " " + e.getDateStart() +"\n", attr);
                    }
                    else if(!gagnant && dernier){
                        attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, Color.RED);
                        this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), e.getNom() + " " + e.getDateStart(), attr);

                    }
                    else if(gagnant && !dernier){
                        attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, VERTTHOMAS);
                        this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), e.getNom() + " " + e.getDateStart() +"\n", attr);
                    }
                    else if(gagnant && dernier){
                        attr = new SimpleAttributeSet();
                        StyleConstants.setForeground(attr, VERTTHOMAS);
                        this.secondaryListTwoDoc.insertString(this.secondaryListTwoDoc.getLength(), e.getNom() + " " + e.getDateStart(), attr);
                    }  
                    k++;
                }
                this.secondaryListTwo.setCaretPosition(0);
            } catch (BadLocationException e) {
            }
        }
    }

    public void updateWC(JTextArea ta, JLabel wc, int limit){
        wc.setText(ta.getText().length() + "/" + limit);
    }
    public void updateLC(JTextArea ta, JLabel lc, int limit){
        lc.setText(getWrappedLines(ta) + "/" + limit);
    }

    public void openNewClubWindow(){
        //newclub:
        ChildWindow newClubWindow = new ChildWindow(600,480);
        newClubWindow.setTitle("Créer un nouveau club");
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        newClubWindow.add(content);
        content.setLayout(new GridLayout(8,0));
        newClubWindow.setLayout(new GridLayout(1,1));

        ImageIcon placeHolderPic = resizeImage(this.trophee, 50, 50);
        
        JPanel nomPanel = new JPanel();
        JPanel sportChoicePanel = new JPanel();
        JPanel mailPanel = new JPanel();
        JPanel telPanel = new JPanel();
        JPanel adressPanel = new JPanel();
        JPanel desPanel = new JPanel();
        JPanel imgPanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        sportChoicePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        telPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        adressPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        desPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        imgPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());

        JLabel nomClubLabel = new JLabel("Nom du club : ");
        JTextArea nomSaisie = new JTextArea(0,18);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel sportChoiceLabel = new JLabel("Sport du club : ");
        JComboBox<Sport> sportBox = new JComboBox<Sport>();

        ResultSet sportsRS = DatabaseManager.getInstance().getAllSports();
        try {
            while(sportsRS.next()){
                sportBox.addItem(new Sport(sportsRS.getInt("id"), sportsRS.getString("nom")));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        sportBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Sport){
                    Sport s = (Sport) value;
                    setText(s.getNom());
                }
                return this;
            }
        });

        JLabel mailLabel = new JLabel("Mail : ");
        JTextArea mailSaisie = new JTextArea(0,22);
        JLabel mailWC = new JLabel();
        JLabel mailDispo = new JLabel();
        updateWC(mailSaisie, mailWC, 37);
        JLabel telLabel = new JLabel("Tel : ");
        JTextArea telSaisie = new JTextArea(0,7);
        JLabel adressLabel = new JLabel("Adresse : ");
        JTextArea adressSaisie = new JTextArea(0,22);
        JLabel adressWC = new JLabel();
        updateWC(adressSaisie, adressWC, 37);
        JLabel desLabel = new JLabel("Description : ");
        JTextArea desSaisie = new JTextArea(2, 27);
        JLabel desLC = new JLabel();
        updateLC(desSaisie, desLC, 6);
        JLabel imgLabel = new JLabel("Image de profil : ");

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClubNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        mailSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClubMailAvailable(mailSaisie, mailDispo);
            }
        }));

        mailSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 37);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 37);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(mailSaisie, mailWC, 37);
            }
        });

        adressSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(adressSaisie, adressWC, 37);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(adressSaisie, adressWC, 37);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(adressSaisie, adressWC, 37);
            }
        });

        desSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateLC(desSaisie, desLC, 6);
            }
        });

        Button chooseImg = new Button("Choisir une image");
        chooseImg.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                int res = choose.showOpenDialog(newClubWindow);
                if (res == JFileChooser.APPROVE_OPTION) 
                {
                    ImageIcon newClubPic = resizeImage(new ImageIcon(choose.getSelectedFile().getPath()), 50, 50);
                    placeHolderPic.setImage(newClubPic.getImage());
                    content.repaint();
                }
            }
        }
        );

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            //newclubsubmit:
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean nameOK = true;
                boolean mailOK = true;
                boolean telOK = false;
                boolean adressOK = false;
                boolean desOK = false;

                if(!nomSaisie.getText().isBlank())
                {
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre nom de club doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isClubNameAvailable(nomSaisie.getText())){
                            nameOK = false;
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Il existe déjà un club à ce nom.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                    }
                }
                
                if(!mailSaisie.getText().isBlank())
                {
                    if(mailSaisie.getText().length()>37){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre adresse mail ne doit pas comporter plus de 37 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isClubMailAvailable(mailSaisie.getText()))
                        {
                            mailOK = false;
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Cette adresse mail est déjà utilisée par un autre club.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                    }
                }
                if(!adressSaisie.getText().isBlank())
                {
                    if(adressSaisie.getText().length()>37){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre adresse postale ne doit pas comporter plus de 37 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        adressOK = true;
                    }
                }
                if(!telSaisie.getText().isBlank())
                {
                    if(telSaisie.getText().length()!=10){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Veuillez vérifier votre numéro de téléphone.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        telOK = true;
                    }
                }

                if(!desSaisie.getText().isBlank())
                {
                    if(getWrappedLines(desSaisie)<7){
                        desOK = true;
                    }
                    else{
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre description ne doit pas comporter plus de 6 lignes.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                }
                
                if(nameOK && mailOK & telOK && adressOK && desOK){
                    Sport newSport = (Sport)sportBox.getSelectedItem();
                    if(choose.getSelectedFile()!=null){
                        DatabaseManager.getInstance().createClub(nomSaisie.getText(), adressSaisie.getText(), telSaisie.getText(), mailSaisie.getText(), desSaisie.getText(), newSport.getID(), thisUser.getTag(), choose.getSelectedFile().getPath());
                    }
                    else{
                        DatabaseManager.getInstance().createClub(nomSaisie.getText(), adressSaisie.getText(), telSaisie.getText(), mailSaisie.getText(), desSaisie.getText(), newSport.getID(), thisUser.getTag());
                    }
                    if(thisClubID == null){
                        ResultSet rs = DatabaseManager.getInstance().getUserClubs(thisUserTag);
                        try {
                            if(rs.next()){//wtf:
                                thisClubID = rs.getInt("clubs.id");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    updateClubBox();

                    ChildWindow confirmed = new ChildWindow(400, 80);
                    JTextPane confirmedText = new JTextPane();
                    Document confirmedTextDoc = confirmedText.getStyledDocument();
                    try {
                        confirmedTextDoc.insertString(0, "Le club " + nomSaisie.getText() + " a bien été créé.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    confirmedText.setEditable(false);
                    confirmedText.setOpaque(false);
                    confirmed.add(confirmedText);
                    confirmed.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        adressSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        telSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        desSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        mailSaisie.setLineWrap(true);
        mailSaisie.setWrapStyleWord(true);
        telSaisie.setLineWrap(true);
        telSaisie.setWrapStyleWord(true);
        desSaisie.setLineWrap(true);
        desSaisie.setWrapStyleWord(true);
        adressSaisie.setLineWrap(true);
        adressSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        sportChoicePanel.setOpaque(false);
        mailPanel.setOpaque(false);
        adressPanel.setOpaque(false);
        telPanel.setOpaque(false);
        desPanel.setOpaque(false);
        imgPanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomClubLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        sportChoicePanel.add(sportChoiceLabel);
        sportChoicePanel.add(sportBox);
        mailPanel.add(mailLabel);
        mailPanel.add(mailSaisie);
        mailPanel.add(mailWC);
        mailPanel.add(mailDispo);
        adressPanel.add(adressLabel);
        adressPanel.add(adressSaisie);
        adressPanel.add(adressWC);
        telPanel.add(telLabel);
        telPanel.add(telSaisie);
        desPanel.add(desLabel);
        desPanel.add(new JScrollPane(desSaisie));
        desPanel.add(desLC);
        imgPanel.add(imgLabel);
        imgPanel.add(chooseImg);
        imgPanel.add(new JLabel(placeHolderPic));

        content.add(nomPanel);
        content.add(sportChoicePanel);
        content.add(mailPanel);
        content.add(telPanel);
        content.add(adressPanel);
        content.add(desPanel);
        content.add(imgPanel);
        content.add(confirmPanel);

        newClubWindow.setVisible(true);
    }

    public void openNewEventWindow(){
        //newEvent:
        ChildWindow newEventWindow = new ChildWindow(600,800);
        newEventWindow.setTitle("Programmer un événement");
        ImageIcon thisFond = resizeImage(this.fond, 600, 800);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        newEventWindow.add(content);
        content.setLayout(new GridLayout(7,0));
        newEventWindow.setLayout(new GridLayout(1,1));

        ImageIcon placeHolderPic = resizeImage(this.trophee, 50, 50);

        ArrayList<Participant> participants = new ArrayList<Participant>();
        
        JPanel nomPanel = new JPanel();
        JPanel sportPanel = new JPanel();
        JPanel typePanel = new JPanel();
        JPanel datePanel = new JPanel();
        JPanel participantsPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel desPanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        sportPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        typePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        datePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        desPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        participantsPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        confirmPanel.setLayout(new GridBagLayout());
        
        JLabel nomClubLabel = new JLabel("Nom de l'événement : ");
        JTextArea nomSaisie = new JTextArea(0,18);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel participantsLabel = new JLabel();
        JLabel sportLabel = new JLabel("Sport : " + thisClub.getSport().getNom());
        JLabel typeLabel = new JLabel("Catégorie : ");

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        UtilDateModel model = new UtilDateModel();
        JDatePanelImpl calendarPanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(calendarPanel, new DateLabelFormatter());
        //date:

        JComboBox<String> typeBox = new JComboBox<String>();
        typeBox.addItem("Individuel");
        typeBox.addItem("En équipe");

        JLabel dateLabel = new JLabel("Date : ");

        JLabel desLabel = new JLabel("Description : ");
        JTextArea desSaisie = new JTextArea(2, 27);
        JLabel desLC = new JLabel();
        updateLC(desSaisie, desLC, 6);

        JList<Participant> participantToList = new JList<Participant>();
        participantToList.setOpaque(false);
        participantToList.setBackground(new Color(0, 0, 0, 0));
        participantToList.setPreferredSize(new Dimension(250,120));
        participantToList.setMinimumSize(new Dimension(180,120));
        DefaultListModel<Participant> participantToListModel = new DefaultListModel<Participant>();
        participantToList.setModel(participantToListModel);
        JComboBox<Participant> participantBox = new JComboBox<Participant>();

        ResultSet playersRS = DatabaseManager.getInstance().getAllPlayers();
        participantBox.removeAllItems();
        try {
            while(playersRS.next()){
                RegisteredPlayer regP = new RegisteredPlayer(new Player(playersRS.getInt("players.id"), playersRS.getString("players.nom"), playersRS.getString("players.age"), playersRS.getBoolean("players.sexe"), playersRS.getString("players.tel"), playersRS.getString("players.mail")), playersRS.getInt("clubplayers.clubID"));
                participantBox.addItem(regP);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        typeBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                participantBox.removeAllItems();
                participantToListModel.clear();
                participants.clear();
                if(typeBox.getSelectedIndex() == 0){
                    ResultSet playersRS2 = DatabaseManager.getInstance().getAllPlayers();
                    try {
                        while(playersRS2.next()){
                            RegisteredPlayer regP = new RegisteredPlayer(new Player(playersRS2.getInt("players.id"), playersRS2.getString("players.nom"), playersRS2.getString("players.age"), playersRS2.getBoolean("players.sexe"), playersRS2.getString("players.tel"), playersRS2.getString("players.mail")), playersRS2.getInt("clubplayers.clubID"));
                            participantBox.addItem(regP);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                if(typeBox.getSelectedIndex() == 1){
                    ResultSet teamsRS = DatabaseManager.getInstance().getAllTeams();
                    try {
                        while(teamsRS.next()){
                            Team t = new Team(teamsRS.getInt("teams.id"), teamsRS.getString("teams.nom"), teamsRS.getInt("teams.clubID"));
                            participantBox.addItem(t);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } 
                }
            }
        });

        participantBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Participant){
                    Participant p = (Participant) value;
                    setText(p.getNom());
                }
                if(value == null){
                    setText("Vide");
                }
                return this;
            }
        });

        participantToList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                if(value instanceof Participant){
                    Participant p = (Participant) value;
                    setText(p.getNom());
                }
                return this;
            }
        });
        
        participantBox.setPreferredSize(new Dimension(200,20));
        
        Button addParticipantButton = new Button("Ajouter");
        Button removeParticipantButton = new Button("Retirer");

        addParticipantButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(participantBox.getItemCount()<1))
                {
                    participants.add((Participant)participantBox.getSelectedItem());
                    participantBox.removeItem((participantBox.getSelectedItem()));
                    participantToListModel.clear();
                    for(Participant p:participants){
                        participantToListModel.addElement(p);
                    }
                    participantToList.setSelectedIndex(0);
                }
            }
        }
        );
        
        removeParticipantButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(participantToListModel.getSize()<1))
                {
                    participants.remove(participantToList.getSelectedValue());
                    participantBox.addItem((participantToList.getSelectedValue()));
                    participantToListModel.clear();
                    for(Participant p:participants){
                        participantToListModel.addElement(p);
                    }
                    participantToList.setSelectedIndex(0);
                }
            }
        }
        );

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClubNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        desSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateLC(desSaisie, desLC, 6);
            }
        });

        Button chooseImg = new Button("Choisir une image");
        chooseImg.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                int res = choose.showOpenDialog(newEventWindow);
                if (res == JFileChooser.APPROVE_OPTION) 
                {
                    ImageIcon newClubPic = resizeImage(new ImageIcon(choose.getSelectedFile().getPath()), 50, 50);
                    placeHolderPic.setImage(newClubPic.getImage());
                    content.repaint();
                }
            }
        }
        );

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                boolean nameOK = false;
                boolean desOK = false;
                boolean individuel = true;
                if(typeBox.getSelectedIndex() == 1){
                    individuel = false;
                }

                if(!nomSaisie.getText().isBlank())
                {
                    nameOK = true;
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        nameOK = false;
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre nom d'événement doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                }

                if(!desSaisie.getText().isBlank() && getWrappedLines(desSaisie)<7)
                {
                    desOK = true;
                }
                else{
                    ChildWindow denied = new ChildWindow(250, 100);
                    JTextPane deniedText = new JTextPane();
                    Document deniedTextDoc = deniedText.getStyledDocument();
                    try {
                        deniedTextDoc.insertString(0, "Votre description ne doit pas comporter plus de 6 lignes.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    deniedText.setEditable(false);
                    deniedText.setOpaque(false);
                    denied.add(deniedText);
                    denied.setVisible(true);
                }
                
                if(nameOK && desOK){
                    // Date date = (Date) datePicker.getModel().getValue();
                   
                    DatabaseManager.getInstance().createEvent(nomSaisie.getText(), thisClub.getSportID(), thisClub.getID(), participants, desSaisie.getText(), datePicker.getJFormattedTextField().getText(), individuel);
                    
                    //here:
                    //update things

                    ChildWindow confirmed = new ChildWindow(400, 80);
                    JTextPane confirmedText = new JTextPane();
                    Document confirmedTextDoc = confirmedText.getStyledDocument();
                    try {
                        confirmedTextDoc.insertString(0, "L'event' " + nomSaisie.getText() + " a bien été créé.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    confirmedText.setEditable(false);
                    confirmedText.setOpaque(false);
                    confirmed.add(confirmedText);
                    confirmed.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        desSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        desSaisie.setLineWrap(true);
        desSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        sportPanel.setOpaque(false);
        desPanel.setOpaque(false);
        typePanel.setOpaque(false);
        datePanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        JScrollPane participantToListScroll = new JScrollPane(participantToList);
        participantToListScroll.setOpaque(false);
        participantToListScroll.getViewport().setOpaque(false);
        participantToList.setPreferredSize(new Dimension(250,100));
        participantToList.setMaximumSize(new Dimension(250,100));
        participantToListScroll.setPreferredSize(new Dimension(275,100));
        participantToListScroll.setMaximumSize(new Dimension(275,100));

        buttonPanel.add(addParticipantButton);
        buttonPanel.add(removeParticipantButton);

        participantsPanel.add(participantsLabel);
        participantsPanel.add(participantBox);
        participantsPanel.add(buttonPanel);
        participantsPanel.add(participantToListScroll);
        participantsPanel.setOpaque(false);

        nomPanel.add(nomClubLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        sportPanel.add(sportLabel);
       
        desPanel.add(desLabel);
        desPanel.add(new JScrollPane(desSaisie));
        desPanel.add(desLC);

        typePanel.add(typeLabel);
        typePanel.add(typeBox);

        datePanel.add(dateLabel);
        datePanel.add(datePicker);

        content.add(nomPanel);
        content.add(typePanel);
        content.add(participantsPanel);
        content.add(datePanel);
        content.add(desPanel);
        content.add(sportPanel);
        content.add(confirmPanel);
        newEventWindow.pack();

        newEventWindow.setVisible(true);
    }
    

    public void openModClubWindow()
    {   
        //modclub:
        ChildWindow modClubWindow = new ChildWindow(600,480);
        modClubWindow.setTitle("Modifier " + this.thisClub.getNom());
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        modClubWindow.add(content);
        content.setLayout(new GridLayout(8,0));
        modClubWindow.setLayout(new GridLayout(1,1));
        
        JPanel nomPanel = new JPanel();
        JPanel sportChoicePanel = new JPanel();
        JPanel mailPanel = new JPanel();
        JPanel telPanel = new JPanel();
        JPanel adressPanel = new JPanel();
        JPanel desPanel = new JPanel();
        JPanel imgPanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        //here::
        ResultSet ppRS = DatabaseManager.getInstance().getClubProfilePic(this.thisClub.getID());
        this.placeholderPic = new ImageIcon();
        try {
            if(ppRS.next()){
                ppRS.getString("clubs.imgPath");
                if(!ppRS.wasNull()){
                    placeholderPic = new ImageIcon(ppRS.getString("clubs.imgPath"));
                }
                else{
                    placeholderPic = new ImageIcon(getClass().getResource("/ressources/Trophee.png"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        placeholderPic = resizeImage(placeholderPic, 50, 50);

        

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        sportChoicePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        telPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        adressPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        desPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        imgPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());

        //prout:
        JLabel nomClubLabel = new JLabel("Nom du club : ");
        JTextArea nomSaisie = new JTextArea(this.thisClub.getNom(), 0,18);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel sportChoiceLabel = new JLabel("Sport du club : ");
        JComboBox<Sport> sportBox = new JComboBox<Sport>();

        ResultSet sportsRS = DatabaseManager.getInstance().getAllSports();
        try {
            while(sportsRS.next()){
                sportBox.addItem(new Sport(sportsRS.getInt("sports.id"), sportsRS.getString("sports.nom")));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        sportBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Sport){
                    Sport s = (Sport) value;
                    setText(s.getNom());
                }
                return this;
            }
        });

        sportBox.setSelectedIndex(this.thisClub.getSport().getID()-1);

        JLabel mailLabel = new JLabel("Mail : ");
        JTextArea mailSaisie = new JTextArea(this.thisClub.getMail(), 0,22);
        JLabel mailWC = new JLabel();
        JLabel mailDispo = new JLabel();
        updateWC(mailSaisie, mailWC, 37);
        JLabel telLabel = new JLabel("Tel : ");
        JTextArea telSaisie = new JTextArea(this.thisClub.getTel(), 0,7);
        JLabel adressLabel = new JLabel("Adresse : ");
        JTextArea adressSaisie = new JTextArea(this.thisClub.getAdresse(), 0,22);
        JLabel adressWC = new JLabel();
        updateWC(adressSaisie, adressWC, 37);
        JLabel desLabel = new JLabel("Description : ");
        JTextArea desSaisie = new JTextArea(this.thisClub.getDescription(), 2, 27);
        JLabel desLC = new JLabel();
        updateLC(desSaisie, desLC, 6);
        JLabel imgLabel = new JLabel("Image de profil : ");

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClubNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        mailSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isClubMailAvailable(mailSaisie, mailDispo);
            }
        }));

        mailSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 37);
                
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 37);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(mailSaisie, mailWC, 37);
            }
        });

        adressSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(adressSaisie, adressWC, 37);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(adressSaisie, adressWC, 37);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(adressSaisie, adressWC, 37);
            }
        });

        desSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLC(desSaisie, desLC, 6);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateLC(desSaisie, desLC, 6);
            }
        });
        //here:::
        Button chooseImg = new Button("Choisir une image");
        chooseImg.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                int res = choose.showOpenDialog(modClubWindow);
                if (res == JFileChooser.APPROVE_OPTION) 
                {
                    ImageIcon newClubPic = resizeImage(new ImageIcon(choose.getSelectedFile().getPath()), 50, 50);
                    placeholderPic.setImage(newClubPic.getImage());
                    content.repaint();
                }
            }
        }
        );

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                String newClubName = nomSaisie.getText();
                Sport newSport = (Sport) sportBox.getSelectedItem();
                String newMail = mailSaisie.getText();
                String newTel = telSaisie.getText();
                String newAdress = adressSaisie.getText();
                String newDes = desSaisie.getText();

                if(!newClubName.equals(thisClub.getNom()) && !newClubName.isBlank())
                {
                    if(newClubName.length()<2 || newClubName.length()>27){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre nom de club doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isClubNameAvailable(newClubName) && !newClubName.equalsIgnoreCase(thisClub.getNom())){
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Il existe déjà un club à ce nom !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            DatabaseManager.getInstance().setClubName(thisClub.getID(), newClubName);
                            ChildWindow confirmed = new ChildWindow(250, 100);
                            JTextPane confirmedText = new JTextPane();
                            Document confirmedTextDoc = confirmedText.getStyledDocument();
                            try {
                                confirmedTextDoc.insertString(0, "Votre nom de club a bien été modifié !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            confirmedText.setEditable(false);
                            confirmedText.setOpaque(false);
                            confirmed.add(confirmedText);
                            confirmed.setVisible(true);
                        }
                    }
                }
                
                if(!newSport.equals(thisClub.getSport()))
                {
                    DatabaseManager.getInstance().setClubSport(thisClub.getID(), newSport.getID());
                    paintProfile();
                    updateDisplayArea();
                    ChildWindow confirmed = new ChildWindow(250, 100);
                    JTextPane confirmedText = new JTextPane();
                    Document confirmedTextDoc = confirmedText.getStyledDocument();
                    try {
                        confirmedTextDoc.insertString(0, "Le sport du club a bien été modifié !", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    confirmedText.setEditable(false);
                    confirmedText.setOpaque(false);
                    confirmed.add(confirmedText);
                    confirmed.setVisible(true);
                }
                if(!newMail.equals(thisClub.getMail()) && !newMail.isEmpty())
                {
                    if(newMail.length()>37){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre adresse mail ne doit pas comporter plus de 37 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        
                        if(!DatabaseManager.getInstance().isClubMailAvailable(newMail) && !newMail.equalsIgnoreCase(thisClub.getMail())){
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Ce mail est déjà utilisé par un autre club !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            thisClub.setMail(newMail);
                            DatabaseManager.getInstance().setClubMail(thisClub.getID(), newMail);
                            ChildWindow confirmed = new ChildWindow(250, 100);
                            JTextPane confirmedText = new JTextPane();
                            Document confirmedTextDoc = confirmedText.getStyledDocument();
                            try {
                                confirmedTextDoc.insertString(0, "Votre mail a bien été modifié !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            confirmedText.setEditable(false);
                            confirmedText.setOpaque(false);
                            confirmed.add(confirmedText);
                            confirmed.setVisible(true);
                        }
                    }
                }
                if(!newAdress.equals(thisClub.getAdresse()) && !newAdress.isEmpty())
                {
                    if(newAdress.length()>37){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre adresse postale ne doit pas comporter plus de 37 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        DatabaseManager.getInstance().setClubAdresse(thisClub.getID(), newAdress);
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "Votre adresse postale a bien été modifiée !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                }
                if(!newTel.equals(thisClub.getTel()) && !newTel.isEmpty())
                {
                    if(newTel.length()!=10){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Veuillez vérifier votre numéro de téléphone.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        DatabaseManager.getInstance().setClubTel(thisClub.getID(), newTel);
                        
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "Votre tel a bien été modifié !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                }
                if(!newDes.equals(thisClub.getDescription()) && !newDes.isBlank())
                {
                    if(desSaisie.getLineCount()<7){
                        DatabaseManager.getInstance().setClubDes(thisClub.getID(), newDes);
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "Votre description a bien été modifiée !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                    else{
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Votre description ne doit pas comporter plus de 6 lignes.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                }
                if(choose.getSelectedFile()!=null){
                    DatabaseManager.getInstance().setProfilePic(thisClub.getID(), choose.getSelectedFile().getPath());
                }
                updateThisClub();
                paintProfile();
            }
        }
        );
        confirmPanel.add(enregistrer);

        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        mailSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        adressSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        telSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        desSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);

        mailSaisie.setLineWrap(true);
        mailSaisie.setWrapStyleWord(true);
        telSaisie.setLineWrap(true);
        telSaisie.setWrapStyleWord(true);
        desSaisie.setLineWrap(true);
        desSaisie.setWrapStyleWord(true);
        adressSaisie.setLineWrap(true);
        adressSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        
        sportChoicePanel.setOpaque(false);
        mailPanel.setOpaque(false);
        adressPanel.setOpaque(false);
        telPanel.setOpaque(false);
        desPanel.setOpaque(false);
        imgPanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomClubLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        
        sportChoicePanel.add(sportChoiceLabel);
        sportChoicePanel.add(sportBox);
        mailPanel.add(mailLabel);
        mailPanel.add(mailSaisie);
        mailPanel.add(mailWC);
        mailPanel.add(mailDispo);
        adressPanel.add(adressLabel);
        adressPanel.add(adressSaisie);
        adressPanel.add(adressWC);
        telPanel.add(telLabel);
        telPanel.add(telSaisie);
        desPanel.add(desLabel);
        desPanel.add(new JScrollPane(desSaisie));
        desPanel.add(desLC);
        imgPanel.add(imgLabel);
        imgPanel.add(chooseImg);
        imgPanel.add(new JLabel(placeholderPic));

        content.add(nomPanel);
        content.add(sportChoicePanel);
        content.add(mailPanel);
        content.add(telPanel);
        content.add(adressPanel);
        content.add(desPanel);
        content.add(imgPanel);
        content.add(confirmPanel);

        modClubWindow.setVisible(true);
    }

    public void openNewPlayerWindow(){
        //newPlayer:
        ChildWindow newPlayerWindow = new ChildWindow(600,480);
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        newPlayerWindow.add(content);
        newPlayerWindow.setTitle("Ajouter un nouveau joueur");
        newPlayerWindow.getContentPane().setBackground(new Color(168,112,238));
        content.setLayout(new GridLayout(6,0));
        newPlayerWindow.setLayout(new GridLayout(1,1));
        
        JPanel nomPanel = new JPanel();
        JPanel sexePanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel telPanel = new JPanel();
        JPanel mailPanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        sexePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        agePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        telPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());

        JLabel nomLabel = new JLabel("Nom : ");
        JTextArea nomSaisie = new JTextArea(0,18);
        JLabel nomWC = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel sexeLabel = new JLabel("Sexe : ");
        JComboBox<Boolean> sexBox = new JComboBox<Boolean>();
        sexBox.addItem(true);
        sexBox.addItem(false);

        sexBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Boolean){
                    Boolean b = (Boolean) value;
                    if(b == true){
                        setText("Homme");
                    }
                    else{
                        setText("Femme");
                    }
                }
                return this;
            }
        });

        JLabel ageLabel = new JLabel("Age : ");
        JTextArea ageSaisie = new JTextArea(0,4);
        JLabel mailLabel = new JLabel("Mail : ");
        JTextArea mailSaisie = new JTextArea(0,22);
        JLabel mailWC = new JLabel();
        JLabel mailDispo = new JLabel();
        updateWC(mailSaisie, mailWC, 28);
        JLabel telLabel = new JLabel("Tel : ");
        JTextArea telSaisie = new JTextArea(0,7);

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        mailSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerMailAvailable(mailSaisie, mailDispo);
            }
        }));

        mailSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 28);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 28);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(mailSaisie, mailWC, 28);
            }
        });


        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {//newPlayerSubmit:
            @Override
            public void actionPerformed(ActionEvent event) {
                String newPlayerAge = "";
                boolean nameOK = false;
                boolean ageOK = false;
                boolean telOK = false;
                boolean mailOK = true;
                
                if(!nomSaisie.getText().isBlank())
                {
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Le nom de votre joueur doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        nameOK = true;
                    }
                }

                if(!ageSaisie.getText().isBlank())
                {
                    if(ageSaisie.getText().length()>3 || !isNumeric(ageSaisie.getText())){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "L'age du joueur doit être un nombre de 1 à 3 chiffres", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(ageSaisie.getText().charAt(0) == '0'){
                            newPlayerAge = ageSaisie.getText().substring(1);
                            if(ageSaisie.getText().charAt(0) == '0' && ageSaisie.getText().charAt(1) == '0'){
                                newPlayerAge = ageSaisie.getText().substring(2);
                            }
                        }
                        else{
                            newPlayerAge = ageSaisie.getText();
                        }
                        ageOK = true;
                    }
                }
                
                if(!mailSaisie.getText().isBlank())
                {
                    if(mailSaisie.getText().length()>28){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "L'adresse mail du joueur ne doit pas comporter plus de 28 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isPlayerMailAvailable(mailSaisie.getText())){
                            mailOK = false;
                            ChildWindow denied = new ChildWindow(400, 80);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Cette adresse mail est déjà utilisée par un autre joueur !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                    }
                }
                if(!telSaisie.getText().isBlank())
                {
                    if(telSaisie.getText().length()!=10 || !isNumeric(telSaisie.getText())){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Le numéro de téléphone du joueur doit être un nombre à 10 chiffres.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        telOK = true;
                    }
                }
                if(telSaisie.getText().isBlank() || mailSaisie.getText().isBlank() || ageSaisie.getText().isBlank() || nomSaisie.getText().isBlank()){
                    nameOK = false;
                    mailOK = false;
                    telOK = false;
                    ageOK = false;
                    ChildWindow denied = new ChildWindow(400, 80);
                    JTextPane deniedText = new JTextPane();
                    Document deniedTextDoc = deniedText.getStyledDocument();
                    try {
                        deniedTextDoc.insertString(0, "Certains champs n'ont pas été remplis.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    deniedText.setEditable(false);
                    deniedText.setOpaque(false);
                    denied.add(deniedText);
                    denied.setVisible(true);
                }
                if(nameOK && ageOK && telOK && mailOK){
                    DatabaseManager.getInstance().createPlayer(nomSaisie.getText(), newPlayerAge, (boolean)sexBox.getSelectedItem(), telSaisie.getText(), mailSaisie.getText(), thisClub.getID());
                    updateCounts();
                    updateLists();
                    ChildWindow confirmed = new ChildWindow(400, 80);
                    JTextPane confirmedText = new JTextPane();
                    Document confirmedTextDoc = confirmedText.getStyledDocument();
                    try {
                        confirmedTextDoc.insertString(0, "Le joueur " + nomSaisie.getText() + " a été ajouté au club \"" + thisClub.getNom() +"\".", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    confirmedText.setEditable(false);
                    confirmedText.setOpaque(false);
                    confirmed.add(confirmedText);
                    confirmed.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);

        //newplayerpage:
        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        telSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        ageSaisie.setLineWrap(true);
        ageSaisie.setWrapStyleWord(true);
        mailSaisie.setLineWrap(true);
        mailSaisie.setWrapStyleWord(true);
        telSaisie.setLineWrap(true);
        telSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        sexePanel.setOpaque(false);
        agePanel.setOpaque(false);
        mailPanel.setOpaque(false);
        telPanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        sexePanel.add(sexeLabel);
        sexePanel.add(sexBox);
        agePanel.add(ageLabel);
        agePanel.add(ageSaisie);
        mailPanel.add(mailLabel);
        mailPanel.add(mailSaisie);
        mailPanel.add(mailWC);
        mailPanel.add(mailDispo);
        telPanel.add(telLabel);
        telPanel.add(telSaisie);

        content.add(nomPanel);
        content.add(sexePanel);
        content.add(agePanel);
        content.add(telPanel);
        content.add(mailPanel);
        content.add(confirmPanel);

        newPlayerWindow.setVisible(true);
    }
    public void openNewTeamWindow(){
        //newteam:
        ChildWindow newTeamWindow = new ChildWindow(800,600);
        ImageIcon thisFond = resizeImage(this.fond, 800, 600);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        newTeamWindow.setTitle("Ajouter une nouvelle équipe");
        newTeamWindow.getContentPane().setLayout(new GridLayout(1,1));
        newTeamWindow.add(content);
        content.setLayout(new GridLayout(4,0));
        
        ArrayList<Player> newTeamPlayerComp = new ArrayList<Player>();

        JPanel nomPanel = new JPanel();
        JPanel playerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel capitainePanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        capitainePanel.setLayout(new GridBagLayout());
        confirmPanel.setLayout(new GridBagLayout());

        JLabel capitaine = new JLabel("Capitaine : ");
        
        capitainePanel.add(capitaine);
        JLabel nomLabel = new JLabel("Nom : ");
        JTextArea nomSaisie = new JTextArea(0,18);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel playerLabel = new JLabel("Composition de l'équipe : ");
        JList<Player> playerToTeamList = new JList<Player>();
        playerToTeamList.setOpaque(false);
        playerToTeamList.setBackground(new Color(0, 0, 0, 0));
        playerToTeamList.setPreferredSize(new Dimension(250,120));
        playerToTeamList.setMinimumSize(new Dimension(180,120));
        DefaultListModel<Player> playerToTeamListModel = new DefaultListModel<Player>();
        playerToTeamList.setModel(playerToTeamListModel);
        JComboBox<Player> playerBox = new JComboBox<Player>();

        ResultSet clubPlayersRS = DatabaseManager.getInstance().getClubPlayers(thisClub.getID());
        try {
            while(clubPlayersRS.next()){
                playerBox.addItem(new Player(clubPlayersRS.getInt("players.id"), clubPlayersRS.getString("players.nom"), clubPlayersRS.getString("players.age"), clubPlayersRS.getBoolean("players.sexe"), clubPlayersRS.getString("players.tel"), clubPlayersRS.getString("players.mail")));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        playerBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Player){
                    Player p = (Player) value;
                    setText(p.getNom());
                }
                if(value == null){
                    setText("Vide");
                }
                return this;
            }
        });
        playerBox.setPreferredSize(new Dimension(200,20));
        playerToTeamList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                if(value instanceof Player){
                    Player p = (Player) value;
                    setText(p.getNom());
                }
                return this;
            }
        });
        
        Button addPlayerToTeamButton = new Button("Ajouter");
        Button removePlayerFromTeamButton = new Button("Retirer");
        Button setCapitaine = new Button("Capitaine");

        addPlayerToTeamButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(playerBox.getItemCount()<1))
                {
                    newTeamPlayerComp.add((Player)playerBox.getSelectedItem());
                    playerBox.removeItem((playerBox.getSelectedItem()));
                    playerToTeamListModel.clear();
                    for(Player p:newTeamPlayerComp){
                        playerToTeamListModel.addElement(p);
                    }
                    playerToTeamList.setSelectedIndex(0);
                }
            }
        }
        );
        
        setCapitaine.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(playerToTeamListModel.getSize()<1))
                {
                    newTeamCapitain = playerToTeamList.getSelectedValue();
                    capitaine.setText("Capitaine : " + playerToTeamList.getSelectedValue().getNom());
                }
            }
        }
        );
        removePlayerFromTeamButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(playerToTeamListModel.getSize()<1))
                {
                    newTeamPlayerComp.remove(playerToTeamList.getSelectedValue());
                    playerBox.addItem((playerToTeamList.getSelectedValue()));
                    if(playerToTeamList.getSelectedValue().getID()==newTeamCapitain.getID()){
                        capitaine.setText("Capitaine : ");
                    }
                    playerToTeamListModel.clear();
                    for(Player p:newTeamPlayerComp){
                        playerToTeamListModel.addElement(p);
                    }
                    playerToTeamList.setSelectedIndex(0);
                }
            }
        }
        );

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTeamNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!nomSaisie.getText().isBlank())
                {
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Le nom de votre équipe doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isTeamNameAvailable(nomSaisie.getText())){
                            ChildWindow denied = new ChildWindow(400, 80);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Il existe déjà une équipe du même nom", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            DatabaseManager.getInstance().createTeam(nomSaisie.getText(), thisClub.getID(), newTeamPlayerComp, newTeamCapitain.getID());
                        
                            updateCounts();
                            updateLists();
                            ChildWindow confirmed = new ChildWindow(400, 80);
                            JTextPane confirmedText = new JTextPane();
                            Document confirmedTextDoc = confirmedText.getStyledDocument();
                            try {
                                confirmedTextDoc.insertString(0, "L'équipe \"" + nomSaisie.getText() + "\" a été ajoutée au club \"" + thisClub.getNom() +"\".", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            confirmedText.setEditable(false);
                            confirmedText.setOpaque(false);
                            confirmed.add(confirmedText);
                            confirmed.setVisible(true);
                            newTeamWindow.dispose();
                        }
                    }
                }
                else{
                    ChildWindow denied = new ChildWindow(400, 80);
                    JTextPane deniedText = new JTextPane();
                    Document deniedTextDoc = deniedText.getStyledDocument();
                    try {
                        deniedTextDoc.insertString(0, "Le champs nom est requis.", null);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    deniedText.setEditable(false);
                    deniedText.setOpaque(false);
                    denied.add(deniedText);
                    denied.setVisible(true);
                }
            }
        }
        );
        confirmPanel.add(enregistrer);
        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        capitainePanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        buttonPanel.add(addPlayerToTeamButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(removePlayerFromTeamButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(setCapitaine);
        playerPanel.add(playerLabel);
        playerPanel.add(playerBox);
        playerPanel.add(buttonPanel);
        JScrollPane playerToTeamListScroll = new JScrollPane(playerToTeamList);
        playerToTeamListScroll.setOpaque(false);
        playerToTeamListScroll.getViewport().setOpaque(false);
        playerPanel.add(playerToTeamListScroll);
        playerToTeamList.setPreferredSize(new Dimension(200,300));
        content.add(nomPanel);
        content.add(playerPanel);
        content.add(capitainePanel);
        content.add(confirmPanel);
        newTeamWindow.setVisible(true);
    }

    public void openModPlayerWindow(){
        //modplayer:
        ChildWindow modPlayerWindow = new ChildWindow(600,480);
        ImageIcon thisFond = resizeImage(this.fond, 600, 480);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        modPlayerWindow.add(content);
        Player thisPlayer = pl.getSelectedValue();
        modPlayerWindow.setTitle("Modifier le joueur " + thisPlayer.getNom());
        modPlayerWindow.getContentPane().setBackground(new Color(168,112,238));
        content.setLayout(new GridLayout(6,0));
        modPlayerWindow.setLayout(new GridLayout(1,1));
        
        JPanel nomPanel = new JPanel();
        JPanel sexePanel = new JPanel();
        JPanel agePanel = new JPanel();
        JPanel telPanel = new JPanel();
        JPanel mailPanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        JLabel nomLabel = new JLabel("Nom : ");
        JTextArea nomSaisie = new JTextArea(thisPlayer.getNom(), 0,18);
        JLabel nomWC = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel sexeLabel = new JLabel("Sexe : ");
        JComboBox<Boolean> sexBox = new JComboBox<Boolean>();
        sexBox.addItem(true);
        sexBox.addItem(false);

        sexBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Boolean){
                    Boolean b = (Boolean) value;
                    if(b == true){
                        setText("Homme");
                    }
                    else{
                        setText("Femme");
                    }
                }
                return this;
            }
        });
        if(thisPlayer.getSexBool() ==true){
            sexBox.setSelectedItem(true);
        }
        else{
            sexBox.setSelectedItem(false);
        }
        JLabel ageLabel = new JLabel("Age : ");
        JTextArea ageSaisie = new JTextArea(thisPlayer.getAge(), 0,4);
        JLabel mailLabel = new JLabel("Mail : ");
        JTextArea mailSaisie = new JTextArea(thisPlayer.getMail(), 0,22);
        JLabel mailWC = new JLabel();
        JLabel mailDispo = new JLabel();
        updateWC(mailSaisie, mailLabel, 28);
        JLabel telLabel = new JLabel("Tel : ");
        JTextArea telSaisie = new JTextArea(thisPlayer.getTel(), 0,7);
        
        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        sexePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        agePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        mailPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        telPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        confirmPanel.setLayout(new GridBagLayout());

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        mailSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlayerMailAvailable(mailSaisie, mailDispo);
            }
        }));

        mailSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 28);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(mailSaisie, mailWC, 28);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(mailSaisie, mailWC, 28);
            }
        });

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {//modPlayerConfirm:
            @Override
            public void actionPerformed(ActionEvent event) {

                thisPlayer.setSexBool((boolean)sexBox.getSelectedItem());
            
                String newPlayerName = nomSaisie.getText();
                String newPlayerAge = ageSaisie.getText();
                String newPlayerMail = mailSaisie.getText();
                String newPlayerTel = telSaisie.getText();

                if(!newPlayerName.equals(thisPlayer.getNom()) && !newPlayerName.isEmpty())
                {
                    if(newPlayerName.length()<2 || newPlayerName.length()>27){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Le nom du joueur doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{ 
                        DatabaseManager.getInstance().setPlayerName(thisPlayer.getID(), newPlayerName);
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "Le nom du joueur a bien été modifié !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                }
                if(!newPlayerMail.equals(thisPlayer.getMail()) && !newPlayerMail.isEmpty())
                {
                    if(newPlayerMail.length()>28){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "L'adresse mail ne doit pas comporter plus de 28 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        boolean mailOK = true;
                        if(!DatabaseManager.getInstance().isPlayerMailAvailable(newPlayerMail) && newPlayerMail != thisPlayer.getMail())
                        {
                            ChildWindow denied = new ChildWindow(250, 100);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Ce mail est déjà utilisé par un autre joueur !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            DatabaseManager.getInstance().setPlayerMail(thisPlayer.getID(), newPlayerMail);
                            
                            ChildWindow confirmed = new ChildWindow(250, 100);
                            JTextPane confirmedText = new JTextPane();
                            Document confirmedTextDoc = confirmedText.getStyledDocument();
                            try {
                                confirmedTextDoc.insertString(0, "Le mail a bien été modifié !", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            confirmedText.setEditable(false);
                            confirmedText.setOpaque(false);
                            confirmed.add(confirmedText);
                            confirmed.setVisible(true);
                        }
                    }
                }
                if(!newPlayerTel.equals(thisPlayer.getTel()) && !newPlayerTel.isEmpty())
                {
                    if(newPlayerTel.length()!=10){
                        ChildWindow denied = new ChildWindow(250, 100);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Veuillez vérifier le numéro de téléphone.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        DatabaseManager.getInstance().setPlayerTel(thisPlayer.getID(), newPlayerTel);
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "Votre tel a bien été modifié !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                }
                if(!newPlayerAge.equals(thisPlayer.getAge()) && !newPlayerAge.isEmpty()){
                    if(ageSaisie.getText().length()>3 || !isNumeric(ageSaisie.getText())){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "L'age du joueur doit être un nombre de 1 à 3 chiffres", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(ageSaisie.getText().charAt(0) == '0'){
                            DatabaseManager.getInstance().setPlayerAge(thisPlayer.getID(), ageSaisie.getText().substring(1));
                            if(ageSaisie.getText().charAt(0) == '0' && ageSaisie.getText().charAt(1) == '0'){
                                DatabaseManager.getInstance().setPlayerAge(thisPlayer.getID(), ageSaisie.getText().substring(2));
                            }
                        }
                        else{
                            DatabaseManager.getInstance().setPlayerAge(thisPlayer.getID(), ageSaisie.getText());
                        }
                        ChildWindow confirmed = new ChildWindow(250, 100);
                        JTextPane confirmedText = new JTextPane();
                        Document confirmedTextDoc = confirmedText.getStyledDocument();
                        try {
                            confirmedTextDoc.insertString(0, "L'âge du joueur a bien été modifié !", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        confirmedText.setEditable(false);
                        confirmedText.setOpaque(false);
                        confirmed.add(confirmedText);
                        confirmed.setVisible(true);
                    }
                }
                thisPlayer.updateSex();
                updateLists();
                updateDisplayArea();
            }
        }
        );
        confirmPanel.add(enregistrer);

        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ageSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mailSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        telSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        ageSaisie.setLineWrap(true);
        ageSaisie.setWrapStyleWord(true);
        mailSaisie.setLineWrap(true);
        mailSaisie.setWrapStyleWord(true);
        telSaisie.setLineWrap(true);
        telSaisie.setWrapStyleWord(true);

        nomPanel.setOpaque(false);
        sexePanel.setOpaque(false);
        agePanel.setOpaque(false);
        mailPanel.setOpaque(false);
        telPanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        nomPanel.add(nomLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        sexePanel.add(sexeLabel);
        sexePanel.add(sexBox);
        agePanel.add(ageLabel);
        agePanel.add(ageSaisie);
        mailPanel.add(mailLabel);
        mailPanel.add(mailSaisie);
        mailPanel.add(mailWC);
        mailPanel.add(mailDispo);
        telPanel.add(telLabel);
        telPanel.add(telSaisie);

        content.add(nomPanel);
        content.add(sexePanel);
        content.add(agePanel);
        content.add(telPanel);
        content.add(mailPanel);
        content.add(confirmPanel);
        
        modPlayerWindow.setVisible(true);
    }

    private void updateThisClub(){
        this.thisClub = DatabaseManager.getInstance().getClub(this.thisClub.getID());
    }

    public void openIllegalPlayerWindow(RegisteredPlayer p, Event e){
        ChildWindow denied = new ChildWindow(250, 100);
        JTextPane deniedText = new JTextPane();
        Document deniedTextDoc = deniedText.getStyledDocument();
        try {
            deniedTextDoc.insertString(0, "Le joueur " + p.getNom() + " ne peut pas participer à un evement avec un participant de type " + e.getParticipants().get(0).getClass().getName(), null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        deniedText.setEditable(false);
        deniedText.setOpaque(false);
        denied.add(deniedText);
        denied.setVisible(true);
    }

    public void openIllegalTeamWindow(Team t, Event e){
        ChildWindow denied = new ChildWindow(250, 100);
        JTextPane deniedText = new JTextPane();
        Document deniedTextDoc = deniedText.getStyledDocument();
        try {
            deniedTextDoc.insertString(0, "L'équipe " + t.getNom() + " ne peut pas participer à un evement avec un participant de type " + e.getParticipants().get(0).getClass().getName(), null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        deniedText.setEditable(false);
        deniedText.setOpaque(false);
        denied.add(deniedText);
        denied.setVisible(true);
    }


    public void openModTeamWindow(){
        //modteam:
        ChildWindow modTeamWindow = new ChildWindow(800, 600);
        ImageIcon thisFond = resizeImage(this.fond, 800, 600);
        Image thisFondImage = thisFond.getImage();
        BackgroundPanel content = new BackgroundPanel(thisFondImage);
        modTeamWindow.add(content);
        content.setLayout(new GridLayout(4,0));
        modTeamWindow.getContentPane().setLayout(new GridLayout(1,1));
        Team thisTeam = tl.getSelectedValue();
        modTeamWindow.setTitle("Modifier l'équipe " + thisTeam.getNom());
        
        ArrayList<Player> newTeamPlayerComp = new ArrayList<Player>();
        
        JPanel nomPanel = new JPanel();
        JPanel playerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel capitainePanel = new JPanel();
        JPanel confirmPanel = new JPanel();

        nomPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        playerPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        capitainePanel.setLayout(new GridBagLayout());
        confirmPanel.setLayout(new GridBagLayout());

        JLabel capitaine = new JLabel();
        if(thisTeam.getCapitaineID()!=0){
            ResultSet teamcapRS = DatabaseManager.getInstance().getTeamCapitaine(thisTeam.getID());
            try {
                if(teamcapRS.next())capitaine.setText("Capitaine : " + teamcapRS.getString("players.nom"));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        else{
            capitaine.setText("Capitaine : aucun");
        }
        
        capitainePanel.add(capitaine);
        JLabel nomLabel = new JLabel("Nom : ");
        JTextArea nomSaisie = new JTextArea(thisTeam.getNom(), 0,18);
        JLabel nomWC = new JLabel();
        JLabel nomDispo = new JLabel();
        updateWC(nomSaisie, nomWC, 27);
        JLabel playerLabel = new JLabel("Composition de l'équipe : ");
        JComboBox<Player> playerBox = new JComboBox<Player>();
        JList<Player> playerToTeamList = new JList<Player>();

        playerToTeamList.setOpaque(false);
        playerToTeamList.setBackground(new Color(0, 0, 0, 0));

        playerBox.setPreferredSize(new Dimension(200,20));
        playerToTeamList.setPreferredSize(new Dimension(220,120));
        playerToTeamList.setMinimumSize(new Dimension(180,120));
        
        DefaultListModel<Player> playerToTeamListModel = new DefaultListModel<Player>();

        ResultSet teamPlayers = DatabaseManager.getInstance().getTeamPlayers(thisTeam.getID());
        try {
            while(teamPlayers.next()){
                Player p = new Player(teamPlayers.getInt("players.id"), teamPlayers.getString("players.nom"), teamPlayers.getString("players.age"), teamPlayers.getBoolean("players.sexe"), teamPlayers.getString("players.tel"), teamPlayers.getString("players.mail"));
                newTeamPlayerComp.add(p);
                playerToTeamListModel.addElement(p);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        playerToTeamList.setSelectedIndex(0);
        
        playerToTeamList.setModel(playerToTeamListModel);

        ResultSet clubPlayersRS = DatabaseManager.getInstance().getClubPlayersNotInTeam(thisClub.getID(), thisTeam.getID());
        try {
            while(clubPlayersRS.next()){
                clubPlayersRS.getInt("players.id");
                if(!clubPlayersRS.wasNull())
                {
                    playerBox.addItem(new Player(clubPlayersRS.getInt("players.id"), clubPlayersRS.getString("players.nom"), clubPlayersRS.getString("players.age"), clubPlayersRS.getBoolean("players.sexe"), clubPlayersRS.getString("players.tel"), clubPlayersRS.getString("players.mail")));
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
        playerBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if(value instanceof Player){
                    Player p = (Player) value;
                    setText(p.getNom());
                }
                if(value == null){
                    setText("Vide");
                }
                return this;
            }
        });
        playerToTeamList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(JLabel.CENTER);
                if(value instanceof Player){
                    Player p = (Player) value;
                    setText(p.getNom());
                }
                return this;
            }
        });
        Button addPlayerToTeamButton = new Button("Ajouter");
        Button removePlayerFromTeamButton = new Button("Retirer");
        Button setCapitaine = new Button("Capitaine");

        addPlayerToTeamButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(playerBox.getItemCount()<1))
                {
                    newTeamPlayerComp.add((Player)playerBox.getSelectedItem());
                    playerBox.removeItem((playerBox.getSelectedItem()));
                    playerToTeamListModel.clear();
                    for(Player p:newTeamPlayerComp){
                        playerToTeamListModel.addElement(p);
                    }
                    playerToTeamList.setSelectedIndex(0);
                }
            }
        }
        );
        removePlayerFromTeamButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(!(playerToTeamListModel.getSize()<1))
                {
                    newTeamPlayerComp.remove(playerToTeamList.getSelectedValue());
                    playerBox.addItem((playerToTeamList.getSelectedValue()));
                    if(thisTeam.getCapitaineID()!=0 && playerToTeamList.getSelectedValue().getID()==thisTeam.getCapitaineID()){
                        capitaine.setText("Capitaine : aucun");
                        DatabaseManager.getInstance().deleteCapitaine(thisTeam.getID());
                    }
                    playerToTeamListModel.clear();
                    for(Player p:newTeamPlayerComp){
                        playerToTeamListModel.addElement(p);
                    }
                    playerToTeamList.setSelectedIndex(0);
                }
            }
        }
        );
        setCapitaine.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(playerToTeamListModel.getSize()>1 && playerToTeamList.getSelectedValue()!=null && thisTeam.getCapitaineID()!=playerToTeamList.getSelectedValue().getID())
                {
                    DatabaseManager.getInstance().setTeamCapitaine(thisTeam.getID(), playerToTeamList.getSelectedValue().getID());
                    capitaine.setText("Capitaine : " + playerToTeamList.getSelectedValue().getNom());
                }
                else if(thisTeam.getCapitaineID()==playerToTeamList.getSelectedValue().getID()){
                    DatabaseManager.getInstance().deleteCapitaine(thisTeam.getID()); 
                    capitaine.setText("Capitaine : aucun");
                }
                updateLists();
                updateSecondaryLists();
            }
        }
        );

        nomSaisie.getDocument().addDocumentListener(new DelayedDocumentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTeamNameAvailable(nomSaisie, nomDispo);
            }
        }));

        nomSaisie.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateWC(nomSaisie, nomWC, 27);
            }
    
            @Override
            public void changedUpdate(DocumentEvent arg0) {
                updateWC(nomSaisie, nomWC, 27);
            }
        });

        Button enregistrer = new Button("Enregistrer");
        enregistrer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                String newTeamName = nomSaisie.getText();
                
                if(!newTeamName.isBlank() && !newTeamName.equals(thisTeam.getNom()))
                {
                    if(nomSaisie.getText().length()<2 || nomSaisie.getText().length()>27){
                        ChildWindow denied = new ChildWindow(400, 80);
                        JTextPane deniedText = new JTextPane();
                        Document deniedTextDoc = deniedText.getStyledDocument();
                        try {
                            deniedTextDoc.insertString(0, "Le nom de votre équipe doit comporter entre 3 et 27 caractères.", null);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        deniedText.setEditable(false);
                        deniedText.setOpaque(false);
                        denied.add(deniedText);
                        denied.setVisible(true);
                    }
                    else{
                        if(!DatabaseManager.getInstance().isTeamNameAvailable(newTeamName) && !newTeamName.equalsIgnoreCase(thisTeam.getNom())){
                            ChildWindow denied = new ChildWindow(400, 80);
                            JTextPane deniedText = new JTextPane();
                            Document deniedTextDoc = deniedText.getStyledDocument();
                            try {
                                deniedTextDoc.insertString(0, "Il existe déjà une équipe avec ce nom.", null);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            deniedText.setEditable(false);
                            deniedText.setOpaque(false);
                            denied.add(deniedText);
                            denied.setVisible(true);
                        }
                        else{
                            DatabaseManager.getInstance().setTeamName(thisTeam.getID(), newTeamName);
                        }
                    }
                }
                DatabaseManager.getInstance().setTeamPlayers(thisTeam.getID(), newTeamPlayerComp);
                
                thisTeam.setAvailability();
                
                ChildWindow confirmed = new ChildWindow(400, 80);
                JTextPane confirmedText = new JTextPane();
                Document confirmedTextDoc = confirmedText.getStyledDocument();
                try {
                    confirmedTextDoc.insertString(0, "Modifications à l'équipe " + thisTeam.getNom() + " enregistrées", null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                confirmedText.setEditable(false);
                confirmedText.setOpaque(false);
                confirmed.add(confirmedText);
                confirmed.setVisible(true);
                updateLists();
                updateDisplayArea();
                updateSecondaryLists();
            }
        }
        );
        
        confirmPanel.add(enregistrer);
        nomSaisie.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        nomSaisie.setLineWrap(true);
        nomSaisie.setWrapStyleWord(true);
        nomPanel.add(nomLabel);
        nomPanel.add(nomSaisie);
        nomPanel.add(nomWC);
        nomPanel.add(nomDispo);
        buttonPanel.add(addPlayerToTeamButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(removePlayerFromTeamButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        nomPanel.setOpaque(false);
        playerPanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        capitainePanel.setOpaque(false);
        confirmPanel.setOpaque(false);

        buttonPanel.add(setCapitaine);
        playerPanel.add(playerLabel);
        playerPanel.add(playerBox);
        playerPanel.add(buttonPanel);
        JScrollPane playerToTeamListScroll = new JScrollPane(playerToTeamList);
        playerToTeamListScroll.setOpaque(false);
        playerToTeamListScroll.getViewport().setOpaque(false);
        playerPanel.add(playerToTeamListScroll);
        playerToTeamList.setPreferredSize(new Dimension(200,300));
        
        content.add(nomPanel);
        content.add(playerPanel);
        content.add(capitainePanel);
        content.add(confirmPanel);
        modTeamWindow.setVisible(true);
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static int getWrappedLines(JTextArea component)
	{
		javax.swing.text.View view = component.getUI().getRootView(component).getView(0);
		int preferredHeight = (int)view.getPreferredSpan(javax.swing.text.View.Y_AXIS);
		int lineHeight = component.getFontMetrics( component.getFont() ).getHeight();
		return preferredHeight / lineHeight;
	}
}