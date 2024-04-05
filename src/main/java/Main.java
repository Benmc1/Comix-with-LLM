import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.embedding.Embedding;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your OpenAI API token:");
        String token = scanner.nextLine();

        System.out.println("Enter the model (e.g., gpt-3.5-turbo):");
        String model = scanner.nextLine();

        System.out.println("Enter the prompt for chat completion:");
        String prompt = scanner.nextLine();

        ChatMessage chatMessage = new ChatMessage("user", prompt);

        String chatCompletionResponse = API.getChatCompletion(token, model, List.of(chatMessage));
        System.out.println("Chat Completion Response: " + chatCompletionResponse);

        System.out.println("Enter the text for which you want embeddings:");
        String text = scanner.nextLine();

        List<String> messages = new ArrayList<>();
        messages.add(text);

        List<Embedding> embeddings = API.getEmbedding(token, "text-embedding-ada-002", messages);
        System.out.println("Embeddings: " + embeddings);
    }
}
