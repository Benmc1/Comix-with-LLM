package DataProcessing;

import Main.API;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.theokanning.openai.embedding.Embedding;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PoseProcessing {


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

    public static void main(String[] args) {

    }
}
