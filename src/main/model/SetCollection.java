package model;

import java.util.ArrayList;

public class SetCollection {
    private ArrayList<Set> myLists;

    public SetCollection() {
        myLists = new ArrayList<Set>();
    }

    public void addSet(Set s) {
        myLists.add(s);
    }

    public void deleteSet(int i) {
        myLists.remove(i);
    }

    public int findIndex(String name) {
        for (int i = 0; i < myLists.size(); i++) {
            if (myLists.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean editSet() {
        return false;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> rtn = new ArrayList<>();
        for (Set s : myLists) {
            rtn.add(s.getName());
        }
        return rtn;
    }

    public ArrayList<Integer> getLengths() {
        ArrayList<Integer> rtn = new ArrayList<>();
        for (Set s : myLists) {
            rtn.add(s.getLength());
        }
        return rtn;
    }

    public int getSize() {
        return myLists.size();
    }

    public Set getSetAt(int i) {
        return myLists.get(i);
    }
}
