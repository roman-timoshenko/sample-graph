package name.sample.graphs.mutable;

import name.sample.graphs.Edge;
import name.sample.graphs.mutable.edges.ImmutableUndirectedUnweightedEdge;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class MutableUndirectedUnweightedGraphTest {

    private final MutableUnweightedGraph<Integer> graph = new MutableUndirectedUnweightedGraph<>();

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
            graph.putEdge(i, i + 1);
            expectedVertices.add(i);
            expectedVertices.add(i + 1);
            expectedEdges.add(new ImmutableUndirectedUnweightedEdge<>(i, i + 1));
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
                graph.putEdge(i, j);
            }
        }
        // add incoming edges
        for (int i = 0; i < count; ++i) {
            graph.putEdge(count + i + 1, i);
        }
        // add free hanging edges
        for (int i = (count + 1) * 2; i < (count + 1) * 3; ++i) {
            graph.putVertex(i);
        }
        for (int i = 0; i < count; ++i) {
            final Collection<Edge<Integer>> outgoingEdges = graph.getOutgoingEdges(i);
            assertEquals(count, outgoingEdges.size());
        }
    }

    @Test
    @DisplayName("Test reversed edge does exist")
    void testReverseEdgeDoesExist() {
        graph.putEdge(0, 1);
        assertEquals(1, graph.getOutgoingEdges(0).size());
        assertEquals(1, graph.getOutgoingEdges(1).size());
    }
}