package Assests;

import Main.API;
import java.util.List;

public class AssetSelector {

    private static EmbeddingDatabase embeddingDatabase;
    private static IndexDatabase indexDatabase;


    public static String getRelevantChoice(String description,String type) {
        String ans;
        embeddingDatabase = new EmbeddingDatabase();
        indexDatabase = new IndexDatabase();
        String key = description +","+ type;

        if(indexDatabase.contains(key)) {
            ans = indexDatabase.getByKey(key);
        } else {
            int index = findClosestMatchIndex(description,type);
            embeddingDatabase.appendToFile(description);

            String match = indexDatabase.getByIndex(index);
            String value = indexDatabase.getByKey(match);
            String newEntry = description + "," + type + "," + value;
            indexDatabase.appendToFile(newEntry);
            ans = value;
        }
        return ans;
    }

    private static int findClosestMatchIndex(String description, String type) {

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
            index++;
            keys = indexDatabase.getByIndex(index);
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
}
