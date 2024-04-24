package DataProcessing;

import Main.API;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.theokanning.openai.embedding.Embedding;

import java.io.*;
import java.util.*;


public class PoseProcessing {

    Map<String , List<Double>> embeddingData;

    private List<List<String>> readData(String file) {
        List<List<String>> PoseData = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                String strB = values[0] + values[2] + values[3] + values[4];
                values[2] = strB;

                // we pass the first 3 entries of value as these now contain all the data we need
                PoseData.add(Arrays.asList(Arrays.copyOf(values,3)));
            }
        }catch (FileNotFoundException ignored){
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return PoseData;
    }

    // Creating a Map of name,type ->  embedding
    private Map<String, List<Double>> getEmbeddings(List<List<String>> PoseData){
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

    private void writeEmbeddingData(Map<String, List<Double>> data){

        try (CSVWriter writer = new CSVWriter(new FileWriter("data.csv"))) {
            // Writing header: 1 key + 1536 doubles
            String[] headers = new String[1537];
            headers[0] = "Key";
            for (int i = 1; i <= 1536; i++) {
                headers[i] = "Value " + i;
            }
            writer.writeNext(headers);

            // Writing data from the map
            for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
                String key = entry.getKey();
                List<Double> values = entry.getValue();
                String[] record = new String[1537];
                record[0] = key;
                for (int i = 0; i < 1536; i++) {
                    record[i + 1] = String.valueOf(values.get(i));
                }
                writer.writeNext(record);
            }

            System.out.println("CSV file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Map<String, List<Double>> readEmbeddingData(){
        Map<String, List<Double>> data = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader("data.csv"))) {
            String[] nextLine;
            boolean headersSkipped = false;
            while ((nextLine = reader.readNext()) != null) {
                if (!headersSkipped) {
                    headersSkipped = true;
                    continue; // Skip headers
                }
                String key = nextLine[0];
                List<Double> values = new ArrayList<>();
                for (int i = 1; i < nextLine.length; i++) {
                    values.add(Double.parseDouble(nextLine[i]));
                }
                data.put(key, values);
            }
        } catch (FileNotFoundException e){
            writeEmbeddingData(getEmbeddings(readData("pose database.csv")));
            return readEmbeddingData();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void main(String[] args) {
    PoseProcessing p = new PoseProcessing();
        System.out.println(p.readEmbeddingData());
    }
}
