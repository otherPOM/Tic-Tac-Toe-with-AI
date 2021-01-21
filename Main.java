package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static Grid grid;

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
            grid = new Grid(cells);
            grid.print();
            boolean xMove = true;
            while (true) {
                char c = xMove ? 'X' : 'O';
                move(c, xMove ? player1 : player2);
                grid.print();
                var state = grid.analyze();
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
                easyMove(c);
                break;
            case "medium":
                break;
            case "hard":
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
                if (grid.isOccupied(x, y)) {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }

                grid.move(x, y, c);
                break;
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }

    private static void easyMove(char c) {
        System.out.println("Making move level \"easy\"");
        var rand = new Random();
        var x = rand.nextInt(3) + 1;
        var y = rand.nextInt(3) + 1;
        while (grid.isOccupied(x, y)) {
            x = rand.nextInt(3) + 1;
            y = rand.nextInt(3) + 1;
        }
        grid.move(x, y, c);
    }
}