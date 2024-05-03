package Main;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.embedding.Embedding;
import com.theokanning.openai.embedding.EmbeddingRequest;
import com.theokanning.openai.embedding.EmbeddingResult;
import com.theokanning.openai.service.OpenAiService;
import config.ConfigurationFile;

import java.util.List;

public class API {
    static OpenAiService service = null;
    static public ChatMessage getChatCompletion( List<ChatMessage> Messages) {
        if(service ==  null) service = new OpenAiService(ConfigurationFile.getProperty("API_KEY"));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(ConfigurationFile.getProperty("MODEL"))
                .messages(Messages)
                .maxTokens(Integer.valueOf(ConfigurationFile.getProperty("TOKEN_LIMIT")))
                .build();

        ChatCompletionResult resultsList = service.createChatCompletion(chatCompletionRequest);

        //only ever 1 choice unless stated otherwise in the chatCompletionRequest
        ChatCompletionChoice result = resultsList.getChoices().get(0);
        return result.getMessage();
    }

    static public List<Embedding> getEmbedding(List<String> Message) {
        if(service ==  null) service = new OpenAiService(ConfigurationFile.getProperty("API_KEY"));

        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
                .model(ConfigurationFile.getProperty("EMBEDDINGS_MODEL"))
                .input(Message)
                .build();

        EmbeddingResult result = service.createEmbeddings(embeddingRequest);

        return result.getData();
    }

}
