package model;

import java.util.Random;
import java.util.ArrayList;

public class Set {
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

    // MODIFIES: this
    // EFFECTS: edits one Card's term or definition
    public void editCard(int i, char s, String text) {

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



}
