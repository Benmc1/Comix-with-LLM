package Main;
import Comic.Comic;
import config.ConfigurationFile;
import Comic.ComicSerializer;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        if(Objects.equals(ConfigurationFile.getProperty("API_KEY"), "")) {
            System.out.println("api key is missing add it in configuration file");
            return;
        }

        Comic comic = new Comic(IO.getTopic());


        ComicSerializer serializer = new ComicSerializer();
        // Serialize Comic object to XML and write to file
        serializer.serializeToXml(comic);
    }
}
