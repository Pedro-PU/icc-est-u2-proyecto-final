import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;
import solver.solverImpl.*;

import java.util.*;

public class MazeApp {
    public static void main(String[] args) {
        int filas = 5;
        int columnas = 5;

        // Crear laberinto vacío
        Cell[][] laberinto = new Cell[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                laberinto[i][j] = new Cell(i, j); // Por defecto es EMPTY
            }
        }

        // Añadir algunas paredes
        laberinto[1][1].setState(CellState.WALL);
        laberinto[2][1].setState(CellState.WALL);
        laberinto[3][1].setState(CellState.WALL);
        laberinto[3][3].setState(CellState.WALL);

        // Definir punto de inicio y fin
        Cell start = laberinto[0][0];
        Cell end = laberinto[4][4];
        start.setState(CellState.START);
        end.setState(CellState.END);

        // Escoge el algoritmo a probar
        MazeSolver solver = new MazeSolverBFS();
        // MazeSolver solver = new MazeSolverDFS();
        // MazeSolver solver = new MazeSolverRecursivo();
        // MazeSolver solver = new MazeSolverRecursivoCompleto();
        // MazeSolver solver = new MazeSolverRecursivoCompletoBT();

        // Ejecutar el algoritmo
        SolveResults resultado = solver.getPath(laberinto, start, end);

        // Mostrar estadísticas
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
                // Actualiza el estado para visualizarlo en el laberinto
                if (c.getState() == CellState.EMPTY) {
                    c.setState(CellState.PATH);
                }
            }
        }

        // Mostrar el laberinto final en consola
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
}

