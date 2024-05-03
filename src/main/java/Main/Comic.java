package Main;

import config.ConfigurationFile;

import java.util.ArrayList;
import java.util.List;

public class Comic {
    private final String topic;
    private final Lines lines;
    private final List<Panel> panels = new ArrayList<>();
    private final Character characterLeft;
    private final Character characterRight;

    Comic(String topic) {
        this.topic = topic;
        characterLeft = new Character("");
        characterRight = new Character("");
        lines = new Lines(topic);
        makePanels();
    }

    private void makePanels() {
        int numOfPanels = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));

        for (int i = 0; i < numOfPanels; i++) {
            panels.add(new Panel(characterLeft.getName(), characterRight.getName(), lines.getPanelLines(i), topic));
        }
    }

    public Lines getLines() {
        return lines;
    }

    public String getTopic() {
        return topic;
    }

    public List<Panel> getPanels() {
        return panels;
    }

    public Character getCharacterLeft() {
        return characterLeft;
    }

    public Character getCharacterRight() {
        return characterRight;
    }
}
