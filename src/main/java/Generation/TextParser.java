package Generation;

import java.util.ArrayList;
import java.util.List;

public class TextParser {

    public static List<String[]> parseDialogue(String text){
        //divide into individual lines
        String[] lines = text.split("\n");
        System.out.println(text);

        //clean up lines
        for (String string :lines) {
            if(string.contains("Pro:") && string.contains("Anti:")){
                string = string.split("Pro:")[1];
                proLines.add(string.split("Anti:")[0]);
                antiLines.add(string.split("Anti:")[1]);
            }else if (string.contains("Pro:")){
                string = string.split("Pro:")[1];
                string = string.stripLeading();
                proLines.add(string);
            } else if (string.contains("Anti:")) {
                string = string.split("Anti:")[1];
                string = string.stripLeading();
                antiLines.add(string);
            }
        }
    }

    public static void parseCaptions(String text){

    }

    public static void parseSuggestions(String text){
        List<String[]> parsedSuggestions = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            int startIndex = line.indexOf("(");
            int endIndex = line.lastIndexOf(")");
            if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                String content = line.substring(startIndex + 1, endIndex);
                parsedSuggestions.add(content.split(", "));
            } else {
                System.out.println("Skipping invalid line: " + line);
            }
        }
        return parsedSuggestions;
    }
}
