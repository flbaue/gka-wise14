package aufgabe1.algorithms;

import aufgabe1.Vertex;

/**
 * Created by schlegel11 on 27.10.14.
 */
public class FloydWarshallPair {

    private final Vertex sourceVertex;
    private final Vertex targetVertex;

    public FloydWarshallPair(Vertex sourceVertex, Vertex targetVertex) {
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
    }

    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FloydWarshallPair)) return false;

        FloydWarshallPair that = (FloydWarshallPair) o;

        if (sourceVertex != null ? !sourceVertex.equals(that.sourceVertex) : that.sourceVertex != null) return false;
        if (targetVertex != null ? !targetVertex.equals(that.targetVertex) : that.targetVertex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sourceVertex != null ? sourceVertex.hashCode() : 0;
        result = 31 * result + (targetVertex != null ? targetVertex.hashCode() : 0);
        return result;
    }
}
