package Main;

import java.util.Scanner;

public class IO {
    public static String getTopic(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the prompt for chat completion:");
        String prompt = scanner.nextLine();
        //Possibility of text validation
        return prompt;
    }
    public static String getCharacterName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the character you want in the comic.");
        String prompt = scanner.nextLine();
        return prompt;
    }
}
