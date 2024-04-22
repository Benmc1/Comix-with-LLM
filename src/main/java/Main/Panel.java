package Main;

import java.util.Arrays;

public class Panel {
    private final String charLeft;

    private final String charRight;
    private final String[] lines;
    private String poseLeft;
    private String poseRight;

    private String setting;

    public Panel(String charLeft, String charRight,String[] lines) {
        this.charLeft = charLeft;
        this.charRight = charRight;
        this.lines = lines;
        generatePoses();
        generateSetting();
    }

    private void generatePoses(){

    }
    private void generateSetting(){

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
                "           <facing>left</facing>\n"+
                "       </figure>\n\n"+
                        speechBalloon(lines[0])+
                "   </left>\n\n"+
                "   <right>\n"+
                "       <figure>\n"+
                "           <name>" + charRight + "</name>\n"+
                "           <pose>" + poseRight + "</pose>\n"+
                "           <facing>right</facing>\n"+
                "       </figure>\n\n"+
                        speechBalloon(lines[1])+
                "   </right>\n\n"+
                "   <setting>" + setting + "</setting>\n"+
                "</panel>";
    }
    private String speechBalloon(String speech){
        return "\t\t<balloon status=\"speech\">\r\t\t\t<content>" + speech + "</content>\n\t\t</balloon>\n";
    }

    public static void main(String[] args) {
        Panel panel = new Panel("tom","jack", new String[]{"Hello there", "How are you", "This is a test"});
        panel.poseLeft="tpose";
        panel.poseRight="Apose";
        System.out.println("\t"+panel.toXML());
    }
}
