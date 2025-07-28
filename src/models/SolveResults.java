package models;

import java.util.List;

public class SolveResults {
    private final List<Cell> visitadas;
    private final List<Cell> camino;
    private long tiempo; // Nuevo campo

    public SolveResults(List<Cell> visitadas, List<Cell> camino) {
        this.visitadas = visitadas;
        this.camino = camino;
    }

    public List<Cell> getVisitadas() {
        return visitadas;
    }

    public List<Cell> getCamino() {
        return camino;
    }

    public boolean seEncontroCamino() {
        return camino != null && !camino.isEmpty();
    }

    public int getLongitudCamino() {
        return camino.size();
    }

    // Nuevo getter y setter para tiempo
    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
