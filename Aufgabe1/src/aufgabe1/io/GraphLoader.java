package aufgabe1.io;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;

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
    public Graph<String, DefaultEdge> fromFile(File path) {
        List<String> lines = convertFileToListOfStrings(path);
        return convertListOfStringsToGraph(lines);
    }

    private Graph<String, DefaultEdge> convertListOfStringsToGraph(List<String> lines) {
        //TODO
        return null;
    }

    private List<String> convertFileToListOfStrings(File path) {
        List<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot read from file", e);
        }

        return lines;
    }
}
