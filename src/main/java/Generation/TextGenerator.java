package Generation;

import Comic.Comic;
import Main.ConfigurationFile;

import java.util.List;

public class TextGenerator {
    private final String topic;
    private final Suggestions suggestions;
    private final Lines lines = new Lines();
    private static final int NUM_PANELS = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));
    public TextGenerator(String topic, Comic.Mode mode){
        this.topic = topic;
        String dialogue = generateDialogue(mode);

        List<List<String>> parsedDialogue = TextParser.parseDialogue(dialogue);
        lines.addLeftLines(parsedDialogue.get(0));
        lines.addRightLines(parsedDialogue.get(1));

        suggestions = new Suggestions(dialogue,topic);
        System.out.println(suggestions);

        Narrator narrator = new Narrator(dialogue,topic);
        lines.addCaptions(narrator.getCaptions());
    }

    public Lines getLines(){
        return lines;
    }

    public List<String[]> getSuggestions() {
        return suggestions.getSuggestions();
    }

    private String generateDialogue(Comic.Mode mode){
        System.out.println("\nGenerating Dialogue...\n");
        Conversation conversation = new Conversation();


        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String pointsPrompt = "Write a list of " + NUM_PANELS + " points about " + topic
                + ". Theses points should be concise and benign and interesting.";
        String pointsList = conversation.getResponse(pointsPrompt);
        //If it's a DOS conversation returns a blank string
        if(pointsList.isEmpty()) {
            System.out.println("Generating points returned a DOS moving to backup.");
            String dosPrompt = ConfigurationFile.getProperty("DOS_PROMPT") + "now we are talking about" + topic;
            String dosResponse = ConfigurationFile.getProperty("DOS_RESPONSE");
            conversation.addMessageAndResponse(dosPrompt,dosResponse);
            conversation.getResponse(pointsPrompt);
        }
        //Generate dialogue for both sides from the points
        String dialoguePrompt = ConfigurationFile.getProperty(mode.toString() + "_PROMPT");
        String text = conversation.getResponse(dialoguePrompt);
        int tries = 0;
        while(!isValidDialogue(text) && tries < 3 ){
            System.out.println("Dialogue invalid trying again with example");
            conversation.removeLastExchange();
            String example = ConfigurationFile.getProperty("DIALOGUE_EXAMPLE");
            text =  conversation.getResponse(dialoguePrompt + ". It should follow the same structure as this: " + example);
            tries++;
        }
        if(tries ==  3) text = conversation.getResponse("try again but make it shorter");
        System.out.println(text);
        return text;
    }

    private Boolean isValidDialogue(String text){
        List<List<String>> parsed = TextParser.parseDialogue(text);

        if(parsed.size() != 2) return false;
        for (List<String> list : parsed) {
            for (String s: list) {
                if(s.length() > 85) return false;
            }
            if(list.size() != NUM_PANELS) return false;
        }
        return true;
    }
}


