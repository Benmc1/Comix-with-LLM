package Test;

import Databases.EmbeddingSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmbeddingSelectorTest {

    @Test
    void selectEmbedding() {
        String poseInput = "thinking hard";
        String poseAns = "thinking";
        String poseResult = EmbeddingSelector.getRelevantChoice(poseInput,"pose");
        assertEquals(poseAns,poseResult);

        String settingInput = "putting on a show";
        String settingAns = "stage curtains";
        String settingResult = EmbeddingSelector.getRelevantChoice(settingInput,"setting");
        assertEquals(settingAns,settingResult);
    }
    @Test
    void selectOddEmbedding() {
        String randomPose = "Lighting a memorial candle";
        String ans = "terrorizing";
        String result = EmbeddingSelector.getRelevantChoice(randomPose,"pose");
        assertEquals(ans,result);
    }

}