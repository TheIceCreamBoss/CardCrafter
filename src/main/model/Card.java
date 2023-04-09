package model;

import org.json.JSONObject;
import persistence.Writable;

// represents a Card in Flashcard Set
public class Card implements Writable {
    private String term;
    private String dfn;
    private int difficulty;

    // EFFECTS: creates a new card with a term and definition with a default difficulty of 0
    public Card(String term, String dfn) {
        this.term = term;
        this.dfn = dfn;
        difficulty = 0;
        EventLog.getInstance().logEvent(new Event("New Card Constructed."));
    }

    // MODIFIES: this
    // EFFECTS: sets term to the specified string
    public void editTerm(String term) {
        this.term = term;
        EventLog.getInstance().logEvent(new Event("Card Term Edited to \"" + term + "\"."));
    }

    // MODIFIES: this
    // EFFECTS: sets dfn to the specified string
    public void editDfn(String dfn) {
        this.dfn = dfn;
        EventLog.getInstance().logEvent(new Event("Card Definition Edited to \"" + term + "\"."));
    }

    // MODIFIES: this
    // EFFECTS: sets difficulty to the specified integer
    public void editDifficulty(int d) {
        difficulty = d;
        EventLog.getInstance().logEvent(new Event("Card Difficulty Edited to \"" + term + "\"."));
    }

    // EFFECTS: returns term
    public String getTerm() {
        return term;
    }

    // EFFECTS: returns dfn
    public String getDfn() {
        return dfn;
    }

    // EFFECTS: returns difficulty
    public int getDifficulty() {
        return difficulty;
    }

    // EFFECTS: returns Card as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("term", term);
        json.put("dfn", dfn);
        json.put("diff", difficulty);
        return json;
    }
}
