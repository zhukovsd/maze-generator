package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Face is the part of plain, bounded by edges
 */
class Face {
    /**
     * Edges, which bounds current face.
     */
    public final List<Edge> edgeList = new ArrayList<Edge>();

    /**
     * Vertexes, which lays in corners of current faces.
     */
    public final List<Vertex> vertexList = new ArrayList<Vertex>();
    /**
     * Faces of same graph, which has one or multiple common edges with current graph.
     */
    public final List<Face> adjacentFaceList = new ArrayList<Face>();

    /**
     * Create face by list of edges bounding it.
     * @param edgeList list of edges. Constructor expects that edges in this list forms cycle, i.e. each next edge's
     * source is previous edge's destination
     */
    Face(List<Edge> edgeList) {
        this.edgeList.addAll(edgeList);

        for (Edge edge : this.edgeList) {
            vertexList.add(edge.source);
        }
    }

    /**
     * Get string representation for current face. If all it's vertexes has tags, result is concatenation of all this tags.
     * @return string representation for current
     */
    public String toString() {
        boolean flag = true;
        String s = "";

        for (Vertex vertex : vertexList) {
            if (vertex.tag == 0) {
                flag = false;
                break;
            }
            else
                s += "" + vertex.tag;
        }

        if (flag)
            return s;
        else
            return super.toString();
    }
}
