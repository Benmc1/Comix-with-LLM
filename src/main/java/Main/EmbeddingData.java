package Main;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import com.theokanning.openai.embedding.Embedding;
import config.ConfigurationFile;

import java.io.*;
import java.util.*;

public class EmbeddingData  {
    private static List<Embedding> poseData;
    private static List<Embedding> settingData;
    public static List<Embedding> getPoseEmbedding(){
        if (poseData.isEmpty()){
            EmbeddingData embeddingData = new EmbeddingData();
            embeddingData.readEmbeddingData();
        }
        return poseData;
    }
    public static List<Embedding> getSettingEmbedding(){
        if (settingData.isEmpty()){
            EmbeddingData embeddingData = new EmbeddingData();
            embeddingData.readEmbeddingData();
        }
        return settingData;
    }

    private List<List<String>> readPlainData() {
        List<List<String>> PoseData = new ArrayList<>();
        String file = ConfigurationFile.getProperty("PLAIN_DATA");

        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values;
            boolean headersSkipped = false;
            while ((values = csvReader.readNext()) != null) {
                if (!headersSkipped) {
                    headersSkipped = true;
                    continue; // Skip headers
                }
                String strB = values[0] + values[2] + values[3] + values[4];
                values[2] = strB;

                // we pass the first 3 entries of value as these now contain all the data we need
                PoseData.add(Arrays.asList(Arrays.copyOf(values,3)));
            }
        }catch (FileNotFoundException e){
            System.out.println(file + " not found, cannot calculate embeddings");
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
        int embeddingLen = Integer.parseInt(ConfigurationFile.getProperty("EMBEDDING_LEN"));

        try (CSVWriter writer = new CSVWriter(new FileWriter("EmbeddingData.csv"))) {
            // Writing header: 1 key + 1536 doubles
            String[] headers = new String[embeddingLen+1];
            headers[0] = "Key";
            for (int i = 1; i <= embeddingLen; i++) {
                headers[i] = "Value " + i;
            }
            writer.writeNext(headers);

            // Writing data from the map
            for (Map.Entry<String, List<Double>> entry : data.entrySet()) {
                String key = entry.getKey();
                List<Double> values = entry.getValue();
                String[] record = new String[embeddingLen];
                record[0] = key;
                for (int i = 0; i < embeddingLen; i++) {
                    record[i + 1] = String.valueOf(values.get(i));
                }
                writer.writeNext(record);
            }

            System.out.println("CSV file has been created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readEmbeddingData(){
        String file = ConfigurationFile.getProperty("EMBEDDING_DATA");

        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            boolean headersSkipped = false;
            while ((nextLine = reader.readNext()) != null) {
                if (!headersSkipped) {
                    headersSkipped = true;
                    continue; // Skip headers
                }
                //getting the name and type. name = 0, type = 1
                String[] key = nextLine[0].split(",");

                List<Double> values = new ArrayList<>();
                for (int i = 1; i < nextLine.length; i++) {
                    values.add(Double.parseDouble(nextLine[i]));
                }
                Embedding e = new Embedding();
                e.setEmbedding(values);
                e.setObject(key[0]);

                if(key[1].equals("pose")) poseData.add(e);
                else settingData.add(e);
            }
        } catch (FileNotFoundException e){
            //need to make the data if it isn't here
            List<List<String>> plainData = readPlainData();
            Map<String, List<Double>> embeddings = getEmbeddings(plainData);
            writeEmbeddingData(embeddings);
            readEmbeddingData();

        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

    }
}
