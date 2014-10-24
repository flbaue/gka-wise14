package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by flbaue on 20.10.14.
 */
class GraphLoader {

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
                Vertex vertex1 = new Vertex(entry.getNode1Name());
                Vertex vertex2 = new Vertex(entry.getNode2Name());
                DefaultWeightedEdge edge = graph.addEdge(vertex1, vertex2);
                if (entry.getWeight() != 0) {
                    graph.setEdgeWeight(edge, (double) entry.getWeight());
                }
            }
        }
    }

    private void addNodesToGraph(Graph<Vertex, DefaultWeightedEdge> graph, List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            graph.addVertex(new Vertex(entry.getNode1Name()));
            if (entry.getNode2Name() != null) {
                graph.addVertex(new Vertex(entry.getNode2Name()));
            }
        }
    }

    private boolean graphIsDirected(final List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            if (!entry.isDirected()) {
                return false;
            }
        }
        return true;
    }

    private List<FileEntry> convertFileToListOfFileEntrys(final File path) {
        final List<FileEntry> lines = new LinkedList<>();
        try (final BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(new FileEntry(line));
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
        return lines;
    }
}
