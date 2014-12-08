package aufgabe1.algorithms;

import aufgabe1.Vertex;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.jgrapht.graph.WeightedPseudograph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by schlegel11 on 17.11.14.
 */
public class GraphGenerator {

    private final int edgeCount;
    private final int vertexCount;
    private final boolean isDirected;
    private WeightedGraph<Vertex, DefaultWeightedEdge> graph;
    private Map<Integer, Vertex> vertices = new HashMap<>();

    public GraphGenerator(int edgeCount, int vertexCount, boolean isDirected) {
        this.edgeCount = edgeCount;
        this.vertexCount = vertexCount;
        this.isDirected = isDirected;
    }

    public void generate() {
        createGraph();
        generateVertices();
        generateEdges();
    }

    public void addShortestPath(Collection<Vertex> vertices) {
        Vertex tempVertex = null;
        for (Iterator<Vertex> itr = vertices.iterator(); itr.hasNext(); ) {
            Vertex vertex = this.vertices.get(Integer.valueOf(itr.next().getName()));
            if (Objects.nonNull(tempVertex)) {
                graph.setEdgeWeight(graph.addEdge(tempVertex, vertex), 1);
            }
            tempVertex = vertex;
        }
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public boolean isDirected() {
        return isDirected;
    }

    public WeightedGraph<Vertex, DefaultWeightedEdge> getGraph() {
        return graph;
    }

    public Vertex[] convertGraphToNetwork(DirectedWeightedPseudograph<Vertex,DefaultWeightedEdge> graph){
        Vertex source = new Vertex("generatedNetworkSource");
        Vertex terminal = new Vertex("generatedNetworkTerminal");
        Random random = new Random();
        int s = random.nextInt(graph.vertexSet().size());
        int t = random.nextInt(graph.vertexSet().size());
        List<Vertex> vertexList = new ArrayList<>(graph.vertexSet());

        Vertex dSource = vertexList.get(s);
        Vertex dTerminal = vertexList.get(t);

        graph.addVertex(source);
        graph.addVertex(terminal);
        graph.setEdgeWeight(graph.addEdge(source, dSource), Double.POSITIVE_INFINITY);
        graph.setEdgeWeight(graph.addEdge(dTerminal, terminal), Double.POSITIVE_INFINITY);

        return new Vertex[]{source,terminal};
    }

    private void createGraph() {
        graph = isDirected() ? new DirectedWeightedPseudograph<>(DefaultWeightedEdge.class) : new WeightedPseudograph<>(DefaultWeightedEdge.class);
    }

    private void generateVertices() {
        for (int i = 1; i <= getVertexCount(); i++) {
            Vertex vertex = new Vertex(String.valueOf(i));
            graph.addVertex(vertex);
            vertices.put(i, vertex);
        }
    }

    private void generateEdges() {
        int edgesPerVertex = getEdgeCount() / getVertexCount();
        int edgesPerVertexRest = getEdgeCount() % getVertexCount();
        for (int i = 1; i <= getVertexCount(); i++) {
            for (int j = 0; j < edgesPerVertex; j++) {
                Vertex sourceVertex = vertices.get(i);
                graph.setEdgeWeight(graph.addEdge(sourceVertex, getRandomVertex(vertices.values())), getRandomWeight(10, 100));
            }
        }
        for (int i = 0; i < edgesPerVertexRest; i++) {
            graph.setEdgeWeight(graph.addEdge(getRandomVertex(vertices.values()), getRandomVertex(vertices.values())), getRandomWeight(10, 100));
        }
    }

    private Vertex getRandomVertex(Collection<Vertex> collection) {
        List<Vertex> list = collection.stream().collect(Collectors.toList());
        Collections.shuffle(list);
        return list.get(0);
    }

    private int getRandomWeight(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
