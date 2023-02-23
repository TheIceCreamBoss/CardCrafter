package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;;

public class SetCollectionTest {
    SetCollection s1;
    SetCollection s2;
    SetCollection s3;

    @BeforeEach
    public void setUp() {
        s1 = new SetCollection();
        s2 = new SetCollection();
        s3 = new SetCollection();
    }

    @Test
    public void constructorTest() {
        assertEquals(s1.getSize(), 0);
        assertEquals(s2.getSize(), 0);
        assertEquals(s1.getLengths().size(), 0);
        assertEquals(s2.getLengths().size(), 0);
        assertEquals(s1.getNames().size(), 0);
        assertEquals(s2.getNames().size(), 0);
    }

    @Test
    public void addTest() {
        Set s = new Set("TestSet");
        s1.addSet(s);
        assertEquals(s1.getSize(), 1);
        assertEquals(s1.getLengths().size(), 1);
        assertEquals(s1.getNames().size(), 1);
        assertEquals(s1.getSetAt(0), s);
        s1.addSet(s);
        assertEquals(s1.getSize(), 2);
        assertEquals(s1.getLengths().size(), 2);
        assertEquals(s1.getNames().size(), 2);
        assertEquals(s1.getSetAt(1), s);
    }

    @Test
    public void removeTest() {
        Set set1 = new Set("Set1");
        Set set2 = new Set("Set2");

        s1.addSet(set1);
        s1.addSet(set2);
        s2.addSet(set1);
        s2.addSet(set1);
        s3.addSet(set1);
        s3.addSet(set2);
        s3.addSet(set1);

        s1.deleteSet(0);
        assertEquals(s1.getSize(), 1);
        assertEquals(s1.getSetAt(0), set2);

        s2.deleteSet(1);
        assertEquals(s2.getSize(), 1);
        assertEquals(s2.getSetAt(0), set1);

        s3.deleteSet(1);
        assertEquals(s3.getSize(), 2);
        assertEquals(s3.getSetAt(0), set1);
        assertEquals(s3.getSetAt(1), set1);

        s3.deleteSet(1);
        assertEquals(s3.getSize(), 1);
        assertEquals(s3.getSetAt(0), set1);
    }

    @Test
    public void findIndexTest() {
        Set set1 = new Set("Set1");
        Set set2 = new Set("Set2");

        s1.addSet(set1);
        s1.addSet(set2);
        s2.addSet(set1);
        s2.addSet(set1);
        s2.addSet(set2);
        s2.addSet(set2);

        assertEquals(s1.findIndex("Set1"), 0);
        assertEquals(s1.findIndex("Set2"), 1);
        assertEquals(s1.findIndex("Set3"), -1);
        assertEquals(s2.findIndex("Set1"), 0);
        assertEquals(s2.findIndex("Set2"), 2);
        assertEquals(s2.findIndex("Set3"), -1);
    }
}
