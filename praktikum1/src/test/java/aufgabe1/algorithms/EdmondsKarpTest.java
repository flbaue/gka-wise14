package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Test;

public class EdmondsKarpTest {

    @Test
    public void testFindMaxFlow() throws Exception {
        SimpleDirectedWeightedGraph graph = new SimpleDirectedWeightedGraph(DefaultWeightedEdge.class);

        Vertex s = new Vertex("s");
        graph.addVertex(s);
        Vertex u = new Vertex("u");
        graph.addVertex(u);
        Vertex v = new Vertex("v");
        graph.addVertex(v);
        Vertex t = new Vertex("t");
        graph.addVertex(t);

        DefaultWeightedEdge s1 = new DefaultWeightedEdge();
        graph.setEdgeWeight(graph.addEdge(s,u),20);
        DefaultWeightedEdge s2 = new DefaultWeightedEdge();
        graph.setEdgeWeight(graph.addEdge(s,v),10);
        DefaultWeightedEdge u1 = new DefaultWeightedEdge();
        graph.setEdgeWeight(graph.addEdge(u,t),10);
        DefaultWeightedEdge u2 = new DefaultWeightedEdge();
        graph.setEdgeWeight(graph.addEdge(u,v),30);
        DefaultWeightedEdge v1 = new DefaultWeightedEdge();
        graph.setEdgeWeight(graph.addEdge(v,t),20);

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph,s,t);
        int maxFlow = edmondsKarp.findMaxFlow();
        System.out.println(maxFlow);
    }
}