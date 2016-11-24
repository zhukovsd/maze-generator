package com.zhukovsd.generator;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.dual.*;
import com.zhukovsd.graphs.embedded.*;
import com.zhukovsd.graphs.subgraphs.*;

//import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

/**
 * Actual maze implementation, parametrized with vertex type.
 * @param <T> type of vertex which forms initial graph of current maze generator
 */
public class Maze<T extends EmbeddedVertex<T>> extends DrawableMaze {
    static Random seedRand = new Random();

    private long seed;

    public long getSeed() {
        return seed;
    }

    /**
     * Options of maze generation.
     */
    class MazeGenerationOptions {
        /**
         * List of {@link #dualGraph dual graph} vertexes which have to be excluded from path spanning tree.
         */
        ArrayList<DualVertex<T>> pathTreeExcludedVertexes = new ArrayList<>();
    }

    /**
     * Current maze generation options.
     */
    public final MazeGenerationOptions mazeGenerationOptions = new MazeGenerationOptions();

    /**
     * {@link EmbeddedGraph Graph} with initial configuration of walls, represents {@link MazeGraphKind#INITIAL_GRAPH}.
     */
    final EmbeddedGraph<T> graph;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #graph}.
     */
    final EmbeddedGraphView<T> graphView;

    /**
     * {@link DualGraph Dual graph} of {@link #graph initial graph}, each its vertex corresponds to face of initial graph,
     * represents {@link MazeGraphKind#DUAL_GRAPH}.
     */
    public DualGraph<T> dualGraph;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #dualGraph}.
     */
    DualGraphView<T> dualView;

    /**
     * Spanning tree of {@link #dualGraph dual graph}, which represents all possible paths in {@link #graph initial graph},
     * corresponds to {@link MazeGraphKind#PATH_SPANNING_TREE}.
     */
    public SubGraph<DualVertex<T>> pathSpanningTree;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #pathSpanningTree}.
     */
    SubGraphView<DualVertex<T>, DualGraph<T>, DualGraphView<T>> pathView;

    /**
     * Shortest path in {@link #pathSpanningTree path spanning tree}, which represents maze solution, corresponds to
     * {@link MazeGraphKind#SHORTEST_PATH}.
     */
    public SubGraph<SubGraphVertex<DualVertex<T>>> shortestPath;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #shortestPath}.
     */
    SubGraphView<SubGraphVertex<DualVertex<T>>, SubGraph<DualVertex<T>>, SubGraphView<DualVertex<T>, DualGraph<T>, DualGraphView<T>>> shortestPathView;

    /**
     * Resulting walls configuration, which is subgraph of {@link #graph initial graph} non-including walls, intersected by
     * {@link #pathSpanningTree path spanning tree}, corresponds to {@link MazeGraphKind#RESULTING_GRAPH}.
     */
    public SubGraph<T> resultingGraph;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #resultingGraph}.
     */
    SubGraphView<T, EmbeddedGraph<T>, EmbeddedGraphView<T>> resultingView;

    /**
     * Vertex, which lays inside of face, which is first face of maze path.
     */
    public DualVertex<?> entryVertex;

    /**
     * Vertex, which lays inside of face, which is the destination face of maze path.
     */
    public DualVertex<T> exitVertex;

    /**
     * Create maze for given initial walls graph and it's graph view. Dual graph also created in constructor.
     * @param graph initial walls graph
     * @param graphView graph view of initial graph
     */
    Maze(EmbeddedGraph<T> graph, EmbeddedGraphView<T> graphView) {
        this.graph = graph;
        this.graphView = graphView;

        dualGraph = new DualGraph<>(graph);
        dualView = new DualGraphView<>(dualGraph, graphView);

        entryVertex = dualGraph.vertexList.get(0);
        exitVertex = dualGraph.vertexList.get(dualGraph.vertexList.size() - 1);
    }

//    /**
//     * Calculate size of image necessary to paint current maze.
//     * @return image size as {@link Point} object
//     */
//    @Override
//    public Point getSize() {
//        return graphView.getSize();
//    }

//    /**
//     * Paint given graph kinds on given {@link Graphics2D graphics}.
//     * @param graphics graphics options to paint on
//     * @param graphs set of kinds of graphs to be painted
//     */
//    @Override
//    public void paint(Graphics2D graphics, EnumSet<MazeGraphKind> graphs) {
//        for (MazeGraphKind graphKind : MazeGraphKind.values()) {
//            if (graphs.contains(graphKind)) {
//                DrawableMaze.MazePaintOptions.GraphPaintOptions options = mazePaintOptions.get(graphKind);
//
//                switch (graphKind) {
//                    case INITIAL_GRAPH:
//                        graphView.paint(graphics, options.color, options.stroke);
//                        break;
//
//                    case DUAL_GRAPH:
//                        dualView.paint(graphics, options.color, options.stroke);
//                        break;
//
//                    case PATH_SPANNING_TREE:
//                        pathView.paint(graphics, options.color, options.stroke);
//                        break;
//
//                    case SHORTEST_PATH:
//                        shortestPathView.paint(graphics, options.color, options.stroke);
//                        break;
//
//                    case RESULTING_GRAPH:
//                        resultingView.paint(graphics, options.color, options.stroke);
//                }
//            }
//        }
//    }

    /**
     * Method for adjusting size of graphic representation for current maze.
     * @param availableWidth width to adjust to
     */
    @Override
    public void adjustSizeByWidth(int availableWidth) {
        graphView.adjustSizeByWidth(availableWidth);
    }

    /**
     * Actual maze generation. Find path, find shortest path, build resulting graph.
     */
    @Override
    public void generate() {
        this.generate(seedRand.nextLong());
    }

    public void generate(long randomSeed) {
        this.seed = randomSeed;

        pathSpanningTree = SubGraphFactory.createSpanningTree(
                dualGraph, mazeGenerationOptions.pathTreeExcludedVertexes, new Random(randomSeed)
        );
        pathView = new SubGraphView<>(pathSpanningTree, dualView);

        shortestPath = SubGraphFactory.createShortestPath(pathSpanningTree, pathSpanningTree.vertexByParent(entryVertex),
                pathSpanningTree.vertexByParent(exitVertex), SubGraphFactory.ShortestPathAlgorithm.BREADTH_FIRST_SEARCH);

        shortestPathView = new SubGraphView<>(shortestPath, pathView);

        // delete walls, intersected by edges of spanning tree graph
        buildResultingGraph();

        // remove entry and destination edges
        removeBoundaryEdge(entryVertex);
        removeBoundaryEdge(exitVertex);
    }

    /**
     * Build resulting graph, which is {@link #graph initial graph} non-including walls, intersected by
     * {@link #pathSpanningTree path spanning tree}.
     */
    private void buildResultingGraph() {
        resultingGraph = SubGraphFactory.createFullSubGraph(graph);
        resultingView = new SubGraphView<>(resultingGraph, graphView);

        for (SubGraphVertex<DualVertex<T>> sourceVertex : pathSpanningTree.vertexList) {
            for (Edge<SubGraphVertex<DualVertex<T>>> edge : sourceVertex.edgeList) {
                DualVertex<T> destVertex = edge.destination.parentVertex;
                EdgeList<T> commonEdges = sourceVertex.parentVertex.face.findCommonEdges(destVertex.face);

                for (Edge<T> commonEdge : commonEdges) {
                    resultingGraph.disconnectFromEachOtherByParents(commonEdge.source, commonEdge.destination);
                }
            }
        }
    }

    /**
     * Remove boundary edge, which represents entry or exit of maze for player for solver. Find boundary edge
     * of given vertex's face and remove it. This edge have to be outer (not adjacent to any other face) or
     * adjacent to ant of excluded vertexes face.
     * @param vertex given vertex
     */
    private void removeBoundaryEdge(DualVertex<?> vertex) {
        // delete entry and exit walls
        for (Edge<?> edge : vertex.face.edgeList) {
            boolean flag = false;

            for (Face<?> adjacentFace : vertex.face.adjacentFaceList) {
                EdgeList<?> commonEdges = vertex.face.findCommonEdges(adjacentFace);

                if (commonEdges.contains(edge) && !mazeGenerationOptions.pathTreeExcludedVertexes.contains(
                        dualGraph.vertexByFace(adjacentFace)
                )) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                resultingGraph.disconnectFromEachOtherByParents(edge.source, edge.destination);
                return;
            }
        }
    }

    public double findMinimalRoomsPassage() {
        // чтобы найти максимально допустимую ширину шарика, перемещающегося по лабиринту, нужно найти
        // самый узкий проход между комнатами.
        //
        // шириной прохода между комнатами является длина общей грани этих комнат. примем допущение, что у соседних граней
        // не может быть больше одной общей стены, для моих круглых/квадратных/шестигранных лабириентов это так, но это
        // не обязательно так для лабиринта в произвольном графе

        SubGraph<? extends DualVertex<? extends EmbeddedVertex>> pathGraph = pathSpanningTree;
        // объединим все рёбра графа в один список
        EdgeList<? extends SubGraphVertex<? extends DualVertex<? extends EmbeddedVertex>>> edgeList = pathGraph.getEdgeList();

        double minWidth = Double.MAX_VALUE;

        // проитерируем все рёбра
        for (Edge<? extends SubGraphVertex<? extends DualVertex<? extends EmbeddedVertex>>> edge : edgeList) {
            // пара граней, которые соединены текущим ребром
            Face<? extends EmbeddedVertex> sourceFace = edge.source.parentVertex.face;
            Face<? extends EmbeddedVertex> destFace = edge.destination.parentVertex.face;

            // найдём их общее ребро и её длину
            EdgeList<? extends EmbeddedVertex> commonEdges = sourceFace.findCommonEdges(destFace);

            Edge<? extends EmbeddedVertex> passageEdge = commonEdges.get(0);
            double width = passageEdge.source.getPosition().distance(passageEdge.destination.getPosition());

            // ищем минимальную длину
            minWidth = Math.min(minWidth, width);
        }

        return minWidth;
    }
}