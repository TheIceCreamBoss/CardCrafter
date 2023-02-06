package model;

import java.util.ArrayList;

public class Set {
    private ArrayList<Card> flashSet;

    public Set() {
        flashSet = new ArrayList<Card>();
    }

    public void addToSet(Card c) {
        flashSet.add(c);
    }

    public void removeFromSet(Card c) {
        flashSet.remove(c);
    }




}
