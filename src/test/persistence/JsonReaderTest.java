package persistence;

import model.SetCollection;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SetCollection sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySets() {
        JsonReader reader = new JsonReader("./data/testEmptySets.json");
        try {
            SetCollection sc = reader.read();
            assertEquals(0, sc.getSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSets() {
        JsonReader reader = new JsonReader("./data/testGeneralSets.json");
        try {
            SetCollection sc = reader.read();
            assertEquals(2, sc.getSize());
            assertEquals(3, sc.getSetAt(0).getLength());
            assertEquals("1", sc.getSetAt(0).getName());
            assertEquals(0, sc.getSetAt(1).getLength());
            assertEquals("2", sc.getSetAt(1).getName());

            assertEquals("1D", sc.getSetAt(0).getCardAt(0).getDfn());
            assertEquals("1T", sc.getSetAt(0).getCardAt(0).getTerm());
            assertEquals(5, sc.getSetAt(0).getCardAt(0).getDifficulty());
            assertEquals("2D", sc.getSetAt(0).getCardAt(1).getDfn());
            assertEquals("2T", sc.getSetAt(0).getCardAt(1).getTerm());
            assertEquals(3, sc.getSetAt(0).getCardAt(1).getDifficulty());
            assertEquals("3D", sc.getSetAt(0).getCardAt(2).getDfn());
            assertEquals("3T", sc.getSetAt(0).getCardAt(2).getTerm());
            assertEquals(1, sc.getSetAt(0).getCardAt(2).getDifficulty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}