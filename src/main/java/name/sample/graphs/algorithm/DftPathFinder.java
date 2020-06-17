package name.sample.graphs.algorithm;

import name.sample.graphs.Edge;
import name.sample.graphs.Graph;

import java.util.*;

/**
 * An implementation of a depth first traversal used to find a path from source to destination.
 *
 * @param <Vertex> type of vertex
 */
public class DftPathFinder<Vertex> implements PathFinder<Vertex> {

    private final Graph<Vertex, ? extends Edge<Vertex>> graph;

    public DftPathFinder(final Graph<Vertex, ? extends Edge<Vertex>> graph) {
        this.graph = Objects.requireNonNull(graph);
    }

    @Override
    public List<Edge<Vertex>> getPath(Vertex source, Vertex destination) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(destination);
        final Map<Vertex, Edge<Vertex>> parent = new HashMap<>();
        final Set<Vertex> visited = new HashSet<>();
        final Stack<Vertex> open = new Stack<>();
        open.push(source);
        while (!open.isEmpty()) {
            final Vertex current = open.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                final Collection<? extends Edge<Vertex>> outgoing = graph.getOutgoingEdges(current);
                for (final Edge<Vertex> edge : outgoing) {
                    open.push(edge.getNodeB());
                    parent.putIfAbsent(edge.getNodeB(), edge);
                    if (edge.getNodeB().equals(destination)) {
                        return unwindParent(parent, source, destination);
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    /**
     * Restores path found from provided records.
     *
     * @param parent records of nodes being visited
     * @param source node to restore path from
     * @param destination node to restore path to
     * @return a new {@link List} of {@link Edge}s of vertices representing path found
     */
    private List<Edge<Vertex>> unwindParent(final Map<Vertex, Edge<Vertex>> parent, final Vertex source,
                                            final Vertex destination) {
        final List<Edge<Vertex>> result = new ArrayList<>();
        Vertex current = destination;
        while (!current.equals(source)) {
            final Edge<Vertex> from = parent.get(current);
            result.add(0, from);
            current = from.getNodeA();
        }
        return result;
    }
}
