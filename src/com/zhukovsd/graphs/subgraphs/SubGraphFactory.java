package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

/**
 * Created by ZhukovSD on 30.09.2015.
 */
public class SubGraphFactory {
    public static <T extends Vertex> SubGraph<T> createSpanningTree(Graph<T> parentGraph) {
        SubGraph<T> result = new SubGraph<T>(parentGraph);
        result.findSpanningTree();

        return result;
    }

    public static <T extends Vertex> SubGraph<T> createFullSubGraph(Graph<T> parentGraph) {
        SubGraph<T> result = new SubGraph<T>(parentGraph);

        for (Vertex vertex : parentGraph.vertexList) {
            result.addVertex(new SubGraphVertex(vertex));
        }

        for (Vertex vertex : parentGraph.vertexList) {
            for (Edge edge : vertex.edgeList) {
                result.connect(result.parentVertexesMap.get(edge.source), result.parentVertexesMap.get(edge.destination));
            }
        }

        return result;
    }
}
