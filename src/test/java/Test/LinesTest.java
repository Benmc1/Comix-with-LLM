package Test;

import Main.Lines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {

    @Test
    void testNumOfLines() {
        Lines l = new Lines("Towers of sorrow");
        assertEquals(10, l.getAntiLines().size());
        assertEquals(10, l.getProLines().size());
    }

    @Test
    public void test_returnsListOfStringArrays() {
        Lines lines = new Lines("Speed limit");
        List<String[]> suggestions = lines.generateBatchedSuggestions("speed limit");

        assertNotNull(suggestions);
        assertEquals(10, suggestions.size());
        for (String[] suggestion : suggestions) {
            assertEquals(3, suggestion.length);
        }
    }

    @Test
    public void test_handlesInvalidInputPromptAndReturnsEmptyList() {
        Lines lines = new Lines("topic");
        List<String[]> suggestions = lines.generateBatchedSuggestions("invalid_topic");

        assertNotNull(suggestions);
        assertTrue(suggestions.isEmpty());
    }
}