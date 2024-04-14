package Main;

public class Comic {
    private final String topic;
    private final int numOfPanels;
    private final Lines lines;

    Comic(String topic, int numOfPanels){
        this.topic = topic;
        this.numOfPanels = numOfPanels;
        lines = new Lines(topic, numOfPanels);
    }

    public Lines getLines() {
        return lines;
    }
}
