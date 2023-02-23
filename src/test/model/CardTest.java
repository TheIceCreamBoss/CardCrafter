package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;;

public class CardTest {
    Card c1;
    Card c2;
    Card c3;

    @BeforeEach
    public void setUp() {
        c1 = new Card("C1Term", "C1Definition");
        c2 = new Card("C2Term", "C2Definition");
        c3 = new Card("C3Term", "C3Definition");
    }

    @Test
    public void constructorTest() {
        assertEquals(c1.getTerm(), "C1Term");
        assertEquals(c1.getDfn(), "C1Definition");
        assertEquals(c1.getDifficulty(), 0);

        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "C2Definition");
        assertEquals(c2.getDifficulty(), 0);
    }

    @Test
    public void editTest() {
        c1.editTerm("NewC1Term");
        c2.editDfn("NewC2Definition");
        c3.editTerm("NewC3Term");
        c3.editDfn("NewC3Definition");
        c1.editDifficulty(2);
        c2.editDifficulty(3);

        assertEquals(c1.getTerm(), "NewC1Term");
        assertEquals(c1.getDfn(), "C1Definition");
        assertEquals(c1.getDifficulty(), 2);
        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "NewC2Definition");
        assertEquals(c2.getDifficulty(), 3);
        assertEquals(c3.getTerm(), "NewC3Term");
        assertEquals(c3.getDfn(), "NewC3Definition");
        assertEquals(c3.getDifficulty(), 0);
    }

    @Test
    public void doubleEditTest() {
        c1.editTerm("NewC1Term");
        c2.editDfn("NewC2Definition");
        c3.editTerm("NewC3Term");
        c3.editDfn("NewC3Definition");
        c1.editDifficulty(2);
        c2.editDifficulty(3);

        assertEquals(c1.getTerm(), "NewC1Term");
        assertEquals(c1.getDfn(), "C1Definition");
        assertEquals(c1.getDifficulty(), 2);
        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "NewC2Definition");
        assertEquals(c2.getDifficulty(), 3);
        assertEquals(c3.getTerm(), "NewC3Term");
        assertEquals(c3.getDfn(), "NewC3Definition");
        assertEquals(c3.getDifficulty(), 0);

        c1.editTerm("2NewC1Term");
        c2.editDfn("2NewC2Definition");
        c3.editTerm("2NewC3Term");
        c3.editDfn("2NewC3Definition");
        c1.editDifficulty(4);
        c2.editDifficulty(6);

        assertEquals(c1.getTerm(), "2NewC1Term");
        assertEquals(c1.getDfn(), "C1Definition");
        assertEquals(c1.getDifficulty(), 4);
        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "2NewC2Definition");
        assertEquals(c2.getDifficulty(), 6);
        assertEquals(c3.getTerm(), "2NewC3Term");
        assertEquals(c3.getDfn(), "2NewC3Definition");
        assertEquals(c3.getDifficulty(), 0);
    }
}
