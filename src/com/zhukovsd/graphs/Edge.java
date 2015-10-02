package com.zhukovsd.graphs;

/**
 * Edge of {@link Graph graph}, a directional connection between 2 {@link Vertex vertexes}.
 * @param <T> type of vertexes, connected by edge
 */
public class Edge<T extends Vertex<T>> {
    /**
     * One of {@link Vertex vertexes} connected by current edge
     */
    public final T source, destination;

    /**
     * Create new edge by given {@link Vertex vertexes}
     * @param source source vertex
     * @param destination destination vertex
     */
    public Edge(T source, T destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Get string representation for current edge. If all it's {@link Vertex vertexes} has tags,
     * result is concatenation of all their tags.
     * @return string representation for current edge
     */
    @Override
    public String toString() {
        if ((source.tag != 0) && (destination.tag != 0))
            return "" + source.tag + destination.tag;
        else
            return super.toString();
    }
}