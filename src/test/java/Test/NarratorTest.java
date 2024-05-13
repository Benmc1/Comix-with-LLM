package Test;

import Generation.Narrator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NarratorTest {
    @Test
    void generateCaption() {
        Narrator narrator = new Narrator("Ancient Rome");
        List<String> captions = narrator.generateCaptions(text);
        System.out.println(captions);
        assertEquals(10,captions.size());
    }
    String text = """
            1. "Founding of Ancient Rome"
            Left: "How did this great city come into being?"
            Right: "Well, according to legend, the story goes like this. Romulus, and his twin brother Remus, were abandoned at birth and raised by a she-wolf. When they grew up, Romulus became the first king of Rome."

            2. "Shift from Monarchy to Republic"
            Left: "Why did Rome become a republic?"
            Right: "The Roman people became fed up with the harsh rule of the kings, so they decided to have a government that was more democratic and responsive to their needs."

            3. "Power of the Roman Military"
            Left: "Why was Rome so successful in conquering lands?"
            Right: "Ah yes, the Roman Legions. They had superior weapons and unmatched military tactics that made them almost invincible on the battlefield. Plus, they always had a well-stocked pantry."

            4. "Role of Julius Caesar"
            Left: "Who was Julius Caesar and why did he matter?"
            Right: "Well, he was a brilliant general and politician who rose to power during the end of the Republic. He was so popular that he became a dictator and changed the course of Roman history forever. Et tu, Brute?"

            5. "Rise of the Roman Empire"
            Left: "What happened after the fall of the Republic?"
            Right: "Augustus, Julius Caesar's great-nephew, became the first emperor of Rome and ushered in a new era of stability and prosperity."

            6. "Iconic Colosseum"
            Left: "What is that enormous building over there?"
            Right: "My dear student, that is the Colosseum – a massive amphitheater used for gladiator battles, animal hunts and all sorts of public spectacles. It's like the Roman equivalent of modern sport arenas."

            7. "Engineering feats of ancient Rome"
            Left: "How did the Romans manage to build all of this?"
            Right: "Ah, the wonders of Roman engineering – aqueducts to transport water from distant sources, roads to connect far-flung regions, and public baths to keep us all clean and refreshed. Just don't drink the bathwater."

            8. "Spread of Christianity"
            Left: "What religion were the Romans?"
            Right: "Well, for a long time, Romans worshipped the gods of the pantheon. But Christianity emerged and eventually became the dominant religion throughout the empire. And I'm sure St. Peter’s Basilica is going to be a big hit one day!"

            9. "Fall of the Roman Empire"
            Left: "What led to the downfall of Rome?"
            Right: "Well, there's no one answer to that, but factors such as political corruption, economic instability, and invasions by barbarian tribes played a role in the fall. And really, who wants to be emperor during a recession, am I right?"

            10. "Legacy of Ancient Rome"
            Left: "In what ways has Rome influenced modern society?"
            Right: "Oh, in countless ways – from the Roman alphabet to our legal system. Even in fashion, togas and sandals will always be a classic look. Honestly, who needs sneakers when you can wear gladiator sandals?"
            """;
}