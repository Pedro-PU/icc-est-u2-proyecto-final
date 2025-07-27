package models;

public class Cell {
    public int row;

    public int col;

    public CellState state;

    public Cell(int paramInt1, int paramInt2) {
        this.row = paramInt1;
        this.col = paramInt2;
        this.state = CellState.EMPTY;
    }

    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof Cell))
            return false;
        Cell cell = (Cell)paramObject;
        return (this.row == cell.row && this.col == cell.col);
    }

    public int hashCode() {
        return 31 * this.row + this.col;
    }
}
