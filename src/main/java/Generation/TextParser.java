package Generation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextParser {

    public static List<List<String>> parseDialogue(String text){
        //divide into individual lines
        String[] lines = text.split("\n");
        List<String> leftLines = new ArrayList<>();
        List<String> rightLines = new ArrayList<>();

        //clean up lines
        for (String string :lines) {
            //remove quotes
            string = string.replaceAll("\"","");

            if(string.contains("Left:") && string.contains("Right:")){
                string = string.split("Left:")[1];
                leftLines.add(string.split("Right:")[0]);
                rightLines.add(string.split("Right:")[1]);
            }else if (string.contains("Left:")){
                string = string.split("Left:")[1];
                string = string.stripLeading();
                leftLines.add(string);
            } else if (string.contains("Right:")) {
                string = string.split("Right:")[1];
                string = string.stripLeading();
                rightLines.add(string);
            }
        }
        return List.of(leftLines,rightLines);
    }

    public static List<String> parseCaptions(String text){
        List<String> captions = new ArrayList<>();
        String[] lines = text.split("\n");
        for (String s : lines) {
           captions.add(s.replaceAll("\"",""));
        }
        return captions;
    }

    public static List<String[]> parseSuggestions(String text){
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
