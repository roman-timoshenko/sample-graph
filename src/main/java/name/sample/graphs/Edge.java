package name.sample.graphs;

/**
 * An edge is pair of vertices that are connected in a graph.
 * <p/>
 * In a directed graph, node goes from vertex A to vertex B, in undirected graph both vertices are considered adjacent.
 *
 * @param <Vertex> graph vertex type
 */
public interface Edge<Vertex> {
    /**
     * First vertex of an edge.
     *
     * @return vertex instance
     */
    Vertex getNodeA();

    /**
     * Second vertex of an edge.
     *
     * @return vertex instance
     */
    Vertex getNodeB();
}
