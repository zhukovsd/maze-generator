package com.zhukovsd.graphs.dual;

import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.embedded.EmbeddedGraph;
import com.zhukovsd.graphs.embedded.EmbeddedVertex;
import com.zhukovsd.graphs.embedded.Face;

import java.util.HashMap;

/**
 * Every vertex of dual graph lays inside of corresponding face of outer graph. In contrast to classical dual graph
 * in graph theory, this implementation does not have vertex for outer face of outer graph. Outer graph must be embedded
 * in order to have faces, <a href="https://en.wikipedia.org/wiki/Dual_graph">Dual graph on wikipedia</a>.
 */
public class DualGraph<T extends EmbeddedVertex> extends Graph<DualVertex> {
    /**
     * Outer graph of current dual graph.
     */
    private EmbeddedGraph<T> outerGraph;

    /**
     * Create and fill graph. Create vertex for every outer face in outer graph, create 2 directional edges for
     * every pair of adjacent faces in outer graph
     */
    public DualGraph(EmbeddedGraph<T> outerGraph) {
        this.outerGraph = outerGraph;

        // maps faces to vertexes to connect nodes according by outer graph's faces adjacency relations
        HashMap<Face, DualVertex> map = new HashMap<Face, DualVertex>();

        // first, create all vertexes
        for (Face face : outerGraph.getFaces()) {
            DualVertex vertex = new DualVertex(face);

            vertexList.add(vertex);
            map.put(face, vertex);
        }

        // second, connect them
        for (Face face : outerGraph.getFaces()) {
            for (Face adjacentFace : face.adjacentFaceList) {
                connect(map.get(face), map.get(adjacentFace));
            }
        }
    }
}
