package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Field {
    private final char[] grid;
    private boolean xWon;
    private boolean oWon;
    private char player;

    public Field(char[] grid) {
        this.grid = grid;
        xWon = false;
        oWon = false;
        player = 'X';
    }

    public void switchPlayer() {
        player = player == 'X' ? 'O' : 'X';
    }

    public char getPlayer() {
        return player;
    }

    void resetSpace(int ind) {
        grid[ind] = '_';
    }

    void resetStates() {
        xWon = false;
        oWon = false;
    }

    public void move(int x, int y) {
        var ind = x * 3 + y;
        move(ind);
    }

    public void move(int ind) {
        grid[ind] = player;
    }

    public boolean isOccupied(int x, int y) {
        var ind = x * 3 + y;
        return grid[ind] != '_';
    }

    public List<Integer> emptySpots() {
        var res = new ArrayList<Integer>();
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] == '_') {
                res.add(i);
            }
        }
        return res;
    }

    public void print() {
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + grid[i] + " " +
                    grid[i + 1] + " " + grid[i + 2] + " |");
        }
        System.out.println("---------");
    }

    public GameState analyze() {
        if (oneSideTooMany()) {
            return GameState.IMPOSSIBLE;
        }

        findWinner();
        if (xWon && oWon) {
            return GameState.IMPOSSIBLE;
        }
        if (xWon) {
            return GameState.X_WINS;
        }
        if (oWon) {
            return GameState.O_WINS;
        }
        return String.valueOf(grid).contains("_") ? GameState.GAME_NOT_FINISHED : GameState.DRAW;
    }

    private boolean oneSideTooMany() {
        var cells = String.valueOf(grid);
        return Math.abs(cells.chars().filter(c -> c == 'X').count() -
                cells.chars().filter(c -> c == 'O').count()) > 1;
    }

    private void findWinner() {
        for (int i = 0; i < 9; i += 3) {
            if (grid[i] == grid[i + 1] && grid[i + 1] == grid[i + 2]) {
                if (grid[i] == 'O') {
                    oWon = true;
                } else if (grid[i] == 'X') {
                    xWon = true;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (grid[i] == grid[i + 3] && grid[i + 3] == grid[i + 6]) {
                if (grid[i] == 'O') {
                    oWon = true;
                } else if (grid[i] == 'X') {
                    xWon = true;
                }
            }
        }

        if ((grid[0] == grid[4] && grid[4] == grid[8]) ||
                (grid[2] == grid[4] && grid[4] == grid[6])) {
            if (grid[4] == 'O') {
                oWon = true;
            } else if (grid[4] == 'X') {
                xWon = true;
            }
        }
    }

    public char[] getGrid() {
        return grid;
    }
}
