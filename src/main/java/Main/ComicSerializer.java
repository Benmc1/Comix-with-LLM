package Main;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import Main.config.ConfigurationFile;

public class ComicSerializer {
    
    public void serializeToXml(Comic comic, String filePath) {
        try {
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<Comic>\n");
            xmlBuilder.append("<Topic>").append(comic.getTopic()).append("</Topic>\n");
            xmlBuilder.append("<Panels>\n");

            List<String> proLines = comic.getLines().getProLines();
            List<String> antiLines = comic.getLines().getAntiLines();
            List<String> captions = generateCaptions(proLines.size()); // Generate captions for each panel

            for (int i = 0; i < proLines.size(); i++) {
                xmlBuilder.append("<Panel>\n");
                xmlBuilder.append("<Dialogue>\n");
                xmlBuilder.append("<Pro>").append(proLines.get(i)).append("</Pro>\n");
                xmlBuilder.append("<Anti>").append(antiLines.get(i)).append("</Anti>\n");
                xmlBuilder.append("</Dialogue>\n");
                xmlBuilder.append("<Caption>").append(captions.get(i)).append("</Caption>\n");
                xmlBuilder.append("</Panel>\n");
            }

            xmlBuilder.append("</Panels>\n");
            xmlBuilder.append("</Comic>");

            File file = new File(filePath);
            Files.writeString(file.toPath(), xmlBuilder.toString());

            System.out.println("XML file generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> generateCaptions(int numPanels) {
        // Implement logic to generate captions for each panel
        // This could be randomized or based on specific criteria
        // For simplicity, let's generate dummy captions here
        List<String> captions = new ArrayList<>();
        for (int i = 1; i <= numPanels; i++) {
            captions.add("Caption for Panel " + i);
        }
        return captions;
    }
}
