package ui;

import model.Card;
import model.Set;
import model.SetCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

// App that deals with UI of organization of Cards in a Set
public class SetEditor extends JFrame {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private ArrayList<JButton> menu = new ArrayList<>();
    private JPanel currentPanel;

    private Set current;
    private FlashcardApp editor;

    private Scanner sc = new Scanner(System.in);
    private String action = "";

    // MODIFIES: this
    // EFFECTS: Runs the SetEditor
    public SetEditor(Set s, FlashcardApp fa) {
        super("Flashcard App");
        current = s;
        editor = fa;
        initializeWindow();
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
        JButton newButton = new JButton("New Card");
        JButton deleteButton = new JButton("Delete Card");
        JButton editButton = new JButton("Edit Card");
        JButton viewButton = new JButton("View Card(s)");
        JButton shuffleButton = new JButton("Shuffle Card(s)");
        JButton exitButton = new JButton("Exit");
        menu.add(newButton);
        menu.add(deleteButton);
        menu.add(editButton);
        menu.add(viewButton);
        menu.add(shuffleButton);
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

    // MODIFIES: lb
    // EFFECTS: styles the label with font size and background colour changes
    private void styleCard(JLabel lb) {
        lb.setOpaque(true);
        lb.setBackground(new Color(200,200,200));
    }

    // MODIFIES: this
    // EFFECTS: returns to home menu panel
    private void returnHome() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        menuPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates a menu panel
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

    // EFFECTS: calls the method corresponding to the button
    private void parseAction(JButton b) {
        if (b.getText().equals("New Card")) {
            create();
        } else if (b.getText().equals("Delete Card")) {
            delete();
        } else if (b.getText().equals("Edit Card")) {
            edit();
        } else if (b.getText().equals("View Card(s)")) {
            view();
        } else if (b.getText().equals("Shuffle Card(s)")) {
            shuffle();
        } else if (b.getText().equals("Exit")) {
            end();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new set panel
    private void createPanel() {
        JPanel createPanel = new JPanel();
        createPanel.setLayout(new GridLayout(4, 1, 50, 50));
        currentPanel = createPanel;
        add(createPanel);
        JTextField term = new JTextField(10);
        JTextField dfn = new JTextField(10);
        createPanel.add(term);
        createPanel.add(dfn);

        JButton enter = new JButton("Enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.addToSet(new Card(term.getText(), dfn.getText()));
                JOptionPane.showMessageDialog(createPanel,"Card Added.");
                term.setText("");
                dfn.setText("");
            }
        });
        createPanel.add(enter);
        createPanel.add(backButton());
        createPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a deletion panel
    private void deletePanel() {
        JPanel deletePanel = new JPanel();
        currentPanel = deletePanel;
        add(deletePanel);
        deletePanel.setLayout(new GridLayout(0,1,50,50));
        for (int i = 0; i < current.getLength(); i++) {
            Card c = current.getCardAt(i);
            JButton n = new JButton(c.getTerm() + " - " + c.getDfn());
            n.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    current.removeFromSet(c);
                    view();
                }
            });
            deletePanel.add(n);
        }
        deletePanel.add(backButton());
        deletePanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a view panel
    private void viewPanel() {
        JPanel viewPanel = new JPanel();
        currentPanel = viewPanel;
        add(viewPanel);
        viewPanel.setLayout(new GridLayout(0,2,50,50));
        for (int i = 0; i < current.getLength(); i++) {
            Card c = current.getCardAt(i);
            JLabel term = new JLabel(c.getTerm());
            JLabel dfn = new JLabel(c.getDfn());
            styleCard(term);
            styleCard(dfn);
            viewPanel.add(term);
            viewPanel.add(dfn);
        }
        viewPanel.add(backButton());
        viewPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates an edit panel
    @SuppressWarnings("methodlength")
    private void editPanel() {
        JPanel editPanel = new JPanel();
        currentPanel = editPanel;
        add(editPanel);
        editPanel.setLayout(new GridLayout(0,2,50,50));
        for (int i = 0; i < current.getLength(); i++) {
            Card c = current.getCardAt(i);
            JButton term = new JButton(c.getTerm());
            JButton dfn = new JButton(c.getDfn());
            term.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getText(c, "t");
                }
            });
            dfn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getText(c, "d");
                }
            });
            editPanel.add(term);
            editPanel.add(dfn);
        }
        editPanel.add(backButton());
        editPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: generates a text box for the user to enter text, and edits specified card
    private void getText(Card c, String s) {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2, 1, 50, 50));
        currentPanel.setVisible(false);
        remove(currentPanel);
        currentPanel = textPanel;
        add(textPanel);
        JTextField text = new JTextField(10);
        textPanel.add(text);
        JButton enter = new JButton("Enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current.editCard(c, s, text.getText());
                view();
            }
        });
        textPanel.add(enter);
    }


    // MODIFIES: this
    // EFFECTS: creates a new Card in Set
    private void create() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        createPanel();
    }

    // MODIFIES: this
    // EFFECTS: deletes a Card in Set
    private void delete() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        deletePanel();
    }

    // MODIFIES: this, Card
    // EFFECTS: edits a Card in Set
    private void edit() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        editPanel();
    }

    // EFFECTS: prints all Cards in Set
    private void view() {
        currentPanel.setVisible(false);
        remove(currentPanel);
        viewPanel();
    }

    // MODIFIES: this
    // EFFECTS: shuffles the indexes of Cards in Set
    private void shuffle() {
        current.shuffleSet();
        JOptionPane.showMessageDialog(this,"Set Shuffled.");
        view();
    }

    // EFFECTS: disposes this JFrame and returns to main app
    private void end() {
        dispose();
        editor.showWindow();
    }
}