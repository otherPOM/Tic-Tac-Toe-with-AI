package tictactoe;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static Field field;

    public static void main(String[] args) {
        while (true) {
            System.out.print("Input command: ");
            String command = scan.nextLine();

            while (!command.matches("(?i)start (user|easy|medium|hard) (user|easy|medium|hard)")) {
                if (command.equalsIgnoreCase("exit")) {
                    return;
                }
                System.out.println("Bad parameters!");
                command = scan.nextLine();
            }

            var commandArgs = command.split("\\s+");
            var player1 = commandArgs[1];
            var player2 = commandArgs[2];
            field = new Field(new char[]{'_', '_', '_', '_', '_', '_', '_', '_', '_'});

            field.print();
            boolean xMove = true;
            while (true) {
                move(xMove ? player1 : player2);
                field.print();
                var state = field.analyze();
                if (state != GameState.GAME_NOT_FINISHED) {
                    System.out.println(state.getMessage());
                    break;
                }
                xMove = !xMove;
                field.switchPlayer();
            }
        }
    }

    private static void move(String player) {
        switch (player) {
            case "user":
                playerMove();
                break;
            case "easy":
                System.out.println("Making move level \"easy\"");
                easyMove();
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                mediumMove();
                break;
            case "hard":
                System.out.println("Making move level \"hard\"");
                hardMove();
                break;
        }
    }

    private static void playerMove() {
        while (true) {
            System.out.println("Enter the coordinates:");
            try {
                var coordsInput = scan.nextLine().split("\\s+");
                var x = Integer.parseInt(coordsInput[0]);
                var y = Integer.parseInt(coordsInput[1]);
                if (x > 3 || x < 1 || y > 3 || y < 1) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                x -= 1;
                y -= 1;
                if (field.isOccupied(x, y)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                field.move(x, y);
                break;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static void easyMove() {
        var rand = new Random();
        var x = rand.nextInt(3);
        var y = rand.nextInt(3);
        while (field.isOccupied(x, y)) {
            x = rand.nextInt(3);
            y = rand.nextInt(3);
        }
        field.move(x, y);
    }

    private static void mediumMove() {
        var ind = findGameEndingMove(field.getPlayer());

        char b = field.getPlayer() == 'X' ? 'O' : 'X';
        if (ind == -1) {
            ind = findGameEndingMove(b);
            if (ind == -1) {
                easyMove();
                return;
            }
        }

        field.move(ind);
    }

    private static int findGameEndingMove(char c) {
        var grid = field.getGrid();
        int ind = -1;

        for (int i = 0; i < grid.length; i += 3) {
            var line = "" + grid[i] + grid[i + 1] + grid[i + 2];
            if (line.chars().filter(l -> l == c).count() == 2 && line.contains("_")) {
                ind = i + line.indexOf('_');
                break;
            }

        }
        for (int i = 0; i < 3; i++) {
            var column = "" + grid[i] + grid[i + 3] + grid[i + 6];
            if (column.chars().filter(l -> l == c).count() == 2 && column.contains("_")) {
                ind = i + 3 * column.indexOf('_');
                break;
            }
        }

        var dia1 = "" + grid[0] + grid[4] + grid[8];
        var dia2 = "" + grid[2] + grid[4] + grid[6];
        if (dia1.chars().filter(l -> l == c).count() == 2 && dia1.contains("_")) {
            ind = dia1.indexOf('_') * 4;
        }
        if (dia2.chars().filter(l -> l == c).count() == 2 && dia2.contains("_")) {
            ind = dia2.indexOf('_') * 2 + 2;
        }
        return ind;
    }

    private static void hardMove() {
        var bestMove = calculateBestMove(0, new HashMap<>());
        field.move(bestMove);
    }

    private static int calculateBestMove(int depth, Map<Integer, Integer> potentialOutcomes) {
        var state = field.analyze();
        field.resetStates();
        if (state == GameState.DRAW) {
            return 0;
        } else if (state == GameState.X_WINS || state == GameState.O_WINS) {
            return -1;
        } else {
            for (int space : field.emptySpots()) {
                field.move(space);
                field.switchPlayer();
                potentialOutcomes.put(space, (-1 * calculateBestMove(depth + 1, new HashMap<>())));
                field.resetSpace(space);
                field.switchPlayer();
            }
            if (depth == 0) {
                return potentialOutcomes.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
            } else {
                return potentialOutcomes.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
            }
        }
    }
}