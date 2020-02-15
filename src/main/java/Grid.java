import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] grid;
    private PlayList playList;
    private PlayFieldValues playFieldValues;
    private Integer sizePlayField;
    private Integer sizePlayFieldRoot;

    public Grid(PlayList playList) {
        this.playList = playList;
        playFieldValues = PlayFieldValues.getInstance();
        sizePlayField = playFieldValues.sizePlayField(playList);
        sizePlayFieldRoot = playFieldValues.rootPlayField(playList);
        initGrid();
    }
    private void initGrid() {
        grid = new Cell[playFieldValues.sizePlayField(playList)][playFieldValues.sizePlayField(playList)];
        for (Integer row=0;row<sizePlayField;row++) {
            for (Integer col=0;col<sizePlayField;col++) {
                grid[row][col] = new Cell(new RowCol(row,col),new Object(),playList);
            }
        }
    }
    private Integer[] getRowValues(Integer row) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer col = 0;col<sizePlayField;col++) {
            result[col] = grid[row][col].getValue();
        }
        return result;
    }
    private Integer[] getColValues(Integer col) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer row = 0;row<sizePlayField;row++) {
            result[row] = grid[row][col].getValue();
        }
        return result;
    }
    private Integer[] getSquareValues(Integer rowSquare,Integer colSquare) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer row = 0;row<sizePlayFieldRoot;row++) {
            for (Integer col = 0; col<sizePlayFieldRoot;col++){
                result[row] = grid[row+(rowSquare*sizePlayFieldRoot)][col+(colSquare*sizePlayFieldRoot)].getValue();
            }
        }
        return result;
    }
    private Integer countEmptyCellsInRow(Integer row,List<RowCol> emptyCells) {
        Integer value;
        emptyCells.clear();
        for (Integer col=0;col<sizePlayField;col++) {
            value = grid[row][col].getValue();
            if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                emptyCells.add(new RowCol(row,col));
            }
        }
        return emptyCells.size();
    }
    private Integer countEmptyCellsInCol(Integer col,List<RowCol> emptyCells) {
        Integer value;
        emptyCells.clear();
        for (Integer row=0;row<sizePlayField;row++) {
            value = grid[row][col].getValue();
            if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                emptyCells.add(new RowCol(row,col));
            }
        }
        return emptyCells.size();
    }
    private Integer countEmptyCellsInSquare(Integer rowSquare,Integer colSquare,List<RowCol> emptyCells) {
        Integer value;
        emptyCells.clear();
        for (Integer row = 0;row<sizePlayFieldRoot;row++) {
            for (Integer col = 0; col<sizePlayFieldRoot;col++){
                value = grid[row+(rowSquare*sizePlayFieldRoot)][col+(colSquare*sizePlayFieldRoot)].getValue();
                if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                    emptyCells.add(new RowCol(row+(rowSquare*sizePlayFieldRoot),col+(colSquare*sizePlayFieldRoot)));
                }
            }
        }
        return emptyCells.size();
    }
    private Integer countMissingCandidatesInRow(Integer row,List<Integer>missingCandidates) {
        missingCandidates = playFieldValues.getPlayFieldValues(playList);
        for (Integer col=0;col<sizePlayField;col++) {
            missingCandidates.removeAll(grid[row][col].getAllCandidates());
        }
        return missingCandidates.size();
    }
    private Integer countMissingCandidatesInCol(Integer col,List<Integer>missingCandidates) {
        missingCandidates = playFieldValues.getPlayFieldValues(playList);
        for (Integer row=0;row<sizePlayField;row++) {
            missingCandidates.removeAll(grid[row][col].getAllCandidates());
        }
        return missingCandidates.size();
    }
}
