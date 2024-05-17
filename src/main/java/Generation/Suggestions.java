package Generation;

import Main.ConfigurationFile;

import java.util.Arrays;
import java.util.List;
public class Suggestions {
    private List<String[]> suggestions;
    private final int NUM_PANELS = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));
    Suggestions(String text, String topic){
        generateSuggestions(text,topic);
    }

    private void generateSuggestions(String text, String topic){
        System.out.println("\nGenerating Suggestions...\n");

        Conversation conversation = new Conversation();
        String conversationResponse = conversation.getResponse(text + "\n"
                + ConfigurationFile.getProperty("SUGGESTIONS_PROMPT")
                + "The topic of the comic is: " + topic);
        List<String[]> attempt = TextParser.parseSuggestions(conversationResponse);
        int tries = 0;
        while(!isValidSuggestion(attempt) && tries < 3 ){
            System.out.println("Suggestion invalid trying again with example");
            String example = ConfigurationFile.getProperty("SUGGESTIONS_EXAMPLE");
            conversationResponse =  conversation.getResponse("Try again. It should follow the same structure as this: " + example);
            attempt = TextParser.parseSuggestions(conversationResponse);
            tries++;
        }
        if(tries >= 4) System.out.println("Unable to produce valid suggestions.");
        suggestions = attempt;
    }

    public List<String[]> getSuggestions() {
        return suggestions;
    }

    private boolean isValidSuggestion(List<String[]> input){
        if(input.size() != NUM_PANELS) return false;
        for (String[] array : input) {
            if (array.length != 3) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String[] arr :suggestions){
            str.append(Arrays.toString(arr)).append("\n");
        }
        return str.toString();
    }
}
