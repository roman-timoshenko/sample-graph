package name.sample.graphs;


/**
 * A weighed graph edge is an {@link Edge} that has also a "weight" object associated with it.
 *
 * @param <Vertex> graph vertex type
 * @param <Weight> vertex associated payload type
 */
public interface WeightedEdge<Vertex, Weight> extends Edge<Vertex> {
    /**
     * Weigt of the edge.
     *
     * @return vertex instance
     */
    Weight getWeight();
}
