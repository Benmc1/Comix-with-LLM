package Interfaces;

public interface Conversation {
    void addSystemMessage(String message);
    void addMessageAndResponse(String userMessage, String response);
    String getResponse(String message);
    Boolean isDOS (String message);
}
