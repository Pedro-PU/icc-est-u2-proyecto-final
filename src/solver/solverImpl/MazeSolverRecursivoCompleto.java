package solver.solverImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import models.Cell;
import models.CellState;
import models.SolveResults;
import solver.MazeSolver;

public class MazeSolverRecursivoCompleto implements MazeSolver {
    private final Set<Cell> visitadas = new LinkedHashSet<>();
    private final List<Cell> camino = new ArrayList<>();

    @Override
    public SolveResults getPath(Cell[][] maze, Cell start, Cell end) {
        visitadas.clear();
        camino.clear();
        findPath(maze, start.getRow(), start.getCol(), end);
        Collections.reverse(camino);
        return new SolveResults(new ArrayList<>(visitadas), new ArrayList<>(camino));
    }

    private boolean findPath(Cell[][] maze, int row, int col, Cell end) {
        if (!isValid(maze, row, col)) return false;
        Cell cell = maze[row][col];
        if (visitadas.contains(cell)) return false;

        visitadas.add(cell);
        camino.add(cell);

        if (cell.equals(end)) {
            camino.add(cell);
            return true;
        }

        if (findPath(maze, row + 1, col, end) ||
                findPath(maze, row, col + 1, end) ||
                findPath(maze, row - 1, col, end) ||
                findPath(maze, row, col - 1, end)) return true;

        return false;
    }

    private boolean isValid(Cell[][] maze, int row, int col) {
        return row >= 0 && row < maze.length &&
                col >= 0 && col < maze[0].length &&
                maze[row][col].getState() != CellState.WALL;
    }
}

