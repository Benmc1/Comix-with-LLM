package Main;

import java.util.Random;

public class Narrator {
    private static final String[] NARRATOR_STYLES = {
            "Yoda", "Darth Vader", "Donald Trump", "Bane", "Boris Johnson"
    };

    public static String getRandomNarratorStyle() {
        Random rand = new Random();
        return NARRATOR_STYLES[rand.nextInt(NARRATOR_STYLES.length)];
    }

    // Simulate generating a caption in the style of the narrator
    public static String generateCaption(String point, String style) {
        return switch (style) {
            case "Yoda" -> "Mmm, " + point.toLowerCase() + ", it is.";
            case "Darth Vader" -> "Ensure you must, that " + point.toLowerCase() + ".";
            case "Donald Trump" -> "Everybody’s talking about " + point + ".";
            case "Bane" -> "Gotham's reckoning comes to " + point.toLowerCase() + ".";
            case "Boris Johnson" -> "Frankly, my dear fellows, " + point + " is what it is.";
            default -> point;
        };
    }
}