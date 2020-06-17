package name.sample.graphs.mutable;

import name.sample.graphs.Edge;
import name.sample.graphs.Graph;
import name.sample.graphs.WeightedEdge;

/**
 * A generic interface for representing a mutable weighted {@link Graph} structure.
 * <p/>
 * A mutable graph is a {@link Graph} structure that may change over time in-place.
 *
 * @param <V> type of graph vertex
 * @param <W> type of graph Edge, must be an implementation of {@link Edge} interface.
 */
public interface MutableWeightedGraph<V, W> extends Graph<V, WeightedEdge<V, W>> {

    /**
     * Add a vertex to a graph if not exists. If exists, vertex will not be added again.
     * <p/>
     * Mostly useful to add a non-adjacent vertex.
     *
     * @param vertex new vertex to add.
     */
    void putVertex(V vertex);

    /**
     * Add an edge between vertexes 'a' and 'b' with specified 'weight' object.
     *
     * @param a first vertex
     * @param b second vertex
     */
    void putEdge(V a, V b, W weight);

}
