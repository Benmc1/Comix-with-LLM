package Main;

import config.ConfigurationFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class ComicSerializer {
    
    public void serializeToXml(Comic comic) {
        try {
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlBuilder.append("<comic>\n");
            xmlBuilder.append("\t<topic>").append(comic.getTopic()).append("</topic>\n");
            xmlBuilder.append("\t<panels>\n");

            List<String> proLines = comic.getLines().getProLines();
            List<String> antiLines = comic.getLines().getAntiLines();
            List<String> captions = comic.getLines().getCaptions(); // Generate captions for each panel

            for (int i = 0; i < proLines.size(); i++) {
                xmlBuilder.append("\t\t<panel>\n");
                xmlBuilder.append("\t\t<above>").append(captions.get(i)).append("</above>\n");
                xmlBuilder.append("\t\t<dialogue>\n");
                xmlBuilder.append(speechBalloon(proLines.get(i)));
                xmlBuilder.append(speechBalloon(antiLines.get(i)));
                xmlBuilder.append("\t\t</dialogue>\n");
                xmlBuilder.append("\t\t</panel>\n");
            }

            xmlBuilder.append("\t</panels>\n");
            xmlBuilder.append("</comic>");

            File file = new File(ConfigurationFile.getProperty("XML_FILE"));
            Files.writeString(file.toPath(), xmlBuilder.toString());

            System.out.println("XML file generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String speechBalloon(String speech){
        return "\t\t\t<balloon status=\"speech\">\r\t\t\t\t<content>" + speech + "</content>\n\t\t\t</balloon>\n";
    }
}
