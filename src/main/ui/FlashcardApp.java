package ui;

import model.Set;
import model.SetCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// App that deals with UI of organization of Sets
public class FlashcardApp extends JFrame {
    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private static final String JSON_STORE = "./data/lists.json";
    private ArrayList<JButton> menu = new ArrayList<>();
    private JPanel currentPanel;

    private SetCollection program;

    private Scanner sc = new Scanner(System.in);

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs Flashcard App and runs it
    public FlashcardApp() throws FileNotFoundException {
        super("Flashcard App");
        initializeWindow();
        initializeFields();
        menuPanel();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window of this Flashcard App
    private void initializeWindow() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new GridLayout(1,1));
        addButtons();
    }

    // MODIFIES: this
    // EFFECTS: adds the buttons into menu list
    private void addButtons() {
        JButton newButton = new JButton("New Set");
        JButton deleteButton = new JButton("Delete Set");
        JButton editButton = new JButton("Edit Set");
        JButton viewButton = new JButton("View Set(s)");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        menu.add(newButton);
        menu.add(deleteButton);
        menu.add(editButton);
        menu.add(viewButton);
        menu.add(saveButton);
        menu.add(loadButton);
    }

    // EFFECTS: returns a back button
    private JButton backButton() {
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnHome();
            }
        });
        return back;
    }

    // MODIFIES: this
    // EFFECTS: initializes the SetCollection and JSON writer/reader.
    private void initializeFields() {
        program = new SetCollection();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void menuPanel() {
        JPanel menuPanel = new JPanel();
        currentPanel = menuPanel;
        add(menuPanel);
        menuPanel.setLayout(new GridLayout(3,2, 30, 30));
        for (JButton b : menu) {
            menuPanel.add(b);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parseAction(b);
                }
            });
        }
        menuPanel.setVisible(true);
    }

    private void viewPanel() {
        JPanel viewPanel = new JPanel();
        currentPanel = viewPanel;
        add(viewPanel);
        viewPanel.setLayout(new GridLayout(0,2,50,50));
        ArrayList<Integer> l = program.getLengths();
        ArrayList<String> n = program.getNames();
        for (int i = 0; i < program.getSize(); i++) {
            JLabel name = new JLabel(n.get(i));
            JLabel length = new JLabel(l.get(i) + " cards");
            styleCard(name);
            styleCard(length);
            viewPanel.add(name);
            viewPanel.add(length);
        }
        viewPanel.add(backButton());
        viewPanel.setVisible(true);
    }

    private void createPanel() {
        JPanel createPanel = new JPanel();
        currentPanel = createPanel;
        add(createPanel);
        JTextField input = new JTextField(10);
        createPanel.add(input);

        JButton back = new JButton("Enter");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set s = new Set(input.getText());
                program.addSet(s);
                view();
            }
        });
        createPanel.add(back);
        createPanel.setVisible(true);
    }

    private void deletePanel() {
        JPanel deletePanel = new JPanel();
        currentPanel = deletePanel;
        add(deletePanel);
        deletePanel.setLayout(new GridLayout(0,1,50,50));
        for (String name : program.getNames()) {
            JButton n = new JButton(name);
            n.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    program.deleteSet(program.findIndex(name));
                    view();
                }
            });
            deletePanel.add(n);
        }
        deletePanel.add(backButton());
        deletePanel.setVisible(true);
    }

    // EFFECTS: calls the method corresponding to the button
    private void parseAction(JButton b) {
        if (b.getText().equals("New Set")) {
            create();
        } else if (b.getText().equals("Delete Set")) {
            delete();
        } else if (b.getText().equals("Edit Set")) {
            edit();
        } else if (b.getText().equals("View Set(s)")) {
            view();
        } else if (b.getText().equals("Save")) {
            saveSets();
        } else if (b.getText().equals("Load")) {
            loadSets();
        }
    }

    // MODIFIES: this
    // EFFECTS: returns to home menu panel
    private void returnHome() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        menuPanel();
    }

    // MODIFIES: lb
    // EFFECTS: styles the label with font size and background colour changes
    private void styleCard(JLabel lb) {
        lb.setOpaque(true);
        lb.setBackground(new Color(200,200,200));
    }

    // MODIFIES: this
    // EFFECTS: creates a new Set in collection
    private void create() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        createPanel();
    }

    // MODIFIES: this
    // EFFECTS: deletes a Set in collection
    private void delete() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        deletePanel();
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
        currentPanel.setVisible(false);
        remove(currentPanel);
        viewPanel();
    }

    // EFFECTS: saves Sets to file
    private void saveSets() {
        try {
            jsonWriter.open();
            jsonWriter.write(program);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this,"Saved current Sets to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,"Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Sets from file
    private void loadSets() {
        try {
            program = jsonReader.read();
            JOptionPane.showMessageDialog(this,"Loaded in Sets from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"Unable to read from file: " + JSON_STORE);
        }
    }
}
