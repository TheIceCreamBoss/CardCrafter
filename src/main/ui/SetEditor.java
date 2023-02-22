package ui;

import model.Card;
import model.Set;

import java.util.Scanner;

public class SetEditor {
    private static final String END_WORD = "end";
    private Set current;
    private Scanner sc = new Scanner(System.in);
    private String action = "";

    public SetEditor(Set s) {
        current = s;
        label:
        while (true) {
            chooseInput();
            switch (action) {
                case "new":
                    create();
                    break;
                case "delete":
                    delete();
                    break;
                case "edit":
                    edit();
                    break;
                case "view":
                    view();
                    break;
                case "shuffle":
                    shuffle();
                    break;
                case END_WORD:
                    end();
                    break label;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        }
    }

    private void chooseInput() {
        System.out.println("Choose an action from available inputs:");
        System.out.println("new, delete, edit, view, shuffle, end");
        action = sc.nextLine();
    }

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

    private void delete() {
        System.out.println("Current Set: ");
        view();

        System.out.println("Enter the index of Card you wish to delete:");
        int index = sc.nextInt();
        sc.nextLine();
        if (0 <= index && index <= current.getLength() - 1) {
            current.removeFromSet(current.getCardAt(index));
            System.out.println("Card Removed.");
        } else {
            System.out.println("Invalid Index.");
        }
        System.out.println("Edited Set: ");
        view();
    }

    private void edit() {
        System.out.println("Current Set: ");
        view();

        System.out.println("Enter the index of Card you wish to edit:");
        int index = sc.nextInt();
        sc.nextLine();
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

    private void view() {
        System.out.println("--------------------");
        System.out.println("Terms - Definitions");
        for (int i = 0; i < current.getLength(); i++) {
            System.out.println("[" + i + "] " + current.getCardAt(i).getTerm() + " - " + current.getCardAt(i).getDfn());
        }
        System.out.println("--------------------");
    }

    private void shuffle() {
        System.out.println("Shuffling Set...");
        current.shuffleSet();
        view();
    }

    private void end() {
        System.out.println("Exiting Card Editor...");
    }
}