package Main.Interfaces;

import com.theokanning.openai.completion.chat.ChatMessage;

public interface Conversation {
    void addSystemMessage(String message);
    void addMessageAndResponse(String userMessage, String response);
    String getResponse(String message);
    Boolean isDOS (ChatMessage message);
}
