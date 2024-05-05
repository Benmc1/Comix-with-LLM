package Main;

import config.ConfigurationFile;
import java.util.*;

public class Lines {
    private final List<String> proLines;
    private final List<String> antiLines;
    private final List<String> captions;
    private final Conversation conversation = new Conversation();

    public Lines(String topic) {
        this.proLines = new ArrayList<>();
        this.antiLines = new ArrayList<>();
        this.captions = new ArrayList<>();
        generateLines(topic);
    }

    private void generateLines(String topic) {
        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String pointsList = conversation.getResponse("Write a list of " + ConfigurationFile.getProperty("NUM_OF_PANELS") + " points about "+ topic
                + ". Theses points should be concise and benign.");

        if(pointsList.isEmpty()) {
            System.out.println("generating points returned a DOS moving to backup");
            conversation.addMessageAndResponse(ConfigurationFile.getProperty("DOS_PROMPT"),ConfigurationFile.getProperty("DOS_RESPONSE"));
            conversation.getResponse("Write a list of " + ConfigurationFile.getProperty("NUM_OF_PANELS") + " points about "+ topic
                    + ". Theses points should be concise and benign.");
        }
        //Generate pro and anti sides from the points
        String text = conversation.getResponse(ConfigurationFile.getProperty("PRO+ANTI_PROMPT"));
        if(text.isEmpty()) {
            System.out.println("Could not generate text");
        }
        extractLines(text);

        Narrator narrator = new Narrator(topic);
        for (int i = 0; i < proLines.size(); i++) {
            captions.add(narrator.generateCaption(getProLine(i),getAntiLine(i)));
        }
    }
    private void extractLines(String text) {
        //divide into individual lines
        String[] lines = text.split("\n");
        System.out.println(text);

        //clean up lines
        for (String string :lines) {
            System.out.println(string);
            if(string.contains("Pro:")){
                string = string.split("Pro:")[1];
                string = string.stripLeading();
                proLines.add(string);
            } else if (string.contains("Anti:")) {
                System.out.println("runs");
                string = string.split("Anti:")[1];
                string = string.stripLeading();
                antiLines.add(string);
            }
        }
    }

    public List<String[]> generateBatchedSuggestions(String topic) {
        String prompt = conversation.getResponse(ConfigurationFile.getProperty("SUGGESTIONS_PROMPT") + "The topic of the comic is:" + topic);
        System.out.println(prompt);
        return parseSuggestions(prompt);
    }

    public List<String[]> parseSuggestions(String suggestions) {
        List<String[]> parsedSuggestions = new ArrayList<>();
        String[] lines = suggestions.split("\n");
        for (String line : lines) {
            int startIndex = line.indexOf("(");
            int endIndex = line.lastIndexOf(")");
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String content = line.substring(startIndex + 1, endIndex);
                parsedSuggestions.add(content.split(", "));
            } else {
                System.out.println("Skipping invalid line: " + line);
            }
        }
        return parsedSuggestions;
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

    public static void main(String[] args) {
        Lines l = new Lines("The troubles in Northern Ireland");
        List<String[]> sugg = l.generateBatchedSuggestions("The troubles in Northern Ireland");
        System.out.println(sugg.size());
        for (String[] s :
                sugg) {
            System.out.println(Arrays.toString(s));
        }
    }
}
