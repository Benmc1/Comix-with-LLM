package Generation;

import Main.ConfigurationFile;

import java.util.Arrays;
import java.util.List;

public class Suggestions {
    private List<String[]> suggestions;

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
        if(input.size() != 10) return false;
        for (String[] array : input) {
            if (array.length != 3) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String[] arr :suggestions){
            str.append(Arrays.toString(arr));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        String text = """
                1. "Ancient Rome was founded in 753 BC and lasted for over a millennium until its decline in the 5th century AD."
                Left: "So, professor, what's the first thing we need to know about Ancient Rome?"
                Right: "Well, my dear student, it all started back in 753 BC when two brothers started a city, but they had a falling out and one killed the other. The rest is history, literally."

                2. "The Roman empire expanded greatly, encompassing parts of Europe, Africa, and the Middle East at its height."
                Left: "How big did the Roman Empire get?"
                Right: "The Romans spread their empire so far that they needed maps just to keep track of it all. And if you got lost, well, you wouldn't be the first!"

                3. "The empire was renowned for its engineering feats, including the construction of aqueducts, roads, and public buildings such as the Colosseum."
                Left: "Wow, the Colosseum is massive. How did they build it so long ago?"
                Right: "Oh, they just used a bit of ancient Roman magic. Just kidding! They used clever engineering and a whole lot of manpower, but did you know that they built it in under 10 years?"

                4. "Rome's military prowess played a significant role in its expansion, with well-trained soldiers and advanced weaponry."
                Left: "Did the Romans have a powerful army?"
                Right: "Let me put it this way: if you were going up against the Roman army, you might as well throw in the towel from the start. They had armor, weapons, and training that made their soldiers virtually invincible."

                5. "Latin was the language of Ancient Rome and has had a lasting impact on modern language, including the development of Romance languages."
                Left: "Hey Professor, are we speaking Latin right now?"
                Right: "No, we're not speaking Latin, but you might be surprised to know that Latin has influenced a lot of the languages we speak today, like English, Spanish, Italian...the list goes on."

                6. "The Ancient Roman religion was polytheistic, with many gods and goddesses worshipped and celebrated through festivals and rituals."
                Left: "What gods did the Romans worship?"
                Right: "Too many to count! But some of the famous ones were Jupiter, the god of the sky, and Venus, the goddess of love. They had festivals for them all, too - think Halloween, but with togas."

                7. "Slavery was a widespread practice in Ancient Rome, with slaves forming a significant portion of the population."
                Left: "Why did the Romans have so many slaves?"
                Right: "Well, they needed someone to do all the dirty work, like cooking and cleaning and labor-intensive tasks. It sounds bad by today's standards, but back then it was just the norm."

                8. "Roman society was divided into different classes, with the wealthy patricians holding the most power and influence."
                Left: "I've heard of the rich getting richer, but is it true that the rich got everything in Ancient Rome?"
                Right: "Oh, you bet they did! The rich patricians had everything they could ever want - from fancy clothes to delicious food. Even the queues for the public toilets were divided up by status."

                9. "Famous figures from Ancient Rome include Julius Caesar, Augustus, and Cicero."
                Left: "Wait, did you say Julius Caesar was from Ancient Rome?"
                Right: "Yes, that's right! Julius Caesar was one of the most famous figures from Ancient Rome. He was known for a lot of things, but his salad-dressing skills probably weren't one of them."

                10. "Ancient Roman art and culture heavily influenced Western art, literature, and philosophy, with enduring examples such as the epic poem The Aeneid and the writings of philosophers like Seneca."
                Left: "How did Ancient Roman culture influence the world?"
                Right: "Ancient Rome had a big impact on art, literature, and philosophy. If it weren't for the Romans, we wouldn't have buildings like the Capitol or ideas like Stoicism. Let's just say they were a pretty big deal.\"""";
        Suggestions sug = new Suggestions(text,"Ancient Rome");
        System.out.println(sug);
    }
}
