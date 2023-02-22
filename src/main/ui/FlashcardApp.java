package ui;

import model.Set;
import model.SetCollection;

import java.util.ArrayList;
import java.util.Scanner;

public class FlashcardApp {
    private static final String END_WORD = "end";
    private SetCollection program = new SetCollection();
    private Scanner sc = new Scanner(System.in);
    private String action = "";

    public FlashcardApp() {
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
        System.out.println("new, delete, edit, view, end");
        action = sc.nextLine();
    }

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

    private void end() {
        System.out.println("Shutting Down...");
    }
}
