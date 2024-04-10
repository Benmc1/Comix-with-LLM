package Main;

public class Comic {
    private final String topic;
    private final int numOfPanels;
    Comic(String topic, int numOfPanels){
        this.topic = topic;
        this.numOfPanels = numOfPanels;

        generateLines();
    }

    private void generateLines(){
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

}
