package tictactoe;

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
            var cells = "_________";
            field = new Field(cells);
            field.print();
            boolean xMove = true;
            while (true) {
                char c = xMove ? 'X' : 'O';
                move(c, xMove ? player1 : player2);
                field.print();
                var state = field.analyze();
                if (state != GameState.GAME_NOT_FINISHED) {
                    System.out.println(state.getMessage());
                    break;
                }
                xMove = !xMove;
            }
        }
    }

    private static void move(char c, String player) {
        switch (player) {
            case "user":
                playerMove(c);
                break;
            case "easy":
                System.out.println("Making move level \"easy\"");
                easyMove(c);
                break;
            case "medium":
                System.out.println("Making move level \"medium\"");
                mediumMove(c);
                break;
            case "hard":
                hardMove(c);
                break;
        }
    }

    private static void playerMove(char c) {
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
                if (field.isOccupied(x, y)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                field.move(x, y, c);
                break;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static void easyMove(char c) {
        var rand = new Random();
        var x = rand.nextInt(3) + 1;
        var y = rand.nextInt(3) + 1;
        while (field.isOccupied(x, y)) {
            x = rand.nextInt(3) + 1;
            y = rand.nextInt(3) + 1;
        }
        field.move(x, y, c);
    }

    private static void mediumMove(char c) {
        var grid = field.getGrid();
        int x = 10;
        int y = 10;

        for (int i = 0; i < grid.length; i++) {
            var line = String.valueOf(grid[i]);
            var column = "" + grid[0][i] + grid[1][i] + grid[2][i];
            if (line.chars().filter(l -> l == c).count() == 2 && line.contains("_")) {
                x = i + 1;
                y = line.indexOf('_') + 1;
                break;
            }
            if (column.chars().filter(l -> l == c).count() == 2 && column.contains("_")) {
                x = column.indexOf('_') + 1;
                y = i + 1;
                break;
            }
        }
        var dia1 = "" + grid[0][0] + grid[1][1] + grid[2][2];
        var dia2 = "" + grid[2][0] + grid[1][1] + grid[0][2];
        if (dia1.chars().filter(l -> l == c).count() == 2 && dia1.contains("_")) {
            x = dia1.indexOf('_') + 1;
            y = dia1.indexOf('_') + 1;
        }
        if (dia2.chars().filter(l -> l == c).count() == 2 && dia2.contains("_")) {
            x = dia2.indexOf('_') == 0 ? 3 : dia2.indexOf('_') == 2 ? 1 : 2;
            y = dia2.indexOf('_') + 1;
        }

        char b = c == 'X' ? 'O' : 'X';
        if (x == 10) {
            for (int i = 0; i < grid.length; i++) {
                var line = String.valueOf(grid[i]);
                var column = "" + grid[0][i] + grid[1][i] + grid[2][i];
                if (line.chars().filter(l -> l == b).count() == 2 && line.contains("_")) {
                    x = i + 1;
                    y = line.indexOf('_') + 1;
                    break;
                }
                if (column.chars().filter(l -> l == b).count() == 2 && column.contains("_")) {
                    x = column.indexOf('_') + 1;
                    y = i + 1;
                    break;
                }
            }
            if (dia1.chars().filter(l -> l == b).count() == 2 && dia1.contains("_")) {
                x = dia1.indexOf('_') + 1;
                y = dia1.indexOf('_') + 1;
            }
            if (dia2.chars().filter(l -> l == b).count() == 2 && dia2.contains("_")) {
                x = dia2.indexOf('_') == 0 ? 3 : dia2.indexOf('_') == 2 ? 1 : 2;
                y = dia2.indexOf('_') + 1;
            }
        }
        if (x == 10) {
            easyMove(c);
        } else {
            field.move(x, y, c);
        }
    }

    private static void hardMove(char c) {

    }
}