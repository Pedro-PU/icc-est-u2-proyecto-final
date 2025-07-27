package views;

import controllers.MazeController;
import models.Cell;

import javax.swing.*;
import java.awt.*;

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
    }
}
