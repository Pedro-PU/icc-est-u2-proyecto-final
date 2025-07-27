package views;

import controllers.MazeController;
import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazePanel extends JPanel {
    private final int rows;
    private final int cols;
    private final Cell[][] cells;
    private final JButton[][] buttons;
    private MazeController mazeController;

    public MazePanel(int parametro1, int parametro2) {
        this.rows = parametro1;
        this.cols = parametro2;
        this.cells = new Cell[rows][cols];
        this.buttons = new JButton[parametro1][parametro2];
        setLayout(new GridLayout(parametro1, parametro2));
        cargarMatriz();
    }

    public void setController(MazeController controllerMaze) {
        this.mazeController = controllerMaze;
    }

    public void cargarMatriz() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j);
                JButton jButton = new JButton();
                jButton.setBackground(Color.WHITE);
                jButton.setOpaque(true);
                jButton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                int i2 = i, j2 = j;
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (MazePanel.this.mazeController != null) {
                            MazePanel.this.mazeController.celdaClickeada(i2, j2);
                        }
                    }
                });
                add(jButton);
                this.cells[i2][j2] = cell;
                this.buttons[i2][j2] = jButton;
            }
        }
    }

    public void limpiarCeldasVisitadas() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                Cell cell = this.cells[i][j];
                if (cell.getState() != CellState.WALL && cell.getState() != CellState.START && cell.getState() != CellState.END) {
                    cell.setState(CellState.EMPTY);
                    this.buttons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public JButton getButton(int paramInt1, int paramInt2) {
        return this.buttons[paramInt1][paramInt2];
    }
}
