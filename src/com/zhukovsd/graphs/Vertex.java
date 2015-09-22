package com.zhukovsd.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * Vertex of {@link Graph graph}, connected with another vertexes.
 */
public class Vertex {
    /**
     * List of {@link Edge edges}, directional connections between current vertex and adjacent to it
     */
    public final List<Edge> edgeList = new ArrayList<Edge>();

    /**
     * Method to connect current vertex to another vertex by creating edge(s)
     * @param vertex vertex to connect to
     * @param inBothDirections if true, 2 edges will be created, from <code>this</code> to <code>vertex</code>
     * and vice versa, otherwise only one edge will be created (<code>this -> vertex</code>)
     */
    public void connect(Vertex vertex, boolean inBothDirections) {
        edgeList.add(new Edge(this, vertex));

        if (inBothDirections) vertex.edgeList.add(new Edge(vertex, this));
    }
}
