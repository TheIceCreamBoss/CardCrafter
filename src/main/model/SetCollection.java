package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// represents a Collections of Sets
public class SetCollection implements Writable {
    private ArrayList<Set> myLists;

    // EFFECTS: constructs a new ArrayList of Sets of Cards
    public SetCollection() {
        myLists = new ArrayList<Set>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new Set to myLists
    public void addSet(Set s) {
        myLists.add(s);
    }

    // REQUIRES: 0 <= i && i < myLists.size()
    // MODIFIES: this
    // EFFECTS: adds a new Set to myLists
    public void deleteSet(int i) {
        myLists.remove(i);
    }

    // EFFECTS: returns index of Set with specified name, or -1 if not in the list
    public int findIndex(String name) {
        for (int i = 0; i < myLists.size(); i++) {
            if (myLists.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    // EFFECTS: returns ArrayList of Strings of all the Names of Sets in collection
    public ArrayList<String> getNames() {
        ArrayList<String> rtn = new ArrayList<>();
        for (Set s : myLists) {
            rtn.add(s.getName());
        }
        return rtn;
    }

    // EFFECTS: returns ArrayList of Integers of all the Lengths of Sets in collection
    public ArrayList<Integer> getLengths() {
        ArrayList<Integer> rtn = new ArrayList<>();
        for (Set s : myLists) {
            rtn.add(s.getLength());
        }
        return rtn;
    }

    // EFFECTS: returns length of collection
    public int getSize() {
        return myLists.size();
    }

    // REQUIRES: 0 <= i && i < myLists.size()
    // EFFECTS: return at index of i in collection
    public Set getSetAt(int i) {
        return myLists.get(i);
    }

    // EFFECTS: returns SetCollection as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sets", setsToJson());
        return json;
    }

    // EFFECTS: returns Sets in this SetCollection as a JSON array
    private JSONArray setsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Set s : myLists) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}
