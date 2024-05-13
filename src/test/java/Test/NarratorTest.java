package Test;

import Generation.Narrator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NarratorTest {
    String text = """
            1. "The Troubles in Northern Ireland"\s
            Left: It's a historical period we must know
            Right: It's just a violent waste of time and blow

            2. "Political and religious differences"
            Left: We must embrace diversity and respect each other's opinions
            Right: It's hard to reconcile when both sides can't find common dominions

            3. "Numerous acts of violence"
            Left: It's a reminder of the human cost of war
            Right: It's just an endless cycle, how can peace be born?

            4. "The Good Friday Agreement"
            Left: A landmark achievement in the face of adversity\s
            Right: It's just the illusion of peace with no real continuity
            """;
    @Test
    void generateCaption() {
        Narrator narrator = new Narrator("The Troubles in Northern Ireland");
        List<String> captions = narrator.generateCaptions(text);
        assertEquals(10,captions.size());
    }
}