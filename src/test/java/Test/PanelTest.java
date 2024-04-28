package Test;

import Main.Panel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanelTest {

    @Test
    void string() {
        String[] lines = {"Hello there", "How are you", "This is a test"};
        Panel panel = new Panel("tom","jack", lines);
        String xmlAns = "Panel{charLeft='tom', charRight='jack', lines=[Hello there, How are you, This is a test], poseLeft='greeting', poseRight='shaking hands', setting='hollywood'}";

        assertEquals(xmlAns,panel.toString());
    }
}