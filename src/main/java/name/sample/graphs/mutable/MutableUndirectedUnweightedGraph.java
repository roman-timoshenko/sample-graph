package name.sample.graphs.mutable;

import name.sample.graphs.Edge;
import name.sample.graphs.mutable.edges.ImmutableUndirectedUnweightedEdge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * A directed unweighted graph implementation.
 * <p/>
 * In a directed graph all {@link Edge}s have a direction from vertex A to vertex B.
 * <p/>
 * In an unweighted graph edges do not have an associated weight object.
 *
 * @param <V> graph vertex type
 */
public class MutableUndirectedUnweightedGraph<V> implements MutableUnweightedGraph<V> {

    private final ConcurrentMap<V, Map<V, Edge<V>>> data = new ConcurrentHashMap<>();

    @Override
    public Set<V> getVertices() {
        return data.keySet();
    }

    @Override
    public Collection<Edge<V>> getEdges() {
        return data.values()
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<Edge<V>> getOutgoingEdges(final V vertex) {
        return data.getOrDefault(vertex, Collections.emptyMap()).values();
    }

    @Override
    public void putVertex(final V vertex) {
        Objects.requireNonNull(vertex);
        data.computeIfAbsent(vertex, (k) -> new ConcurrentHashMap<>());
    }

    @Override
    public void putEdge(final V a, final V b) {
        putVertex(a);
        putVertex(b);
        data.computeIfPresent(a, (k, v) -> {
            v.put(b, new ImmutableUndirectedUnweightedEdge<>(a, b));
            return v;
        });
        data.computeIfPresent(b, (k, v) -> {
            v.put(a, new ImmutableUndirectedUnweightedEdge<>(b, a));
            return v;
        });
    }

    @Override
    public String toString() {
        return "MutableUndirectedUnweightedGraph{}";
    }
}
