package Databases;

import Main.API;
import com.theokanning.openai.embedding.Embedding;
import config.ConfigurationFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingDatabase  {
    private static final int DOUBLE_SIZE = Double.BYTES;
    private static final int EMBEDDING_LEN = Integer.parseInt(ConfigurationFile.getProperty("EMBEDDING_LEN"));
    private static final String DATA_FILE = ConfigurationFile.getProperty("EMBEDDING_DATA");
    private static RandomAccessFile file;
    EmbeddingDatabase(List<String> data) {
        try{
            file = new RandomAccessFile(DATA_FILE, "rw");
            if(file.length() == 0) {
                initializeDatabase(data);
            }
        }catch (FileNotFoundException e){
            System.out.println("could not find or create embedding file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDatabase(List<String> data){
        List<Embedding> embeddingList = API.getEmbedding(data);
        ByteBuffer buffer = ByteBuffer.allocate(DOUBLE_SIZE * EMBEDDING_LEN * data.size());
        for (Embedding embedding: embeddingList) {
            for (Double num : embedding.getEmbedding()) {
                buffer.putDouble(num);
            }
        }
        try {
            file.write(buffer.array());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public int findClosestMatchIndex(String s){
        List<Double> promptEmbedding = API.getEmbedding(List.of(s)).get(0).getEmbedding();

        int selectedBackground = -1;
        double maxSimilarity = -1.0;

        // Iterate over each background setting and calculate cosine similarity
        // Select the background setting with the highest similarity score

        Embedding[] choices = new Embedding[1];
        for (Embedding entry : choices) {
            List<Double> backgroundVector = entry.getEmbedding();
            double similarity = calculateCosineSimilarity(promptEmbedding, backgroundVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                selectedBackground = Integer.parseInt(entry.getObject());
            }
        }
        return selectedBackground;
    }

    public List<Double> readFromFile(int index)  {
        List<Double> list = new ArrayList<>();
        try {
            long position = (long) index * DOUBLE_SIZE * EMBEDDING_LEN;
            file.seek(position);

            byte[] buffer = new byte[DOUBLE_SIZE * EMBEDDING_LEN];
            file.read(buffer);
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            for (int i = 0; i < EMBEDDING_LEN; i++) {
                list.add(byteBuffer.getDouble());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    //add a single embedding to the file
    private void appendToFile(String input) {
        Embedding embedding = API.getEmbedding(List.of(input)).get(0);
        try{
            file.seek(file.length());

            ByteBuffer buffer = ByteBuffer.allocate(DOUBLE_SIZE * EMBEDDING_LEN);
            for (Double num : embedding.getEmbedding()) {
                buffer.putDouble(num);
            }
            file.write(buffer.array());

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }
        return dotProduct;
    }

}
