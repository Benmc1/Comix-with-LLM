package Main;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.theokanning.openai.embedding.Embedding;
import config.ConfigurationFile;

import java.io.*;
import java.util.*;

public class EmbeddingData {

    private final RandomAccessFile indexFile;
    private final RandomAccessFile dataFile;

    EmbeddingData(){
        try{
            indexFile = new RandomAccessFile("","");
            dataFile = new RandomAccessFile("","");
        } catch (FileNotFoundException e) {
            System.out.println("Data missing creating from scratch");
            List<String[]> data = readPlainData();
            writeIndexFile(data);
            getEmbeddings(data);
        }
    }
    public RandomAccessFile getIndexFile(){
        return indexFile;
    }

    public RandomAccessFile getDataFile(){
        return dataFile;
    }

    private List<String[]> readPlainData() {
        List<String[]> PoseData = new ArrayList<>();
        String file = ConfigurationFile.getProperty("PLAIN_DATA");

        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            boolean headersSkipped = false;
            while ((values = csvReader.readNext()) != null) {
                // Skip headers
                if (!headersSkipped) {
                    headersSkipped = true;
                    continue;
                }
                String[] descriptions = values[2].split(",");
                for (String s : descriptions) {
                    String[] line = {s,values[1],values[0]};
                    PoseData.add(line);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(file + " not found, cannot create embeddings data");
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return PoseData;
    }

    private List<List<Double>> getEmbeddings(List<String[]> PoseData){
        Map<String, List<Double>> embeddingData = new HashMap<>();
        //create a separate list to send all the requests at once
        List<String> messageList = new ArrayList<>();
        for (List<String> list : PoseData) {
            messageList.add(list.get(2));
        }

        List<Embedding> embeddingList = API.getEmbedding(messageList);
        for (int i = 0; i < embeddingList.size(); i++) {
            String key = PoseData.get(i).get(0).concat(","+PoseData.get(i).get(1));
            embeddingData.put(key, embeddingList.get(i).getEmbedding());
        }
        return embeddingData;
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
    private void writeDataFile(List<String[]> keys){

    }
}
