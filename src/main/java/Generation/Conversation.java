package Generation;

import Main.API;
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
        if(isDOS(responseMessage)) return "";
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
    public Boolean isDOS(ChatMessage message) {
        List<String> denialPhrases = Arrays.asList(
                "unable to comply with that request",
                "can't assist with",
                "I don't have the ability to",
                "I'm not able to",
                "unable to complete your request",
                "an AI language model",
                "cannot mimic people"
        );
        for (String phrase : denialPhrases) {
            if (message.getContent().contains(phrase)) {
                return true;
            }
        }
        return false;
    }
    public void removeLastExchange(){
        int size = messageList.size();
        if(size < 2) return;
        messageList.remove(size-1);
        messageList.remove(size-2);
    }
}
