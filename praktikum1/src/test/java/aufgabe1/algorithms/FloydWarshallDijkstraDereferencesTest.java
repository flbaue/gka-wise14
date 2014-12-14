package aufgabe1.algorithms;

import aufgabe1.Vertex;
import junit.framework.TestCase;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by schlegel11 on 15.12.14.
 */
public class FloydWarshallDijkstraDereferencesTest extends TestCase {

    @Test
    public void testFloydWarshallDijkstra() throws Exception {
        GraphGenerator graphGenerator = new GraphGenerator(5000, 500, true);
        graphGenerator.generate();

        Graph<Vertex, DefaultWeightedEdge> graph = graphGenerator.getGraph();

        Vertex a = new Vertex("40");
        Vertex b = new Vertex("499");
        Vertex c = new Vertex("30");
        Vertex d = new Vertex("375");
        graphGenerator.addShortestPath(Arrays.asList(a, b, c, d));

        FloydWarshall floydWarshall = new FloydWarshall(graph);
        floydWarshall.run();
        Assert.assertEquals(3, floydWarshall.getShortestPathWeight(a, d));

        Dijkstra dijkstra = new Dijkstra(graph, a);
        dijkstra.run();

        Stream<Vertex> stream = graph.vertexSet().stream().filter(vertex -> vertex.getName().equals("375"));
        Assert.assertEquals(3, stream.findFirst().get().getDistance());

        System.out.println("Floyd Warshall dereferences: " + floydWarshall.getGraphDereferences());
        System.out.println("Dijkstra dereferences: " + dijkstra.getGraphDereferences());
    }
}
