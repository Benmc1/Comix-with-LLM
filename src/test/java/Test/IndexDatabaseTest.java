package Test;

import Databases.IndexDatabase;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.*;

class IndexDatabaseTest {

    @Test
    void getByIndex() throws IOException {
        File tempFile = File.createTempFile("index", ".dat");
        tempFile.deleteOnExit();

        IndexDatabase indexDatabase = new IndexDatabase();

        // Test getting descriptions by index
        assertEquals("in heat, pose, attracted, ", indexDatabase.getByIndex(1));
        assertEquals(" attraction, pose, attracted, ", indexDatabase.getByIndex(2));
        assertEquals(" attarcted to someone, pose, attracted, ", indexDatabase.getByIndex(3));
        assertEquals("a bow, pose, bowing, ", indexDatabase.getByIndex(4));
        assertEquals(" showing respect, pose, bowing, ", indexDatabase.getByIndex(5));
    }

    @Test
    void appendToFile() throws IOException {
        File tempFile = File.createTempFile("index", ".dat");
        tempFile.deleteOnExit();

        IndexDatabase indexDatabase = new IndexDatabase();
        indexDatabase.appendToFile("Description, Type, Value");
        int indexLength = (int) tempFile.length();

        assertEquals("Description, Type, Value", indexDatabase.getByIndex(indexLength));
    }
}
