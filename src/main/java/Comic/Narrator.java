package Comic;

import Main.Conversation;

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
        conversation.addSystemMessage("You are writing captions for a comic you will be given a line and expected to provide a captions " +
                "that describes the conversation. Your answer should be no more than 12 words. Answer in the style and vocabulary of" + style+". " +
                "The lines will be talking about " + topic);
    }

    private static String getRandomNarratorStyle() {
        Random rand = new Random();
        return NARRATOR_STYLES[rand.nextInt(NARRATOR_STYLES.length)];
    }

    // Simulate generating a caption in the style of the narrator
    public String generateCaption(String pro, String anti) {
        return conversation.getResponse(pro + "\n" + anti);
    }
}