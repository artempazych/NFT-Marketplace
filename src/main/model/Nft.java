package model;

import org.json.JSONObject;

// Nft class represents a single NFT with
// name, price, filePath (the file location of the image), and description
public class Nft {

    private String name;
    private double price;
    private String filePath = "";
    private String description = "";

    // REQUIRES: length of the name >= 1 and <= 100,
    //           price > 0,
    //           length of description <= 200
    // EFFECTS: creates an instance of NFT class with initialized fields:
    //          - name
    //          - filePath
    //          - price
    public Nft(String name, String filePath, double price) {
        this.name = name;
        this.filePath = filePath;
        this.price = price;
    }

    // EFFECTS: creates an instance of NFT class with initialized fields:
    //          - name
    //          - filePath
    //          - price
    //          - description
    public Nft(String name, String filePath, double price, String description) {
        this.name = name;
        this.filePath = filePath;
        this.price = price;
        this.description = description;
    }


    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("price", this.price);
        json.put("filepath", this.filePath);
        json.put("description", this.description);
        return json;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String getFilePath() {
        return this.filePath;
    }


}
