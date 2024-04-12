package Main;

import com.theokanning.openai.completion.chat.ChatMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Conversation implements Interfaces.Conversation {
    private final List<ChatMessage> messageList = new ArrayList<>();

    public void addSystemMessage(String message) {
        messageList.add(new ChatMessage("system", message));
    }

    public void addMessageAndResponse(String userMessage, String response) {
        messageList.add(new ChatMessage("user", userMessage));

        messageList.add(new ChatMessage("assistant", response));
    }

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
    public Boolean isDOS(String message) {
        List<String> denialPhrases = Arrays.asList(
                "I'm sorry, but I can't",
                "unable to comply with that request",
                "can't assist with",
                "I don't have the ability to",
                "I'm not able to",
                "unable to complete your request"
        );
        for (String phrase : denialPhrases) {
            if (message.contains(phrase)) {
                return true;
            }
        }

        return false;
    }
}
