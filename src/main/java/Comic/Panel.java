package Comic;

import Assests.AssetSelector;
import Main.ConfigurationFile;

import java.util.Arrays;

public class Panel {
    private final String charLeft;
    private final String charRight;
    //0 = left, 1 = right, 2 = caption
    private final String[] lines;
    private String poseLeft;
    private String poseRight;
    private String setting;
    //Max size a caption can be before being split above and below.
    private static final int MAX_CAPTION_PER_LINE = Integer.parseInt(ConfigurationFile.getProperty("MAX_CAPTION_PER_LINE"));
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

    public static Panel OpeningPanel() {
        String charLeft = "Professor";
        String charRight = "Student";
        String[] lines = {
            "Join our heroes on a laugh-out-loud journey through the ages!",
            "Where every era brings new comedic chaos!",
            "The Hilarious History Adventures",
            "Time Traveling Hijinks Await!"
        };
        String[] suggestions = {
            "waving", "watching the clock", "historical setting"
        };

        return new Panel(charLeft, charRight, lines, suggestions);
    }

    public static Panel ClosingPanel() {
        String charLeft = "Professor";
        String charRight = "Student";
        String[] lines = {
            "Laugh until you cry with this timeless comedy!",
            "Available now at your nearest bookstore or online retailer!",
            "Don't Miss Out!",
            "The Ultimate Historical Comedy Extravaganza!"
        };
        String[] suggestions = {
            "Leaving", "goodbye", "historical setting"
        };

        return new Panel(charLeft, charRight, lines, suggestions);
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
        String[] splitCaption = splitCaption(lines[2]);
        return "<panel>\n" +
                "   <above>" + splitCaption[0] + "</above>\n"+
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
                (splitCaption[1].isBlank() ?"": "   <below>"+ splitCaption[1] +"</below>\n")+
                "   <setting>" + setting + "</setting>\n"+
                "</panel>";
    }

    private String speechBalloon(String speech) {
        return "\t\t<balloon status=\"speech\">\r\t\t\t<content>" + speech + "</content>\n\t\t</balloon>\n";
    }

    private String[] splitCaption(String caption){
        String[] splitCaption = new String[2];
        String[] captionWords = caption.split(" ");

        if(captionWords.length < MAX_CAPTION_PER_LINE){
            splitCaption[0] = caption;
            splitCaption[1] = "";
            return splitCaption;
        }

        int totalWords = captionWords.length;
        StringBuilder firstHalf = new StringBuilder();
        StringBuilder secondHalf = new StringBuilder();

        for (int i = 0; i < totalWords/2; i++) {
            firstHalf.append(captionWords[i]).append(" ");
            secondHalf.append(captionWords[i + totalWords/2]).append(" ");
        }

        splitCaption[0] = firstHalf.toString();
        splitCaption[1] = secondHalf.toString();
        return splitCaption;
    }
}
