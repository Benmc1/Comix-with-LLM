package Generation;

import java.util.*;

public class Lines {
    private final List<String> leftLines;
    private final List<String> rightLines;
    private final List<String> captions;

    public Lines() {
        this.leftLines = new ArrayList<>();
        this.rightLines = new ArrayList<>();
        this.captions = new ArrayList<>();
    }

    public void addLeftLines(List<String> input){
        leftLines.addAll(input);
    }
    public void addRightLines(List<String> input){
        rightLines.addAll(input);
    }
    public void addCaptions(List<String> input){
        captions.addAll(input);
    }
    public String getRightLine(int n) {
        return rightLines.get(n);
    }
    public String getLeftLine(int n) {
        return leftLines.get(n);
    }
    public String getCaption(int n) {
        return captions.get(n);
    }
    public String[] getPanelLines(int n) {
        return new String[]{getLeftLine(n),getRightLine(n),getCaption(n)};
    }
    public List<String> getRightLines() {
        return rightLines;
    }
    public List<String> getLeftLines() {
        return leftLines;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < leftLines.size(); i++) {
            //TODO enable
            //str.append("\nCaption:").append(captions.get(i));
            str.append("\nLeft: ").append(leftLines.get(i));
            str.append("\nRight: ").append(rightLines.get(i));
        }
        return str.toString();
    }
}
