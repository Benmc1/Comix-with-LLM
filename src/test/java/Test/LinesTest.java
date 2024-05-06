package Test;

import Main.Lines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {
    Lines lines = new Lines("Speed limit");
    @Test
    void testNumOfLines() {

        assertEquals(10, lines.getAntiLines().size());
        assertEquals(10, lines.getProLines().size());
    }

    @Test
    public void test_returnsListOfStringArrays() {
        List<String[]> suggestions = lines.generateBatchedSuggestions();

        assertNotNull(suggestions);
        assertEquals(10, suggestions.size());
        for (String[] suggestion : suggestions) {
            assertEquals(3, suggestion.length);
        }
    }
}