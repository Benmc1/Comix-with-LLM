package Main;

import config.ConfigurationFile;

import java.util.*;

public class Lines {
    private final List<String> proLines;
    private final List<String> antiLines;
    private final List<String> captions;

    public Lines(String topic){
        this.proLines = new ArrayList<>();
        this.antiLines = new ArrayList<>();
        this.captions = new ArrayList<>();
        generateLines(topic);
    }

    private void generateLines(String topic){
        Conversation conversation = new Conversation();
        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String pointsList = conversation.getResponse("Write a list of " + ConfigurationFile.getProperty("NUM_OF_PANELS") + " points about "+ topic
                + ". Theses points should be concise and benign.");

        if(pointsList.isEmpty()){
            System.out.println("points is empty");
            conversation.addMessageAndResponse(ConfigurationFile.getProperty("DOS_PROMPT"),ConfigurationFile.getProperty("DOS_RESPONSE"));
              conversation.getResponse("Write a list of " + ConfigurationFile.getProperty("NUM_OF_PANELS") + " points about "+ topic
                    + ". Theses points should be concise and benign.");
        }
        //Generate pro and anti sides from the points
        String text = conversation.getResponse(ConfigurationFile.getProperty("LINE_PROMPT"));
        if(text.isEmpty()){
            System.out.println("Lines returned a DOS");
        }
        extractLines(text);

        String narratorStyle = Narrator.getRandomNarratorStyle();
        for (String point : this.proLines) {
            this.captions.add(Narrator.generateCaption(point, narratorStyle));
        }
    }
    private void extractLines(String text){
        //divide into individual lines
        String[] lines = text.split("\n");
        System.out.println(text);

        //clean up lines
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

    public List<String> getCaptions() { return captions; }

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
