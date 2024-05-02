package Main;

import com.opencsv.CSVWriter;
import config.ConfigurationFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class IndexDatabase {

    private final RandomAccessFile indexFile;

    public IndexDatabase(RandomAccessFile indexFile) {
        this.indexFile = indexFile;
    }

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

    // Method to get description value by index
    public String getByIndex(int index){
        try {
            indexFile.seek(index); // Move the file pointer to the position of desired index
            return indexFile.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to append a new description value to the index file 
    public void appendToFile(String newDescription, int index){
        try {
            indexFile.seek(indexFile.length());
            indexFile.writeBytes(newDescription + "\n");
            System.out.println("Description has been appended to the index file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
