package be.doubbel.hard9x9.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * InitGrid()
 * initialize the grid, size depending on constructor parameter playlist, making array grid[][] with new Cells
 * getRowValues() (idem for getColValues or getSquareValues)
 * return all values from that row
 * getEmptyCellsInRow (idem for getColValues or getSquareValues)
 * return all empty cells
 * getUsedCandidatesInRow (idem for getColValues or getSquareValues)
 * return all missing candidates for that row, meaning
 * removeCandidatesFromRow (idem for getColValues or getSquareValues)
 * removes all given candidates from that row
 */

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
        for (Integer row = 0; row < sizePlayField; row++) {
            for (Integer col = 0; col < sizePlayField; col++) {
                grid[row][col] = new Cell(new RowCol(row, col), new Object(), playList);
            }
        }
    }
    private void deletaAllCandidates() {
        for (Integer row = 0; row < sizePlayField; row++) {
            for (Integer col = 0; col < sizePlayField; col++) {
                grid[row][col].removeAllCandidates();
            }
        }
    }

    private Integer[] getRowValues(Integer row) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer col = 0; col < sizePlayField; col++) {
            result[col] = grid[row][col].getValue();
        }
        return result;
    }

    private Integer[] getColValues(Integer col) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer row = 0; row < sizePlayField; row++) {
            result[row] = grid[row][col].getValue();
        }
        return result;
    }

    private Integer[] getSquareValues(Integer rowSquare, Integer colSquare) {
        Integer[] result = new Integer[sizePlayField];
        for (Integer row = 0; row < sizePlayFieldRoot; row++) {
            for (Integer col = 0; col < sizePlayFieldRoot; col++) {
                result[row] = grid[row + (rowSquare * sizePlayFieldRoot)][col + (colSquare * sizePlayFieldRoot)].getValue();
            }
        }
        return result;
    }

    private List<RowCol> getEmptyCellsInRow(Integer row) {
        List<RowCol> emptyCells = new ArrayList<>();
        Integer value;
        for (Integer col = 0; col < sizePlayField; col++) {
            value = grid[row][col].getValue();
            if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                emptyCells.add(new RowCol(row, col));
            }
        }
        return emptyCells;
    }

    private List<RowCol> getEmptyCellsInCol(Integer col) {
        Integer value;
        List<RowCol> emptyCells = new ArrayList<>();
        for (Integer row = 0; row < sizePlayField; row++) {
            value = grid[row][col].getValue();
            if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                emptyCells.add(new RowCol(row, col));
            }
        }
        return emptyCells;
    }

    private List<RowCol> getEmptyCellsInSquare(Integer rowSquare, Integer colSquare) {
        Integer value;
        List<RowCol> emptyCells = new ArrayList<>();
        emptyCells.clear();
        for (Integer row = 0; row < sizePlayFieldRoot; row++) {
            for (Integer col = 0; col < sizePlayFieldRoot; col++) {
                value = grid[row + (rowSquare * sizePlayFieldRoot)][col + (colSquare * sizePlayFieldRoot)].getValue();
                if (value.equals(PlayFieldValues.NOT_COMPLETED)) {
                    emptyCells.add(new RowCol(row + (rowSquare * sizePlayFieldRoot), col + (colSquare * sizePlayFieldRoot)));
                }
            }
        }
        return emptyCells;
    }

    private List<Integer> getMissingCandidatesInRow(Integer row) {
        List<Integer> missingCandidates = playFieldValues.getPlayFieldValues(playList);
        for (Integer col = 0; col < sizePlayField; col++) {
            missingCandidates.removeAll(grid[row][col].getAllCandidates());
        }
        return missingCandidates;
    }

    private List<Integer> countMissingCandidatesInCol(Integer col) {
        List<Integer> missingCandidates = playFieldValues.getPlayFieldValues(playList);
        for (Integer row = 0; row < sizePlayField; row++) {
            missingCandidates.removeAll(grid[row][col].getAllCandidates());
        }
        return missingCandidates;
    }

    private List<Integer> countMissingCandidatesInSquare(Integer rowSquare, Integer colSquare) {
        List<Integer> missingCandidates = playFieldValues.getPlayFieldValues(playList);
        for (Integer row = 0; row < sizePlayFieldRoot; row++) {
            for (Integer col = 0; col < sizePlayFieldRoot; col++) {
                missingCandidates
                        .removeAll(grid[row + (rowSquare * sizePlayFieldRoot)][col + (colSquare * sizePlayFieldRoot)].getAllCandidates());
            }
        }
        return missingCandidates;
    }

    private void removeCandidatesFromRow(Integer row, List<Integer> candidatesToRemove) {
        for (Integer col = 0; col < sizePlayField; col++) {
            grid[row][col].removeCandidates(candidatesToRemove);
        }
    }

    private void removeCandidatesFromCol(Integer col, List<Integer> candidatesToRemove) {
        for (Integer row = 0; row < sizePlayField; row++) {
            grid[row][col].removeCandidates(candidatesToRemove);
        }
    }

    private void removeCandidatesFromSquare(Integer rowSquare, Integer colSquare, List<Integer> candidatesToRemove) {
        for (Integer row = 0; row < sizePlayFieldRoot; row++) {
            for (Integer col = 0; col < sizePlayFieldRoot; col++) {
                grid[row + (rowSquare * sizePlayFieldRoot)][col + (colSquare * sizePlayFieldRoot)]
                        .removeCandidates(candidatesToRemove);
            }
        }
    }

    private List<RowCol> getCellsInRowCandidatesMatchingExactly(Integer row, List<Integer> candidateToMatch) {
        Integer size = candidateToMatch.size();
        List<RowCol> result = new ArrayList<>();
        for (Integer col = 0; col < sizePlayField; col++) {
            if (grid[row][col].countCandidates().equals(size) &&
                    grid[row][col].containsAllCandidates(candidateToMatch)) {
                result.add(new RowCol(row, col));
            }
        }
        return result;
    }
    private List<RowCol> getCellsInColCandidatesMatchingExactly(Integer col, List<Integer> candidateToMatch) {
        Integer size = candidateToMatch.size();
        List<RowCol> result = new ArrayList<>();
        for (Integer row = 0; row < sizePlayField; row++) {
            if (grid[row][col].countCandidates().equals(size) &&
                    grid[row][col].containsAllCandidates(candidateToMatch)) {
                result.add(new RowCol(row, col));
            }
        }
        return result;
    }


}
