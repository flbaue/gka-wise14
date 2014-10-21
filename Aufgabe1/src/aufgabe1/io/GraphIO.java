package aufgabe1.io;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.File;

/**
 * Created by flbaue on 20.10.14.
 */
public class GraphIO {

    GraphLoader loader = new GraphLoader();
    GraphSaver saver = new GraphSaver();

    public Graph<String, DefaultWeightedEdge> readGraphFromFile(File path) {
        if (path.exists() && path.isFile()) {
            return loader.fromFile(path);
        }
        throw new IllegalArgumentException("file does not exist");
    }

    public void saveGraphAsFile(Graph<String, DefaultWeightedEdge> graph, File path) {
        if (path.isDirectory()) {
            throw new IllegalArgumentException("path is not a file");
        }

        if (path.exists()) {
            path.delete();
        }

        saver.asFile(graph, path);
    }
}
