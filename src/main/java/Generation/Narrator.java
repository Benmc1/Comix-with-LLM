package Generation;

import java.util.List;
import java.util.Random;

public class Narrator {
    private final String style;
    private final Conversation conversation;
    private final List<String> captions;
    private static final String[] NARRATOR_STYLES = {
            "Yoda", "Darth Vader", "Donald Trump", "Bane", "Boris Johnson"
    };

    public Narrator(String text,String topic) {
        style = getRandomNarratorStyle();
        conversation = new Conversation();
        captions = generateCaptions(text,topic);
    }
    private static String getRandomNarratorStyle() {
        Random rand = new Random();
        return NARRATOR_STYLES[rand.nextInt(NARRATOR_STYLES.length)];
    }

    public List<String> getCaptions() {
        return captions;
    }

    // Simulate generating a caption in the style of the narrator
    public List<String> generateCaptions(String text,String topic) {
        System.out.println("\nGenerating Captions...");
        System.out.println("Chosen character: " + style);

        conversation.addSystemMessage("You are writing narration for a comic you will be given a numbered list of 10 points and are expected to provide a narration of each point."
                + "Your answer should be brief and expressive. Answer in the style and vocabulary of " + style + ". "
                + "example: 1. narration  2. narration 3. ... ."
                + "The lines will be talking about " + topic);

        String response = conversation.getResponse(text);
        if(response.isBlank()) {
            System.out.println("generate captions failed trying again");
            response = conversation.getResponse(text);
        }
        System.out.println(response + "\n");
        return TextParser.parseCaptions(response);
    }
}