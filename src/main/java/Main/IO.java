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
        System.out.println("Enter the appearance (male or female):");
        String appearance = scanner.nextLine();
        System.out.println("Enter a hair color:");
        String hairColor = scanner.nextLine();
        System.out.println("Enter a skin color:");
        String skinColor = scanner.nextLine();
        System.out.println("Enter a lip color:");
        String lipColor = scanner.nextLine();
        Character student = new Character("Student");
        student.setFeatures(appearance, hairColor, skinColor, lipColor);
        return student;
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
}
