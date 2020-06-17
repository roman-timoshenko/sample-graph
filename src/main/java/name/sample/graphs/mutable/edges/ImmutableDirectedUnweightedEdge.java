package name.sample.graphs.mutable.edges;

import name.sample.graphs.Edge;

import java.util.Objects;

public final class ImmutableDirectedUnweightedEdge<Vertex> implements Edge<Vertex> {

    private final Vertex nodeA;
    private final Vertex nodeB;

    public ImmutableDirectedUnweightedEdge(Vertex nodeA, Vertex nodeB) {
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
        ImmutableDirectedUnweightedEdge<?> that = (ImmutableDirectedUnweightedEdge<?>) o;
        return Objects.equals(nodeA, that.nodeA) &&
                Objects.equals(nodeB, that.nodeB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB);
    }

    @Override
    public String toString() {
        return "ImmutableDirectedUnweightedEdge{" +
                "nodeA=" + nodeA +
                ", nodeB=" + nodeB +
                '}';
    }
}
