package name.sample.graphs.mutable;

import name.sample.graphs.Edge;
import name.sample.graphs.mutable.edges.ImmutableUndirectedWeightedEdge;
import name.sample.graphs.WeightedEdge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.opentest4j.AssertionFailedError;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ConcurrentMutableUndirectedWeightedGraphTest {

    private final MutableWeightedGraph<Integer, Integer> implementation = new MutableUndirectedWeightedGraph<>();
    private final MutableWeightedGraph<Integer, Integer> graph = new ConcurrentMutableWeightedGraph<>(implementation);

    @DisplayName("Test get same vertices that were put")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} vertices")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetSameVerticesThatWerePut(int count) {
        final Set<Integer> expected = new HashSet<>();
        for (int i = 0; i < count; ++i) {
            graph.putVertex(i);
            expected.add(i);
        }
        Assertions.assertEquals(count, graph.getVertices().size());
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
            expectedEdges.add(new ImmutableUndirectedWeightedEdge<>(i, i + 1, i));
            expectedEdges.add(new ImmutableUndirectedWeightedEdge<>(i + 1, i, i));
        }
        Assertions.assertEquals(count == 0 ? 0 : count + 1, graph.getVertices().size());
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
        }
    }

    @Test
    @DisplayName("Test reversed edge does exist")
    void testReverseEdgeDoesExist() {
        graph.putEdge(0, 1, 1);
        Assertions.assertEquals(1, graph.getOutgoingEdges(0).size());
        Assertions.assertEquals(1, graph.getOutgoingEdges(1).size());
    }

    @DisplayName("Test reversed edge does exist")
    @ParameterizedTest(name = "[{index}] with {argumentsWithNames} edges")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 10, 20, 50})
    void testGetSameEdgeWeightThatWasSet(int weight) {
        graph.putEdge(0, 1, weight);
        Assertions.assertEquals(1, graph.getOutgoingEdges(0).size());
        final WeightedEdge<Integer, Integer> from0to1 = graph.getOutgoingEdges(0).stream().findFirst()
                .orElseThrow(() -> new AssertionFailedError("number of outgoing edges should be more than zer0"));
        assertEquals(weight, from0to1.getWeight());
        Assertions.assertEquals(1, graph.getOutgoingEdges(1).size());
        final WeightedEdge<Integer, Integer> from1to0 = graph.getOutgoingEdges(1).stream().findFirst()
                .orElseThrow(() -> new AssertionFailedError("number of outgoing edges should be more than zer0"));
        assertEquals(weight, from1to0.getWeight());
    }

    /**
     * This code only verifies that no exception occurred during adding edges to graph.
     */
    @Test
    @DisplayName("Test thread safety")
    void testConcurrentExecution() throws Exception {
        final int vertexCount = 10;
        final int threads = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(threads);
        final CountDownLatch latch = new CountDownLatch(1);
        final List<Future<?>> futures =
                Stream.generate(() -> executorService.submit(
                        () -> {
                            try {
                                latch.await();
                                for (int i = 0; i < vertexCount; ++i) {
                                    graph.putVertex(i);
                                    graph.putEdge(i, i + 1, i);
                                }
                            } catch (InterruptedException e) {
                                throw new AssertionFailedError("thread was interrupted", e);
                            }
                        }
                )).limit(threads).collect(Collectors.toList());
        latch.countDown();
        futures.forEach(future -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new AssertionFailedError("thread execution failed", e);
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        Assertions.assertEquals(vertexCount + 1, graph.getVertices().size());
    }

}