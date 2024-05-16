package Generation;

import Comic.Comic;
import Main.ConfigurationFile;

import java.util.List;

public class TextGenerator {
    private final String topic;
    private List<String[]> suggestions;
    private final Lines lines = new Lines();
    private static final int NUM_PANELS = Integer.parseInt(ConfigurationFile.getProperty("NUM_OF_PANELS"));
    public TextGenerator(String topic, Comic.Mode mode){
        this.topic = topic;
        String dialogue = generateDialogue(mode);

        List<List<String>> parsedDialogue = TextParser.parseDialogue(dialogue);
        lines.addLeftLines(parsedDialogue.get(0));
        lines.addRightLines(parsedDialogue.get(1));

        generateSuggestions(dialogue);

        Narrator narrator = new Narrator(topic);
        List<String> captions = narrator.generateCaptions(dialogue);
        lines.addCaptions(captions);

    }

    public Lines getLines(){
        return lines;
    }

    public List<String[]> getSuggestions() {
        return suggestions;
    }

    private String generateDialogue(Comic.Mode mode){
        System.out.println("\nGenerating Dialogue...\n");
        Conversation conversation = new Conversation();


        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String pointsPrompt = "Write a list of " + NUM_PANELS + " points about "+ topic
                + ". Theses points should be concise and benign and interesting.";
        String pointsList = conversation.getResponse(pointsPrompt);

        if(pointsList.isEmpty()) {
            System.out.println("Generating points returned a DOS moving to backup.");
            conversation.addMessageAndResponse(ConfigurationFile.getProperty(mode.toString() + "_DOS_PROMPT"),ConfigurationFile.getProperty(mode + "_DOS_RESPONSE"));
            conversation.getResponse(pointsPrompt);
        }
        //Generate pro and anti sides from the points
        String text = conversation.getResponse(ConfigurationFile.getProperty(mode.toString() + "_PROMPT"));
        if(text.isEmpty()) {
            System.out.println("Could not generate text");
        }
        System.out.println(text);
        return text;
    }

    private void generateSuggestions(String text){
        System.out.println("\nGenerating Suggestions...\n");

        Conversation conversation = new Conversation();
        String conversationResponse = conversation.getResponse(text + "\n"
                + ConfigurationFile.getProperty("SUGGESTIONS_PROMPT")
                + "The topic of the comic is: " + topic);
        System.out.println(conversationResponse);
        suggestions = TextParser.parseSuggestions(conversationResponse);
    }
}


