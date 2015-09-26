package com.zhukovsd.graphs.embedded;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.Graph;

import java.util.*;

/**
 * Abstract graph, embedded to the coordinate space.
 */
public class EmbeddedGraph<E extends EmbeddedVertex> extends Graph<E> {
    private List<Face> faceList;

    /**
     * Lazy initialize of get list of current embedded graph's face list.
     * @return face list
     */
    public List<Face> getFaces() {
        if (faceList == null) faceList = findFaces();

        return faceList;
    }

    private List<Face> findFaces() {
        return null;
    }
}