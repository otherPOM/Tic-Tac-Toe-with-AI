package tictactoe;

public class Move {
    private final int score;
    private final int x;
    private final int y;

    public Move(int score, int x, int y) {
        this.score = score;
        this.x = x;
        this.y = y;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
