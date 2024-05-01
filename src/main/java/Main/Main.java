package Main;
import config.ConfigurationFile;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(Objects.equals(ConfigurationFile.getProperty("API_KEY"), "")) {
            System.out.println("api key is missing add it in configuration file");
            return;
        }

        Comic comic = new Comic(IO.getTopic());

        System.out.println(comic.getLines().toString());
        Scanner s = new Scanner(System.in);
        s.nextLine();
        
        ComicSerializer serializer = new ComicSerializer();
        // Serialize Comic object to XML and write to file
        serializer.serializeToXml(comic);
    }
}
