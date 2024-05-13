package Comic;

import Generation.TextGenerator;
import Main.ConfigurationFile;
import Main.IO;

import java.util.ArrayList;
import java.util.List;

public class Comic {
    private final String topic;
    private final TextGenerator generator;
    private final List<Panel> panels = new ArrayList<>();
    private final Character characterLeft;
    private final Character characterRight;
    public enum Mode {DEBATE,HISTORY}

    private final Mode mode;
    public Comic(String topic) {
        this.topic = topic;
        mode = IO.chooseMode();
        if(mode == Mode.HISTORY) {
            characterLeft = new Character("Professor Malcolm Sterling");
            characterLeft.setFeatures("male", "grey", "white", "pink");
            characterRight = IO.createStudentCharacter();
            panels.add(Panel.OpeningPanel());
        }else{
            characterLeft = new Character("");
            characterRight = new Character("");
        }

        generator = new TextGenerator(topic,mode);
        makePanels();
    }

    private void makePanels() {
        int numOfPanels = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));
        List<String[]> suggestions = generator.getSuggestions();
        if(mode == Mode.HISTORY) panels.add(Panel.OpeningPanel());
        for (int i = 0; i < numOfPanels; i++) {
            panels.add(new Panel(characterLeft.getName(), characterRight.getName(), generator.getLines().getPanelLines(i), suggestions.get(i)));
        }
        if(mode == Mode.HISTORY) panels.add(Panel.ClosingPanel());
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
