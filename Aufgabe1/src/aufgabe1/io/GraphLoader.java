package aufgabe1.io;

import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

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

    public Graph<String, DefaultWeightedEdge> fromFile(final File path) {
        final List<FileEntry> lines = convertFileToListOfFileEntrys(path);
        return convertListOfStringsToGraph(lines);
    }

    private Graph<String, DefaultWeightedEdge> convertListOfStringsToGraph(final List<FileEntry> lines) {
        if (graphIsDirected(lines)) {
            return createDirectedGraph(lines);
        } else {
            return createUndirectedGraph(lines);
        }
    }

    private Graph<String, DefaultWeightedEdge> createUndirectedGraph(List<FileEntry> lines) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        addNodesToGraph(graph, lines);
        addEdgesToGraph(graph, lines);

        return graph;
    }

    private Graph<String, DefaultWeightedEdge> createDirectedGraph(List<FileEntry> lines) {
        DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        addNodesToGraph(graph, lines);
        addEdgesToGraph(graph, lines);

        return graph;
    }

    private void addEdgesToGraph(WeightedGraph<String, DefaultWeightedEdge> graph, List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            if (entry.getNode2Name() != null) {
                DefaultWeightedEdge edge = graph.addEdge(entry.getNode1Name(), entry.getNode2Name());
                if (entry.getWeight() != 0) {
                    graph.setEdgeWeight(edge, (double) entry.getWeight());
                }
            }
        }
    }

    private void addNodesToGraph(Graph<String, DefaultWeightedEdge> graph, List<FileEntry> lines) {
        for (FileEntry entry : lines) {
            graph.addVertex(entry.getNode1Name());
            if (entry.getNode2Name() != null) {
                graph.addVertex(entry.getNode2Name());
            }
        }
    }

    private boolean graphIsDirected(final List<FileEntry> lines) {
        for (FileEntry entry : lines){
            if(!entry.isDirected()){
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
