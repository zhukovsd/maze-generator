package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.graphs.Edge;
import com.zhukovsd.graphs.embedded.EmbeddedGraph;

/**
 * Vertex of graph in form of hexahedral lattice, which position described by row and column indexes in this lattice.
 */
public class HexahedralGraph extends EmbeddedGraph<HexahedralVertex> {
    final int latticeRowCount;
    final int latticeColumnCount;

    public HexahedralGraph(int latticeRowCount, int latticeColumnCount) {
        // column count can't be odd
        if (latticeColumnCount % 2 != 0)
            latticeColumnCount += 1;

        // if column count if 4, then in case of even row count all vertexes of all row will have < 2 edges and therefore
        // will be removed, which will cause last lattice row has no vertexes at all. To prevent this, decreate row count
        // in advance
        if (latticeColumnCount == 4) {
            if (latticeRowCount % 2 == 0)
                latticeRowCount -= 1;
        }

        this.latticeRowCount = latticeRowCount;
        this.latticeColumnCount = latticeColumnCount;

        HexahedralVertex[][] lattice = new HexahedralVertex[latticeRowCount][latticeColumnCount];

        for (int i = 0; i < latticeRowCount; i++) {
            for (int j = 0; j < latticeColumnCount; j++) {
                boolean isExists;

                if (i % 2 == 0) {
                    // on rows with even indexes, vertexes exist on column indexes where (index % 4) == 1 or 2
                    isExists = ((j % 4) == 1) || ((j % 4) == 2);
                } else {
                    // on rows with odd indexes, vertexes exists on column indexes where (index % 4) == 0 or 3
                    isExists = ((j % 4) == 0) || ((j % 4) == 3);
                }

                if (isExists) {
                    HexahedralVertex vertex = new HexahedralVertex(i, j);

                    lattice[i][j] = vertex;
                    addVertex(vertex);
                }
            }
        }

        for (int i = 0; i < latticeRowCount; i++) {
            for (int j = 0; j < latticeColumnCount; j++) {
                HexahedralVertex vertex = lattice[i][j];

                if (vertex != null) {
                    // connect with right vertex
                    if (j < latticeColumnCount - 1) {
                        if (lattice[i][j + 1] != null)
                            connectToEachOther(vertex, lattice[i][j + 1]);
                    }

                    // connect with left-bottom vertex
                    if ((i < latticeRowCount - 1) && (j > 0)) {
                        HexahedralVertex neighbour = lattice[i + 1][j - 1];

                        if (neighbour != null)
                            connectToEachOther(vertex, neighbour);
                    }

                    // connect with left-bottom vertex
                    if ((i < latticeRowCount - 1) && (j < latticeColumnCount - 1)) {
                        HexahedralVertex neighbour = lattice[i + 1][j + 1];

                        if (neighbour != null)
                            connectToEachOther(vertex, neighbour);
                    }
                }
            }
        }

        // delete "floating" vertexes, which has less than 2 edges
        for (int i = vertexList.size() - 1; i >= 0; i--) {
            HexahedralVertex vertex = vertexList.get(i);

            if (vertex.edgeList.size() < 2) {
                for (int j = vertex.edgeList.size() - 1; j >= 0; j--) {
                    Edge<HexahedralVertex> edge = vertex.edgeList.get(j);

                    disconnectFromEachOther(vertex, edge.destination);
                }

                vertexList.remove(i);
            }
        }
    }
}
