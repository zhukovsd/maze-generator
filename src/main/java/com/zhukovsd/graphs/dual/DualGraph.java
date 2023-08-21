package com.zhukovsd.graphs.dual;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;
import com.zhukovsd.graphs.embedded.Face;

import java.util.HashMap;

/**
 * Every vertex of dual graph lays inside of corresponding {@link Face face} of outer graph. In contrast to classical dual graph
 * in graph theory, this implementation does not have vertex for outer face of outer graph.
 * Outer graph must be {@link EmbeddedGraph embedded} in order to have faces.
 * <a href="https://en.wikipedia.org/wiki/Dual_graph">Dual graph on wikipedia</a>.
 */
public class DualGraph<E extends EmbeddedVertex<E>> extends Graph<DualVertex<E>> {
    /**
     * Outer {@link EmbeddedGraph embedded graph} of current dual graph.
     */
    private EmbeddedGraph<E> outerGraph;

    /**
     * Create and fill graph. Create {@link DualVertex vertex} for every outer {@link Face face} in outer graph,
     * create 2 directional {@link Edge edges} for every pair of adjacent faces in outer graph.
     * @param outerGraph graph faces of which contains new dual graph's vertexes
     */
    public DualGraph(EmbeddedGraph<E> outerGraph) {
        this.outerGraph = outerGraph;

        // maps faces to vertexes to connect nodes according by outer graph's faces adjacency relations
        HashMap<Face<E>, DualVertex<E>> map = new HashMap<>();

        // first, create all vertexes
        for (Face<E> face : outerGraph.getFaces()) {
            DualVertex<E> vertex = new DualVertex<>(face);

            addVertex(vertex);
            map.put(face, vertex);
        }

        // second, connect them
        for (Face<E> face : outerGraph.getFaces()) {
            for (Face<E> adjacentFace : face.adjacentFaceList) {
                connectToEachOther(map.get(face), map.get(adjacentFace));
            }
        }
    }

    /**
     * Find {@link DualVertex vertex} by it's {@link Face face}
     * @param face given face
     * @return found vertex
     */
    public DualVertex<E> vertexByFace(Face<E> face) {
        for (DualVertex<E> vertex : vertexList) {
            if (vertex.face == face)
                return vertex;
        }

        return null;
    }
}
