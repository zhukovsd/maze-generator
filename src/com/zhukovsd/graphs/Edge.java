package com.zhukovsd.graphs;

/**
 * Edge of {@link Graph graph}, a directional connection between 2 {@link Vertex vertexes}.
 */
public class Edge {
    /**
     * {@link Vertex vertexes} connected by current edge
     */
    public final Vertex source, destination;

    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }
}
