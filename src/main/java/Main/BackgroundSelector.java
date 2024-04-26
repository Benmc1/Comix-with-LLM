package Main;

import com.theokanning.openai.embedding.Embedding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackgroundSelector {

    // Map to store pre-built vectors for each background setting
    private static final Map<String, List<Double>> backgroundVectors = new HashMap<>();

    // Method to select the most suitable background setting for the given prompt
    public static String selectBackground(String prompt) {
        List<Double> promptEmbeddings = getPromptEmbedding(prompt);

        String selectedBackground = null;
        double maxSimilarity = -1.0;

        // Iterate over each background setting and calculate cosine similarity
        // Select the background setting with the highest similarity score  [cos(0°) = 1]*
        for (Map.Entry<String, List<Double>> entry : backgroundVectors.entrySet()) {
            List<Double> backgroundVector = entry.getValue();
            double similarity = calculateCosineSimilarity(promptEmbeddings, backgroundVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                selectedBackground = entry.getKey();
            }
        }

        return selectedBackground;
    }

    // Method to calculate cosine similarity between two vectors
    private static double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
            norm1 += Math.pow(vector1.get(i), 2);
            norm2 += Math.pow(vector2.get(i), 2);
        }
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    private static List<Double> getPromptEmbedding(String text) {
        // TODO fix this
        return API.getEmbedding(text);
    }
}
