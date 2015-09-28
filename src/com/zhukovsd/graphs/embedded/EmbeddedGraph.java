package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.Graph;
import com.zhukovsd.graphs.Vertex;

import java.util.*;

/**
 * Abstract graph, embedded to the coordinate space.
 */
public class EmbeddedGraph<E extends EmbeddedVertex> extends Graph<E> {
    /**
     * Private storage for current graph {@link Face faces}.
     */
    private List<Face> faceList;

    /**
     * Map that consists of reverse {@link Edge} edges pairs. Used for fast access to reverse edge for given edge.
     */
    private HashMap<Edge, Edge> reverseEdgesMap = new HashMap<Edge, Edge>();

    /**
     * Method connects 2 {@link Vertex vertexes} by creating 2 directional {@link Edge edges}.
     * Method assumes that given vertexes belongs to current graph.
     * @param left first vertex to connect
     * @param right second vertex to connect
     */
    @Override
    public void connectToEachOther(Vertex left, Vertex right) {
        Edge leftEdge = connect(left, right);
        Edge rightEdge = connect(right, left);

        reverseEdgesMap.put(leftEdge, rightEdge);
        reverseEdgesMap.put(rightEdge, leftEdge);
    }

    /**
     * Lazy initialize of get list of current embedded graph's face list.
     * @return face list
     */
    public List<Face> getFaces() {
        if (faceList == null) faceList = findFaces();

        return faceList;
    }

    /**
     * Find all {@link Face faces} of current embedded graph.
     * Algorithm based on math.stackexchange.com <u><a href="http://math.stackexchange.com/questions/8140/find-all-cycles-faces-in-a-graph">discussion</a></u>.
     * Algorithm steps:
     * <ul style="list-style-type: decimal;">
     *     <li>Sort {@link Edge edges} for each {@link Vertex vertex} of graph in counterclockwise order with {@link EdgeComparator}</li>
     *     <li>Compose edges for each vertex into one <b>edge list</b></li>
     *     <li>
     *         Iterate all edges in <b>edge list</b> from first to last and for each edge:
     *         <ul style="list-style-type: lower-roman;">
     *             <li>Check if current edge is not marked as iterated, if so, proceed to step (ii), go to next loop iteration otherwise</li>
     *             <li>Add current edge to the empty <b>edge loop list</b></li>
     *             <li>
     *                 While loop not found, repeat this statements:
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
     *             face of current graph, it may bound multiple faces, even entire graph. To determine that
     *             we have to compare each <b>edge loop list</b> edge with previous one (first with last, second with first and so on).
     *             If comparison result is 0 or 1 (greater or equal) then <b>edge loop list</b> edges bounds face,
     *             in that case we store that face and mark all it's edges as iterated</li>
     *         </ul>
     *     </li>
     *     <li>Iterate all found faces and search for pairs of adjacent ones. Two faces is adjacent if they have one or more
     *     common non-directional (<b>AB</b> = <b>BA</b> in that case) edges</li>
     * </ul>
     * @return list with found faces
     */
    private List<Face> findFaces() {
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
            Edge precedingReverseEdge;
            /**
             * Face that bounded by this edge.
             */
            List<Face> faces = new ArrayList<Face>();

            /**
             * Initialize instance with pre-calculated preceding reverse edge.
             * @param precedingReverseEdge pre-calculated preceding reverse edge
             */
            public EdgeData(Edge precedingReverseEdge) {
                this.precedingReverseEdge = precedingReverseEdge;
            }
        }

        List<Face> result = new ArrayList<Face>();

        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        EdgeComparator edgeComparator = new EdgeComparator();

        // compose add all edges of graph into one list
        for (Vertex vertex : vertexList) {
            vertex.edgeList.sort(edgeComparator);
            edgeList.addAll(vertex.edgeList);

//            String buf = vertex.tag + ": ";
//            for (Edge edge : vertex.edgeList) buf += "" + (edge.source.tag) + (edge.destination.tag) + " ";
//            System.out.println(buf);
        }

        // map, that stores additional data for every edge in edgeList, that necessary for faces finding algorithm
        HashMap<Edge, EdgeData> edgeMap = new HashMap<Edge, EdgeData>();
        for (Edge edge : edgeList) {
            // find preceding reverse edges as described in 3.iii.b step of find faces algorithm
            Edge precedingReverseEdge = edge.destination.edgeList.previous(reverseEdgesMap.get(edge));

            edgeMap.put(edge, new EdgeData(precedingReverseEdge));
        }

        for (Edge edge : edgeList) {
            EdgeList loopList = new EdgeList();
            loopList.add(edge);

            boolean isLoopFound = false;
            if (!edgeMap.get(edge).isIterated) {
                while (!isLoopFound) {
                    Edge loopEdge = loopList.get(loopList.size() - 1);

                    loopList.add(edgeMap.get(loopEdge).precedingReverseEdge);
                    if (loopList.get(0).source == loopList.get(loopList.size() - 1).destination)
                        isLoopFound = true;
                }
            }

            if (isLoopFound) {
                // check if found loop bounds only one face
                boolean isLoopValid = true;
                for (int i = 0; i < loopList.size(); i++) {
                    if (edgeComparator.compare(loopList.get(i), loopList.previous(i)) < 0) {
                        isLoopValid = false;
                        break;
                    }
                }

                // if valid face found, store it
                if (isLoopValid) {
//                    String buf = "loop found: ";
//                    for (Edge loopEdge : loopList) buf += "" + (loopEdge.source.tag);
//                    System.out.println(buf);

                    Face face = new Face(loopList);
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
        for (Face face : result) {
            for (Edge edge : face.edgeList) {
                // 2 faces are adjacent if they have one or multiple common non-directional edge
                List<Face> facesByEdge = edgeMap.get(edge).faces;

                for (Face adjacentFace : facesByEdge) {
                    if ((adjacentFace != face) && (!face.adjacentFaceList.contains(adjacentFace))) {
                        face.adjacentFaceList.add(adjacentFace);
                    }
                }
            }
        }

        return result;
    }
}