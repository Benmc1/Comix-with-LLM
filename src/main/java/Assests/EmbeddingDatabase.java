package Assests;

import Main.API;
import Main.IO;
import com.theokanning.openai.embedding.Embedding;
import Main.ConfigurationFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingDatabase  {
    private static final int DOUBLE_SIZE = Double.BYTES;
    private static final int EMBEDDING_LEN = Integer.parseInt(ConfigurationFile.getProperty("EMBEDDING_LEN"));
    private static RandomAccessFile file;
    public EmbeddingDatabase() {
        String DATA_FILE = ConfigurationFile.getProperty("EMBEDDING_DATA");
        try{
            file = new RandomAccessFile(DATA_FILE, "rw");
            if(file.length() == 0) {
                List<String[]> data = IO.readPlainData();
                initializeDatabase(data);
            }
        } catch (FileNotFoundException e) {
            System.out.println("could not find or create embedding file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeDatabase(List<String[]> data) {
        //get a new list with just the descriptions
        List<String> descriptions = data.stream().map(array -> array[0]).toList();
        List<Embedding> embeddingList = API.getEmbedding(descriptions);

        ByteBuffer buffer = ByteBuffer.allocate(DOUBLE_SIZE * EMBEDDING_LEN * (data.size() + 1));
        for (Embedding embedding : embeddingList) {
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

    public List<Double> getByIndex(int index) {
        List<Double> list = new ArrayList<>();
        if(index > 9999 || index < 0) return null;
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
    public void appendToFile(String input) {
        Embedding embedding = API.getEmbedding(List.of(input)).get(0);
        try{
            file.seek(file.length());

            ByteBuffer buffer = ByteBuffer.allocate(DOUBLE_SIZE * EMBEDDING_LEN);
            for (Double num : embedding.getEmbedding()) {
                buffer.putDouble(num);
            }
            file.write(buffer.array());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
