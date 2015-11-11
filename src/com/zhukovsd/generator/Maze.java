package com.zhukovsd.generator;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.EdgeList;
import com.zhukovsd.graphs.dual.*;
import com.zhukovsd.graphs.embedded.*;
import com.zhukovsd.graphs.subgraphs.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Actual maze implementation, parametrized with vertex type.
 * @param <T> type of vertex which forms initial graph of current maze generator
 */
public class Maze<T extends EmbeddedVertex<T>> extends DrawableMaze {
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
    DualGraph<T> dualGraph;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #dualGraph}.
     */
    DualGraphView<T> dualView;

    /**
     * Spanning tree of {@link #dualGraph dual graph}, which represents all possible paths in {@link #graph initial graph},
     * corresponds to {@link MazeGraphKind#PATH_SPANNING_TREE}.
     */
    SubGraph<DualVertex<T>> pathSpanningTree;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #pathSpanningTree}.
     */
    SubGraphView<DualVertex<T>, DualGraph<T>, DualGraphView<T>> pathView;

    /**
     * Shortest path in {@link #pathSpanningTree path spanning tree}, which represents maze solution, corresponds to
     * {@link MazeGraphKind#SHORTEST_PATH}.
     */
    SubGraph<SubGraphVertex<DualVertex<T>>> shortestPath;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #shortestPath}.
     */
    SubGraphView<SubGraphVertex<DualVertex<T>>, SubGraph<DualVertex<T>>, SubGraphView<DualVertex<T>, DualGraph<T>, DualGraphView<T>>> shortestPathView;

    /**
     * Resulting walls configuration, which is subgraph of {@link #graph initial graph} non-including walls, intersected by
     * {@link #pathSpanningTree path spanning tree}, corresponds to {@link MazeGraphKind#RESULTING_GRAPH}.
     */
    SubGraph<T> resultingGraph;

    /**
     * {@link com.zhukovsd.graphs.GraphView View} of {@link #resultingGraph}.
     */
    SubGraphView<T, EmbeddedGraph<T>, EmbeddedGraphView<T>> resultingView;

    /**
     * Vertex, which lays inside of face, which is first face of maze path.
     */
    DualVertex<T> entryVertex;

    /**
     * Vertex, which lays inside of face, which is the destination face of maze path.
     */
    DualVertex<T> exitVertex;

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

    /**
     * Calculate size of image necessary to paint current maze.
     * @return image size as {@link Point} object
     */
    @Override
    public Point getSize() {
        return graphView.getSize();
    }

    /**
     * Paint given graph kinds on given {@link Graphics2D graphics}.
     * @param graphics graphics options to paint on
     * @param graphs set of kinds of graphs to be painted
     */
    @Override
    public void paint(Graphics2D graphics, EnumSet<MazeGraphKind> graphs) {
        for (MazeGraphKind graphKind : MazeGraphKind.values()) {
            if (graphs.contains(graphKind)) {
                DrawableMaze.MazePaintOptions.GraphPaintOptions options = mazePaintOptions.get(graphKind);

                switch (graphKind) {
                    case INITIAL_GRAPH:
                        graphView.paint(graphics, options.color, options.stroke);
                        break;

                    case DUAL_GRAPH:
                        dualView.paint(graphics, options.color, options.stroke);
                        break;

                    case PATH_SPANNING_TREE:
                        pathView.paint(graphics, options.color, options.stroke);
                        break;

                    case SHORTEST_PATH:
                        shortestPathView.paint(graphics, options.color, options.stroke);
                        break;

                    case RESULTING_GRAPH:
                        resultingView.paint(graphics, options.color, options.stroke);
                }
            }
        }
    }

    /**
     * Actual maze generation. Find path, find shortest path, build resulting graph.
     */
    @Override
    public void generate() {
        pathSpanningTree = SubGraphFactory.createSpanningTree(dualGraph, mazeGenerationOptions.pathTreeExcludedVertexes);
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
    private void removeBoundaryEdge(DualVertex<T> vertex) {
        // delete entry and exit walls
        for (Edge<T> edge : vertex.face.edgeList) {
            boolean flag = false;

            for (Face<T> adjacentFace : vertex.face.adjacentFaceList) {
                EdgeList<T> commonEdges = vertex.face.findCommonEdges(adjacentFace);

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
}