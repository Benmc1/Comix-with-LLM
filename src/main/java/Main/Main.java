package Main;
import Main.config.ConfigurationFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(Objects.equals(ConfigurationFile.getProperty("API_KEY"), "")) {
            System.out.println("api key is missing add it in configuration file");
            return;
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the prompt for chat completion:");
        String prompt = scanner.nextLine();
        System.out.println("Enter the number of panels to generate:");
        Comic comic = new Comic(prompt);

        System.out.println(comic.getLines().toString());

        
        ComicSerializer serializer = new ComicSerializer();
        String filePath = "comic.xml";

        // Serialize Comic object to XML and write to file
        serializer.serializeToXml(comic, filePath);
        
        try {
            String xmlContent = Files.readString(Paths.get(filePath));
            System.out.println("Generated XML content:");
            System.out.println(xmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
