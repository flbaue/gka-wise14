package aufgabe1.io;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by flbaue on 20.10.14.
 */
class GraphSaver {
    public void asFile(Graph<String, DefaultWeightedEdge> graph, File path) {
        List<FileEntry> lines = convertGraphToListOfStrings(graph);
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

    private List<FileEntry> convertGraphToListOfStrings(Graph<String, DefaultWeightedEdge> graph) {
        //TODO
        return null;
    }
}
