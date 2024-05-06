package Comic;

import Assests.AssetSelector;

import java.util.Arrays;

public class Panel {
    private final String charLeft;
    private final String charRight;
    //0 = left, 1 = right, 2 = caption
    private final String[] lines;
    private String poseLeft;
    private String poseRight;
    private String setting;

    public Panel(String charLeft, String charRight, String[] lines, String[] suggestions) {
        this.charLeft = charLeft;
        this.charRight = charRight;
        this.lines = lines;
        generatePoses(suggestions);
        generateSetting(suggestions);
    }

    private void generatePoses(String[] suggestions){
        poseLeft = AssetSelector.getRelevantChoice(suggestions[0], "pose");
        poseRight = AssetSelector.getRelevantChoice(suggestions[1], "pose");
    }

    private void generateSetting(String[] suggestions){
        setting = AssetSelector.getRelevantChoice(suggestions[2], "setting");
    }

    public String getSetting() {
        return this.setting;
    }

    @Override
    public String toString() {
        return "Panel{" +
                "charLeft='" + charLeft + '\'' +
                ", charRight='" + charRight + '\'' +
                ", lines=" + Arrays.toString(lines) +
                ", poseLeft='" + poseLeft + '\'' +
                ", poseRight='" + poseRight + '\'' +
                ", setting='" + setting + '\'' +
                '}';
    }

    public String toXML() {
        return "<panel>\n" +
                "   <above>" + lines[2] + "</above>\n"+
                "   <left>\n"+
                "       <figure>\n"+
                "           <name>" + charLeft + "</name>\n"+
                "           <pose>" + poseLeft + "</pose>\n"+
                "           <facing>right</facing>\n"+
                "       </figure>\n\n"+
                        speechBalloon(lines[0])+
                "   </left>\n\n"+
                "   <right>\n"+
                "       <figure>\n"+
                "           <name>" + charRight + "</name>\n"+
                "           <pose>" + poseRight + "</pose>\n"+
                "           <facing>left</facing>\n"+
                "       </figure>\n\n"+
                        speechBalloon(lines[1])+
                "   </right>\n\n"+
                "   <setting>" + setting + "</setting>\n"+
                "</panel>";
    }

    private String speechBalloon(String speech) {
        return "\t\t<balloon status=\"speech\">\r\t\t\t<content>" + speech + "</content>\n\t\t</balloon>\n";
    }
}
