package persistence;

import org.json.JSONObject;

// Interface for Writable Objects
// Code from JsonSerializationDemo in CPSC 210
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}