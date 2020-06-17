package name.sample.graphs.algorithm;

import name.sample.graphs.Edge;
import name.sample.graphs.Graph;
import name.sample.graphs.mutable.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DftPathFinderTest {

    private static final List<MutableWeightedGraph<Integer, Integer>> MUTABLE_WEIGHTED_GRAPHS = Arrays.asList(
            new MutableDirectedWeightedGraph<>(),
            new MutableUndirectedWeightedGraph<>(),
            new ConcurrentMutableWeightedGraph<>(new MutableDirectedWeightedGraph<>())
    );

    private static final List<MutableUnweightedGraph<Integer>> MUTABLE_UNWEIGHTED_GRAPHS = Arrays.asList(
            new MutableDirectedUnweightedGraph<>(),
            new MutableUndirectedUnweightedGraph<>()
    );

    private static final List<Graph<Integer, ? extends Edge<Integer>>> GRAPHS = Stream.concat(MUTABLE_UNWEIGHTED_GRAPHS.stream(),
            MUTABLE_WEIGHTED_GRAPHS.stream()).collect(Collectors.toList());

    @BeforeAll
    static void setUpUnweightedGraphs() {
        for (final MutableUnweightedGraph<Integer> graph : MUTABLE_UNWEIGHTED_GRAPHS) {
            graph.putEdge(0, 1);
            graph.putEdge(0, 2);
            graph.putEdge(0, 3);
            graph.putEdge(1, 4);
            graph.putEdge(2, 4);
            graph.putEdge(3, 4);
            graph.putVertex(5);
        }
    }

    @BeforeAll
    static void setUpWeightedGraphs() {
        for (final MutableWeightedGraph<Integer, Integer> graph : MUTABLE_WEIGHTED_GRAPHS) {
            graph.putEdge(0, 1, 1);
            graph.putEdge(0, 2, 1);
            graph.putEdge(0, 3, 1);
            graph.putEdge(1, 4, 1);
            graph.putEdge(2, 4, 1);
            graph.putEdge(3, 4, 1);
            graph.putVertex(5);
        }
    }


    @ParameterizedTest
    @DisplayName("Test path finding when path exists")
    @MethodSource("getGraphsMethod")
    void testGetExistingRoute(Graph<Integer, ? extends Edge<Integer>> graph) {
        final PathFinder<Integer> pathFinder = new DftPathFinder<>(graph);
        final List<Edge<Integer>> result = pathFinder.getPath(0, 4);
        assertEquals(2, result.size());
        assertEquals(0, result.get(0).getNodeA());
        assertEquals(4, result.get(1).getNodeB());
        assertEquals(result.get(0).getNodeB(), result.get(1).getNodeA());
    }

    @ParameterizedTest
    @DisplayName("Test path finding when no path exists")
    @MethodSource("getGraphsMethod")
    void testGetNonExistingRoute(Graph<Integer, ? extends Edge<Integer>> graph) {
        final PathFinder<Integer> pathFinder = new DftPathFinder<>(graph);
        final List<Edge<Integer>> result = pathFinder.getPath(0, 5);
        assertTrue(result.isEmpty());
    }

    // since this method is received using reflection it is required to be non-private
    @SuppressWarnings("WeakerAccess")
    static Collection<Graph<Integer, ? extends Edge<Integer>>> getGraphsMethod() {
        return GRAPHS;
    }

}