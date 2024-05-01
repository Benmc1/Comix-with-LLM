package Main;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.theokanning.openai.embedding.Embedding;
import config.ConfigurationFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingSelector {
    enum type{POSE,SETTING}

    public static String getRelevantChoice(String description,String type){
        File file = new File("data.txt");
        if(!file.exists()){

        }
        String ans =  IndexDatabase.CheckInDatabase(type.POSE,description);
        if(ans.isBlank()) {

            ans = EmfindClosest(description);
        }
        return ans;
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

}
