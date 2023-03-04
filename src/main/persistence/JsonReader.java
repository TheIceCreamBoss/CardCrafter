package persistence;

import model.Card;
import model.Set;
import model.SetCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

// Represents a reader that reads workroom from JSON data stored in file
// Code from JsonSerializationDemo in CPSC 210
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads SetCollection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SetCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSets(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses SetCollection from JSON object and returns it
    private SetCollection parseSets(JSONObject jsonObject) {
        SetCollection sc = new SetCollection();
        addSets(sc, jsonObject);
        return sc;
    }

    // MODIFIES: sc
    // EFFECTS: adds Sets to sc
    private void addSets(SetCollection sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sets");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addSet(sc, nextThingy);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses Set from JSON object and adds it to SetCollection
    private void addSet(SetCollection sc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("cards");
        String name = jsonObject.getString("name");
        Set s = new Set(name);
        for (Object json : jsonArray) {
            JSONObject nextCard = (JSONObject) json;
            addCard(s, nextCard);
        }
        sc.addSet(s);
    }

    // MODIFIES: s
    // EFFECTS: parses Cards from JSON object and adds it to Set
    private void addCard(Set s, JSONObject jsonObject) {
        String term = jsonObject.getString("term");
        String dfn = jsonObject.getString("dfn");
        int diff = jsonObject.getInt("diff");
        Card c = new Card(term, dfn);
        c.editDifficulty(diff);
        s.addToSet(c);
    }
}
