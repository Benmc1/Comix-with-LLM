package Main;

import java.util.List;

public class Lines {
    private List<String> proLines;
    private List<String> antiLines;

    Lines(String topic,int numOfLines){
        generateLines(topic,numOfLines);
    }

    private void generateLines(String topic, int numOfPanels){
        Conversation conversation = new Conversation();
        conversation.addSystemMessage("You are a writer for a topical comic. You dont hold back from contentious or " +
                "controversial topics and you always try to be interesting and unique.");
        String points = conversation.getResponse("Write a list of " + numOfPanels + " points about "+ topic
                + ". Theses points should be concise and benign.");
        if(conversation.isDOS(points)){
            //TODO
        }
        String lines = conversation.getResponse("Use these points to create a numbered list where each point " +
                "has a pro outlook and a negative anti outlook. This should read like a conversation between two people");

    }

    public String getAntiLine(int n) {
        return antiLines.get(n);
    }

    public String getProLine(int n) {
        return proLines.get(n);
    }

}
