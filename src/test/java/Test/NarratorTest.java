package Test;

import Generation.Narrator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NarratorTest {

    @Test
    void generateCaption() {
        Narrator narrator = new Narrator("the weather");
        String answer = narrator.generateCaption("the weather is good","the weather is awful");
        assertNotNull(answer);
    }
}