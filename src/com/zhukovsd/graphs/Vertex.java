package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Vertex of {@link Graph graph}, connected with another vertexes
 */
public class Vertex {
    /**
     * List of {@link Edge edges}, directional connections between current vertex and adjacent to it
     */
    public final List<Edge> edgeList = new ArrayList<Edge>();
}
