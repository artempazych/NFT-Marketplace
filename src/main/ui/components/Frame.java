package ui.components;

import model.Event;
import model.EventLog;
import model.Marketplace;
import model.Nft;
import model.User;
import persistance.MarketplaceJsonReader;
import persistance.MarketplaceJsonWriter;
import persistance.UserJsonReader;
import persistance.UserJsonWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Class Frame is the class that describes the main frame of the project
public class Frame extends JFrame implements ActionListener {
    private JPanel menu;
    private JPanel content;
    private JPanel gridView;
    private JPanel header;
    private Marketplace marketplace;
    private User user;

    private JButton marketplaceButton;
    private JButton myGalleryButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton depositButton;
    private JButton withdrawButton;

    private JLabel balanceLabel;
    private JLabel title;

    private static final String USER_JSON_STORE = "./data/user.json";
    private static final String MARKETPLACE_JSON_STORE = "./data/marketplace.json";
    private UserJsonWriter userJsonWriter;
    private UserJsonReader userJsonReader;
    private MarketplaceJsonWriter marketplaceJsonWriter;
    private MarketplaceJsonReader marketplaceJsonReader;

    // MODIFIES: this
    // EFFECTS: constructs the main Frame
    public Frame() {
        init();
        addAllElements();
        initFrame();
        this.add(menu, BorderLayout.WEST);
        this.add(content, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: adds all core components to the Frame
    private void addAllElements() {
        header.add(title);
        content.add(header, BorderLayout.NORTH);
        content.add(gridView, BorderLayout.CENTER);
        menu.add(marketplaceButton);
        menu.add(myGalleryButton);
        menu.add(loadButton);
        menu.add(saveButton);
        menu.add(depositButton);
        menu.add(withdrawButton);
//        menu.add(balanceLabel);
    }

    // MODIFIES: this
    // EFFECTS: initializes all core components
    private void init() {
        initReaderWriter();
        initMenuJPanel();
        initContentJPanel();
        initHeaderJPanel();
        initGridViewJPanel();
        initMarketplace();
        initUser();
        setImageLogo();
        initTitle();
        initMarketplaceButton();
        initMyGalleryButton();
        initLoadButton();
        initSaveButton();
        initDepositButton();
        initWithdrawButton();
        initBalanceLabel();

        addPrintLogOnClose();

    }

    // EFFECTS: adds event listener to print the logs after UI is closed
    private void addPrintLogOnClose() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
    }

    // EFFECTS: prints the log list into console after executing a program
    public static void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString() + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the Frame element
    private void initFrame() {
        this.setTitle("NFT Marketplace");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setSize(900, 700);
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: initializes the BalanceLabel element
    private void initBalanceLabel() {
        balanceLabel = new JLabel("Balance: " + user.getBalance());
        balanceLabel.setHorizontalTextPosition(JLabel.CENTER);
        balanceLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: initializes the withdrawButton element
    private void initWithdrawButton() {
        withdrawButton = new JButton("Withdraw Money");
        withdrawButton.setFocusable(false);
        withdrawButton.addActionListener(this);
        withdrawButton.setBackground(new Color(50, 141, 168));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setBorder(new LineBorder(Color.WHITE));
        withdrawButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the depositButton element
    private void initDepositButton() {
        depositButton = new JButton("Deposit Money");
        depositButton.setFocusable(false);
        depositButton.addActionListener(this);
        depositButton.setBackground(new Color(50, 141, 168));
        depositButton.setForeground(Color.WHITE);
        depositButton.setBorder(new LineBorder(Color.WHITE));
        depositButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the saveButton element
    private void initSaveButton() {
        saveButton = new JButton("Save Data");
        saveButton.setFocusable(false);
        saveButton.addActionListener(this);
        saveButton.setBackground(new Color(50, 141, 168));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(new LineBorder(Color.WHITE));
    }

    // MODIFIES: this
    // EFFECTS: initializes the loadButton element
    private void initLoadButton() {
        loadButton = new JButton("Load Data");
        loadButton.setFocusable(false);
        loadButton.addActionListener(this);
        loadButton.setBackground(new Color(50, 141, 168));
        loadButton.setForeground(Color.WHITE);
        loadButton.setBorder(new LineBorder(Color.WHITE));
    }

    // MODIFIES: this
    // EFFECTS: initializes the myGalleryButton element
    private void initMyGalleryButton() {
        myGalleryButton = new JButton("My Gallery");
        myGalleryButton.setFocusable(false);
        myGalleryButton.addActionListener(this);
        myGalleryButton.setBackground(new Color(50, 141, 168));
        myGalleryButton.setForeground(Color.WHITE);
        myGalleryButton.setBorder(new LineBorder(Color.WHITE));
    }

    // MODIFIES: this
    // EFFECTS: initializes the marketplaceButton element
    private void initMarketplaceButton() {
        marketplaceButton = new JButton("Marketplace");
        marketplaceButton.setFocusable(false);
        marketplaceButton.addActionListener(this);
        marketplaceButton.setBackground(new Color(50, 141, 168));
        marketplaceButton.setForeground(Color.WHITE);
        marketplaceButton.setBorder(new LineBorder(Color.WHITE));
    }

    // MODIFIES: this
    // EFFECTS: initializes the title element
    private void initTitle() {
        title = new JLabel("Welcome back to NFT Marketplace!");
        title.setFont(new Font("Arial", Font.BOLD, 28));
    }

    // MODIFIES: this
    // EFFECTS: adds a logo to the Frame
    private void setImageLogo() {
        ImageIcon logo = new ImageIcon("src/main/ui/img/logo.png");
        this.setIconImage(logo.getImage());
    }

    // MODIFIES: this
    // EFFECTS: initializes the gridView element
    private void initGridViewJPanel() {
        gridView = new JPanel();
        gridView.setPreferredSize(new Dimension(100, 100));
        gridView.setBackground(Color.white);
        gridView.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    // MODIFIES: this
    // EFFECTS: initializes the header element
    private void initHeaderJPanel() {
        header = new JPanel();
        header.setPreferredSize(new Dimension(100, 50));
        header.setBackground(Color.white);
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
    }

    // MODIFIES: this
    // EFFECTS: initializes the content element
    private void initContentJPanel() {
        content = new JPanel();
        content.setPreferredSize(new Dimension(100, 100));
        content.setBackground(Color.white);
        content.setLayout(new BorderLayout());
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu element
    private void initMenuJPanel() {
        menu = new JPanel();
        menu.setPreferredSize(new Dimension(200, 100));
        menu.setBackground(Color.WHITE);
        menu.setLayout(new GridLayout(6, 1));
    }

    // MODIFIES: this
    // EFFECTS: initializes the json reader and writer for marketplace and user
    private void initReaderWriter() {
        userJsonWriter = new UserJsonWriter(USER_JSON_STORE);
        userJsonReader = new UserJsonReader(USER_JSON_STORE);
        marketplaceJsonWriter = new MarketplaceJsonWriter(MARKETPLACE_JSON_STORE);
        marketplaceJsonReader = new MarketplaceJsonReader(MARKETPLACE_JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes the Marketplace
    private void initMarketplace() {
        this.marketplace = new Marketplace();

        Nft nft1 = new Nft("0N1 #4111", "src/main/ui/img/nft1.jpg", 111.00);
        Nft nft2 = new Nft("0N1 #4112", "src/main/ui/img/nft2.jpg", 567.71);
        Nft nft3 = new Nft("0N1 #4113", "src/main/ui/img/nft3.jpg", 1010.01);
        Nft nft4 = new Nft("0N1 #4114", "src/main/ui/img/nft4.jpg", 131313.13);
        Nft nft5 = new Nft("0N1 #4115", "src/main/ui/img/nft5.jpg", 150.50);
        Nft nft6 = new Nft("0N1 #4116", "src/main/ui/img/nft6.jpg", 25000.00);

        this.marketplace.addNft(nft1);
        this.marketplace.addNft(nft2);
        this.marketplace.addNft(nft3);
        this.marketplace.addNft(nft4);
        this.marketplace.addNft(nft5);
        this.marketplace.addNft(nft6);
    }

    // MODIFIES: this
    // EFFECTS: initializes the User
    private void initUser() {
        this.user = new User();
        this.user.deposit(1000000);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.marketplaceButton) {
            modifyMarketplaceOnClick();
        } else if (e.getSource() == this.myGalleryButton) {
            modifyMyGalleryOnClick();
        } else if (e.getSource() == this.saveButton) {
            saveOnClick();
        } else if (e.getSource() == this.loadButton) {
            loadOnClick();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved state and shows the message
    private void loadOnClick() {
        loadPreviousState();
        JOptionPane.showMessageDialog(null,
                "Successfully loaded previously saved state of the NFT Marketplace",
                "Load Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: save the state and shows the message
    private void saveOnClick() {
        saveCurrentState();
        JOptionPane.showMessageDialog(null,
                             "Successfully saved current state of the NFT Marketplace",
                                "Save Confirmation",
                                    JOptionPane.INFORMATION_MESSAGE);
    }

    // MODIFIES: this
    // EFFECTS: modifies the gridView and title elements when myGalleryButton clicked
    private void modifyMyGalleryOnClick() {
        title.setText("My Gallery");
        this.gridView.removeAll();
        for (Nft nft: this.user.getGallery()) {
            this.gridView.add(new NftComponentMyGallery(this.marketplace, this.user, this.balanceLabel, nft));
        }
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: modifies the gridView and title elements when marketplaceButton clicked
    private void modifyMarketplaceOnClick() {
        title.setText("Marketplace");
        this.gridView.removeAll();
        for (Nft nft: this.marketplace.getNfts()) {
            this.gridView.add(new NftComponentMarketplace(this.marketplace, this.user, this.balanceLabel, nft));
        }
        this.revalidate();
        this.repaint();
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved state from files
    private void loadPreviousState() {
        loadMarketplace();
        loadUser();
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved Marketplace object from file
    private void loadMarketplace() {
        try {
            marketplace = marketplaceJsonReader.read();
            System.out.println("Loaded marketplace from " + MARKETPLACE_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + MARKETPLACE_JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved User object from file
    private void loadUser() {
        try {
            user = userJsonReader.read();
            System.out.println("Loaded user from " + USER_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + USER_JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: saves current state to files
    private void saveCurrentState() {
        saveMarketplace();
        saveUser();
    }

    // MODIFIES: this
    // EFFECTS: saves Marketplace object to file
    private void saveMarketplace() {
        try {
            marketplaceJsonWriter.open();
            marketplaceJsonWriter.write(marketplace);
            marketplaceJsonWriter.close();
            System.out.println("Saved marketplace to " + MARKETPLACE_JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + MARKETPLACE_JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: saves User object to file
    private void saveUser() {
        try {
            userJsonWriter.open();
            userJsonWriter.write(user);
            userJsonWriter.close();
            System.out.println("Saved user to " + USER_JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + USER_JSON_STORE);
        }
    }


}
