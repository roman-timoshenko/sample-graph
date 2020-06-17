package name.sample.graphs.mutable.edges;

import name.sample.graphs.Edge;

import java.util.Objects;

public final class ImmutableUndirectedUnweightedEdge<Vertex> implements Edge<Vertex> {

    private final Vertex nodeA;
    private final Vertex nodeB;

    public ImmutableUndirectedUnweightedEdge(Vertex nodeA, Vertex nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public Vertex getNodeA() {
        return nodeA;
    }

    @Override
    public Vertex getNodeB() {
        return nodeB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableUndirectedUnweightedEdge<?> that = (ImmutableUndirectedUnweightedEdge<?>) o;
        return (Objects.equals(nodeA, that.nodeA) &&
                Objects.equals(nodeB, that.nodeB)) ||
                (Objects.equals(nodeA, that.nodeB) &&
                        Objects.equals(nodeB, that.nodeA));
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB) + Objects.hash(nodeB, nodeA);
    }

    @Override
    public String toString() {
        return "ImmutableUndirectedUnweightedEdge{" +
                "nodeA=" + nodeA +
                ", nodeB=" + nodeB +
                '}';
    }
}
