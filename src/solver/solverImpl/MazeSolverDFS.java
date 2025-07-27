package solver.solverImpl;
import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;

import java.util.*;

public class MazeSolverDFS implements MazeSolver {
    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        int rows = maze.length;
        int cols = maze[0].length;

        Set<Cell> visited = new HashSet<>();
        List<Cell> camino = new ArrayList<>();
        Map<Cell, Cell> cameFrom = new HashMap<>();

        dfs(start, end, maze, visited, cameFrom);

        // Reconstruir camino
        Cell step = end;
        while (step != null && cameFrom.containsKey(step)) {
            camino.add(0, step);
            step = cameFrom.get(step);
        }

        if (step == start) {
            camino.add(0, start);
        }

        return new SolveResults(new ArrayList<>(visited), camino);
    }

    private boolean dfs(Cell current, Cell end, Cell[][] maze, Set<Cell> visited, Map<Cell, Cell> cameFrom) {
        if (visited.contains(current) || current.getState() == CellState.WALL) return false;

        visited.add(current);

        if (current.equals(end)) return true;

        for (Cell neighbor : getNeighbors(current, maze)) {
            if (!visited.contains(neighbor)) {
                cameFrom.put(neighbor, current);
                if (dfs(neighbor, end, maze, visited, cameFrom)) {
                    return true;
                }
            }
        }

        return false;
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