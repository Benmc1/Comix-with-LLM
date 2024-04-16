package Main;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class ComicSerializer {
    
    public void serializeToXml(Comic comic, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Comic.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(filePath);
            marshaller.marshal(comic, file);

            System.out.println("XML file generated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
