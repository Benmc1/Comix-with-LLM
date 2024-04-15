package Test;

import Main.Lines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {

    @Test
    void testNumOfLines() {
    Lines l = new Lines("Towers of sorrow",5);
    assertEquals(5, l.getAntiLines().size());
    assertEquals(5, l.getProLines().size());
    }
}