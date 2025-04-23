import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        
        // Set<int[]> coordinateSet = new HashSet<>();
        // int[] coord1 = new int[]{1, 5};
        // int[] coord2 = new int[]{3, 7};
        // int[] coord3 = new int[]{3, 7};

        // coordinateSet.add(coord1);
        // coordinateSet.add(coord2);
        // coordinateSet.add(coord3);

        // System.out.println(coordinateSet.size());

        Position pos = new Position(3, 5);
        System.out.println(pos);
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
        // use helper method to pinpoint current location
        int[] start = salamanderLocation(enclosure);
        // create a visited matrix to store visited cells
        boolean[][] visited = new boolean[enclosure.length][enclosure[0].length];
        // return the if its possible to reach target=food
        return canReach(enclosure, start, visited);
    }

    public static boolean canReach(char[][] enclosure, int[] current, boolean[][] visited) {
        // get current position
        int currRow = current[0];
        int currCol = current[1];

        // base case 1: if already visited stop and avoid loops
        if (visited[currRow][currCol]) return false;
        // base case 2: reached the goal if 'f' food is found
        if (enclosure[currRow][currCol] == 'f') return true;

        // mark the cell as visited
        visited[currRow][currCol] = true;

        // get all possible moves using helper method
        List<int[]> moves = possibleMoves(enclosure, current);

        // recurse each move and check base cases, if reached goal return true
        for(int[] move : moves) {
            if(canReach(enclosure, move, visited)) return true;
        }

        return false;
    }

    public static List<int[]> possibleMoves(char[][] enclosure, int[] current) {
        // example int[] current = {0,2} - mark row as 0 and column as 2 
        // to get correct coordinates of starter position
        int currRow = current[0];
        int currCol = current[1];

        List<int[]> moves = new ArrayList<>();

        int[][] directions = new int[][]{
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
        };

        for(int[] direction : directions) {
            int newRow = currRow + direction[0]; // add first element in directions move
            int newCol = currCol + direction[1]; // add second element in directions move
            // ex: if current coord = 0,0 adding dir[0] and dir[1] means:
            // 0 + -1 = -1 and 0 + 0 = 0 final: -1, 0 = go UP a row, same for other movements

            // then check if a move is out of bounds or if its a wall
            // if not add to a possible moves list called moves
            if (newRow >= 0 && newRow < enclosure.length && 
                newCol >= 0 && newCol < enclosure[0].length && 
                enclosure[newRow][newCol] != 'W') {
                    moves.add(new int[]{newRow, newCol});
            }
        }

        return moves;

        // Long and dirty implementation:
        // // UP
        // int newRow = currRow - 1;
        // int newCol = currCol;
        // if (newRow >= 0 && enclosure[newRow][newCol] != 'W') {
        //     moves.add(new int[]{newRow, newCol});
        // }

        // // DOWN
        // newRow = currRow + 1;
        // newCol = currCol;
        // if (newRow < enclosure.length && enclosure[newRow][newCol] != 'W') {
        //     moves.add(new int[]{newRow, newCol});
        // }

        // // LEFT
        // newRow = currRow;
        // newCol = currCol - 1;
        // if (newCol >= 0 && enclosure[newRow][newCol] != 'W') {
        //     moves.add(new int[]{newRow, newCol});

        // }

        // // RIGHT
        // newRow = currRow;
        // newCol = currCol + 1;
        // if (newCol < enclosure[0].length && enclosure[newRow][newCol] != 'W') {
        //     moves.add(new int[]{newRow, newCol});
        // }
    }

    public static int[] salamanderLocation(char[][] enclosure) {
        for(int r = 0; r < enclosure.length; r++) {
            for(int c = 0; c < enclosure[0].length; c++) {
                if (enclosure[r][c] == 's') {
                    return new int[]{r,c};
                }
            }
        }

        throw new IllegalArgumentException("No salamander present");
    }
}
