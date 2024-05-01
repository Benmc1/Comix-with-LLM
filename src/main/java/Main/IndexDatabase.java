package Main;

import com.opencsv.CSVWriter;
import config.ConfigurationFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class IndexDatabase {

    private final RandomAccessFile indexFile;

    private void writeIndexFile(List<String[]> keys){
        String indexFile = ConfigurationFile.getProperty("INDEX_FILE");
        try (CSVWriter writer = new CSVWriter(new FileWriter(indexFile))) {

            String[] headers = {"Description","Type","Value"};
            writer.writeNext(headers);
            writer.writeAll(keys);
            System.out.println("Index file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    getByIndex
    appendNewLine

}
