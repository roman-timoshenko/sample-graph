package name.sample.graphs;

import java.util.Collection;
import java.util.Set;

/**
 * A generic interface for representing graph structure.
 * <p/>
 * A graph is a structured set of objects (called vertices) in which some pairs of the objects are connected by edges.
 *
 * @param <V> type of graph vertex, vertex class must be possible to use as a class key.
 * @param <E> type of graph Edge, must be an implementation of {@link Edge} interface.
 */
public interface Graph<V, E extends Edge<V>> {

    /**
     * Retrieve all vertices in the graph.
     *
     * @return a set of objects of V type
     */
    Set<V> getVertices();

    /**
     * Retrieve all edges in the graph.
     *
     * @return a set of objects of {@link Edge} type
     */
    Collection<E> getEdges();

    /**
     * Retrieve all edges that are going out of specified node.
     *
     * @return a set of objects of {@link Edge} type
     */
    Collection<E> getOutgoingEdges(V vertex);
}
