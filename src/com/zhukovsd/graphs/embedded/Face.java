package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhukovSD on 25.09.2015.
 */
class Face {
    public final List<Edge> edgeList = new ArrayList<Edge>();
    public final List<Vertex> vertexList = new ArrayList<Vertex>();
    public final List<Face> adjacentFaceList = new ArrayList<Face>();

    // Create face for list of edges bounding it. Constructor expects that edges in list forms cycle, i.e. each next edge's
    // source is previous edge's destination
    Face(List<Edge> edgeList) {
        this.edgeList.addAll(edgeList);

        for (Edge edge : this.edgeList) {
            vertexList.add(edge.source);
        }
    }
}
