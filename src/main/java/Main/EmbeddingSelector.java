package Main;

import com.theokanning.openai.embedding.Embedding;

import java.util.List;

public class EmbeddingSelector {

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

    public static void main(String[] args) {
        List<Embedding> test = EmbeddingData.getPoseEmbeddings();
        double ans = EmbeddingSelector.calculateCosineSimilarity(test.get(0).getEmbedding(),test.get(1).getEmbedding());
        System.out.println(ans);
        System.out.println("0.7611426465881476");
    }
}
