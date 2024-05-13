package Generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser {

    public static List<List<String>> parseDialogue(String text){
        //divide into individual lines
        String[] lines = extractLines(text);
        List<String> leftLines = new ArrayList<>();
        List<String> rightLines = new ArrayList<>();

        //clean up lines
        for (String string :lines) {
            if(string.contains("Left:") && string.contains("Right:")){
                String left = string.split("Left:")[1].split("Right:")[0].trim();
                String right = string.split("Right:")[1].trim();
                leftLines.add(left);
                rightLines.add(right);
            }else if (string.contains("Left:")){
                String left = string.split("Left:")[1].trim();
                leftLines.add(left);
            } else if (string.contains("Right:")) {
                String right = string.split("Right:")[1].trim();
                rightLines.add(right);
            }
        }
        return List.of(leftLines,rightLines);
    }

    public static List<String> parseCaptions(String text){
        List<String> captions = new ArrayList<>();
        String[] lines = extractLines(text);
        for (String s : lines) {
            int begining = s.indexOf(".");
            s = s.substring(begining+1);
            s = s.stripLeading();
            captions.add(s);
        }
        return captions;
    }

    public static List<String[]> parseSuggestions(String text){
        List<String[]> parsedSuggestions = new ArrayList<>();
        String[] lines = extractLines(text);

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
    private static String[] extractLines(String text){
        String[] lines = text.split("\n");
        //remove quotes
        return Arrays.stream(lines).map(string -> string.replaceAll("\"",""))
                .toArray(String[]::new);
    }
}
