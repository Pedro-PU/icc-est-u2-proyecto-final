package solver.solverImpl;

import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverBFS implements MazeSolver {

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        int rows = maze.length;
        int cols = maze[0].length;

        Map<Cell, Cell> cameFrom = new HashMap<>();
        Set<Cell> visited = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        List<Cell> camino = new ArrayList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            if (current.equals(end)) break;

            for (Cell neighbor : getNeighbors(current, maze)) {
                if (!visited.contains(neighbor) && neighbor.getState() != CellState.WALL) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        // Verificar si se encontró camino
        if (!cameFrom.containsKey(end)) {
            return new SolveResults(new ArrayList<>(visited), camino); // camino vacío
        }

        // Reconstruir camino desde end hasta start
        Cell current = end;
        while (!current.equals(start)) {
            camino.add(0, current);
            current = cameFrom.get(current);
        }
        camino.add(0, start); // incluir el nodo de inicio

        return new SolveResults(new ArrayList<>(visited), camino);
    }

    private List<Cell> getNeighbors(Cell cell, Cell[][] maze) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Arriba, Abajo, Izquierda, Derecha
        int r = cell.getRow();
        int c = cell.getCol();

        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr >= 0 && nr < maze.length && nc >= 0 && nc < maze[0].length) {
                neighbors.add(maze[nr][nc]);
            }
        }

        return neighbors;
    }
}
