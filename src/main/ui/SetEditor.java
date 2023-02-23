package ui;

import model.Card;
import model.Set;

import java.util.Scanner;

public class SetEditor {
    private static final String END_WORD = "end";
    private Set current;
    private Scanner sc = new Scanner(System.in);
    private String action = "";

    // MODIFIES: this
    // EFFECTS: Runs the SetEditor
    public SetEditor(Set s) {
        current = s;
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
            } else if (action.equals("shuffle")) {
                shuffle();
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
        System.out.println("new, delete, edit, view, shuffle, end");
        action = sc.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: creates a new Card in Set
    private void create() {
        System.out.println("Creating New Card...");
        System.out.println("Term of New Card:");
        String term = sc.nextLine();
        System.out.println("Definition of New Card:");
        String dfn = sc.nextLine();
        Card c = new Card(term, dfn);
        current.addToSet(c);
        System.out.println("Card Added.");
    }

    // MODIFIES: this
    // EFFECTS: deletes a Card in Set
    private void delete() {
        System.out.println("Current Set: ");
        view();

        System.out.println("Enter the index of Card you wish to delete:");
        int index = 0;
        try {
            index = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            index = -1;
        }

        if (0 <= index && index <= current.getLength() - 1) {
            current.removeFromSet(current.getCardAt(index));
            System.out.println("Card Removed.");
        } else {
            System.out.println("Invalid Index.");
        }
        System.out.println("Edited Set: ");
        view();
    }

    // MODIFIES: this
    // EFFECTS: edits a Card in Set
    @SuppressWarnings("methodlength")
    private void edit() {
        System.out.println("Current Set: ");
        view();
        System.out.println("Enter the index of Card you wish to edit:");
        int index = 0;
        try {
            index = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            index = -1;
        }
        if (0 <= index && index <= current.getLength() - 1) {
            System.out.println("Editing the Term or Definition?");
            String str = sc.nextLine();
            if (str.equals("t") || str.equals("d")) {
                System.out.println("Please enter desired text:");
                String text = sc.nextLine();
                current.editCard(current.getCardAt(index), str, text);
            } else {
                System.out.println("Invalid Input.");
            }
        } else {
            System.out.println("Invalid Index.");
        }
        System.out.println("Edited Set: ");
        view();
    }

    // EFFECTS: prints all Cards in Set
    private void view() {
        System.out.println("--------------------");
        System.out.println("Terms - Definitions");
        for (int i = 0; i < current.getLength(); i++) {
            System.out.println("[" + i + "] " + current.getCardAt(i).getTerm() + " - " + current.getCardAt(i).getDfn());
        }
        System.out.println("--------------------");
    }

    // MODIFIES: this
    // EFFECTS: shuffles the indexes of Cards in Set
    private void shuffle() {
        System.out.println("Shuffling Set...");
        current.shuffleSet();
        view();
    }

    // EFFECTS: prints the end message
    private void end() {
        System.out.println("Exiting Card Editor...");
    }
}