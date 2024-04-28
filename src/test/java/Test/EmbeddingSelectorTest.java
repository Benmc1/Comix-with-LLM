package Test;

import Main.EmbeddingData;
import Main.EmbeddingSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingSelectorTest {

    @Test
    void selectEmbedding() {
        String poseInput = " You know, these historical divisions, while they've caused a lot of pain, also remind us of our roots and the struggles our ancestors went through.";
        String poseAns = "split in two";
        String poseResult = EmbeddingSelector.selectEmbedding(poseInput, EmbeddingData.getPoseEmbeddings());
        assertEquals(poseAns,poseResult);

        String settingInput = "Pro: You know, these historical divisions, while they've caused a lot of pain, also remind us of our roots and the struggles our ancestors went through.\n" +
                "Anti: But don't you think these divisions are just fueling the fire? They're keeping us trapped in the past, preventing any real progress towards unity.";
        String settingAns = "debate stage";
        String settingResult = EmbeddingSelector.selectEmbedding(settingInput, EmbeddingData.getSettingEmbeddings());
        assertEquals(settingAns,settingResult);
    }


}