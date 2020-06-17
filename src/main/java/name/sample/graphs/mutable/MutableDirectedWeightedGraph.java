package name.sample.graphs.mutable;

import name.sample.graphs.Edge;
import name.sample.graphs.WeightedEdge;
import name.sample.graphs.mutable.edges.ImmutableDirectedWeightedEdge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * A directed unweighted graph implementation.
 * <p/>
 * In a directed graph all {@link Edge}s have a direction from vertex A to vertex B.
 * <p/>
 * In an weighted graph each edge has an associated weight object.
 *
 * @param <V> graph vertex type
 * @param <W> graph edge weight type
 */
public class MutableDirectedWeightedGraph<V, W> implements MutableWeightedGraph<V, W> {

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
        Objects.requireNonNull(vertex);
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
        final WeightedEdge<V, W> edge = new ImmutableDirectedWeightedEdge<>(a, b, weight);
        data.computeIfPresent(edge.getNodeA(), (k, v) -> {
            v.put(edge.getNodeB(), edge);
            return v;
        });
    }

    @Override
    public String toString() {
        return "MutableDirectedWeightedGraph{}";
    }
}
