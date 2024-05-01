package Main;

import com.theokanning.openai.embedding.Embedding;

import java.util.List;

public class EmbeddingDatabase {

    public void createDatabase(List<String> data){

    }
    public int findClosestMatchIndex(String s){
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
    appendToFile(List<Double>);

    private double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }
        return dotProduct;
    }
}
