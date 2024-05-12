package Generation;

import Main.ConfigurationFile;
import java.util.*;

public class Lines {
    private final List<String> proLines;
    private final List<String> antiLines;
    private final List<String> captions;

    public Lines() {
        this.proLines = new ArrayList<>();
        this.antiLines = new ArrayList<>();
        this.captions = new ArrayList<>();
    }

    private void generateLines() {

        extractLines(text);

        Narrator narrator = new Narrator(topic);
        for (int i = 0; i < proLines.size(); i++) {
            captions.add(narrator.generateCaption(getProLine(i),getAntiLine(i)));
        }
    }



    public String getAntiLine(int n) {
        return antiLines.get(n);
    }
    public String getProLine(int n) {
        return proLines.get(n);
    }
    public String getCaption(int n) {
        return captions.get(n);
    }
    public String[] getPanelLines(int n) {
        return new String[]{getProLine(n),getAntiLine(n),getCaption(n)};
    }
    public List<String> getAntiLines() {
        return antiLines;
    }
    public List<String> getProLines() {
        return proLines;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < proLines.size(); i++) {
            str.append("\nPro: ").append(proLines.get(i));
            str.append("\nAnti: ").append(antiLines.get(i));
        }
        return str.toString();
    }
}
