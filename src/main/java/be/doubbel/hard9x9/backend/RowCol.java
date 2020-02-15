package be.doubbel.hard9x9.backend;

public class RowCol {
    Integer row;
    Integer col;

    public RowCol(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getCol() {
        return col;
    }

    @Override
    public boolean equals(Object obj) {
        RowCol rowCol = (RowCol) obj;
        return getRow().equals(rowCol.getRow()) && getCol().equals(rowCol.getCol());
    }
}
