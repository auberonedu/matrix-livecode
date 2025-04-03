import java.util.ArrayList;
import java.util.*;

public class SalamanderSearch {
    public static void main(String[] args) {
        char[][] enclosure1 = {
            {'.','.','.','.','.','.'},
            {'W','.','W','W','.','.'},
            {'.','.','W','.','.','W'},
            {'f','W','.','.','W','.'},
            {'W','.','W','s','.','.'},
        };

        char[][] enclosure2 = {
            {'.','.','.','.','.','.'},
            {'W','W','W','W','s','.'},
            {'.','.','W','.','.','W'},
            {'f','W','.','.','W','.'},
            {'W','.','W','.','.','.'},
        };
    }

    /**
     * Returns whether a salamander can reach the food in an enclosure.
     * 
     * The enclosure is represented by a rectangular char[][] that contains
     * ONLY the following characters:
     * 
     * 's': represents the starting location of the salamander
     * 'f': represents the location of the food
     * 'W': represents a wall
     * '.': represents an empty space the salamander can walk through
     * 
     * The salamander can move one square at a time: up, down, left, or right.
     * It CANNOT move diagonally.
     * It CANNOT move off the edge of the enclosure.
     * It CANNOT move onto a wall.
     * 
     * This method should return true if there is any sequence of steps that
     * the salamander could take to reach food.
     * 
     * @param enclosure
     * @return whether the salamander can reach the food
     */
    public static boolean canReach(char[][] enclosure) {
        // find salamander
        int[] start = salamanderLocation(enclosure);
        // this boolean is the same size as the enclosure and starts off all false by default
        boolean[][] visited = new boolean[enclosure.length][enclosure[0].length];

        // recursive dfs
        return canReach(enclosure, start, visited);
    }

    /* Helper method that passes in current location and boolean record of visited locations. */
    public static boolean canReach(char[][] enclosure, int[] current, boolean[][] visited) {
        int currRow = current[0];
        int currCol = current[1];

        if (visited[currRow][currCol]) return false;
        if (enclosure[currRow][currCol] == 'f') return true;

        visited[currRow][currCol] = true;

        List<int[]> moves = possibleMoves(enclosure, current);

        for (var move: moves) {
            if (canReach(enclosure, move, visited)) return true;
        }

        return false;
    }

    /* Helper method to find salamander starting position.
    Returns an array of integers that represent the row and column of the starting position of the salamander */
    public static int[] salamanderLocation(char[][] enclosure) {

        for (int r = 0; r < enclosure.length; r++) {
            for (int c = 0; c < enclosure[r].length; c++) {
                if (enclosure[r][c] == 's') {
                    return new int[]{r, c};
                }
            }
        }
        throw new IllegalArgumentException("No salamander present");
    }

    /* Helper method
     * Given a current location, what are the possible valid moves
     * Checks up, down, left, right
     */
    public static List<int[]> possibleMoves(char[][] enclosure, int[] currentLocation) {
        int currentRow = currentLocation[0];
        int currentCol = currentLocation[1];

        List<int[]> moves = new ArrayList<>();
        
        // UP
        int newRow = currentRow - 1;
        int newCol = currentCol;
        if (newRow >= 0 && enclosure[newRow][newCol] != 'W') {
            moves.add(new int[]{newRow, newCol});
        }

        // DOWN
        newRow = currentRow + 1;
        newCol = currentCol;
        if (newRow < enclosure.length && enclosure[newRow][newCol] != 'W') {
            moves.add(new int[]{newRow, newCol});
        }

        // LEFT
        newRow = currentRow;
        newCol = currentCol - 1;
        if (newCol >= 0 && enclosure[newRow][newCol] != 'W') {
            moves.add(new int[]{newRow, newCol});
        }

        // RIGHT
        newRow = currentRow;
        newCol = currentCol + 1;
        if (newCol < enclosure[0].length && enclosure[newRow][newCol] != 'W') {
            moves.add(new int[]{newRow, newCol});
        }

        return moves;
    }
}
