package aufgabe1.utils;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Created by schlegel11 on 24.10.14.
 */
public final class GraphUtils {

    private GraphUtils(){}

    public static boolean isDirectedGraph(Graph<? extends Vertex, ? extends DefaultEdge> graph){
        return graph instanceof DirectedGraph;
    }
}
