package Test;

import org.junit.jupiter.api.Test;
import Comic.Character;
import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {

    @Test
    void testRandomGenerate() {
        Character character = new Character("");
        assertNotEquals("",character.getName());
        assertFalse(character.toString().contains("null"));
    }
}