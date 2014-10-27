package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.WeightedPseudograph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by flbaue on 20.10.14.
 */
class GraphLoader {

    Map<String, Vertex> vertexMap = new HashMap<>();

    public Graph<Vertex, DefaultWeightedEdge> fromFile(final File path) {
        final List<FileEntry> lines = convertFileToListOfFileEntrys(path);
        return convertListOfStringsToGraph(lines);
    }

    private Graph<Vertex, DefaultWeightedEdge> convertListOfStringsToGraph(final List<FileEntry> lines) {
        if (graphIsDirected(lines)) {
            return createDirectedGraph(lines);
        } else {
            return createUndirectedGraph(lines);
        }
    }

    private Graph<Vertex, DefaultWeightedEdge> createUndirectedGraph(List<FileEntry> lines) {
        WeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new WeightedPseudograph<>(DefaultWeightedEdge.class);
        addNodesToGraph(graph, lines);
        addEdgesToGraph(graph, lines);

        return graph;
    }

    private Graph<Vertex, DefaultWeightedEdge> createDirectedGraph(List<FileEntry> lines) {
        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class);
        addNodesToGraph(graph, lines);
        addEdgesToGraph(graph, lines);

        return graph;
    }

    private void addEdgesToGraph(WeightedGraph<Vertex, DefaultWeightedEdge> graph, List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            if (entry.getNode2Name() != null) {
                final Vertex vertex1 = vertexMap.get(entry.getNode1Name());
                final Vertex vertex2 = vertexMap.get(entry.getNode2Name());
                DefaultWeightedEdge edge = graph.addEdge(vertex1, vertex2);
                if (entry.getWeight() != 0) {
                    graph.setEdgeWeight(edge, (double) entry.getWeight());
                }
            }
        }
    }

    private void addNodesToGraph(Graph<Vertex, DefaultWeightedEdge> graph, List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            if (!vertexMap.containsKey(entry.getNode1Name())) {
                final Vertex vertex = new Vertex(entry.getNode1Name());
                vertexMap.put(entry.getNode1Name(), vertex);
                graph.addVertex(vertex);
            }

            if (entry.getNode2Name() != null && !vertexMap.containsKey(entry.getNode2Name())) {
                final Vertex vertex = new Vertex(entry.getNode2Name());
                vertexMap.put(entry.getNode2Name(), vertex);
                graph.addVertex(vertex);
            }
        }
    }

    private boolean graphIsDirected(final List<FileEntry> lines) {
        if (lines.get(0).isDirected()) {
            validateAllNodesDirected(lines);
            return true;
        } else {
            validateAllNodesUndirected(lines);
            return false;
        }
    }

    private void validateAllNodesUndirected(List<FileEntry> lines) {
        for (FileEntry e : lines) {
            if (e.getNode2Name() != null && !e.getNode2Name().isEmpty() && e.isDirected()) {
                throw new IllegalArgumentException("entry must not be directed: " + e.toString());
            }
        }
    }

    private void validateAllNodesDirected(List<FileEntry> lines) {
        for (FileEntry e : lines) {
            if (e.getNode2Name() != null && !e.getNode2Name().isEmpty() && !e.isDirected()) {
                throw new IllegalArgumentException("entry must be directed: " + e.toString());
            }
        }
    }


    private List<FileEntry> convertFileToListOfFileEntrys(final File path) {
        final List<FileEntry> lines = new LinkedList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    lines.add(new FileEntry(line));
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        return lines;
    }
}
