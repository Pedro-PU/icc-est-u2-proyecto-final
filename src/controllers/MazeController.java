package controllers;

import enums.Modo;
import models.Cell;
import models.CellState;
import views.MazePanel;

import javax.swing.*;
import java.awt.*;

public class MazeController {

    private Modo currentMode = Modo.WALL;
    private Cell startCell;
    private Cell endCell;
    private final MazePanel panel;

    public MazeController(MazePanel panel) {
        this.panel = panel;
        panel.setController(this);
    }

    public void setModo(Modo modo) {
        this.currentMode = modo;
    }

    public void celdaClickeada(int fila, int columna) {
        switch (this.currentMode) {
            case START:
                setStartCell(fila, columna);
                break;
            case END:
                setEndCell(fila, columna);
                break;
            case WALL:
                toggleWall(fila, columna);
                break;
        }
    }

    private void setStartCell(int fila, int columna) {
        Cell celda = panel.getCells()[fila][columna];
        JButton boton = panel.getButton(fila, columna);

        if (this.startCell != null) {
            panel.getButton(startCell.getRow(), startCell.getCol()).setBackground(Color.WHITE);
            startCell.setState(CellState.EMPTY);
        }

        this.startCell = celda;
        celda.setState(CellState.START);
        boton.setBackground(Color.GREEN);
    }

    private void setEndCell(int fila, int columna) {
        Cell celda = panel.getCells()[fila][columna];
        JButton boton = panel.getButton(fila, columna);

        if (this.endCell != null) {
            panel.getButton(endCell.getRow(), endCell.getCol()).setBackground(Color.WHITE);
            endCell.setState(CellState.EMPTY);
        }

        this.endCell = celda;
        celda.setState(CellState.END);
        boton.setBackground(Color.RED);
    }

    private void toggleWall(int fila, int columna) {
        Cell celda = panel.getCells()[fila][columna];
        JButton boton = panel.getButton(fila, columna);

        if (celda.getState() == CellState.WALL) {
            celda.setState(CellState.EMPTY);
            boton.setBackground(Color.WHITE);
        } else if (celda.getState() == CellState.EMPTY) {
            celda.setState(CellState.WALL);
            boton.setBackground(Color.BLACK);
        }
    }

    public void celdaClickeadaLegacy(int fila, int columna) {
        Cell celda = this.panel.getCells()[fila][columna];
        JButton boton = this.panel.getButton(fila, columna);

        switch (this.currentMode) {
            case START:
                if (this.startCell != null) {
                    this.panel.getButton(startCell.getRow(), startCell.getCol()).setBackground(Color.WHITE);
                    startCell.setState(CellState.EMPTY);
                }
                this.startCell = celda;
                celda.setState(CellState.START);
                boton.setBackground(Color.GREEN);
                break;

            case END:
                if (this.endCell != null) {
                    this.panel.getButton(endCell.getRow(), endCell.getCol()).setBackground(Color.WHITE);
                    endCell.setState(CellState.EMPTY);
                }
                this.endCell = celda;
                celda.setState(CellState.END);
                boton.setBackground(Color.RED);
                break;

            case WALL:
                if (celda.getState() == CellState.WALL) {
                    celda.setState(CellState.EMPTY);
                    boton.setBackground(Color.WHITE);
                } else if (celda.getState() == CellState.EMPTY) {
                    celda.setState(CellState.WALL);
                    boton.setBackground(Color.BLACK);
                }
                break;
        }
    }

    public void limpiar() {
        this.panel.limpiarCeldasVisitadas();
    }

    public Cell getStartCell() {
        return this.startCell;
    }

    public Cell getEndCell() {
        return this.endCell;
    }
}
