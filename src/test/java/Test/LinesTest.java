package Test;

import Main.Lines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {

    @Test
    void testNumOfLines() {
        Lines l = new Lines("Towers of sorrow");
        assertEquals(10, l.getAntiLines().size());
        assertEquals(10, l.getProLines().size());
    }
}