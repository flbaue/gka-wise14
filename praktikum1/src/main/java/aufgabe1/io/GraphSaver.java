package aufgabe1.io;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by flbaue on 20.10.14.
 */
class GraphSaver {
    public void asFile(Graph<Vertex, DefaultWeightedEdge> graph, File path) {
        List<FileEntry> lines = convertGraphToListOfFileEntries(graph);
        saveLinesToFile(lines, path);
    }

    private void saveLinesToFile(List<FileEntry> fileEntries, File path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            for (FileEntry entry : fileEntries) {
                writer.write(entry.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file", e);
        }
    }

    private List<FileEntry> convertGraphToListOfFileEntries(Graph<Vertex, DefaultWeightedEdge> graph) {
        Set<DefaultWeightedEdge> edges = graph.edgeSet();
        List<FileEntry> result = new LinkedList<>();
        boolean isDirected = isGraphDirected(graph);

        for (DefaultWeightedEdge edge : edges) {
            String v1 = graph.getEdgeSource(edge).getName();
            String v2 = graph.getEdgeTarget(edge).getName();
            int w = (int) graph.getEdgeWeight(edge);
            result.add(new FileEntry(v1, v2, isDirected, "", w));
        }

        //TODO unconnected vertexes

        return result;
    }

    private boolean isGraphDirected(Graph<Vertex, DefaultWeightedEdge> graph) {
        return graph instanceof DirectedGraph;
    }


}
