package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Random;
import java.util.ArrayList;

// represents a Flashcard Set with Cards
public class Set implements Writable {
    private ArrayList<Card> flashSet;
    private String name;

    // EFFECTS: constructs a new ArrayList flashSet of Cards with name n
    public Set(String n) {
        flashSet = new ArrayList<Card>();
        name = n;
    }

    // MODIFIES: this
    // EFFECTS: adds a new card to the last index of flashSet
    public void addToSet(Card c) {
        flashSet.add(c);
    }

    // REQUIRES: Card to be in the flashSet
    // MODIFIES: this
    // EFFECTS: removes the specified card from flashSet
    public void removeFromSet(Card c) {
        flashSet.remove(c);
    }

    // MODIFIES: this
    // EFFECTS: shuffles all the Cards in the flashSet randomly
    public void shuffleSet() {
        Random r = new Random();
        for (int i = this.getLength() - 1; i > 0; i--) {
            int j = r.nextInt(i);
            Card temp1 = flashSet.get(j);
            Card temp2 = flashSet.get(i);
            flashSet.set(i, temp1);
            flashSet.set(j, temp2);
        }
    }

    // REQUIRES: Card to be in the flashSet
    // MODIFIES: this
    // EFFECTS: edits one Card's term or definition
    public void editCard(Card c, String s, String text) {
        if (s.equals("t")) {
            c.editTerm(text);
        } else if (s.equals("d")) {
            c.editDfn(text);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets name to the specified string
    public void rename(String n) {
        name = n;
    }

    // EFFECTS: returns name
    public String getName() {
        return name;
    }

    // EFFECTS: returns length of flashSet
    public int getLength() {
        return flashSet.size();
    }

    // REQUIRES: 0 <= i && i < flashSet.size()
    // EFFECTS: returns Card at index i of flashSet
    public Card getCardAt(int i) {
        return flashSet.get(i);
    }

    // EFFECTS: returns Set as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cards", setToJson());
        return json;
    }

    // EFFECTS: returns Cards in this Set as a JSON array
    private JSONArray setToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Card c : flashSet) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }
}
