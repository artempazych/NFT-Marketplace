package persistance;

import model.Nft;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads User from JSON data stored in file
// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class UserJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public UserJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads User from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses User from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        int balance = jsonObject.getInt("balance");
        User user = new User();
        user.deposit(balance);
        addNfts(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses gallery from JSON object and adds them to user
    private void addNfts(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("gallery");
        for (Object json : jsonArray) {
            JSONObject nextNft = (JSONObject) json;
            addNft(user, nextNft);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses Nft from JSON object and adds it to user
    private void addNft(User user, JSONObject jsonObject) {
        String filepath = jsonObject.getString("filepath");
        double price = jsonObject.getDouble("price");
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");

        Nft nft = new Nft(name, filepath, price, description);
        user.addNft(nft);
    }
}
