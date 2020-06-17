package name.sample.graphs.mutable;

import name.sample.graphs.mutable.edges.ImmutableUndirectedWeightedEdge;
import name.sample.graphs.Edge;
import name.sample.graphs.WeightedEdge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * An undirected unweighted graph implementation.
 * <p/>
 * In an undirected graph all {@link Edge}s connect vertex A to vertex B both ways.
 * <p/>
 * In an weighted graph each edge has an associated weight object.
 *
 * @param <V> graph vertex type
 * @param <W> graph edge weight type
 */
public class MutableUndirectedWeightedGraph<V, W> implements MutableWeightedGraph<V, W> {

    private final ConcurrentMap<V, Map<V, WeightedEdge<V, W>>> data = new ConcurrentHashMap<>();

    @Override
    public Set<V> getVertices() {
        return data.keySet();
    }

    @Override
    public Collection<WeightedEdge<V, W>> getEdges() {
        return data.values()
                .stream()
                .map(Map::values)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<WeightedEdge<V, W>> getOutgoingEdges(final V vertex) {
        return data.getOrDefault(vertex, Collections.emptyMap()).values();
    }

    @Override
    public void putVertex(final V vertex) {
        Objects.requireNonNull(vertex);
        data.computeIfAbsent(vertex, (k) -> new ConcurrentHashMap<>());
    }

    @Override
    public void putEdge(V a, V b, W weight) {
        putVertex(a);
        putVertex(b);
        data.computeIfPresent(a, (k, v) -> {
            v.put(b, new ImmutableUndirectedWeightedEdge<>(a, b, weight));
            return v;
        });
        data.computeIfPresent(b, (k, v) -> {
            v.put(a, new ImmutableUndirectedWeightedEdge<>(b, a, weight));
            return v;
        });
    }

    @Override
    public String toString() {
        return "MutableUndirectedWeightedGraph{}";
    }
}
