package model;

public class Card {
    private String term;
    private String dfn;
    private int difficulty;

    // EFFECTS: creates a new card with a term and definition with a default difficulty of 0
    public Card(String term, String dfn) {
        this.term = term;
        this.dfn = dfn;
        difficulty = 0;
    }

    // MODIFIES: this
    // EFFECTS: sets term to the specified string
    public void editTerm(String term) {
        this.term = term;
    }

    // MODIFIES: this
    // EFFECTS: sets dfn to the specified string
    public void editDfn(String dfn) {
        this.dfn = dfn;
    }

    // MODIFIES: this
    // EFFECTS: sets difficulty to the specified integer
    public void editDifficulty(int d) {
        difficulty = d;
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
}
