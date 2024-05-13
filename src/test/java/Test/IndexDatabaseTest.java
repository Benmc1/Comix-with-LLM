package Test;

import Assests.IndexDatabase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndexDatabaseTest {
    IndexDatabase indexDatabase = new IndexDatabase();
    @Test
    void appendToFile() {
        indexDatabase.appendToFile("New Description, Type, New Value");
        assertTrue(indexDatabase.contains("New Description, Type"));

        indexDatabase.appendToFile("");
        assertFalse(indexDatabase.contains(""));

        indexDatabase.appendToFile("New Description, New Value");
        assertFalse(indexDatabase.contains("New Description, New Value"));
    }

    @Test
    void getByIndex() {
        String ans = "attarcted to someone,pose";
        assertEquals(ans, indexDatabase.getByIndex(2));

        String blank = "";
        assertEquals(blank, indexDatabase.getByIndex(-11));

        assertEquals(blank, indexDatabase.getByIndex(9999999));
    }
    @Test
    void getByKey() {
        //It's misspelled in the data don't fix
        String key = "attarcted to someone,pose";
        String ans = "attracted";
        assertEquals(ans, indexDatabase.getByKey(key));

        String invalidOrder = "pose,joy";
        assertNull(indexDatabase.getByKey(invalidOrder));

        String invalidKey = "asdasdasdasdad";
        assertNull(indexDatabase.getByKey(invalidKey));

        String blankKey = "";
        assertNull(indexDatabase.getByKey(blankKey));
    }
}
