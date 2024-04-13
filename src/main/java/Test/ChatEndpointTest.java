package Test;

import Main.Main;
import Main.Conversation;
import Main.API;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ChatEndpointTest {

    @Mock
    private OpenAiService openAiService;

    private API api;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        api = new API(openAiService);
    }

    @Test
    public void testChatCompletionEndpoint() {
        // Mock the behavior of OpenAiService to return a mock ChatCompletionResult
        ChatCompletionResult mockResult = new ChatCompletionResult();
        when(openAiService.createChatCompletion()).thenReturn(mockResult);

        // Invoke the chat completion endpoint method with test input
        ChatCompletionResult result = api.getChatCompletion();

        // Assert that the result is as expected
        assertEquals(mockResult, result);
    }
}
