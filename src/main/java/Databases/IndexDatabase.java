package Databases;

import Main.IO;
import com.opencsv.CSVWriter;
import config.ConfigurationFile;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class IndexDatabase {

    private static RandomAccessFile file;

    public IndexDatabase() {
        String DATA_FILE = ConfigurationFile.getProperty("INDEX_DATA");
        try{
            file = new RandomAccessFile(DATA_FILE, "rw");
            if(file.length() == 0) {
                List<String[]> data = IO.readPlainData();
                initializeDatabase(data);
            }
        }catch (FileNotFoundException e){
            System.out.println("could not find or create embedding file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDatabase(List<String[]> keys){
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
            file.seek(index); // Move the file pointer to the position of desired index
            return file.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to append a new description value to the index file 
    public void appendToFile(String newDescription){
        try {
            file.seek(file.length());
            file.writeBytes(newDescription + "\n");
            System.out.println("Description has been appended to the index file.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
