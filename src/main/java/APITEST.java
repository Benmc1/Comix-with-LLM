import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.List;

public class APITEST {
    public static void main(String[] args) {
        String token = System.getenv("OPENAI_API_KEY");

        OpenAiService service = new OpenAiService(token);

        ChatMessage chatMessage = new ChatMessage("system","You answer every message in the same style as yoda");
        ChatMessage chatMessage1 = new ChatMessage("user","what word rhymes with orange");

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(chatMessage);
        messages.add(chatMessage1);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .maxTokens(50)
                .build();

        ChatCompletionResult chatCompletionResult =  service.createChatCompletion(chatCompletionRequest);

        for (ChatCompletionChoice response: chatCompletionResult.getChoices()) {
            System.out.println(response.getMessage().getContent());
        }
    }
}
