package Databases;

import Main.IO;
import config.ConfigurationFile;

import java.io.*;
import java.util.Arrays;
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
        } catch (FileNotFoundException e) {
            System.out.println("could not find or create embedding file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDatabase(List<String[]> keys) {
        try {
            String headers = "Description, Type, Value";
            file.writeBytes(headers);
            for (String[] key : keys) {
                String line = Arrays.stream(key).map(string ->  string+", ").toString();
                file.writeBytes(line);
            }
            System.out.println("Index file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get description value by index
    public String getByIndex(int index) {
        try {
            file.seek(index); // Move the file pointer to the position of desired index
            return file.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to append a new description value to the index file 
    public void appendToFile(String newDescription) {
        if(!doesDescriptionExist(newDescription)) {
        try {
            file.seek(file.length());
            file.writeBytes(newDescription + "\n");
            System.out.println("Description has been appended to the index file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        System.out.println("Description and/or type already exists in the database.");
    }    
}   

    // Method to check if a description exists in the database
    private boolean doesDescriptionExist(String description) {
        try (BufferedReader reader = new BufferedReader(new FileReader("INDEX_DATA"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].trim().equals(description)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        IndexDatabase indexDatabase = new IndexDatabase();

        String desc = "New description, pose, some_value";
        indexDatabase.appendToFile(desc);

        String existingDesc = "New description, pose, some_value";
        indexDatabase.appendToFile(existingDesc);

        int index = 0;
        String descAtIndex = indexDatabase.getByIndex(index);
        System.out.println("Description at index " + index + ": " + descAtIndex);
    }
}
