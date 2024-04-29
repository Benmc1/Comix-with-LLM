package Main;

import java.util.Arrays;

public class Panel {
    private final String charLeft;

    private final String charRight;
    //0 = left, 1 = right, 2 = caption
    private final String[] lines;
    private String poseLeft;
    private String poseRight;

    private String setting;

    public Panel(String charLeft, String charRight, String[] lines, String topic) {
        this.charLeft = charLeft;
        this.charRight = charRight;
        this.lines = lines;
        generatePoses();
        generateSetting(topic);
    }

    private void generatePoses(){
        poseLeft = EmbeddingSelector.selectEmbedding(lines[0],EmbeddingData.getPoseEmbeddings());
        poseRight = EmbeddingSelector.selectEmbedding(lines[1],EmbeddingData.getPoseEmbeddings());
    }
    private void generateSetting(String topic){
        String input = String.join("", lines) + " " + topic;
        setting = EmbeddingSelector.selectEmbedding(input, EmbeddingData.getSettingEmbeddings());
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

    public String toXML(){
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
    private String speechBalloon(String speech){
        return "\t\t<balloon status=\"speech\">\r\t\t\t<content>" + speech + "</content>\n\t\t</balloon>\n";
    }
}
