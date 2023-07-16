package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Marketplace class represents the marketplace of NFTs.
// It provides a list of NFTs available to purchase.
public class Marketplace {
    private ArrayList<Nft> nfts;

    // EFFECTS:
    public Marketplace() {
        nfts = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds nft to list of nfts if not already in the list
    public void addNft(Nft nft) {
        if (!nfts.contains(nft)) {
            nfts.add(nft);
            // todo: test Event
            EventLog.getInstance().logEvent(new Event("Nft " + nft.getName() + " added to Marketplace"));
        }
    }

    // MODIFIES: this
    // REQUIRES: removes nft from the list of nfts if nft is in the list
    public void removeNft(Nft nft) {
        // todo: test Event
        EventLog.getInstance().logEvent(new Event("Nft " + nft.getName() + " removed from Marketplace"));
        nfts.remove(nft);
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("nfts", nftsToJson());
        return json;
    }

    // EFFECTS: returns the array of nft as a JSON array
    private JSONArray nftsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Nft nft: nfts) {
            jsonArray.put(nft.toJson());
        }
        return jsonArray;
    }

    public ArrayList<Nft> getNfts() {
        return this.nfts;
    }
}
