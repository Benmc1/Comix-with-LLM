package Main;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class Conversation implements Interfaces.Conversation {
    private final List<ChatMessage> messageList = new ArrayList<>();

    @Override
    public void addSystemMessage(String message) {
        messageList.add(new ChatMessage("system", message));
    }

    @Override
    public void addMessageAndResponse(String userMessage, String response) {
        messageList.add(new ChatMessage("user", userMessage));

        messageList.add(new ChatMessage("assistant", response));
    }

    @Override
    public String getResponse(String message) {
        messageList.add(new ChatMessage("user", message));
        ChatMessage responseMessage = API.getChatCompletion(messageList);
        messageList.add(responseMessage);

        return responseMessage.getContent();
    }

    public List<ChatMessage> getMessageList() {
        return messageList;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "messageList=" + messageList +
                '}';
    }

    @Override
    //#TODO write is Denial of Service
    public Boolean isDOS(String message) {
        return null;
    }
}
