package Test;

import Assests.AssetSelector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssetSelectorTest {

    @Test
    void selectEmbedding() {
        String poseInput = "thinking hard";
        String poseAns = "thinking";
        String poseResult = AssetSelector.getRelevantChoice(poseInput,"pose");
        assertEquals(poseAns,poseResult);

        String settingInput = "putting on a show";
        String settingAns = "stage curtains";
        String settingResult = AssetSelector.getRelevantChoice(settingInput,"setting");
        assertEquals(settingAns,settingResult);
    }
    @Test
    void selectOddEmbedding() {
        String randomPose = "Lighting a memorial candle";
        String ans = "terrorizing";
        String result = AssetSelector.getRelevantChoice(randomPose,"pose");
        assertEquals(ans,result);
    }

}