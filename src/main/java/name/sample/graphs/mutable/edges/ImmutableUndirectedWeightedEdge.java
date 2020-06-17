package name.sample.graphs.mutable.edges;

import name.sample.graphs.WeightedEdge;

import java.util.Objects;

public final class ImmutableUndirectedWeightedEdge<Vertex, Weight> implements WeightedEdge<Vertex, Weight> {

    private final Vertex nodeA;
    private final Vertex nodeB;
    private final Weight weight;

    public ImmutableUndirectedWeightedEdge(Vertex nodeA, Vertex nodeB, Weight weight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;
    }

    @Override
    public Weight getWeight() {
        return weight;
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
        ImmutableUndirectedWeightedEdge<?, ?> that = (ImmutableUndirectedWeightedEdge<?, ?>) o;
        return (Objects.equals(nodeA, that.nodeA) &&
                Objects.equals(nodeB, that.nodeB)) ||
                (Objects.equals(nodeA, that.nodeB) &&
                        Objects.equals(nodeB, that.nodeA));
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB);
    }

    @Override
    public String toString() {
        return "ImmutableUndirectedWeightedEdge{" +
                "nodeA=" + nodeA +
                ", nodeB=" + nodeB +
                ", weight=" + weight +
                '}';
    }
}
