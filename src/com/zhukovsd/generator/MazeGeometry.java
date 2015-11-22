package com.zhukovsd.generator;

/**
 * Enumeration of available maze geometries.
 */
public enum MazeGeometry {
    /**
     * Rectangular maze, described by row and column count of its initial rectangular lattice.
     */
    RECTANGULAR,

    /**
     * Circular maze, described by circles count of its initial lattice, consists of concentric circles and "spokes".
     */
    CIRCULAR,

    /**
     * Hexahedral maze, described by row and column count of its initial hexahedral lattice.
     */
    HEXAHEDRAL
}
