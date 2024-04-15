package Main;

import config.ConfigurationFile;
import java.util.ArrayList;
import java.util.List;

public class Lines {
    private final List<String> proLines;
    private final List<String> antiLines;

    public Lines(String topic, int numOfLines){
        this.proLines = new ArrayList<>();
        this.antiLines = new ArrayList<>();
        generateLines(topic,numOfLines);
    }

    private void generateLines(String topic, int numOfPanels){
        Conversation conversation = new Conversation();
        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String points = conversation.getResponse("Write a list of " + numOfPanels + " points about "+ topic
                + ". Theses points should be concise and benign.");
        if(points.isEmpty()){
            System.out.println("points is empty");
            conversation.addMessageAndResponse(ConfigurationFile.getProperty("DOS_PROMPT"),ConfigurationFile.getProperty("DOS_RESPONSE"));
              conversation.getResponse("Write a list of " + numOfPanels + " points about "+ topic
                    + ". Theses points should be concise and benign.");
        }
        String text = conversation.getResponse(ConfigurationFile.getProperty("LINE_PROMPT"));
        if(text.isEmpty()){
            System.out.println("Lines returned a DOS");
        }
        extractLines(text);
    }
    private void extractLines(String text){
        String[] lines = text.split("\n");
        System.out.println(text);

        for (String string :lines) {
            if(string.contains("Pro")){
                string = string.split("Pro:")[1];
                string = string.stripLeading();
                proLines.add(string);
            } else if (string.contains("Anti")) {
                string = string.split("Anti:")[1];
                string = string.stripLeading();
                antiLines.add(string);
            }
        }
    }

    public String getAntiLine(int n) {
        return antiLines.get(n);
    }

    public String getProLine(int n) {
        return proLines.get(n);
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
            str.append("\nPro: " + proLines.get(i));
            str.append("\nAnti: " + antiLines.get(i));
        }
        return str.toString();
    }
}
