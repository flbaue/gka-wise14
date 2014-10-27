package aufgabe1;

/**
 * Created by flbaue on 24.10.14.
 */
public class Marker {

    private final Vertex predecessor;
    private final int distance;
    private boolean visited;

    public Marker(Vertex predecessor, int distance) {
        this.predecessor = predecessor;
        this.distance = distance;
        visited = false;
    }

    public Vertex getPredecessor() {
        return predecessor;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }
}
