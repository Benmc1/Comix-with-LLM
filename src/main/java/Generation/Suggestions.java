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
            str.append(Arrays.toString(arr)).append("\n");
        }
        return str.toString();
    }

    public static void main(String[] args) {
        String text = """
                                
                1. "Drug Trade Worth Over $300 Billion"
                Left: A market that's strong and full of wealth, it brings jobs and motivates good health.
                Right: A market that stands on human pain, addiction, death, and hopelessness gain.
                                
                2. "Opium Poppy as a Primary Source for Illicit Heroin"
                Left: A natural ingredient, grown to cure, a blocker to pain that is often obscure.
                Right: A root with a grip that weighs heavy, it pulls, it grips, it's always unsteady.
                                
                3. "Criticism of Punishment Over Treatment in the War on Drugs"
                Left: A focus on helping, not punishing, opportunities arise, solutions broadening.
                Right: A focus on coddling, without discipline, weakens resolve, leading to further sin.
                                
                4. "Demand for Marijuana, Cocaine, and Prescription Opioids"
                Left: Natural plants, pain relief, good times, the people's will should never be declined.
                Right: Merging with evil, it destroys lives, promotes chaos and malcontented vibes.
                                
                5. "Links with Violence and Corruption"
                Left: Pushing all aside, with it comes strength, breaking barriers, crossing lands and length.
                Right: Tearing families apart, neighborhoods in fear, leaving instability and the future unclear.
                                
                6. "Rising Overdose Deaths Related to Opioids"
                Left: Cry for help and see new light, prevention is key with insight.
                Right: A signal to stop, it's time for change, let's rally the people, a shift in range.
                                
                7. "Decriminalization and Legalization of Drugs"
                Left: Freedom of choice, to do and to grow, medicinal value, public disclosure and more.
                Right: Society's destruction, common good left out, a nation unchecked, chaos about.
                                
                8. "Sophisticated Methods to Avoid Detection and Apprehension"
                Left: The brilliance of their schemes, avoiding hatred and escaping law enforcement teams.
                Right: Cheating at the game, slipping through the cracks, spreading poison and driving the ax.
                                
                9. "Narco-States"
                Left: Ruling the world with one simple plan, to share with the people and lend them a hand.
                Right: The rich get richer, the poor stay the same, justice for all but they love to play the blame.
                                
                10. "Long-Standing Effects on Society"
                Left: A challenge to overcome, a difficult task, we learn from our failings as we move past.
                Right: A sad song of failure, a burden too great, a toxin that poisons far past the state.""";
        Suggestions sug = new Suggestions(text,"The drug trade");
        System.out.println(sug);
    }
}
