/*package Test;

import Main.EmbeddingData;
import com.theokanning.openai.embedding.Embedding;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingDataTest {

    @Test
    void getPoseEmbeddings() {
        Embedding e = EmbeddingData.getPoseEmbeddings().get(0);
        assertEquals("treating",e.getObject());
        int size = EmbeddingData.getPoseEmbeddings().size();
        assertEquals(526,size);
    }

    @Test
    void getSettingEmbeddings() {
        Embedding e = EmbeddingData.getSettingEmbeddings().get(0);
        assertEquals("stalactite",e.getObject());
        int size = EmbeddingData.getSettingEmbeddings().size();
        assertEquals(294,size);
    }
}

 */