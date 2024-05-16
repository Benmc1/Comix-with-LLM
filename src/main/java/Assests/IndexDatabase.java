package Assests;

import Main.IO;
import Main.ConfigurationFile;

import java.io.*;
import java.util.*;


public class IndexDatabase {

    private static RandomAccessFile file;
    private final String DATA_FILE = ConfigurationFile.getProperty("INDEX_DATA");
    // Mapping (Desc , type) -> pose/setting
    private final Map<String, String> assetMap = new HashMap<>(1850);
    // Store (Desc , type) at their index
    private final List<String> indexList = new ArrayList<>(1850);
    public IndexDatabase() {
        try{
            file = new RandomAccessFile(DATA_FILE, "rw");
            if(file.length() == 0) {
                List<String[]> data = IO.readPlainData();
                initializeDatabase(data);
            }
            readInData();
        } catch (FileNotFoundException e) {
            System.out.println("could not find or create embedding file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeDatabase(List<String[]> keys) {
        try {
            for (String[] key : keys) {
                String line = key[0] + "," + key[1] + "," + key[2];
                file.writeBytes(line+"\n");
            }
            System.out.println("Index file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get description value by index
    public String getByIndex(int index) {
        if(index > indexList.size()-1 || index < 0){
            return "";
        }else {
            return indexList.get(index);
        }
    }
    public String getByKey(String key) {
        return assetMap.get(key);
    }
    public Boolean contains(String key) {
        return assetMap.containsKey(key);
    }

    // Method to append a new description value to the index file
    public void appendToFile(String newDescription) {
        if(newDescription.isBlank() || newDescription.split(",").length != 3) return;
        String key = newDescription.substring(0,newDescription.lastIndexOf(","));
        if(!assetMap.containsKey(key)) {
            try {
                file.seek(file.length());
                file.writeBytes(newDescription + "\n");
                addToMemory(newDescription);
                System.out.println(newDescription + " has been appended to the index file.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Description and/or type already exists in the database.");
        }
    }

    // Method to check if a description exists in the database
    private void readInData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                addToMemory(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToMemory(String s){
        String[] parts = s.split(",");
        assetMap.put(parts[0]+","+parts[1],parts[2]);
        indexList.add(parts[0]+","+parts[1]);
    }
}
