package Main;

import config.ConfigurationFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmbeddingSelector {
    public static String getRelevantChoice(String description,String type){
        List<String[]> data = readPlainData();
        //Extract the descriptions to save them as embeddings
        List<String> EmbeddingData = data.stream().map(array -> array[0]).toList();
        EmbeddingDatabase embeddingDatabase = new EmbeddingDatabase(EmbeddingData);
        System.out.println(embeddingDatabase.readFromFile(2));

        return "";
    }
    private static List<String[]> readPlainData() {
        List<String[]> PoseData = new ArrayList<>();
        String file = ConfigurationFile.getProperty("PLAIN_DATA");

        try (Scanner scanner = new Scanner(new File(file))) {
            //skip headers
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("\t");

                //if there arnt any descriptions skip
                if(values.length != 3) continue;

                String[] descriptions = values[2].split(",");
                for (String s : descriptions) {
                    String[] newline = {s,values[1],values[0]};
                    PoseData.add(newline);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(file + " not found, cannot create embeddings data");
        }
        return PoseData;
    }

    public static void main(String[] args) {

        EmbeddingSelector.getRelevantChoice("pose","waving heavily");
    }
}
