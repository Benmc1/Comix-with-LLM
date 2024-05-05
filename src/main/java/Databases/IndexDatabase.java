package Databases;

import Main.IO;
import config.ConfigurationFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IndexDatabase {

    private static RandomAccessFile file;
    private final String DATA_FILE = ConfigurationFile.getProperty("INDEX_DATA");
    public IndexDatabase() {
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
            String headers = "Description, Type, Value\n";
            file.writeBytes(headers);
            for (String[] key : keys) {
                String line = Arrays.stream(key).map(string ->  string+", ").collect(Collectors.joining());
                System.out.println(line);
                file.writeBytes(line+"\n");
            }
            System.out.println("Index file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get description value by index
    public String getByIndex(int index) {
        try {
            // Move the file pointer to the position of desired index
            file.seek(0);
            for (int i = 0; i < index; i++){
                file.readLine();
            }
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
        }else {
        System.out.println("Description and/or type already exists in the database.");
    }
}

    // Method to check if a description exists in the database
    private boolean doesDescriptionExist(String description) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
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

        int index = 5;
        String descAtIndex = indexDatabase.getByIndex(index);
        System.out.println("Description at index " + index + ": " + descAtIndex);
        indexDatabase.appendToFile("asdasdasdasdasdasdasdasdasdasdadadasdasdasdasd");
    }
}
