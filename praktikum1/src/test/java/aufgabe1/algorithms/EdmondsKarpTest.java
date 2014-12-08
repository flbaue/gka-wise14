package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Test;

public class EdmondsKarpTest {

    @Test
    public void testFindMaxFlow() throws Exception {
        DirectedWeightedPseudograph graph = new DirectedWeightedPseudograph(DefaultWeightedEdge.class);

        Vertex s = new Vertex("s");
        graph.addVertex(s);
        Vertex u = new Vertex("u");
        graph.addVertex(u);
        Vertex v = new Vertex("v");
        graph.addVertex(v);
        Vertex t = new Vertex("t");
        graph.addVertex(t);


        graph.setEdgeWeight(graph.addEdge(s, u), 10);
        graph.setEdgeWeight(graph.addEdge(s, u), 10);
        graph.setEdgeWeight(graph.addEdge(s, v), 10);
        graph.setEdgeWeight(graph.addEdge(u, t), 10);
        graph.setEdgeWeight(graph.addEdge(u, v), 30);
        graph.setEdgeWeight(graph.addEdge(v, t), 20);

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, s, t);
        int maxFlow = edmondsKarp.findMaxFlow();
        org.junit.Assert.assertEquals(30, maxFlow);
    }

    @Test
    public void testFindMaxFlow2() throws Exception {
        SimpleDirectedWeightedGraph graph = new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

        Vertex q = new Vertex("q");
        graph.addVertex(q);
        Vertex v1 = new Vertex("v1");
        graph.addVertex(v1);
        Vertex v2 = new Vertex("v2");
        graph.addVertex(v2);
        Vertex v3 = new Vertex("v3");
        graph.addVertex(v3);
        Vertex v5 = new Vertex("v5");
        graph.addVertex(v5);
        Vertex s = new Vertex("s");
        graph.addVertex(s);

        graph.setEdgeWeight(graph.addEdge(q, v1), 5);
        graph.setEdgeWeight(graph.addEdge(q, v2), 4);
        graph.setEdgeWeight(graph.addEdge(q, v5), 1);
        graph.setEdgeWeight(graph.addEdge(v1, v3), 1);
        graph.setEdgeWeight(graph.addEdge(v1, v5), 1);
        graph.setEdgeWeight(graph.addEdge(v1, s), 3);
        graph.setEdgeWeight(graph.addEdge(v2, v3), 2);
        graph.setEdgeWeight(graph.addEdge(v3, s), 3);
        graph.setEdgeWeight(graph.addEdge(v5, s), 3);

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph, q, s);
        int maxFlow = edmondsKarp.findMaxFlow();
        org.junit.Assert.assertEquals(8, maxFlow);
    }
}