package Generation;

import java.util.List;
import java.util.Random;

public class Narrator {
    String style;
    Conversation conversation;
    private static final String[] NARRATOR_STYLES = {
            "Yoda", "Darth Vader", "Donald Trump", "Bane", "Boris Johnson"
    };

    public Narrator(String topic) {
        style = getRandomNarratorStyle();
        conversation = new Conversation();
        System.out.println(style);
        conversation.addSystemMessage("You are writing narration for a comic you will be given a numbered list of points and are expected to provide an narration for each point."
                + "Your answer should be no more than 15 words. Answer in the style and vocabulary of " + style + ". "
                + "example: 1. narration  2. narration 3. ... ."
                + "The lines will be talking about " + topic);
    }
    private static String getRandomNarratorStyle() {
        Random rand = new Random();
        return NARRATOR_STYLES[rand.nextInt(NARRATOR_STYLES.length)];
    }

    // Simulate generating a caption in the style of the narrator
    public List<String> generateCaptions(String text) {
        String response = conversation.getResponse(text);
        if(response.isBlank()) {
            System.out.println("generate captions failed");
            response = conversation.getResponse(text);
        }
        System.out.println(response);
        return TextParser.parseCaptions(response);
    }

    public static void main(String[] args) {

    }
}