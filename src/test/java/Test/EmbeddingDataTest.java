package Test;


import Assests.EmbeddingDatabase;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmbeddingDataTest {
    EmbeddingDatabase embeddingDatabase = new EmbeddingDatabase();

    @Test
    void getByIndex(){
        assertNull(embeddingDatabase.getByIndex(-11));

        assertNull(embeddingDatabase.getByIndex(9999999));
    }

}