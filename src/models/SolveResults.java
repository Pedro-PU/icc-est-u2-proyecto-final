package models;

import java.util.List;
import java.util.Set;

import java.util.List;

public class SolveResults {
    public final List<Cell> visitadas;

    public final List<Cell> camino;

    public SolveResults(List<Cell> paramList1, List<Cell> paramList2) {
        this.visitadas = paramList1;
        this.camino = paramList2;
    }
}
