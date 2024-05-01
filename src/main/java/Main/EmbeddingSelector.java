package Main;

import com.theokanning.openai.embedding.Embedding;

import java.util.List;

public class EmbeddingSelector {
    enum type{POSE,SETTING}

    public String getRelevantChoice(String description,type t){
        String ans =  checkInDatabase(type.POSE,description);
        if(ans.isBlank()) {
            API.getEmbedding(List.of(description)).get(0);
            ans = findClosest(description);
        }
        return ans;
    }
    public String getRelevantSetting(String Description){
        return "";
    }
    private String checkInDatabase(type t, String Description){
    }
    private String findClosest(String description){

    }


    // Method to select the most suitable background setting for the given prompt
    public static String selectEmbedding(String prompt, List<Embedding> choices) {
        List<Double> promptEmbedding = API.getEmbedding(List.of(prompt)).get(0).getEmbedding();

        String selectedBackground = null;
        double maxSimilarity = -1.0;

        // Iterate over each background setting and calculate cosine similarity
        // Select the background setting with the highest similarity score
        for (Embedding entry : choices) {
            List<Double> backgroundVector = entry.getEmbedding();
            double similarity = calculateCosineSimilarity(promptEmbedding, backgroundVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                selectedBackground = entry.getObject();
            }
        }
        return selectedBackground;
    }

    // Method to calculate cosine similarity between two vectors
    private static double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }
        return dotProduct;
    }
}
