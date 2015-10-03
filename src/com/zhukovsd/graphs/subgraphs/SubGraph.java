package com.zhukovsd.graphs.subgraphs;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

import java.util.*;

/**
 * Subgraph is a graph, where each {@link Vertex vertex} of it corresponds to particular vertex
 * of {@link #parentGraph parent graph}. Vertexes of subgraph is a subset of vertexes of outer graph, as so for edges.
 * @param <E> type of {@link Vertex vertex} in parent (not this!) graph.
 */
public class SubGraph<E extends Vertex<E>> extends Graph<SubGraphVertex<E>> {
    Graph<E> parentGraph;

    // map that consists of pairs of parent vertex and corresponding subgraph vertex.
    HashMap<Vertex<E>, SubGraphVertex<E>> parentVertexesMap = new HashMap<>();

    /**
     * Create empty subgraph for given parent {@link Graph graph}.
     * @param parentGraph parent graph
     */
    SubGraph(Graph<E> parentGraph) {
        this.parentGraph = parentGraph;
    }

    /**
     * Method adds {@link SubGraphVertex sub graph vertex} to the graph.
     * @param vertex vertex to add
     */
    @Override
    public void addVertex(SubGraphVertex<E> vertex) {
        super.addVertex(vertex);

        parentVertexesMap.put(vertex.parentVertex, vertex);
    }

    /**
     * Method disconnects 2 {@link SubGraphVertex subgraph vertexes} by removing corresponding {@link Edge edges}
     * from both vertexes {@link Vertex#edgeList edgeLists}.
     * @param leftParentVertex parent vertex for source of removed edge
     * @param rightParentVertex parent vertex for destination of removed edge
     */
    public void disconnectFromEachOtherByParents(E leftParentVertex, E rightParentVertex) {
        disconnectFromEachOther(parentVertexesMap.get(leftParentVertex), parentVertexesMap.get(rightParentVertex));
    }

    /**
     * Fill current subgraph as spanning tree of parent graph. Method assumed that current graph is empty. Algorithm
     * based on depth-first search.
     */
    public void findSpanningTree() {
        Random rand = new Random();
        List<Vertex<E>> list = new ArrayList<>();

        list.add(parentGraph.vertexList.get(0));
        addVertex(new SubGraphVertex<>(parentGraph.vertexList.get(0)));

        while (list.size() > 0) {
            Vertex<E> vertex;
            if ((list.size() < 2) || (rand.nextInt(9) > 0))
                vertex = list.get(list.size() - 1);
            else
                vertex = list.get(list.size() - 2);

            EdgeList<E> edgeList = new EdgeList<>();
            edgeList.addAll(vertex.edgeList);

            boolean isEdgeFound = false;
            while ((edgeList.size() > 0) && (!isEdgeFound)) {
                Edge<E> edge = edgeList.remove(rand.nextInt(edgeList.size()));

                if (!parentVertexesMap.containsKey(edge.destination)) {
                    isEdgeFound = true;

                    SubGraphVertex<E> newVertex = new SubGraphVertex<>(edge.destination);
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
