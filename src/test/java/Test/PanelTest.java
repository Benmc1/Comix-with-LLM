package Test;

import Main.Panel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanelTest {

    @Test
    void string() {
        String[] lines = {"Hello there", "How are you", "This is a test"};
        Panel panel = new Panel("tom","jack", lines, "hollywood");
        String xmlAns = "Panel{charLeft='tom', charRight='jack', lines=[Hello there, How are you, This is a test], poseLeft='greeting', poseRight='shaking hands', setting='hollywood'}";

        assertEquals(xmlAns,panel.toString());
    }

    @Test
    void testSettingGeneration() {
        String[] lines = {"Hello", "Goodbye"};
        Panel panel = new Panel("Alice", "Bob", lines, "Manhattan project");
        assertEquals("manhattan", panel.getSetting()); 
    }
}