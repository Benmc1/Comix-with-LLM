import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.embedding.Embedding;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.service.OpenAiService;

import java.util.List;

public class API {
    static OpenAiService service = null;
    static String  getChatCompletion(String Token, String Model, List<ChatMessage> Messages){
        if(service ==  null) service = new OpenAiService(Token);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Model)
                .messages(Messages)
                .maxTokens(50)
                .build();

        ChatCompletionResult resultsList = service.createChatCompletion(chatCompletionRequest);

        //only ever 1 choice unless stated otherwise in the chatCompletionRequest
        ChatCompletionChoice result = resultsList.getChoices().get(0);
        return result.getMessage().getContent();
    }

    static List<Embedding> getEmbedding(String Token, String Model, List<String> Message){
        if(service ==  null) service = new OpenAiService(Token);

        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
                .model(Model)
                .input(Message)
                .build();

        EmbeddingResult result = service.createEmbeddings(embeddingRequest);


        return result.getData();
    }

}
