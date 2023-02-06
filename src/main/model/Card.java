package model;

public class Card {
    private String term;
    private String dfn;
    private int difficulty;

    public Card(String term, String dfn) {
        this.term = term;
        this.dfn = dfn;
        difficulty = 0;
    }

    public void editTerm(String term) {
        this.term = term;
    }

    public void editDfn(String dfn) {
        this.dfn = dfn;
    }

    public String getTerm() {
        return term;
    }

    public String getDfn() {
        return dfn;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
