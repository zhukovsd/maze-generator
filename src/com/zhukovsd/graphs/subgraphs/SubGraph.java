package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

import java.util.*;

/**
 * Created by ZhukovSD on 30.09.2015.
 */
public class SubGraph<E extends Vertex> extends Graph<SubGraphVertex> {
    Graph<E> parentGraph;

    // map that consists of parent vertex - subgraph vertex pairs
    HashMap<Vertex, SubGraphVertex> parentVertexesMap = new HashMap<>();

    /**
     *
     */
    public SubGraph(Graph<E> parentGraph) {
        this.parentGraph = parentGraph;
    }

    /**
     * Method adds {@link SubGraphVertex sub graph vertex} to the graph.
     * @param vertex vertex to add
     */
    @Override
    public void addVertex(SubGraphVertex vertex) {
        super.addVertex(vertex);

        parentVertexesMap.put(vertex.parentVertex, vertex);
    }

    /**
     *
     */
    public void disconnectFromEachOtherByParents(Vertex leftParentNode, Vertex rightParentNode) {
        disconnectFromEachOther(parentVertexesMap.get(leftParentNode), parentVertexesMap.get(rightParentNode));
    }

    /**
     *
     */
     void findSpanningTree() {
        Random rand = new Random();
        List<Vertex> list = new ArrayList<>();

        list.add(parentGraph.vertexList.get(0));
        addVertex(new SubGraphVertex(parentGraph.vertexList.get(0)));

        while (list.size() > 0) {
            Vertex vertex;
            if ((list.size() < 2) || (rand.nextInt(9) > 0))
                vertex = list.get(list.size() - 1);
            else
                vertex = list.get(list.size() - 2);

            EdgeList edgeList = new EdgeList();
            edgeList.addAll(vertex.edgeList);

            boolean isEdgeFound = false;
            while ((edgeList.size() > 0) && (!isEdgeFound)) {
                Edge edge = edgeList.remove(rand.nextInt(edgeList.size()));

                if (!parentVertexesMap.containsKey(edge.destination)) {
                    isEdgeFound = true;

                    SubGraphVertex newVertex = new SubGraphVertex(edge.destination);
                    addVertex(newVertex);

                    connectToEachOther(parentVertexesMap.get(vertex), newVertex);

                    list.add(edge.destination);
                }
            }

            if (!isEdgeFound) {
                list.remove(vertex);

                if (list.size() > 0)
                    list.add(list.remove(0));
            }
        }
    }
}
