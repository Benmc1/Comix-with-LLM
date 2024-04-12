package Test;

import Main.Conversation;
import Main.API;
import com.theokanning.openai.embedding.Embedding;

import java.util.ArrayList;
import java.util.List;

public class TestEmbeddings {
    public static void main(String[] args) {
        Conversation conversation = new Conversation();

        // Test embeddings  // Change text/prompt if want to
        String text = "Hello, how are you?";
        List<String> messages = new ArrayList<>();
        messages.add(text);

        List<Embedding> embeddings = conversation.getEmbedding(messages);
        System.out.println("Embeddings: " + embeddings);
    }
}
