package ui;

import model.EventLog;
import model.Event;
import model.Set;
import model.SetCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// App that deals with UI of organization of Sets
public class FlashcardApp extends JFrame {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private ArrayList<JButton> menu = new ArrayList<>();
    private JPanel currentPanel;
    private JPanel menuPanel;
    private FlashcardApp fa = this;

    private SetCollection program;

    private static final String JSON_STORE = "./data/lists.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Constructs Flashcard App and runs it
    public FlashcardApp() throws FileNotFoundException {
        super("Flashcard App");
        initializeWindow();
        initializeFields();
        initializeMenu();
        menuPanel();
    }

    // MODIFIES: this
    // EFFECTS: draws the JFrame window of this Flashcard App
    private void initializeWindow() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(fa,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    printLog(EventLog.getInstance());
                } else if (result == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
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
        JButton creditButton = new JButton("Credits");
        JButton exitButton = new JButton("Exit");
        menu.add(newButton);
        menu.add(deleteButton);
        menu.add(editButton);
        menu.add(viewButton);
        menu.add(saveButton);
        menu.add(loadButton);
        menu.add(creditButton);
        menu.add(exitButton);
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

    // MODIFIES: this
    // EFFECTS: initializes the main menu panel
    private void initializeMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4,2, 30, 30));
        for (JButton b : menu) {
            menuPanel.add(b);
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parseAction(b);
                }
            });
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the menu panel
    private void menuPanel() {
        currentPanel = menuPanel;
        add(menuPanel);
        menuPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a view panel
    private void viewPanel() {
        JPanel viewPanel = new JPanel();
        currentPanel = viewPanel;
        add(viewPanel);
        viewPanel.setLayout(new GridLayout(0,2,30,30));
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

    // MODIFIES: this
    // EFFECTS: creates a new set panel
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
                if (program.findIndex(input.getText()) != -1) {
                    JOptionPane.showMessageDialog(createPanel,"Sets may not have duplicate names.");
                } else {
                    Set s = new Set(input.getText());
                    program.addSet(s);
                    view();
                }
            }
        });
        createPanel.add(back);
        createPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a deletion panel
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

    // MODIFIES: this
    // EFFECTS: creates an edit panel
    private void editPanel() {
        JPanel editPanel = new JPanel();
        currentPanel = editPanel;
        add(editPanel);
        editPanel.setLayout(new GridLayout(0,1,50,50));
        for (String name : program.getNames()) {
            JButton n = new JButton(name);
            n.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    new SetEditor(program.getSetAt(program.findIndex(name)), fa);
                }
            });
            editPanel.add(n);
        }
        editPanel.add(backButton());
        editPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a credits panel
    private void creditPanel() {
        JPanel creditPanel = new JPanel();
        currentPanel = creditPanel;
        add(creditPanel);
        creditPanel.setLayout(new GridLayout(0,1,30,30));
        ImageIcon image = new ImageIcon("./data/images/creator.png");
        creditPanel.add(new JLabel(image));
        creditPanel.add(backButton());
        creditPanel.setVisible(true);
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
        } else if (b.getText().equals("Credits")) {
            credit();
        } else if (b.getText().equals("Exit")) {
            exit();
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
        currentPanel.setVisible(false);
        remove(currentPanel);
        editPanel();
    }

    // EFFECTS: prints the Sets in collection
    private void view() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        viewPanel();
    }

    // EFFECTS: shows the credit screen
    private void credit() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        creditPanel();
    }

    // EFFECTS: shows the user a prompt to exit the program
    private void exit() {
        int result = JOptionPane.showConfirmDialog(fa,
                "Do you want to Exit ?", "Exit Confirmation : ",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
            printLog(EventLog.getInstance());
        } else if (result == JOptionPane.NO_OPTION) {
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            returnHome();
        }
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
            JOptionPane.showMessageDialog(null,"Loaded in Sets from " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: shows the JFrame Window and sets the panel to menu panel
    public void showWindow() {
        setVisible(true);
        returnHome();
    }

    // EFFECTS: prints out the list of events
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }
}
