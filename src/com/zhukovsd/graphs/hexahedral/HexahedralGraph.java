package com.zhukovsd.graphs.hexahedral;

import com.zhukovsd.graphs.embedded.EmbeddedGraph;

/**
 * Created by ZhukovSD on 15.11.2015.
 */
public class HexahedralGraph extends EmbeddedGraph<HexahedralVertex> {
    final int latticeRowCount;
    final int latticeColumnCount;

    public HexahedralGraph(int latticeRowCount, int latticeColumnCount) {
        this.latticeRowCount = latticeRowCount;
        this.latticeColumnCount = latticeColumnCount;

        HexahedralVertex[][] lattice = new HexahedralVertex[latticeRowCount][latticeColumnCount];

        for (int i = 0; i < latticeRowCount; i++) {
            for (int j = 0; j < latticeColumnCount; j++) {
                boolean isExists;

                if (i % 2 == 0) {
                    if (((j % 4) == 1) || ((j % 4) == 2)) {
//                        if ((i == 0) && (j == 9))
//                            isExists = false;
//                        else if ((i == 9) && (j == 0))
//                            isExists = false;
//                        else
                            isExists = true;
                    }
                    else
                        isExists = false;
                } else {
                    if (((j % 4) == 0) || ((j % 4) == 3))
                        isExists = true;
                    else
                        isExists = false;
                }

                if (isExists) {
                    HexahedralVertex vertex = new HexahedralVertex(i, j);

                    lattice[i][j] = vertex;
                    vertexList.add(vertex);
                }
            }
        }

        vertexList.remove(lattice[0][latticeColumnCount - 1]);
        lattice[0][latticeColumnCount - 1] = null;

        vertexList.remove(lattice[latticeRowCount - 1][0]);
        lattice[latticeRowCount -1][0] = null;

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
    }
}
