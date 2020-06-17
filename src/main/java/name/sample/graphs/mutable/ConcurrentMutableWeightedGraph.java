package name.sample.graphs.mutable;

import name.sample.graphs.WeightedEdge;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A concurrent decorator for a {@link MutableWeightedGraph} implementation.
 * <p/>
 * Uses {@link ReentrantReadWriteLock} to implement thread safety.
 *
 * @param <V> graph vertex type
 * @param <W> graph edge weight type
 */
public class ConcurrentMutableWeightedGraph<V, W> implements MutableWeightedGraph<V, W> {

    private final MutableWeightedGraph<V, W> implementation;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public ConcurrentMutableWeightedGraph(final MutableWeightedGraph<V, W> implementation) {
        this.implementation = Objects.requireNonNull(implementation);
    }

    @Override
    public Set<V> getVertices() {
        readLock.lock();
        try {
            return implementation.getVertices();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Collection<WeightedEdge<V, W>> getEdges() {
        readLock.lock();
        try {
            return implementation.getEdges();
        } finally {
            readLock.unlock();
        }

    }

    @Override
    public Collection<WeightedEdge<V, W>> getOutgoingEdges(final V vertex) {
        readLock.lock();
        try {
            return implementation.getOutgoingEdges(vertex);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void putVertex(final V vertex) {
        writeLock.lock();
        try {
            implementation.putVertex(vertex);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void putEdge(V a, V b, W weight) {
        writeLock.lock();
        try {
            implementation.putEdge(a, b, weight);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        return "ConcurrentMutableWeightedGraph{" + implementation.toString() + "}";
    }
}
