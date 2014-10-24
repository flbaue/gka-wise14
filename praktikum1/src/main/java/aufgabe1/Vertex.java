package aufgabe1;

import java.io.Serializable;

/**
 * Created by flbaue on 24.10.14.
 */
public class Vertex implements Serializable {

    private final String name;
    private Marker marker;

    public Vertex(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Vertex name must not be null or empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (!name.equals(vertex.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
