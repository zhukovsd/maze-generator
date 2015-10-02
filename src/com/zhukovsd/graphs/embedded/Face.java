package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Face is the part of plain, bounded by edges
 */
public class Face {
    /**
     * Edges, which bounds current face.
     */
    public final List<Edge> edgeList = new ArrayList<Edge>();

    /**
     * Vertexes, which lays in corners of current face.
     */
    public final List<EmbeddedVertex> vertexList = new ArrayList<EmbeddedVertex>();
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
            vertexList.add(((EmbeddedVertex) edge.source));
        }
    }

    public EdgeList findCommonEdges(Face face) {
        EdgeList result = new EdgeList();

        for (Edge edge1 : edgeList) {
            for (Edge edge2 : face.edgeList) {
                if (((edge1.source == edge2.source) && (edge1.destination == edge2.destination)) ||
                        ((edge1.source == edge2.destination) && (edge1.destination == edge2.source))
                ) {
                    result.add(edge1);
                }
            }
        }

        return result;
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
