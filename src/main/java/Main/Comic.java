package Main;

public class Comic {
    private final String topic;
    private final int numOfPanels;
    private Lines lines;

    Comic(String topic, int numOfPanels){
        this.topic = topic;
        this.numOfPanels = numOfPanels;
        lines = new Lines(topic, numOfPanels);
    }
}
