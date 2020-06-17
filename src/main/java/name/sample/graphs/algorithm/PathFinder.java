package name.sample.graphs.algorithm;

import name.sample.graphs.Edge;

import java.util.List;
import java.util.Set;

/**
 * Path searching algorithm may implement this interface for unification.
 *
 * @param <Vertex> type of graph vertexes
 */
public interface PathFinder<Vertex> {
    /**
     * Retrieves a sed of {@link Edge}s that forms a path from vertex a to vertex b.
     *
     * @param source      source vertex to start with
     * @param destination destination vertex to go to
     * @return a {@link Set} of {@link Edge}s that represent path from source to destination
     */
    List<Edge<Vertex>> getPath(final Vertex source, final Vertex destination);
}
