package com.zhukovsd.graphs.embedded;

import com.zhukovsd.Point;
import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Vertex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Face is the part of the plain, on which {@link EmbeddedGraph embedded graph} is placed, bounded by edges.
 * @param <E> type of {@link Vertex vertexes} of embedded graph, which vertexes and edges forms face
 */
public class Face<E extends EmbeddedVertex<E>> implements Serializable {
    /**
     * Edges, which bounds current face.
     */
    public final List<Edge<E>> edgeList = new ArrayList<>();

    /**
     * Vertexes, which lays in corners of current face.
     */
    public final List<E> vertexList = new ArrayList<>();

    /**
     * Faces of same {@link EmbeddedGraph embedded graph}, which has one or multiple common edges with current face.
     */
    public final List<Face<E>> adjacentFaceList = new ArrayList<>();

    /**
     * Create face by list of edges bounding it.
     * @param edgeList list of edges. Constructor expects that edges in this list forms cycle, i.e. each next edge's
     * source is previous edge's destination
     */
    Face(List<Edge<E>> edgeList) {
        this.edgeList.addAll(edgeList);

        for (Edge<E> edge : this.edgeList) {
            vertexList.add(edge.source);
        }
    }

    /**
     * Find edges, which bounds 2 faces simultaneously, current and given. Edges considered as non-directional,
     * that is, if one face contains edge <b>AB</b> and another face contains edge <b>BA</b>, then edge <b>AB</b>
     * counts as common for this 2 faces.
     * @param face face to search common faces with
     * @return list of common edges
     */
    public EdgeList<E> findCommonEdges(Face<?> face) {
        EdgeList<E> result = new EdgeList<>();

        for (Edge<E> edge1 : edgeList) {
            for (Edge<?> edge2 : face.edgeList) {
                if (((edge1.source == edge2.source) && (edge1.destination == edge2.destination)) ||
                        ((edge1.source == edge2.destination) && (edge1.destination == edge2.source))
                ) {
                    result.add(edge1);
                }
            }
        }

        return result;
    }

    public Point calculateCenterPosition() {
        Point faceCenter = new Point();

        // face center is arithmetic mean of face vertexes position's
        for (E vertex : this.vertexList) {
            Point position = vertex.getPosition();

            faceCenter.x += position.x;
            faceCenter.y += position.y;
        }

        faceCenter.x /= this.vertexList.size();
        faceCenter.y /= this.vertexList.size();

        return faceCenter;
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
