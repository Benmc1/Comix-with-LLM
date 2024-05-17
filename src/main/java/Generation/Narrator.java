package Generation;

import Main.ConfigurationFile;

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
        String s = captions.get(0);
        String firstLine = style + "-" + s;
        captions.set(0,firstLine);
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
        System.out.println("Chosen character: " + style + "\n");
        String example = ConfigurationFile.getProperty("NARRATION_EXAMPLE");

        conversation.addSystemMessage("You are writing narration for a comic you will be given a numbered list of 10 points and are expected to provide a narration of each point."
                + "Your answer should be brief and expressive. Answer in the style and vocabulary of " + style + ". "
                + "Answer should be a numbered list in the format: " + example
                + "The lines will be talking about " + topic);

        String response = conversation.getResponse(text);
        int tries = 0;
        while (!isValidNarration(response) && tries < 3) {
            System.out.println("Narration invalid trying again.");
            conversation.removeLastExchange();
            response = conversation.getResponse(text);
            tries++;
        }
        System.out.println(response + "\n");
        return TextParser.parseCaptions(response);
    }
    private Boolean isValidNarration(String input){
        List<String> attempt = TextParser.parseCaptions(input);
        return attempt.size() == 10;
    }
    public static void main(String[] args) {
        String text = """
                1. "The Ice Age"
                Left: "Professor, why is everything so cold?"
                Right: "Well, my dear student, welcome to the Pleistocene epoch or the Ice Age!"

                2. "Glaciers and their coverage"
                Left: "Wow, those ice sheets span for miles and miles!"
                Right: "Yes, indeed! Up to 30% of the Earth's surface was covered in glaciers."

                3. "Unique Ice Age animals"
                Left: "What kinds of animals lived during this era?"
                Right: "We have woolly mammoths, saber-toothed tigers, and giant ground sloths, to name a few."

                4. "Human adaptation during Ice Age"
                Left: "Did early humans survive this freezing cold?"
                Right: "Absolutely! Humans had to adapt to extreme conditions during this time."

                5. "Land Bridges"
                Left: "What are those bridges that connect the land masses?"
                Right: "Those are land bridges that formed during the Ice Age when sea levels dropped."

                6. "Landscapes formation"
                Left: "Why are the landscapes so different from what we know?"
                Right: "The weight of the ice sheets caused the earth's crust to sink, leading to the formation of new landscapes."

                7. "Mega fauna extinction"
                Left: "What happened to the creatures that lived during the Ice Age?"
                Right: "Many large mammals went extinct, including megafauna like the woolly mammoth."

                8. "Weather Pattern"
                Left: "How did the weather change during the Ice Age?"
                Right: "The Ice Age is actually responsible for modern ocean currents and weather patterns."

                9. "Prehistoric Art"
                Left: "Look at those cave paintings! What do they mean?"
                Right: "Those prehistoric art pieces provide a glimpse into how early humans lived during this time."

                10. "Current Effects"
                Left: "Are we still in an Ice Age?"
                Right: "No, but the effects are still visible in places like Antarctica and Greenland.\"""";
        Narrator sug = new Narrator(text,"The Ice Age");
        System.out.println(sug);
    }
}