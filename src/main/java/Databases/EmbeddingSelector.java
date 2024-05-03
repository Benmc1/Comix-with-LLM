package Databases;

import Main.API;
import java.util.List;


public class EmbeddingSelector {

    private static EmbeddingDatabase embeddingDatabase;
    private static IndexDatabase indexDatabase;

    public EmbeddingSelector() {

    }

    public static String getRelevantChoice(String description,String type){
        String ans = "";
        embeddingDatabase = new EmbeddingDatabase();
        indexDatabase = new IndexDatabase();
        if(false){
        }else{
            int index = findClosestMatchIndex(description,type);
            embeddingDatabase.appendToFile(description);

            String[] match = indexDatabase.getByIndex(index).split(",");
            String newEntry = description + ","+match[1]+","+match[2];
            indexDatabase.appendToFile(newEntry);
            ans = match[2];
        }

        return ans;
    }

    private static int findClosestMatchIndex(String description, String type){

        List<Double> promptEmbedding = API.getEmbedding(List.of(description)).get(0).getEmbedding();

        int closestMatchIndex = -1;
        double maxSimilarity = -1.0;

        // Iterate over each background setting and calculate cosine similarity
        // Select the background setting with the highest similarity score
        int index = 0;
        String keys = indexDatabase.getByIndex(index);
        while (!keys.isBlank()) {
            if(keys.contains(type)) {
                List<Double> savedEmbedding = embeddingDatabase.getByIndex(index);
                double similarity = calculateCosineSimilarity(promptEmbedding, savedEmbedding);
                if (similarity > maxSimilarity) {
                    maxSimilarity = similarity;
                    closestMatchIndex = index;
                }
            }
        }
        return closestMatchIndex;
    }
    private static double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }
        return dotProduct;
    }

    public static void main(String[] args) {

        EmbeddingSelector.getRelevantChoice("pose","waving heavily");
    }
}
