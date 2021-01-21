package tictactoe;

public class Field {
    private final char[][] grid;
    private boolean xWon;
    private boolean oWon;

    public Field(String cells) {
        grid = new char[][]{{cells.charAt(0), cells.charAt(1), cells.charAt(2)},
                {cells.charAt(3), cells.charAt(4), cells.charAt(5)},
                {cells.charAt(6), cells.charAt(7), cells.charAt(8)}};
        xWon = false;
        oWon = false;
    }

    public void move(int x, int y, char c) {
        grid[x - 1][y - 1] = c;
    }

    public boolean isOccupied(int x, int y) {
        return grid[x - 1][y - 1] != '_';
    }

    public void print() {
        System.out.println("---------");
        for (char[] chars : grid) {
            System.out.println("| " + chars[0] + " " +
                    chars[1] + " " + chars[2] + " |");
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
        return (String.valueOf(grid[0]) + String.valueOf(grid[1]) + String.valueOf(grid[2]))
                .contains("_") ? GameState.GAME_NOT_FINISHED : GameState.DRAW;
    }

    private boolean oneSideTooMany() {
        var cells = String.valueOf(grid[0]) + String.valueOf(grid[1]) + String.valueOf(grid[2]);
        return Math.abs(cells.chars().filter(c -> c == 'X').count() -
                cells.chars().filter(c -> c == 'O').count()) > 1;
    }

    private void findWinner() {
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2]) {
                if (grid[i][0] == 'O') {
                    oWon = true;
                } else if (grid[i][0] == 'X') {
                    xWon = true;
                }
            }
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i]) {
                if (grid[0][i] == 'O') {
                    oWon = true;
                } else if (grid[0][i] == 'X') {
                    xWon = true;
                }
            }
            if ((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2]) ||
                    (grid[0][2] == grid[1][1] && grid[1][1] == grid[2][0])) {
                if (grid[1][1] == 'O') {
                    oWon = true;
                } else if (grid[1][1] == 'X') {
                    xWon = true;
                }
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }
}
