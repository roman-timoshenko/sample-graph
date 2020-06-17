package name.sample.graphs.mutable;

import name.sample.graphs.mutable.edges.ImmutableDirectedWeightedEdge;
import name.sample.graphs.Edge;
import name.sample.graphs.WeightedEdge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentest4j.AssertionFailedError;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class MutableDirectedWeightedGraphTest {

    private final MutableWeightedGraph<Integer, Integer> graph = new MutableDirectedWeightedGraph<>();

    @DisplayName("Test get same vertices that were put")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} vertices")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetSameVerticesThatWerePut(int count) {
        final Set<Integer> expected = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            graph.putVertex(i);
            expected.add(i);
        }
        assertEquals(count, graph.getVertices().size());
        assertIterableEquals(expected, graph.getVertices());
    }

    @DisplayName("Test get same edges that were put")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} edges")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetSameEdgesThatWerePut(int count) {
        final Set<Integer> expectedVertices = new HashSet<>();
        final Set<Edge<Integer>> expectedEdges = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            graph.putEdge(i, i + 1, i);
            expectedVertices.add(i);
            expectedVertices.add(i + 1);
            expectedEdges.add(new ImmutableDirectedWeightedEdge<>(i, i + 1, i));
        }
        assertEquals(count == 0 ? 0 : count + 1, graph.getVertices().size());
        assertIterableEquals(expectedVertices, graph.getVertices());
        assertIterableEquals(expectedEdges, graph.getEdges());
    }

    @DisplayName("Test get correct outgoing edges")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} edges")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetOutgoingEdges(int count) {
        // add outgoing edges
        for (int i = 0; i < count; ++i) {
            for (int j = count + 1; j < count * 2 + 1; ++j) {
                graph.putEdge(i, j, i);
            }
        }
        // add incoming edges
        for (int i = 0; i < count; ++i) {
            graph.putEdge(count + i + 1, i, i);
        }
        // add free hanging edges
        for (int i = (count + 1) * 2; i < (count + 1) * 3; ++i) {
            graph.putVertex(i);
        }
        for (int i = 0; i < count; ++i) {
            final Collection<WeightedEdge<Integer, Integer>> outgoingEdges = graph.getOutgoingEdges(i);
            assertEquals(count, outgoingEdges.size());
            for (final WeightedEdge<Integer, Integer> edge : outgoingEdges) {
                assertEquals(i, edge.getNodeA());
                assertEquals(i, edge.getWeight());
            }
        }
    }

    @Test
    @DisplayName("Test reversed edge does exist")
    void testReverseEdgeDoesNotExist() {
        graph.putEdge(0, 1, 1);
        assertEquals(1, graph.getOutgoingEdges(0).size());
        assertEquals(0, graph.getOutgoingEdges(1).size());
    }

    @DisplayName("Test reversed edge does exist")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} edges")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetSameEdgeWeightThatWasSet(int weight) {
        graph.putEdge(0, 1, weight);
        assertEquals(1, graph.getOutgoingEdges(0).size());
        final WeightedEdge<Integer, Integer> edge = graph.getOutgoingEdges(0).stream().findFirst()
                .orElseThrow(() -> new AssertionFailedError("number of outgoing edges should be more than zer0"));
        assertEquals(weight, edge.getWeight());
    }

}