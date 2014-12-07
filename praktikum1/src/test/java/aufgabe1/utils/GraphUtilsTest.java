package aufgabe1.utils;

import aufgabe1.Marker;
import aufgabe1.Vertex;
import aufgabe1.io.GraphIO;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

public class GraphUtilsTest {

    @Test
    public void testRemoveMarkers() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/SimpleTestGraph.txt");
        File file = new File(url.toURI());
        Graph<Vertex, DefaultWeightedEdge> g = graphIO.readGraphFromFile(file);

        GraphUtils.removeMarkers(g);


        DirectedWeightedPseudograph<Vertex, DefaultWeightedEdge> graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex a = new Vertex("A");
        a.setMarker(new Marker(null, 1));
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        DefaultWeightedEdge e;
        e = graph.addEdge(a, b);
        graph.setEdgeWeight(e, 2);
        graph.addEdge(b, c);
        graph.addEdge(c, c);
        graph.addEdge(a, c);
        graph.addEdge(c, a);


        for (Vertex v : graph.vertexSet()) {
            v.removeMarker();
            if (v.getName().equals("A")) {
                Vertex v2 = graph.getEdgeSource(e);
                Assert.assertEquals(v, v2);
            }
        }
    }

    @Test
    public void testConvertToNetwork() throws Exception {
        GraphIO graphIO = new GraphIO();
        URL url = getClass().getResource("/graph4.gka.txt");
        File file = new File(url.toURI());
        Graph<Vertex, DefaultWeightedEdge> g = graphIO.readGraphFromFile(file);

        Network<Vertex, NetworkEdge> network = GraphUtils.convertToNetwork(g);

        for(DefaultWeightedEdge edge : g.edgeSet()){
            Vertex source = g.getEdgeSource(edge);
            Vertex target = g.getEdgeTarget(edge);

            Assert.assertEquals(g.getEdgeWeight(edge), network.getEdge(source, target).getCapacity(), 0);
            Assert.assertEquals(0, network.getEdge(source, target).getFlow(), 0);
        }


    }
}