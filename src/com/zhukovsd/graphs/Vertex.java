package com.zhukovsd.graphs;

/**
 * Vertex of {@link Graph graph}, connected with another vertexes.
 */
public class Vertex {
    public char tag;

    /**
     * List of {@link Edge edges}, directional connections between current vertex and adjacent to it
     */
    public final List<Edge> edgeList = new ArrayList<Edge>();
}
