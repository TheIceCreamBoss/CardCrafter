package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;;

public class SetTest {
    Set s1;
    Set s2;
    Set s3;

    @BeforeEach
    public void setUp() {
        s1 = new Set("S1Name");
        s2 = new Set("S2Name");
        s3 = new Set("S3Name");
    }

    @Test
    public void constructorTest() {
        assertEquals(s1.getLength(), 0);
        assertEquals(s2.getLength(), 0);
        assertEquals(s1.getName(), "S1Name");
        assertEquals(s2.getName(), "S2Name");
    }

    @Test
    public void addTest() {
        Card c = new Card("term", "definition");
        s1.addToSet(c);
        assertEquals(s1.getLength(), 1);
        s1.addToSet(c);
        assertEquals(s1.getLength(), 2);
        assertEquals(s1.getCardAt(0), c);
        assertEquals(s1.getCardAt(1), c);
    }

    @Test
    public void removeTest() {
        Card c1 = new Card("term1", "definition1");
        Card c2 = new Card("term2", "definition2");

        s1.addToSet(c1);
        s1.addToSet(c2);
        s2.addToSet(c1);
        s2.addToSet(c1);
        s3.addToSet(c1);
        s3.addToSet(c2);
        s3.addToSet(c1);

        s1.removeFromSet(c1);
        assertEquals(s1.getLength(), 1);
        assertEquals(s1.getCardAt(0), c2);

        s2.removeFromSet(c1);
        assertEquals(s2.getLength(), 1);
        assertEquals(s2.getCardAt(0), c1);

        s3.removeFromSet(c2);
        assertEquals(s3.getLength(), 2);
        assertEquals(s3.getCardAt(0), c1);
        assertEquals(s3.getCardAt(1), c1);

        s3.removeFromSet(c1);
        assertEquals(s3.getLength(), 1);
        assertEquals(s3.getCardAt(0), c1);
    }

    @Test
    public void shuffleTest() {
        Card c1 = new Card("term1", "definition1");
        Card c2 = new Card("term2", "definition2");
        Card c3 = new Card("term3", "definition3");
        Card c4 = new Card("term4", "definition4");

        s1.addToSet(c1);
        s1.addToSet(c2);
        s1.addToSet(c3);
        s1.addToSet(c4);

        assertEquals(s1.getLength(), 4);
        s1.shuffleSet();
        assertEquals(s1.getLength(), 4);

        s1.removeFromSet(c1);
        s1.removeFromSet(c2);
        s1.removeFromSet(c3);
        s1.removeFromSet(c4);

        assertEquals(s1.getLength(), 0);
    }

    @Test
    public void renameTest() {
        s1.rename("S1NewName");
        assertEquals(s1.getName(), "S1NewName");
        assertEquals(s2.getName(), "S2Name");
        s1.rename("S1NewNewName");
        assertEquals(s1.getName(), "S1NewNewName");
    }
}
