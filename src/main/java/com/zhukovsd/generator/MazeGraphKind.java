package com.zhukovsd.generator;

/**
 * Enumeration of graph kinds, presented in maze generation process.
 */
public enum MazeGraphKind {
    /**
     * Graph with initial configuration of walls.
     */
    INITIAL_GRAPH,

    /**
     * Dual graph of initial graph, each its vertex corresponds to face of {@link #INITIAL_GRAPH initial graph}.
     */
    DUAL_GRAPH,

    /**
     * Spanning tree of {@link #DUAL_GRAPH dual graph}, which represents all possible paths in {@link #INITIAL_GRAPH initial graph}.
     */
    PATH_SPANNING_TREE,

    /**
     * Shortest path in {@link #PATH_SPANNING_TREE path spanning tree}, which represents maze solution.
     */
    SHORTEST_PATH,

    /**
     * Resulting walls configuration, which is {@link #INITIAL_GRAPH initial graph} non-including walls, intersected by
     * {@link #PATH_SPANNING_TREE path spanning tree}.
     */
    RESULTING_GRAPH
}