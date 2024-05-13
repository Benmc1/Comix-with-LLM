package Test;

import Generation.Lines;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinesTest {
    Lines lines = new Lines();
    @Test
    void testNumOfLines() {
        lines.addLeftLines(List.of("How are you", "what do you want to do"));
        assertEquals(2, lines.getLeftLines().size());
        assertEquals(0, lines.getRightLines().size());
        lines.addRightLines(List.of("Im doing well", "lets go to the beach"));

        assertEquals(2, lines.getRightLines().size());

        assertEquals("How are you", lines.getLeftLine(0));
    }

    @Test
    void testGetPanelLines(){
        lines.addLeftLines(List.of("How are you", "what do you want to do"));
        lines.addRightLines(List.of("Im doing well", "lets go to the beach"));
        lines.addCaptions(List.of("Start of the day","Going to the beach"));
        String[] panelLines = lines.getPanelLines(1);
        assertEquals("what do you want to do", panelLines[0]);
        assertEquals("lets go to the beach", panelLines[1]);
        assertEquals("Going to the beach", panelLines[2]);
    }
}