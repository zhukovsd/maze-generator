package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

import java.util.*;

/**
 * Abstract graph, embedded to the coordinate space. That kind of graph has faces.
 * @param <E> type of {@link EmbeddedVertex embedded vertex} in graph.
 */
public class EmbeddedGraph<E extends EmbeddedVertex<E>> extends Graph<E> {
    enum BypassOrder {
        PRECEDING, SUCCEEDING
    }

    /**
     * Utility class used for finding faces, stores information for edge, used as value in edge-data map.
     */
    class EdgeData {
        /**
         * Is corresponding edge iterated yet.
         */
        boolean isIterated;

        /**
         * Preceding reverse edge for corresponding edge.
         */
        Edge<E> precedingReverseEdge;

        /**
         * Succeeding reverse edge for corresponding edge.
         */
        Edge<E> succeedingReverseEdge;

        /**
         * Face that bounded by this edge.
         */
        List<Face<E>> faces = new ArrayList<>();

        /**
         * Initialize instance with pre-calculated preceding and succeeding reverse edges.
         * @param precedingReverseEdge pre-calculated preceding reverse edge
         * @param succeedingReverseEdge pre-calculated succeeding reverse edge
         */
        public EdgeData(Edge<E> precedingReverseEdge, Edge<E> succeedingReverseEdge) {
            this.precedingReverseEdge = precedingReverseEdge;
            this.succeedingReverseEdge = succeedingReverseEdge;
        }
    }

    /**
     * Private storage for current graph {@link Face faces}.
     */
    private List<Face<E>> faceList;

    /**
     * Map, that stores additional data for every edge in edgeList, what is necessary for faces finding algorithm.
     */
    transient HashMap<Edge<E>, EdgeData> edgeMap = new HashMap<>();

    /**
     * Lazy initialize of get list of current embedded graph's face list.
     * @return face list
     */
    public List<Face<E>> getFaces() {
        if (faceList == null) faceList = findFaces();

        return faceList;
    }

    /**
     * Find loop of edges, which starts from given edge in given order.
     * @param firstEdge given order to start loop from
     * @param order bypass order
     * @return found loop
     */
    private EdgeList<E> findLoop(Edge<E> firstEdge, BypassOrder order) {
        EdgeList<E> result = new EdgeList<>();
        result.add(firstEdge);

        boolean isLoopFound = false;
        while (!isLoopFound) {
            Edge<E> loopEdge = result.get(result.size() - 1);

            Edge<E> nextEdge;
            if (order == BypassOrder.PRECEDING)
                nextEdge = edgeMap.get(loopEdge).precedingReverseEdge;
            else
                nextEdge = edgeMap.get(loopEdge).succeedingReverseEdge;

            result.add(nextEdge);

            if (result.get(0).source == result.get(result.size() - 1).destination)
                isLoopFound = true;
        }

        return result;
    }

    /**
     * Find loop which bounds the entire graph.
     * @return found perimeter loop
     */
    private EdgeList<E> findPerimeterLoop() {
        // choose vertex to start perimeter loop from. we may be assured that most top vertex included into it
        E mostTopVertex = vertexList.get(0);
        for (E vertex : vertexList) {
            if (vertex.getPosition().y < mostTopVertex.getPosition().y) {
                mostTopVertex = vertex;
            }
        }

        // ? is it safe to pick first edge
        return findLoop(mostTopVertex.edgeList.get(0), BypassOrder.SUCCEEDING);
    }

    /**
     * Find all {@link Face faces} of current embedded graph.
     * Algorithm based on math.stackexchange.com <u><a href="http://math.stackexchange.com/questions/8140/find-all-cycles-faces-in-a-graph">discussion</a></u>.
     * Algorithm steps:
     * <ul style="list-style-type: decimal;">
     *     <li>Sort {@link Edge edges} for each {@link Vertex vertex} of graph in counterclockwise order with {@link EdgeComparator}</li>
     *     <li>Compose edges for each vertex into one <b>edge list</b></li>
     *     <li>Find <b>perimeter loop list</b>, edges of which bounds the entire graph</li>
     *     <li>
     *         Iterate all edges in <b>edge list</b> from first to last and for each edge:
     *         <ul style="list-style-type: lower-roman;">
     *             <li>Check if current edge is not marked as iterated, if so, proceed to step (ii), go to next loop iteration otherwise</li>
     *             <li>Add current edge to the empty <b>edge loop list</b></li>
     *             <li>
     *                 While loop not found, repeat this statements (this logic implemented in {@link #findLoop(Edge, BypassOrder) method}:
     *                 <ul style="list-style-type: lower-alpha;">
     *                     <li>Get last edge of <b>edge loop list</b> as <b>loop edge</b></li>
     *                     <li>Consult the <b>sorted list</b> of edges for <b>loop edge</b> destination vertex to determine the <b>next edge</b> of loop.
     *                     If <b>loop edge</b> is <b>AB</b>, then look for <b>BA</b> edge in <b>sorted list</b> for vertex B and
     *                     get the edge, preceding to it. This edge is <b>next edge</b> of loop
     *                     <li>Add <b>next edge</b> to the <b>edge loop list</b>, if it's destination vertex
     *                     equals to first edge's source - loop found</li>
     *                 </ul>
     *             </li>
     *             <li>Now <b>edge loop list</b> contains edges that forms loop, but that loop is not necessary bounds
     *             face of current graph, it may entire graph. To determine that we have to compare <b>edge loop list</b>
     *             with <b>perimeter loop list</b>. If comparison result is false, then <b>edge loop list</b> edges bounds face,
     *             in that case we store that face and mark all it's edges as iterated</li>
     *         </ul>
     *     </li>
     *     <li>Iterate all found faces and search for pairs of adjacent ones. Two faces is adjacent if they have one or more
     *     common non-directional (<b>AB</b> = <b>BA</b> in that case) edges</li>
     * </ul>
     * @return list with found faces
     */
    private List<Face<E>> findFaces() {
        List<Face<E>> result = new ArrayList<>();

        ArrayList<Edge<E>> edgeList = new ArrayList<>();
        EdgeComparator<E> edgeComparator = new EdgeComparator<>();

        // compose add all edges of graph into one list
        for (E vertex : vertexList) {
//            vertex.edgeList.sort(edgeComparator);
            Collections.sort(vertex.edgeList, edgeComparator);

            edgeList.addAll(vertex.edgeList);
        }

        // fill edgeMap
        for (Edge<E> edge : edgeList) {
            Edge<E> reverseEdge = reverseEdgesMap.get(edge);

            // find preceding reverse edges as described in 3.iii.b step of find faces algorithm
            Edge<E> precedingReverseEdge = edge.destination.edgeList.previous(reverseEdge);
            Edge<E> succeedingReverseEdge = edge.destination.edgeList.next(reverseEdge);

            edgeMap.put(edge, new EdgeData(precedingReverseEdge, succeedingReverseEdge));
        }

        // find perimeter loop list and the reversed one
        EdgeList<E> perimeterLoopList = findPerimeterLoop();
        EdgeList<E> reversePerimeterLoopList = new EdgeList<>();
        for (Edge edge : perimeterLoopList) {
            reversePerimeterLoopList.add(reverseEdgesMap.get(edge));
        }

        for (Edge<E> edge : edgeList) {
            if (!edgeMap.get(edge).isIterated) {
                EdgeList<E> loopList = findLoop(edge, BypassOrder.PRECEDING);

                // check if found loop is not a perimeter
                boolean isLoopValid = !(
                        loopList.isSameListOfUnidirectionalEdges(perimeterLoopList) || loopList.isSameListOfUnidirectionalEdges(reversePerimeterLoopList)
                );

                // if valid face found, store it
                if (isLoopValid) {
                    Face<E> face = new Face<>(loopList);
                    result.add(face);

                    for (Edge loopEdge : loopList) {
                        EdgeData edgeData = edgeMap.get(loopEdge);

                        edgeData.isIterated = true;

                        // remember that edges in loopList forms found edge
                        edgeData.faces.add(face);
                        // if edge AB bounds face ABCD, then egde BA bounds it too
                        edgeMap.get(reverseEdgesMap.get(loopEdge)).faces.add(face);
                    }
                }
            }
        }

        // find which of found faces are adjacent to each other
        for (Face<E> face : result) {
            for (Edge<E> edge : face.edgeList) {
                // 2 faces are adjacent if they have one or multiple common non-directional edge
                List<Face<E>> facesByEdge = edgeMap.get(edge).faces;

                for (Face<E> adjacentFace : facesByEdge) {
                    if ((adjacentFace != face) && (!face.adjacentFaceList.contains(adjacentFace))) {
                        face.adjacentFaceList.add(adjacentFace);
                    }
                }
            }
        }

        return result;
    }
}