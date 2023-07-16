package ui;

import model.Marketplace;
import model.Nft;
import model.User;
import persistance.MarketplaceJsonReader;
import persistance.MarketplaceJsonWriter;
import persistance.UserJsonReader;
import persistance.UserJsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// NftMarketPlaceApp is the application class
public class NftMarketPlaceApp {
    private Marketplace marketplace;
    private User user;
    private Nft nft1;
    private Nft nft2;
    private Nft nft3;
    private Nft nft4;
    private Nft nft5;
    private Scanner input;
    private static final String USER_JSON_STORE = "./data/user.json";
    private static final String MARKETPLACE_JSON_STORE = "./data/marketplace.json";
    private UserJsonWriter userJsonWriter;
    private UserJsonReader userJsonReader;
    private MarketplaceJsonWriter marketplaceJsonWriter;
    private MarketplaceJsonReader marketplaceJsonReader;


    // EFFECTS: runs the NftMarketPlaceApp application
    public NftMarketPlaceApp() throws FileNotFoundException {
        userJsonWriter = new UserJsonWriter(USER_JSON_STORE);
        userJsonReader = new UserJsonReader(USER_JSON_STORE);
        marketplaceJsonWriter = new MarketplaceJsonWriter(MARKETPLACE_JSON_STORE);
        marketplaceJsonReader = new MarketplaceJsonReader(MARKETPLACE_JSON_STORE);
        runMarketplace();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // source: this part was inspired by TallerApp provided as example
    private void runMarketplace() {
        boolean notQuit = true;
        int command;
        init();

        while (notQuit) {
            displayOptions();
            command = Integer.parseInt(input.nextLine());

            if (command == 6) {
                notQuit = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes Marketplace, Nfts, User and Input Scanner
    private void init() {
        this.marketplace = new Marketplace();
        this.user = new User();
        this.nft1 = new Nft("0N1 #4177", "filepath/placeholder", 997.71);
        this.nft2 = new Nft("Okay Apes #19571", "filepath/placeholder", 28.25);
        this.nft3 = new Nft("BoredApeBro# 4067", "filepath/placeholder", 28.25);
        this.nft4 = new Nft("Bored Ape Eighties #143", "filepath/placeholder", 852.15);
        this.nft5 = new Nft("Meta Selfie#887", "filepath/placeholder", 11.56);
        marketplaceInit();
        this.input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: Marketplace
    // EFFECTS: adds nfts to marketplace
    private void marketplaceInit() {
        marketplace.addNft(nft1);
        marketplace.addNft(nft2);
        marketplace.addNft(nft3);
        marketplace.addNft(nft4);
        marketplace.addNft(nft5);
    }

    // EFFECTS: prints out the list of options
    private void displayOptions() {
        System.out.print("[1] Marketplace ");
        System.out.print("[2] Balance ");
        System.out.print("[3] My Gallery ");
        System.out.print("[4] Save data to file ");
        System.out.print("[5] Load data from the file ");
        System.out.println("[6] Quit");
    }

    // EFFECTS: processes user input as a command
    // source: this part was inspired by TallerApp provided as example
    private void processCommand(int command) {
        if (command == 1) {
            processMarketplaceNfts();
        } else if (command == 2) {
            processBalance();
        } else if (command == 3) {
            processMyGallery();
        } else if (command == 4) {
            saveCurrentState();
        } else if (command == 5) {
            loadPreviousState();
        } else {
            System.out.println("Please enter a valid command");
        }
    }

    // EFFECTS: loads previously saved state of Marketplace and User to file
    // todo: test
    private void loadPreviousState() {
        loadMarketplace();
        loadUser();
    }


    // MODIFIES: this
    // EFFECTS: loads Marketplace object from the file
    // todo: test
    private void loadMarketplace() {
        try {
            marketplace = marketplaceJsonReader.read();
            System.out.println("Loaded marketplace from " + MARKETPLACE_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + MARKETPLACE_JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads User object from the file
    // todo: test
    private void loadUser() {
        try {
            user = userJsonReader.read();
            System.out.println("Loaded user from " + USER_JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + USER_JSON_STORE);
        }
    }

    // EFFECTS: saves current state of Marketplace and User to file
    // todo: test
    private void saveCurrentState() {
        saveMarketplace();
        saveUser();
    }

    // EFFECTS: saves the Marketplace to file
    // todo: test
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

    // EFFECTS: saves the User to file
    // todo: test
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

    // EFFECTS: processes user input when chosen the "My Gallery" option
    // source: this part was inspired by TallerApp provided as example
    private void processMyGallery() {
        boolean notQuit = true;
        int command;

        while (notQuit) {
            displayMyGalleryOptions();
            command = Integer.parseInt(input.nextLine());

            if (command == 3) {
                notQuit = false;
            } else {
                processMyGalleryCommand(command);
            }
        }
    }

    // EFFECTS: displays a list of option when chosen "My Gallery"
    private void displayMyGalleryOptions() {
        System.out.print("[1] View My Gallery ");
        System.out.print("[2] Sell NFT ");
        System.out.println("[3] Go back");
    }

    // EFFECTS: processes user input as a command
    // source: this part was inspired by TallerApp provided as example
    private void processMyGalleryCommand(int command) {
        if (command == 1) {
            showMyGallery();
        } else if (command == 2) {
            sellNftToMarketplace();
        } else {
            System.out.println("Please enter a valid command");
        }
    }

    // MODIFIES: User, Marketplace
    // EFFECTS: - removes the user's NFT from his gallery and returns it to marketplace
    //          - increases the balance by the price of NFT
    private void sellNftToMarketplace() {
        System.out.println("Enter the Index of NFT: ");
        int id = Integer.parseInt(input.nextLine());
        id--;
        boolean result = user.sellNFT(user.getGallery().get(id), marketplace);
        if (result) {
            System.out.println("Successfully sold an NFT!");
        } else {
            System.out.println("Something went wrong while attempting to sell NFT.");
        }
    }

    // EFFECTS: prints out the list of NFTs in user's gallery
    private void showMyGallery() {
        if (user.getGallery().size() == 0) {
            System.out.println("There are no NFTs in your gallery yet");
        }
        List<Nft> nfts = user.getGallery();
        for (int i = 0; i < nfts.size(); i++) {
            int id = i + 1;
            System.out.println("Index: [" + id + "]");
            System.out.println("Name: " + nfts.get(i).getName());
            System.out.println("Price: " + nfts.get(i).getPrice() + " USDT");
            if (nfts.get(i).getDescription().length() == 0) {
                System.out.println("No Description");
            } else {
                System.out.println("Description: \n" + nfts.get(i).getDescription());
            }
            System.out.println("<---------->");
            System.out.println();
        }
    }

    // EFFECTS: processes user input when chosen the "Marketplace" option
    // source: this part was inspired by TallerApp provided as example
    private void processMarketplaceNfts() {
        boolean notQuit = true;
        int command;

        while (notQuit) {
            displayMarketplaceNftsOptions();
            command = Integer.parseInt(input.nextLine());

            if (command == 3) {
                notQuit = false;
            } else {
                processMarketplaceNftsCommand(command);
            }
        }
    }

    // EFFECTS: displays a list of option when chosen "Marketplace"
    private void displayMarketplaceNftsOptions() {
        System.out.print("[1] View NFTs ");
        System.out.print("[2] Buy NFT ");
        System.out.println("[3] Go back");
    }

    // EFFECTS: process user's input as a command
    // source: this part was inspired by TallerApp provided as example
    private void processMarketplaceNftsCommand(int command) {
        if (command == 1) {
            showMarketplaceNfts();
        } else if (command == 2) {
            purchaseNft();
        } else {
            System.out.println("Please enter a valid command");
        }
    }

    // MODIFIES: User, Marketplace
    // EFFECTS: if sufficient amount of money:
    //          - adds NFT to user's gallery
    //          - removes NFT from the Marketplace
    //          - decreases the balance by the price of NFT
    private void purchaseNft() {
        System.out.println("Enter the Index of NFT: ");
        int id = Integer.parseInt(input.nextLine());
        id--;
        boolean result = user.buyNFT(marketplace.getNfts().get(id), marketplace);
        if (result) {
            System.out.println("Successfully purchased an NFT!");
        } else {
            System.out.println("Something went wrong while attempting to buy NFT.");
        }
    }

    // EFFECTS: prints out the list of NFTs on the Marketplace available to purchase
    private void showMarketplaceNfts() {
        if (user.getGallery().size() == 0) {
            System.out.println("There are no NFTs in marketplace available");
        }
        List<Nft> nfts = marketplace.getNfts();
        for (int i = 0; i < nfts.size(); i++) {
            int id = i + 1;
            System.out.println("Index: [" + id + "]");
            System.out.println("Name: " + nfts.get(i).getName());
            System.out.println("Price: " + nfts.get(i).getPrice() + " USDT");
            if (nfts.get(i).getDescription().length() == 0) {
                System.out.println("No Description");
            } else {
                System.out.println("Description: \n" + nfts.get(i).getDescription());
            }
            System.out.println("<---------->");
            System.out.println();
        }
    }

    // EFFECTS: processes user input when chosen the "Balance" option
    // source: this part was inspired by TallerApp provided as example
    private void processBalance() {
        boolean notQuit = true;
        int command;

        while (notQuit) {
            displayBalanceOptions();
            command = Integer.parseInt(input.nextLine());

            if (command == 4) {
                notQuit = false;
            } else {
                processBalanceCommand(command);
            }
        }
    }

    // EFFECTS: displays a list of options when "Balance" was chosen
    private void displayBalanceOptions() {
        System.out.print("[1] Withdraw money ");
        System.out.print("[2] Deposit money ");
        System.out.print("[3] View balance ");
        System.out.println("[4] Go back");
    }

    // EFFECTS: processes user's input as a command
    // source: this part was inspired by TallerApp provided as example
    private void processBalanceCommand(int command) {
        if (command == 1) {
            doWithdrawal();
        } else if (command == 2) {
            doDeposit();
        } else if (command == 3) {
            showBalance();
        } else {
            System.out.println("Please enter a valid command");
        }
    }

    // EFFECTS: prints out the user's balance
    private void showBalance() {
        System.out.println("Your balance is: " + user.getBalance() + " USDT");
    }

    // MODIFIES: User
    // EFFECTS: increases the user's balance by the amount that user inputs
    private void doDeposit() {
        System.out.println("Enter the amount of money to deposit: ");
        int amount = Integer.parseInt(input.nextLine());
        user.deposit(amount);
    }

    // MODIFIES: User
    // EFFECTS: decreases the user's balance by the amount that user inputs
    private void doWithdrawal() {
        System.out.println("Enter the amount of money to withdraw: ");
        int amount = Integer.parseInt(input.nextLine());
        user.withdraw(amount);
    }





}


