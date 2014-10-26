package aufgabe1;

/**
 * Created by flbaue on 25.10.14.
 */
public interface Markable {

    int getDistance();

    Vertex getPredecessor();

    void visit();

    boolean isVisited();

    boolean hasMarker();

    void removeMarker();

    void setMarker(Marker marker);
}
