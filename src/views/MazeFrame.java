package views;

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import enums.Modo;
import models.Cell;
import models.CellState;
import models.SolveResults;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MazeFrame extends JFrame {

    private final MazePanel mazePanel;

    private final MazeController controller;

    private final JComboBox<String> algorithmSelector;

    private final JButton solveButton;

    private final JButton pasoAPasoButton = new JButton("Paso a paso");

    private List<Cell> pasoCeldasVisitadas;

    private List<Cell> pasoCamino;

    private int pasoIndex = 0;

    private boolean resolvioPasoAPaso = false;

    private final AlgorithmResultDAO resultDAO;

    private static final Map<CellState, Color> COLOR_MAP = new HashMap<>();

    public MazeFrame(int paramInt1, int paramInt2) {
        this.resultDAO = new AlgorithmResultDAOFile("results.csv");
        setTitle("Maze Creator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        this.mazePanel = new MazePanel(paramInt1, paramInt2);
        this.controller = new MazeController(this.mazePanel);
        this.mazePanel.setController(this.controller);
        add(this.mazePanel, BorderLayout.CENTER);

        JPanel jPanel1 = new JPanel();
        JButton jButton1 = new JButton("Set Start");
        JButton jButton2 = new JButton("Set End");
        JButton jButton3 = new JButton("Toggle Wall");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeFrame.this.controller.setModo(Modo.START);
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeFrame.this.controller.setModo(Modo.END);
            }
        });
        jButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeFrame.this.controller.setModo(Modo.WALL);
            }
        });

        jPanel1.add(jButton1);
        jPanel1.add(jButton2);
        jPanel1.add(jButton3);
        add(jPanel1, BorderLayout.NORTH);

        String[] algoritmos = {
                "Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS", "Backtracking"
        };
        this.algorithmSelector = new JComboBox<>(algoritmos);
        this.solveButton = new JButton("Resolver");

        JPanel jPanel2 = new JPanel();
        jPanel2.add(new JLabel("Algoritmo:"));
        jPanel2.add(this.algorithmSelector);
        jPanel2.add(this.solveButton);
        jPanel2.add(this.pasoAPasoButton);
        add(jPanel2, BorderLayout.SOUTH);

        this.solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SolveResults solveResults = resolverYObtenerResultados();
                if (solveResults != null) {
                    animarVisitadas(solveResults.getVisitadas(), solveResults.getCamino());
                }
            }
        });

        this.pasoAPasoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!MazeFrame.this.resolvioPasoAPaso) {
                    SolveResults solveResults = resolverYObtenerResultados();
                    if (solveResults != null) {
                        MazeFrame.this.pasoCeldasVisitadas = solveResults.getVisitadas();
                        MazeFrame.this.pasoCamino = solveResults.getCamino();
                        MazeFrame.this.pasoIndex = 0;
                        MazeFrame.this.resolvioPasoAPaso = true;
                    }
                } else if (MazeFrame.this.pasoIndex < MazeFrame.this.pasoCeldasVisitadas.size()) {
                    Cell cell = MazeFrame.this.pasoCeldasVisitadas.get(MazeFrame.this.pasoIndex++);
                    if (cell.getState() == CellState.EMPTY)
                        paintCell(cell, CellState.EMPTY);
                } else if (MazeFrame.this.pasoIndex - MazeFrame.this.pasoCeldasVisitadas.size() < MazeFrame.this.pasoCamino.size()) {
                    int i = MazeFrame.this.pasoIndex - MazeFrame.this.pasoCeldasVisitadas.size();
                    Cell cell = MazeFrame.this.pasoCamino.get(i);
                    MazeFrame.this.pasoIndex++;
                    if (cell.getState() != CellState.START && cell.getState() != CellState.END)
                        paintCell(cell, CellState.PATH);
                } else {
                    JOptionPane.showMessageDialog(MazeFrame.this, "Ya se ha mostrado todo el recorrido.");
                    MazeFrame.this.resolvioPasoAPaso = false;
                }
            }
        });

        JButton jButton4 = new JButton("Limpiar");
        jButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeFrame.this.mazePanel.limpiarCeldasVisitadas();
                limpiarPasoAPaso();
            }
        });
        jPanel2.add(jButton4);

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jMenu1 = new JMenu("Archivo");
        JMenuItem jMenuItem1 = new JMenuItem("Nuevo laberinto");
        jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarLaberinto();
            }
        });
        jMenu1.add(jMenuItem1);

        JMenuItem jMenuItem3 = new JMenuItem("Ver resultados");
        jMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResultadosDialog resultadosDialog = new ResultadosDialog(MazeFrame.this, MazeFrame.this.resultDAO);
                resultadosDialog.setVisible(true);
            }
        });
        jMenu1.add(jMenuItem3);
        jMenuBar.add(jMenu1);

        JMenu jMenu2 = new JMenu("Ayuda");
        JMenuItem jMenuItem2 = new JMenuItem("Acerca de");
        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDe();
            }
        });
        jMenu2.add(jMenuItem2);
        jMenuBar.add(jMenu2);

        setJMenuBar(jMenuBar);
        setVisible(true);
    }

    private void mostrarAcercaDe() {
    }

    private void reiniciarLaberinto() {

    }

    private void limpiarPasoAPaso() {
    }

    private void animarVisitadas(List<Cell> visitadas, List<Cell> camino) {

    }

    private void paintCell(Cell cell, CellState cellState) {
    }

    private SolveResults resolverYObtenerResultados() {
        return null;
    }

}
