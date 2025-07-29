package views;

import controllers.MazeController;
import models.Cell;
import models.CellState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazePanel extends JPanel {
    private final int filas;
    private final int columnas;
    private final Cell[][] celdas;
    private final JButton[][] botones;
    private MazeController controlador;

    public MazePanel(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdas = new Cell[filas][columnas];
        this.botones = new JButton[filas][columnas];

        setLayout(new GridLayout(filas, columnas, 1, 1)); // Espacio entre botones
        setBackground(Color.DARK_GRAY);
        inicializarMatriz();
    }

    public void setController(MazeController controlador) {
        this.controlador = controlador;
    }

    private void inicializarMatriz() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell celda = new Cell(i, j);
                JButton boton = new JButton();

                // Estilo visual personalizado
                boton.setBackground(Color.WHITE);
                boton.setOpaque(true);
                boton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                boton.setFocusPainted(false);
                boton.setFont(new Font("Verdana", Font.BOLD, 9));
                boton.setForeground(Color.DARK_GRAY);

                // Redondear esquinas usando UIManager
                boton.setUI(new javax.swing.plaf.basic.BasicButtonUI());

                final int fila = i;
                final int col = j;

                boton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (controlador != null) {
                            controlador.celdaClickeada(fila, col);
                        }
                    }
                });

                add(boton);
                celdas[i][j] = celda;
                botones[i][j] = boton;
            }
        }
    }

    public void limpiarCeldasVisitadas() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell celda = celdas[i][j];
                if (celda.getState() != CellState.WALL &&
                        celda.getState() != CellState.START &&
                        celda.getState() != CellState.END) {

                    celda.setState(CellState.EMPTY);
                    botones[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }

    public Cell[][] getCells() {
        return celdas;
    }

    public JButton getButton(int fila, int columna) {
        return botones[fila][columna];
    }
}
