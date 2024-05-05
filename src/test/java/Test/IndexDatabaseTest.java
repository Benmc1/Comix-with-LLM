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

        // Write some data to the temporary file
        try (RandomAccessFile file = new RandomAccessFile(tempFile, "rw")) {
            file.writeBytes("Description1, Type1, Value1\n");
            file.writeBytes("Description2, Type2, Value2\n");
            file.writeBytes("Description3, Type3, Value3\n");
        }
        IndexDatabase indexDatabase = new IndexDatabase();

        // Test getting descriptions by index
        assertEquals("Description1, Type1, Value1", indexDatabase.getByIndex(0));
        assertEquals("Description2, Type2, Value2", indexDatabase.getByIndex(1));
        assertEquals("Description3, Type3, Value3", indexDatabase.getByIndex(2));
    }

    @Test
    void appendToFile() throws IOException {
        File tempFile = File.createTempFile("index", ".dat");
        tempFile.deleteOnExit();

        IndexDatabase indexDatabase = new IndexDatabase();
        indexDatabase.appendToFile("New Description, Type, New Value");

        assertEquals("New Description, Type, New Value", indexDatabase.getByIndex(0));
    }
}
