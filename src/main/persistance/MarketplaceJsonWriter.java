package persistance;

import model.Marketplace;
import model.User;
import org.json.JSONObject;

// Represents a writer that writes Marketplace to the JSON file
// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class MarketplaceJsonWriter extends JsonWriter {
    private static final int TAB = 4;

    // EFFECTS: constructs MarketplaceJsonWriter to write to destination file
    public MarketplaceJsonWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Marketplace to file
    public void write(Marketplace marketplace) {
        JSONObject json = marketplace.toJson();
        saveToFile(json.toString(TAB));
    }
}
