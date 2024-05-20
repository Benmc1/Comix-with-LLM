package Test;

import Comic.Comic;
import Generation.TextGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TextGeneratorTest {
    TextGenerator t = new TextGenerator("Ancient rome", Comic.Mode.HISTORY);
    @Test
    void getLines() {
        assertNotNull(t.getLines());
        assertEquals(10, t.getLines().getLeftLines().size());

    }

    @Test
    void getSuggestions() {
        assertNotNull(t.getSuggestions());
        assertEquals(10, t.getSuggestions().size());

        for (String[] suggestion : t.getSuggestions()) {
            assertEquals(3, suggestion.length);
        }
    }
}