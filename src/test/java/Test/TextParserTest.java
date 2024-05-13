package Test;

import Generation.TextParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TextParserTest {

    @Test
    void parseDialogue() {
        String text = """
                1. "The Troubles in Northern Ireland"\s
                Left: It's a historical period we must know
                Right: It's just a violent waste of time and blow

                2. "Political and religious differences"
                Left: We must embrace diversity and respect each other's opinions
                Right: It's hard to reconcile when both sides can't find common dominions

                """;
        List<List<String>> dialogue = TextParser.parseDialogue(text);
        //should be 2. one for left and one for right
        assertEquals(2, dialogue.size());

        assertEquals(2, dialogue.get(0).size());
        assertEquals("It's just a violent waste of time and blow", dialogue.get(1).get(0));
        assertEquals("We must embrace diversity and respect each other's opinions", dialogue.get(0).get(1));

        String singleLine = "1. The Troubles in Northern Ireland Left: It's a historical period we must know Right: It's just a violent waste of time and blow";
        List<List<String>> singleDialogue = TextParser.parseDialogue(singleLine);
        assertEquals(2, singleDialogue.size());

        assertEquals(1, singleDialogue.get(0).size());
        assertEquals("It's a historical period we must know", singleDialogue.get(0).get(0));
        assertEquals("It's just a violent waste of time and blow", singleDialogue.get(1).get(0));

    }

    @Test
    void parseCaptions() {
        String text = """
                1. Troubles in Ireland, a dark time, we remember.
                2. Differences so deep, respect for opinions needed, unity we should engender.
                323. Violence begets violence, a cycle of pain.
                4323123123. The Good Friday Accord, a glimmer of hope, but can it be sustained?
                --5. "Past mistakes still haunt us, truth must be faced.\"""";
        List<String> captions = TextParser.parseCaptions(text);
        assertEquals(5, captions.size());
        assertEquals("Troubles in Ireland, a dark time, we remember.", captions.get(0));
        assertEquals("Past mistakes still haunt us, truth must be faced.", captions.get(4));
    }

    @Test
    void parseSuggestions() {

    }
}