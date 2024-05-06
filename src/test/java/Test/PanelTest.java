package Test;

import Comic.Panel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanelTest {

    @Test
    void string() {
        String[] lines = {"Hello there", "How are you", "This is a test"};
        String[] suggestions = {"happy","sad","Manhattan project"};
        Panel panel = new Panel("tom","jack", lines, suggestions);
        String xmlAns = "Panel{charLeft='tom', charRight='jack', lines=[Hello there, How are you, This is a test], poseLeft='joy', poseRight='sad', setting='manhattan'}";

        assertEquals(xmlAns,panel.toString());
    }

    @Test
    void testSettingGeneration() {
        String[] lines = {"Hello", "Goodbye"};
        String[] suggestions = {"happy","sad","Manhattan project"};
        Panel panel = new Panel("Alice", "Bob", lines, suggestions);
        assertEquals("manhattan", panel.getSetting());
    }
}