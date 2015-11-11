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
     * Due to using Edge objects as hashmap keys, override equals() method. Edges which connects same {@link Vertex}
     * treats as equal.
     * @param o object for comparison with current edge
     * @return comparison result
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge<?> edge = (Edge<?>) o;

        if (!source.equals(edge.source)) return false;
        return destination.equals(edge.destination);
    }

    /**
     * Due to using Edge objects as hashmap keys, override hashCode() method. Edges which connects same {@link Vertex}
     * has the same hash code.
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
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