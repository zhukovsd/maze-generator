package com.zhukovsd.graphs;

/**
 * Vertex of {@link Graph graph}, connected with another vertexes. Class use self-referential type parameter for
 * types of external fields, such as edgeList in all class hierarchy starting from Vertex. That is,
 * if <code>SpecificVertex</code> class is derived from <code>Vertex</code>, then <code>specificVertex.edgeList.get(0).source</code>
 * vertex object will instantiated as <code>SpecificVertex</code> object, which is redeems client code from down-casting.
 * @param <T> - self-referential type used as type parameter for {@link #edgeList}
 */
public class Vertex<T extends Vertex<T>> {
    /**
     * Tag for current vertex, usually a capital latin letter, used for more convenient debugging and in <code>toString()</code>
     * methods of {@link Edge edge} and {@link com.zhukovsd.graphs.embedded.Face face}.
     */
    public char tag;

    /**
     * List of {@link Edge edges}, directional connections between current vertex and adjacent to it.
     */
    public final EdgeList<T> edgeList = new EdgeList<>();
}