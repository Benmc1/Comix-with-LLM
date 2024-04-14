package Test;

import Main.Conversation;
import com.theokanning.openai.completion.chat.ChatMessage;

public class TestChatEndpoint {
    public static void main(String[] args) {
        Conversation conversation = new Conversation();

        // Test chat completion
        String prompt = "How are you?";
        String response = conversation.getResponse(prompt);
        System.out.println("Chat Completion Response: " + response);

        // Test denial of service  // Remember to change message
        ChatMessage denialOfServiceMessage = new ChatMessage("assistant","Sorry, as a language model I am unable to comply with that request.");
        boolean isDos = conversation.isDOS(denialOfServiceMessage);
        System.out.println("Is Denial of Service: " + isDos);
    }
}
