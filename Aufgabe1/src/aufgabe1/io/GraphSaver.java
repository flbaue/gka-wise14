package aufgabe1.io;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by flbaue on 20.10.14.
 */
class GraphSaver {
    public void asFile(Graph<String, DefaultEdge> graph, File path) {
        List<String> lines = convertGraphToListOfStrings(graph);
        saveLinesToFile(lines, path);
    }

    private void saveLinesToFile(List<String> lines, File path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {

            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Cannot write file", e);
        }
    }

    private List<String> convertGraphToListOfStrings(Graph<String, DefaultEdge> graph) {
        //TODO
        return null;
    }
}
