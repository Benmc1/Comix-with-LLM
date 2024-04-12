package Test;

import Main.Main;
import Main.API;
import Main.Conversation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EmbeddingsTest {

    @Mock
    private OpenAiService openAiService;

    private API api;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        api = new API(openAiService);
    }

    @Test
    public void testEmbeddingsEndpoint() {
        // Mock the behavior of OpenAiService to return a mock EmbeddingResult
        EmbeddingResult mockResult = new EmbeddingResult();
        when(openAiService.createEmbeddings()).thenReturn(mockResult);

        // Invoke the embeddings endpoint method with test input
        EmbeddingResult result = api.getEmbeddings();

        // Assert that the result is as expected
        assertEquals(mockResult, result);
    }
}
