package persistance;

import model.Marketplace;
import model.Nft;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Marketplace from JSON data stored in file
// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class MarketplaceJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public MarketplaceJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Marketplace from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Marketplace read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMarketplace(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Marketplace from JSON object and returns it
    private Marketplace parseMarketplace(JSONObject jsonObject) {
        Marketplace marketplace = new Marketplace();
        addNfts(marketplace, jsonObject);
        return marketplace;
    }

    // MODIFIES: marketplace
    // EFFECTS: parses nfts from JSON object and adds them to marketplace
    private void addNfts(Marketplace marketplace, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("nfts");
        for (Object json : jsonArray) {
            JSONObject nextNft = (JSONObject) json;
            addNft(marketplace, nextNft);
        }
    }

    // MODIFIES: marketplace
    // EFFECTS: parses Nft from JSON object and adds it to marketplace
    private void addNft(Marketplace marketplace, JSONObject jsonObject) {
        String filepath = jsonObject.getString("filepath");
        double price = jsonObject.getDouble("price");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        Nft nft = new Nft(name, filepath, price, description);
        marketplace.addNft(nft);
    }
}
