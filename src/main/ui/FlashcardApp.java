package ui;

import model.Set;
import model.SetCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// App that deals with UI of organization of Sets
public class FlashcardApp {
    private static final String END_WORD = "end";
    private static final String JSON_STORE = "./data/lists.json";
    private SetCollection program;
    private Scanner sc = new Scanner(System.in);
    private String action = "";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs Flashcard App and runs it
    public FlashcardApp() throws FileNotFoundException {
        program = new SetCollection();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        run();
    }

    // EFFECTS: Runs the Flashcard App
    private void run() {
        while (true) {
            chooseInput();
            if (action.equals("new")) {
                create();
            } else if (action.equals("delete")) {
                delete();
            } else if (action.equals("edit")) {
                edit();
            } else if (action.equals("view")) {
                view();
            } else if (action.equals("save")) {
                saveSets();
            } else if (action.equals("load")) {
                loadSets();
            } else if (action.equals(END_WORD)) {
                end();
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: asks the user for a chosen input
    private void chooseInput() {
        System.out.println("Choose an action from available inputs:");
        System.out.println("new, delete, edit, view, save, load, end");
        action = sc.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: creates a new Set in collection
    private void create() {
        System.out.println("Creating New Set...");
        System.out.println("Name of New Set:");
        String name = sc.nextLine();
        if (program.findIndex(name) != -1) {
            System.out.println("Sets may not have duplicate names.");
        } else {
            Set s = new Set(name);
            program.addSet(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a Set in collection
    private void delete() {
        System.out.println("Enter the name of Set you wish to delete:");
        String name = sc.nextLine();
        int index = program.findIndex(name);
        if (index == -1) {
            System.out.println("Invalid Name.");
        } else {
            program.deleteSet(index);
            System.out.println("Set named '" + name + "' deleted.");
        }
    }

    // MODIFIES: this, Set
    // EFFECTS: edits a Set in collection
    private void edit() {
        System.out.println("Enter the name of Set you wish to edit:");
        String name = sc.nextLine();
        int index = program.findIndex(name);
        if (index == -1) {
            System.out.println("Invalid Name.");
        } else {
            System.out.println("Opening Card Editor...");
            new SetEditor(program.getSetAt(index));
        }
    }

    // EFFECTS: prints the Sets in collection
    private void view() {
        System.out.println("--------------------");
        System.out.println("Your Sets:");
        ArrayList<Integer> l = program.getLengths();
        ArrayList<String> n = program.getNames();
        for (int i = 0; i < program.getSize(); i++) {
            System.out.println(n.get(i) + " - " + l.get(i) + " card(s)");
        }
        System.out.println("--------------------");
    }

    // EFFECTS: prints the end message
    private void end() {
        System.out.println("Shutting Down...");
    }

    // EFFECTS: saves Sets to file
    private void saveSets() {
        try {
            jsonWriter.open();
            jsonWriter.write(program);
            jsonWriter.close();
            System.out.println("Saved current Sets to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Sets from file
    private void loadSets() {
        try {
            program = jsonReader.read();
            System.out.println("Loaded in Sets from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
