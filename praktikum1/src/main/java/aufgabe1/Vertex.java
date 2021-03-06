package aufgabe1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by flbaue on 24.10.14.
 */
public class Vertex implements Serializable, Markable {

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

    @Override
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Collection<Vertex> getThisAndPredecessors() {
        LinkedList<Vertex> predecessors = new LinkedList<>();
        for (Vertex vertex = this; vertex.hasMarker() && !predecessors.contains(vertex); vertex = vertex.getPredecessor()) {
            predecessors.push(vertex);
        }
        return predecessors;
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

    @Override
    public String toString() {
        return '\"' + name + '\"';
    }

    @Override
    public int getDistance() {

        return marker.getDistance();
    }

    @Override
    public Vertex getPredecessor() {
        return marker.getPredecessor();
    }

    @Override
    public void visit() {
        marker.visit();
    }

    @Override
    public boolean isVisited() {
        return marker.isVisited();
    }

    @Override
    public boolean hasMarker() {
        return marker != null;
    }

    @Override
    public void removeMarker() {
        marker = null;
    }
}
