package Test;

import Main.Conversation;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversationTest {

    @Test
    void addSystemMessage() {
        Conversation conversation = new Conversation();
        assertEquals(0,conversation.getMessageList().size());
        conversation.addSystemMessage("Test system message");
        ChatMessage message = conversation.getMessageList().get(0);
        assertEquals("system", message.getRole());
        assertEquals("Test system message", message.getContent());
    }

    @Test
    void addMessageAndResponse() {
        Conversation conversation = new Conversation();
        assertEquals(0,conversation.getMessageList().size());
        conversation.addMessageAndResponse("Test user message","Test response");
        assertEquals(2,conversation.getMessageList().size());
    }

    @Test
    void isDOS() {
        ChatMessage message = new ChatMessage("assistant","As an AI language model, I don't have personal opinions, but I can provide an analysis based on available information up to my last update in January 2022.");

        assertTrue(new Conversation().isDOS(message));
    }
}