package Main;

public class Comic {
    private final String topic;
    private final Lines lines;

    Comic(String topic){
        this.topic = topic;
        lines = new Lines(topic);
    }

    public Lines getLines() {
        return lines;
    }
    public String getTopic() {
        return topic;
    }
}
