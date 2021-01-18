package tictactoe;

import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the cells:");
        var cells = scan.nextLine();
        while (!cells.matches("[XO_]{9}")) {
            System.out.println("Invalid cells given");
            cells = scan.nextLine();
        }

        var grid = new Grid(cells);
        grid.print();

        var xMove = cells.chars().filter(c -> c == 'X').count() ==
                cells.chars().filter(c -> c == 'O').count();
//        var xMove = true;
        while (true) {
            System.out.print("Enter the coordinates: ");
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
                char c = xMove ? 'X' : 'O';
                grid.move(x, y, c);
                xMove = !xMove;
                grid.print();
                var state = grid.analyze();
//                if (state != GameState.GAME_NOT_FINISHED) {
                    System.out.println(state.getMessage());
                    break;
//                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("You should enter numbers!");
            }
        }
    }
}
