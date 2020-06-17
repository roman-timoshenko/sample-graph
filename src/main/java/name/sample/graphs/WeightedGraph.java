package name.sample.graphs;

/**
 * A weighted graph is a {@link Graph} in which each edge has an associated "weight" object.
 *
 * @param <V> graph vertex type
 * @param <W> graph edge weight type
 */
public interface WeightedGraph<V, W> extends Graph<V, WeightedEdge<V, W>> {
}
