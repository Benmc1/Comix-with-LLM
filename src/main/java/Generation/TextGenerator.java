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
        Conversation conversation = new Conversation();


        conversation.addSystemMessage(ConfigurationFile.getProperty("SYSTEM_PROMPT"));
        String pointsPrompt = "Write a list of " + NUM_PANELS + " points about "+ topic
                + ". Theses points should be concise and benign and interesting.";
        String pointsList = conversation.getResponse(pointsPrompt);

        if(pointsList.isEmpty()) {
            System.out.println("generating points returned a DOS moving to backup");
            conversation.addMessageAndResponse(ConfigurationFile.getProperty(mode.toString() + "_DOS_PROMPT"),ConfigurationFile.getProperty(mode.toString() + "_DOS_RESPONSE"));
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
        Conversation conversation = new Conversation();
        String conversationResponse = conversation.getResponse(text + "\n"
                + ConfigurationFile.getProperty("SUGGESTIONS_PROMPT")
                + "The topic of the comic is: " + topic);
        suggestions = TextParser.parseSuggestions(conversationResponse);
    }

    public static void main(String[] args) {

        Narrator n = new Narrator("ancient rome");
        String text = "\n" +
                "1. \"Founding of Ancient Rome\"\n" +
                "Left: \"Professor, how did Rome begin?\"\n" +
                "Right: \"Legend has it, two brothers fought, one built a city, and voilà!\"\n" +
                "\n" +
                "2. \"Size of the Roman Empire\"\n" +
                "Left: \"Professor, just how big was the Roman Empire?\"\n" +
                "Right: \"Big enough to span three continents and rock a population of 100 million!\"\n" +
                "\n" +
                "3. \"Famous Ancient Roman Inventions\"\n" +
                "Left: \"Professor, did the Romans invent anything cool?\"\n" +
                "Right: \"Yes, they did! Aqueducts, concrete, and the arch. Pretty impressive, right?\"\n" +
                "\n" +
                "4. \"Julius Caesar's Assassination\"\n" +
                "Left: \"Professor, who killed Julius Caesar?\"\n" +
                "Right: \"A bunch of senators who weren't too fond of his growing power.\"\n" +
                "\n" +
                "5. \"The Colosseum\"\n" +
                "Left: \"Professor, what is that big structure over there?\"\n" +
                "Right: \"That's the Colosseum. Could hold up to 80,000 people for gladiator fights!\"\n" +
                "\n" +
                "6. \"Portrayal of Emperors\"\n" +
                "Left: \"Professor, why do all the statues of emperors look the same?\"\n" +
                "Right: \"Artistic exaggeration, my dear student. They wanted to look powerful and noble.\"\n" +
                "\n" +
                "7. \"Fall of the Roman Republic\"\n" +
                "Left: \"Professor, when did the Roman Republic become an Empire?\"\n" +
                "Right: \"In 27 BC, Octavian became Rome's first emperor and took on the name Augustus.\"\n" +
                "\n" +
                "8. \"The Roman Army\"\n" +
                "Left: \"Professor, why is everyone marching in such a disciplined way?\"\n" +
                "Right: \"Ah, that's the Roman Army. They were famous for their discipline and advanced tactics.\"\n" +
                "\n" +
                "9. \"Roman Legal System\"\n" +
                "Left: \"Professor, what was the Roman legal system like?\"\n" +
                "Right: \"Complex, precise, and influential to modern legal systems.\"\n" +
                "\n" +
                "10. \"Roman Conquests\"\n" +
                "Left: \"Professor, how did ancient Rome become such a powerful empire?\"\n" +
                "Right: \"They were skilled militarists and invaded much of Europe, North Africa, and the Middle East!\"";

        n.generateCaptions(text);
    }
}


