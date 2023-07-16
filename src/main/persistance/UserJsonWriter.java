package persistance;

import model.User;
import org.json.JSONObject;

// Represents a writer that writes User to the JSON file
// source of the code: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class UserJsonWriter extends JsonWriter {
    private static final int TAB = 4;

    // EFFECTS: constructs UserJsonWriter to write to destination file
    public UserJsonWriter(String destination) {
        super(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of User to file
    public void write(User user) {
        JSONObject json = user.toJson();
        saveToFile(json.toString(TAB));
    }
}
