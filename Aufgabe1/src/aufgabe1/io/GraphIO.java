package aufgabe1.io;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;

import java.io.File;

/**
 * Created by flbaue on 20.10.14.
 */
public class GraphIO {

    GraphLoader loader = new GraphLoader();
    GraphSaver saver = new GraphSaver();

    public Graph<String, DefaultEdge> readGraphFromFile(File path) {
        if (path.exists() && path.isFile()) {
            return loader.fromFile(path);
        }
        throw new IllegalArgumentException("file does not exist");
    }

    public void saveGraphAsFile(Graph<String, DefaultEdge> graph, File path) {
        if (path.isDirectory()) {
            throw new IllegalArgumentException("path is not a file");
        }

        if (path.exists()) {
            path.delete();
        }

        saver.asFile(graph, path);
    }
}
