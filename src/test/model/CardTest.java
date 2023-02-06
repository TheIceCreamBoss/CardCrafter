package model;

import org.junit.jupiter.api.*;
import static org.junit.Assert.*;

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
        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "C2Definition");
    }

    @Test
    public void editTest() {
        c1.editTerm("NewC1Term");
        c2.editDfn("NewC2Definition");
        c3.editTerm("NewC3Term");
        c3.editDfn("NewC3Definition");

        assertEquals(c1.getTerm(), "NewC1Term");
        assertEquals(c1.getDfn(), "C1Definition");
        assertEquals(c2.getTerm(), "C2Term");
        assertEquals(c2.getDfn(), "NewC2Definition");
        assertEquals(c3.getTerm(), "NewC3Term");
        assertEquals(c3.getDfn(), "NewC3Definition");
    }
}
