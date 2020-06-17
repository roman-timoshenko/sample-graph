package name.sample.graphs.mutable;

import name.sample.graphs.Graph;
import name.sample.graphs.UnweightedGraph;

/**
 * A generic interface for representing a mutable {@link Graph} structure.
 * <p/>
 * A mutable graph is a {@link Graph} structure that may change over time in-place.
 *
 * @param <V> type of graph vertex
 */
public interface MutableUnweightedGraph<V> extends UnweightedGraph<V> {

    /**
     * Add a vertex to a graph if not exists. If exists, vertex will not be added again.
     * <p/>
     * Mostly useful to add a non-adjacent vertex.
     *
     * @param vertex new vertex to add.
     */
    void putVertex(V vertex);

    /**
     * Add an edge between vertexes A and B.
     *
     * @param a first vertex
     * @param b second vertex
     */
    void putEdge(V a, V b);

}
