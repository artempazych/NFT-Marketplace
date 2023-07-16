package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

//
// User class represents the user's account where they can:
// - deposit and withdraw money in USDT
// - purchase and sell NFTs
// - view gallery of purchased NFTs
public class User {
    private static final double MINIMUM_DEPOSIT_AMOUNT = 100;

    private double balance;
    private ArrayList<Nft> gallery;

    // MODIFIES: this
    // EFFECTS: creates an instance of User class with 0 initial balance and empty list of nfts;
    public User() {
        this.balance = 0;
        this.gallery = new ArrayList<>();
    }

    // REQUIRES: amount > MINIMUM_DEPOSIT_AMOUNT
    // MODIFIES: this
    // EFFECTS: if amount > MINIMUM_DEPOSIT_AMOUNT:
    //             - increases the balance by the amount
    //             - returns true
    //          otherwise returns false
    public boolean deposit(double amount) {
        if (amount >= MINIMUM_DEPOSIT_AMOUNT) {
            this.balance += amount;
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: amount <= balance
    // MODIFIES: this
    // EFFECTS: if amount <= balance
    //              - decreases the balance by the amount
    //              - returns true
    //          otherwise returns false
    public boolean withdraw(double amount) {
        if (amount <= this.balance) {
            this.balance = this.balance - amount;
            return true;
        } else {
            return false;
        }
    }


    // REQUIRES: nft != null
    // MODIFIES: this, marketpalce
    // EFFECTS: if current balance is more then or equal to the price of NFT:
    //          - adds NFT to the gallery
    //          - subtract the NFT price from the balance
    //          - removes NFT from the Marketplace list
    //          - returns true
    //          otherwise returns false
    public boolean buyNFT(Nft nft, Marketplace marketplace) {
        if (this.balance >= nft.getPrice()) {
            this.addNft(nft);
            this.balance -= nft.getPrice();
            marketplace.removeNft(nft);
            return true;
        }
        return false;
    }


    // REQUIRES: nft != null
    // MODIFIES: this
    // EFFECTS: if NFT is in gallery
    //          - removes NFT to the gallery
    //          - adds the NFT price to the balance
    //          - adds NFT from the Marketplace list
    //          - returns true
    //          otherwise returns false
    public boolean sellNFT(Nft nft, Marketplace marketplace) {
        if (gallery.contains(nft)) {
            this.removeNft(nft);
            this.balance += nft.getPrice();
            marketplace.addNft(nft);
            return true;
        }
        return false;
    }


    // MODIFIES: this
    // EFFECTS: adds nft to list of nfts if not already in the list
    public void addNft(Nft nft) {
        if (!gallery.contains(nft)) {
            gallery.add(nft);
            // todo: test Event
            EventLog.getInstance().logEvent(new Event("Nft " + nft.getName()
                    + " has been purchased and added to the Gallery"));
        }
    }

    // MODIFIES: this
    // REQUIRES: removes nft from the list of nfts if nft is in the list
    public void removeNft(Nft nft) {
        gallery.remove(nft);
        // todo: test Event
        EventLog.getInstance().logEvent(new Event("Nft " + nft.getName()
                + " has been sold and removed from the Gallery"));
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", this.balance);
        json.put("gallery", galleryToJson());
        return json;
    }

    // EFFECTS: returns the array of nft as a JSON array
    private JSONArray galleryToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Nft nft: gallery) {
            jsonArray.put(nft.toJson());
        }
        return jsonArray;
    }

    public double getBalance() {
        return this.balance;
    }

    public ArrayList<Nft> getGallery() {
        return this.gallery;
    }



}
