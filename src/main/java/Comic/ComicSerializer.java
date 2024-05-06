package Comic;

import config.ConfigurationFile;

import java.io.File;
import java.nio.file.Files;

public class ComicSerializer {
    
    public void serializeToXml(Comic comic) {
        try {
            StringBuilder xmlBuilder = new StringBuilder();
            xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            xmlBuilder.append("<comic>\n");

            xmlBuilder.append("\t<figures>\n");
            xmlBuilder.append(comic.getCharacterLeft().toXML());
            xmlBuilder.append(comic.getCharacterRight().toXML());
            xmlBuilder.append("\t</figures>\n");

            xmlBuilder.append("\t<topic>").append(comic.getTopic()).append("</topic>\n");
            xmlBuilder.append("\t<panels>\n");

            for (Panel panel : comic.getPanels()) {
                xmlBuilder.append(panel.toXML());
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

}
