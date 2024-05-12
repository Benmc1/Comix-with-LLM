package Generation;

import Main.ConfigurationFile;

import java.util.List;

public class TextGenerator {
    private final String topic;
    private List<String[]> suggestions;
    private Lines lines;
    private static final int NUM_PANELS = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));

    TextGenerator(String topic){
        this.topic = topic;
    }

    Lines getLines(){
        return lines;
    }

    private String generateDebate(){
        Conversation conversation = new Conversation();

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
        return text;
    }
    private void generateHistoryDialogue(){

    }

    private List<String[]> generateSuggestions(){
        String conversationResponse = conversation.getResponse(ConfigurationFile.getProperty("SUGGESTIONS_PROMPT") + "The topic of the comic is:" + topic);
        System.out.println(conversationResponse);
        return parseSuggestions(conversationResponse);
    }
}


