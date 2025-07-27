import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.MazeSolverRecursivoCompletoBT;

import java.util.*;

public class MazeApp {
    public static void main(String[] args) {
        int filas = 5;
        int columnas = 5;

        // Crear laberinto vacío
        Cell[][] laberinto = new Cell[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto[i][j] = new Cell(i, j); // por defecto es EMPTY
            }
        }

        // Añadir algunas paredes
        laberinto[1][1].state = CellState.WALL;
        laberinto[2][1].state = CellState.WALL;
        laberinto[3][1].state = CellState.WALL;
        laberinto[3][3].state = CellState.WALL;

        // Definir punto de inicio y fin
        Cell start = laberinto[0][0];
        Cell end = laberinto[4][4];
        start.state = CellState.START;
        end.state = CellState.END;

        MazeSolver solver = new MazeSolverRecursivoCompletoBT();
        // MazeSolver solver = new MazeSolverRecursivoCompleto();
        // MazeSolver solver = new MazeSolverRecursivo();

        // Ejecutar algoritmo
        SolveResults resultados = solver.getPath(laberinto, start, end);

        // Imprimir camino encontrado
        System.out.println("Celdas visitadas: " + resultados.visitadas.size());
        for (Cell c : resultados.visitadas) {
            System.out.println("Visitada -> (" + c.row + ", " + c.col + ")");
        }

        System.out.println("\nCamino al final:");
        if (resultados.camino.isEmpty()) {
            System.out.println("No se encontró un camino.");
        } else {
            for (Cell c : resultados.camino) {
                System.out.println("Camino -> (" + c.row + ", " + c.col + ")");
                if (c.state == CellState.EMPTY) {
                    c.state = CellState.PATH;
                }
            }
        }

        // Mostrar el laberinto en texto
        System.out.println("\nLaberinto resuelto:");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Cell c = laberinto[i][j];
                switch (c.state) {
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
}
