package Main;

import Comic.Character;
import Comic.Comic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IO {
    public static String getTopic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the prompt for chat completion:");
        return scanner.nextLine();
    }

    public static Character createStudentCharacter() {
        System.out.println("Choose what the student character will look like");
        Scanner scanner = new Scanner(System.in);

        String appearance = getInputWithValidation(scanner, "Enter the appearance (male or female):", new String[]{"male", "female"});
        String hairColor = getInputWithValidation(scanner, "Enter a hair color:", new String[]{"brown", "blonde", "black", "white", "red", "blue", "green", "purple", "pink", "yellow", "orange", "gray"}); 
        String skinColor = getInputWithValidation(scanner, "Enter a skin color:", new String[]{"white", "black", "yellow", "pink", "purple", "blue", "green", "brown", "red", "gray"});
        String lipColor = getInputWithValidation(scanner, "Enter a lip color:", new String[]{"white", "black", "yellow", "pink", "purple", "blue", "green", "brown", "red", "gray"});

        Character student = new Character("Student");
        student.setFeatures(appearance, hairColor, skinColor, lipColor);
        return student;
    }

    private static String getInputWithValidation(Scanner scanner, String prompt, String[] validResponses) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        if (validResponses != null) {
            while (!isValidResponse(input, validResponses)) {
                System.out.println("Invalid input.\nPlease enter one of the following:\n" + String.join(", ", validResponses));
                input = scanner.nextLine();
            }
        } else {
            while (input.isBlank()) {
                System.out.println("Input cannot be blank.\nPlease enter a valid input:");
                input = scanner.nextLine();
            }
        }
        return input;
    }

    private static boolean isValidResponse(String input, String[] validResponses) {
        for (String validResponse : validResponses) {
            if (input.equalsIgnoreCase(validResponse)) {
                return true;
            }
        }
        return false;
    }

    public static List<String[]> readPlainData() {
        List<String[]> PoseData = new ArrayList<>();
        String file = ConfigurationFile.getProperty("PLAIN_DATA");

        try (Scanner scanner = new Scanner(new File(file))) {
            //skip headers
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split("\t");

                //if there arnt any descriptions skip
                if(values.length != 3) continue;

                String[] descriptions = values[2].split(",");
                for (String s : descriptions) {
                    s = s.stripLeading();
                    if(!s.isBlank()) {
                        String[] newline = {s, values[1], values[0]};
                        PoseData.add(newline);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(file + " not found, cannot create embeddings data");
        }
        return PoseData;
    }
    public static Comic.Mode chooseMode(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                Choose between History and Debate mode.(type the number)
                1.History
                2.Debate.""");
        String input = scanner.next();
        while (!input.equals("1") && !input.equals("2")){
            System.out.println("Invalid input type 1 or 2.");
            input = scanner.next();
        }
        return input.equals("1") ? Comic.Mode.HISTORY : Comic.Mode.DEBATE ;
    }

    public static void main(String[] args) {
        Character studentCharacter = createStudentCharacter();
        System.out.println("Created character details:");
    }
}
