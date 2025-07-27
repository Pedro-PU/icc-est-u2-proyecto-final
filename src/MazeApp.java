import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.*;

import java.util.*;

import javax.swing.JOptionPane;

public class MazeApp {
    public static void main(String[] args) {
        int[] dimensiones = solicitarDimensiones();
        if (dimensiones == null) {
            System.out.println("Cancelado por el usuario o entrada inválida.");
            return;
        }
        int filas = dimensiones[0];
        int columnas = dimensiones[1];
        Cell[][] laberinto = new Cell[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto[i][j] = new Cell(i, j);
            }
        }

        // Paredes
        laberinto[1][1].setState(CellState.WALL);
        laberinto[2][1].setState(CellState.WALL);
        laberinto[3][1].setState(CellState.WALL);
        laberinto[3][3].setState(CellState.WALL);

        Cell start = laberinto[0][0];
        Cell end = laberinto[filas - 1][columnas - 1];
        start.setState(CellState.START);
        end.setState(CellState.END);

        MazeSolver solver = new MazeSolverBFS();
        // MazeSolver solver = new MazeSolverDFS();
        // MazeSolver solver = new MazeSolverRecursivo();
        // MazeSolver solver = new MazeSolverRecursivoCompleto();
        // MazeSolver solver = new MazeSolverRecursivoCompletoBT();

        SolveResults resultado = solver.getPath(laberinto, start, end);

        System.out.println("Celdas visitadas: " + resultado.getVisitadas().size());
        for (Cell c : resultado.getVisitadas()) {
            System.out.println("Visitada -> " + c);
        }

        System.out.println("\nCamino encontrado:");
        if (!resultado.seEncontroCamino()) {
            System.out.println("No se encontró un camino.");
        } else {
            for (Cell c : resultado.getCamino()) {
                System.out.println("Camino -> " + c);
                if (c.getState() == CellState.EMPTY) {
                    c.setState(CellState.PATH);
                }
            }
        }

        System.out.println("\nLaberinto resuelto:");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell c = laberinto[i][j];
                switch (c.getState()) {
                    case START -> System.out.print("S ");
                    case END -> System.out.print("E ");
                    case WALL -> System.out.print("# ");
                    case PATH -> System.out.print("* ");
                    default -> System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    // Dimensiones
    public static int[] solicitarDimensiones() {
        try {
            String str1 = JOptionPane.showInputDialog("Ingrese número de filas:");
            if (str1 == null) return null;

            String str2 = JOptionPane.showInputDialog("Ingrese número de columnas:");
            if (str2 == null) return null;

            int filas = Integer.parseInt(str1.trim());
            int columnas = Integer.parseInt(str2.trim());

            if (filas <= 4 || columnas <= 4) {
                JOptionPane.showMessageDialog(null, "Debe ingresar valores mayores a 4.");
                return null;
            }

            return new int[] { filas, columnas };
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar números válidos.");
            return null;
        }
    }
}


