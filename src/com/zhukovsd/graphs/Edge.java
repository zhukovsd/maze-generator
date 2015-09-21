package com.zhukovsd.graphs;

/**
 * Graph edge, a directional connection between 2 vertexes
 */
class Edge {
    public final Vertex source, destination;

    public Edge(Vertex source, Vertex destination) {
        this.source = source;
        this.destination = destination;
    }
}
