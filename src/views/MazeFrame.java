package views;

import controllers.MazeController;
import dao.AlgorithmResultDAO;
import dao.daoImpl.AlgorithmResultDAOFile;
import enums.Modo;
import models.AlgorithmResult;
import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.MazeSolverBFS;
import solver.solverImpl.MazeSolverDFS;
import solver.solverImpl.MazeSolverRecursivo;
import solver.solverImpl.MazeSolverRecursivoCompleto;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
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
                "Recursivo", "Recursivo Completo", "Recursivo Completo BT", "BFS", "DFS"
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
        // Asignación de colores a cada estado
        COLOR_MAP.put(CellState.EMPTY, new Color(88, 92, 94)); // Celeste claro para visitadas
        COLOR_MAP.put(CellState.PATH, new Color(111, 230, 215));     // Dorado para el camino
        COLOR_MAP.put(CellState.START, Color.GREEN);              // Ya pintado al seleccionar
        COLOR_MAP.put(CellState.END, Color.RED);
        COLOR_MAP.put(CellState.WALL, Color.BLACK);

        setVisible(true);
    }

    private void mostrarAcercaDe() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel autorLabel = new JLabel("Desarrollado por: Pablo Torres");
        autorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(autorLabel);
        panel.add(Box.createVerticalStrut(10));

        JPanel githubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/resources/github-original-wordmark.png"));
            Image img = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel iconoLabel = new JLabel(new ImageIcon(img));
            githubPanel.add(iconoLabel);
        } catch (Exception e) {
            System.out.println("No se pudo cargar el logo de GitHub.");
        }

        JLabel link = new JLabel("<html><a href=''>pablotorresdev</a></html>");
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Pablot18"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        githubPanel.add(link);
        githubPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(githubPanel);

        JOptionPane.showMessageDialog(this, panel, "Acerca de", JOptionPane.INFORMATION_MESSAGE);
    }


    private void reiniciarLaberinto() {
        String filasStr = JOptionPane.showInputDialog(this, "Ingrese el número de filas:");
        if (filasStr == null) return; // Usuario canceló

        String columnasStr = JOptionPane.showInputDialog(this, "Ingrese el número de columnas:");
        if (columnasStr == null) return; // Usuario canceló

        try {
            int filas = Integer.parseInt(filasStr.trim());
            int columnas = Integer.parseInt(columnasStr.trim());

            if (filas <= 4 || columnas <= 4) {
                JOptionPane.showMessageDialog(this, "Debe ingresar valores mayores a 4.");
                return;
            }

            dispose();

            SwingUtilities.invokeLater(() -> new MazeFrame(filas, columnas));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Debe ingresar números válidos.");
        }
    }


    private void limpiarPasoAPaso() {
        pasoCeldasVisitadas = null;
        pasoCamino = null;
        pasoIndex = 0;
        resolvioPasoAPaso = false;
    }

    private void animarVisitadas(List<Cell> visitadas, List<Cell> camino) {
        new SwingWorker<Void, Cell>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Primero animar las celdas visitadas
                for (Cell cell : visitadas) {
                    if (cell.getState() == CellState.EMPTY) {
                        cell.setState(CellState.EMPTY); // aseguramos estado
                        publish(cell);
                        Thread.sleep(40);
                    }
                }

                // Luego animar el camino real
                for (Cell cell : camino) {
                    if (cell.getState() != CellState.START && cell.getState() != CellState.END) {
                        cell.setState(CellState.PATH); // marcamos como camino
                        publish(cell);
                        Thread.sleep(60);
                    }
                }

                return null;
            }

            @Override
            protected void process(List<Cell> chunks) {
                for (Cell cell : chunks) {
                    paintCell(cell, cell.getState());
                }
            }

            @Override
            protected void done() {
                // Opcional: mensaje al terminar
            }
        }.execute();
    }



    private void paintCell(Cell cell, CellState cellState) {
        cell.setState(cellState);
        JButton boton = mazePanel.getButton(cell.getRow(), cell.getCol());
        Color color = COLOR_MAP.getOrDefault(cellState, Color.WHITE);
        boton.setBackground(color);
    }




    private SolveResults resolverYObtenerResultados() {
        MazeSolver solver;
        Cell inicio = controller.getStartCell();
        Cell fin = controller.getEndCell();

    if (inicio == null || fin == null) {
        JOptionPane.showMessageDialog(this, "Debe establecer origen y destino.");
        return null;
    }

    mazePanel.limpiarCeldasVisitadas();
    limpiarPasoAPaso();

    String algoritmo = (String) algorithmSelector.getSelectedItem();

    // Elegir el algoritmo
    switch (algoritmo) {
        case "Recursivo":
            solver = new MazeSolverRecursivo();
            break;
        case "Recursivo Completo":
            solver = new MazeSolverRecursivoCompleto();
            break;
        case "Recursivo Completo BT":
            solver = new MazeSolverRecursivoCompletoBT();
            break;
        case "DFS":
            solver = new MazeSolverDFS();
            break;
        case "BFS":
            solver = new MazeSolverBFS();
            break;
        default:
            solver = new MazeSolverRecursivo(); // por defecto
            break;
    }

    long tiempoInicio = System.nanoTime();
    SolveResults resultado = solver.getPath(mazePanel.getCells(), inicio, fin);
    long tiempoFin = System.nanoTime();


    if (resultado != null && !resultado.getCamino().isEmpty()) {
        AlgorithmResult info = new AlgorithmResult(algoritmo, resultado.getCamino().size(), tiempoFin - tiempoInicio);
        resultDAO.save(info);
    }

    return resultado;

    }

}
