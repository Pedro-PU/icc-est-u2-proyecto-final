package solver;
import models.Cell;
import models.SolveResults;

public interface MazeSolver {
    SolveResults getPath(Cell[][] paramArrayOfCell, Cell paramCell1, Cell paramCell2);
}