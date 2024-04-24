package Main;

import com.theokanning.openai.embedding.Embedding;

import java.io.Serializable;

public class EmbeddingData extends Embedding implements Serializable {
    private Type type;
    enum Type{Setting,Pose}

    EmbeddingData(Type t){
        type = t;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
