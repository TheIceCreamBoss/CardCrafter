package persistence;

import model.Card;
import model.Set;
import model.SetCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;


import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            SetCollection sc = new SetCollection();
            JsonWriter writer = new JsonWriter("./data/my\nillegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SetCollection sc = new SetCollection();
            JsonWriter writer = new JsonWriter("./data/testWriteEmptySets.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteEmptySets.json");
            sc = reader.read();
            assertEquals(0, sc.getSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SetCollection sc = new SetCollection();
            Set s1 = new Set("Set1");
            Set s2 = new Set("Set2");
            Set s3 = new Set("Set3");
            Card c1 = new Card("Term1", "Dfn1");
            Card c2 = new Card("Term2", "Dfn2");
            c2.editDifficulty(5);
            Card c0 = new Card("NewTerm", "NewDfn");
            s1.addToSet(c1);
            s1.addToSet(c2);
            s3.addToSet(c0);
            sc.addSet(s1);
            sc.addSet(s2);
            sc.addSet(s3);
            JsonWriter writer = new JsonWriter("./data/testWriteGeneralSets.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriteGeneralSets.json");
            sc = reader.read();
            assertEquals(3, sc.getSize());
            assertEquals(2, sc.getSetAt(0).getLength());
            assertEquals("Set1", sc.getSetAt(0).getName());
            assertEquals(0, sc.getSetAt(1).getLength());
            assertEquals("Set2", sc.getSetAt(1).getName());
            assertEquals(1, sc.getSetAt(2).getLength());
            assertEquals("Set3", sc.getSetAt(2).getName());

            assertEquals("Dfn1", sc.getSetAt(0).getCardAt(0).getDfn());
            assertEquals("Term1", sc.getSetAt(0).getCardAt(0).getTerm());
            assertEquals(0, sc.getSetAt(0).getCardAt(0).getDifficulty());
            assertEquals("Dfn2", sc.getSetAt(0).getCardAt(1).getDfn());
            assertEquals("Term2", sc.getSetAt(0).getCardAt(1).getTerm());
            assertEquals(5, sc.getSetAt(0).getCardAt(1).getDifficulty());
            assertEquals("NewDfn", sc.getSetAt(2).getCardAt(0).getDfn());
            assertEquals("NewTerm", sc.getSetAt(2).getCardAt(0).getTerm());
            assertEquals(0, sc.getSetAt(2).getCardAt(0).getDifficulty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}