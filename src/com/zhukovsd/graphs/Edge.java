package com.zhukovsd.graphs;

/**
 * Edge of {@link Graph graph}, a directional connection between 2 {@link Vertex vertexes}.
 */
public class Edge {
    /**
     * {@link Vertex vertexes} connected by current edge
     */
    public final Vertex source, destination;

    /**
     * Create new edge by given {@link Vertex vertexes}
     * @param source source vertex
     * @param destination destination vertex
     */
    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Get string representation for current edge. If all it's vertexes has tags, result is concatenation of all this tags.
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
