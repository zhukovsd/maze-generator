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
     * Find {@link SubGraphVertex vertex} by it's parent vertex.
     * @param parentVertex given parent vertex
     * @return found vertex
     */
    public SubGraphVertex<E> vertexByParent(E parentVertex) {
        return parentVertexesMap.get(parentVertex);
    }

    /**
     * Fill current subgraph as spanning tree of parent graph. Method assumed that current graph is empty. Algorithm
     * based on depth-first search.
     * @param excludedVertexes vertexes, which will not be included in resulting spanning tree
     */
    public void findSpanningTree(ArrayList<E> excludedVertexes) {
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

                if (!parentVertexesMap.containsKey(edge.destination) && (!excludedVertexes.contains(edge.destination))) {
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

    /**
     * Fill current graph as shortest path of parent graph, using Dijkstra algorithm, <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">wiki article</a>.
     * @param pathSourceVertex vertex to start path from
     * @param pathDestinationVertex destination vertex
     */
    void findShortestPathDijkstra(E pathSourceVertex, E pathDestinationVertex) {
        class VertexPathData {
            boolean isVisited;
            int length;
            List<E> path = new ArrayList<>();
        }

        HashMap<E, VertexPathData> map = new HashMap<>();

        for (E vertex : parentGraph.vertexList) {
            VertexPathData data = new VertexPathData();

            if (vertex != pathSourceVertex)
                data.length = Integer.MAX_VALUE;
            else
                data.path.add(vertex);

            map.put(vertex, data);
        }

        boolean isAllVisited = false;
        E vertex = pathSourceVertex;

        while (!isAllVisited) {
            E source = vertex;
            VertexPathData sourceData = map.get(source);

            for (Edge<E> edge : source.edgeList) {
                E destination = edge.destination;
                VertexPathData destData = map.get(destination);

                if ((!destData.isVisited) || (sourceData.length + 1 < destData.length)) {
                    destData.length = sourceData.length + 1;

                    destData.path.addAll(sourceData.path);
                    destData.path.add(destination);
                }
            }

            sourceData.isVisited = true;

            isAllVisited = true;
            E nextVertex = null;
            for (E v : parentGraph.vertexList) {
                VertexPathData data = map.get(v);

                if (!data.isVisited) {
                    isAllVisited = false;

                    if ((nextVertex == null) || (data.length < map.get(nextVertex).length)) {
                        nextVertex = v;
                    }
                }
            }

            vertex = nextVertex;
        }

        // when path is found, fill graph with it's vertexes
        List<E> path = map.get(pathDestinationVertex).path;
        for (int i = 0; i < path.size(); i++) {
            SubGraphVertex<E> subvertex = new SubGraphVertex<>(path.get(i));
            addVertex(subvertex);

            if (i > 0)
                connectToEachOther(subvertex, vertexList.get(vertexList.size() - 2));
        }
    }

    /**
     * Fill current graph as shortest path of parent graph, using breadth first search,
     * <a href="https://en.wikipedia.org/wiki/Breadth-first_search">wiki article</a>.
     * @param pathSourceVertex vertex to start path from
     * @param pathDestinationVertex destination vertex
     */
    void findShortestPathBFS(E pathSourceVertex, E pathDestinationVertex) {
        // list of traversed vertexes, search is over when list is empty or destination is found
        List<E> list = new ArrayList<>();
        // map that consists of vertexes and paths to them
        HashMap<E, ArrayList<E>> map = new HashMap<>();

        // search started from source vertex
        list.add(pathSourceVertex);

        // add source vertex to map, source vertex path is this vertex itself
        ArrayList<E> sourceVertexPath = new ArrayList<>();
        sourceVertexPath.add(list.get(0));
        map.put(list.get(0), sourceVertexPath);

        boolean isFound = false;
        while ((list.size() > 0) && (!isFound)) {
            E vertex = list.remove(0);

            for (Edge<E> edge : vertex.edgeList) {
                if (!map.containsKey(edge.destination)) {
                    ArrayList<E> path = new ArrayList<>();
                    path.addAll(map.get(edge.source));
                    path.add(edge.destination);

                    map.put(edge.destination, path);
                    list.add(edge.destination);

                    if (edge.destination == pathDestinationVertex)
                        isFound = true;
                }
            }
        }

        // when path is found, fill graph with it's vertexes
        ArrayList<E> path = map.get(pathDestinationVertex);
        for (int i = 0; i < path.size(); i++) {
            SubGraphVertex<E> vertex = new SubGraphVertex<>(path.get(i));
            addVertex(vertex);

            if (i > 0)
                connectToEachOther(vertex, vertexList.get(vertexList.size() - 2));
        }
    }
}
