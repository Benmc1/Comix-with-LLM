package Main;

import config.ConfigurationFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO {
    public static String getTopic(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the prompt for chat completion:");
        return scanner.nextLine();
    }
    public static String getCharacterName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the character you want in the comic.");
        return scanner.nextLine();
    }
    public static List<String[]> readPlainData() {
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
}
