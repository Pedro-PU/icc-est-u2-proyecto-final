package solver.solverImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;

public class MazeSolverDFS implements MazeSolver {
  private Set<Cell> visitadas = new LinkedHashSet<>();
  private List<Cell> camino = new ArrayList<>();

  @Override
  public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
    visitadas.clear();
    camino.clear();
    boolean encontrado = dfs(maze, start.getRow(), start.getCol(), end);
    if (encontrado) {
      // El camino se llenó de forma inversa, entonces invertimos
      List<Cell> caminoOrdenado = new ArrayList<>();
      for (int i = camino.size() - 1; i >= 0; i--) {
        caminoOrdenado.add(camino.get(i));
      }
      return new SolveResults(new ArrayList<>(visitadas), caminoOrdenado);
    } else {
      // No se encontró camino, retornar camino vacío
      return new SolveResults(new ArrayList<>(visitadas), new ArrayList<>());
    }
  }

  private boolean dfs(Cell[][] maze, int row, int col, Cell end) {
    if (!isValid(maze, row, col)) return false;
    Cell cell = maze[row][col];
    if (visitadas.contains(cell)) return false;
    visitadas.add(cell);
    if (cell.equals(end)) {
      camino.add(cell);
      return true;
    }
    // Explorar vecinos: abajo, arriba, derecha, izquierda
    if (dfs(maze, row + 1, col, end) ||
        dfs(maze, row - 1, col, end) ||
        dfs(maze, row, col + 1, end) ||
        dfs(maze, row, col - 1, end)) {
      camino.add(cell);
      return true;
    }
    return false;
  }

  private boolean isValid(Cell[][] maze, int row, int col) {
    return (row >= 0 && row < maze.length &&
            col >= 0 && col < maze[0].length &&
            maze[row][col].getState() != CellState.WALL);
  }
}
