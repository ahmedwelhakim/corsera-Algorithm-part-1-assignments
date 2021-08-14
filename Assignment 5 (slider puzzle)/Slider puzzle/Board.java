import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private int[][] board;
    private final int len;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException("null argument");

        len = tiles.length;
        board = new int[len][len];
        for (int i = 0; i < len; i++) {
            board[i] = Arrays.copyOf(tiles[i], len);
        }
    }

    // string representation of this board
    public String toString() {
        String s = "";
        s += board.length;
        s += "\n";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                s += board[i][j];
            }
            s += "\n";
        }
        return s;
    }

    // board dimension n
    public int dimension() {
        return len;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (!(board[i][j] == len * i + 1 + j) && i != len - 1) {
                    hamming++;
                }
                else if (!(board[i][j] == len * i + 1 + j) && j != len - 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (!(i == len - 1 && j == len - 1))
                    result += calcDistance(i, j, board[i][j]);
            }
        }
        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return Arrays.deepEquals(board, (int[][]) y);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        class NeighborsIterator implements Iterator<Board> {

            private int count;
            private int blank_i;
            private int blank_j;
            // 0 for left, 1 for bottom, 2 for right, 3 for top
            private boolean[] hasNeighborsAt = { false, false, false, false };
            private int current;

            public NeighborsIterator() {
                findBlank();
                init_hasNeigborsAt();
                updateCurrent();

            }

            public boolean hasNext() {
                if (current < 4) {
                    // if there is a neighbor at current index
                    return hasNeighborsAt[current];
                }
                else {
                    return false;
                }
            }

            public Board next() {
                int[][] newBoard = new int[len][len];
                for (int i = 0; i < len; i++) {
                    newBoard[i] = Arrays.copyOf(board[i], len);
                }

                switch (current) {
                    case 0: // case left
                        newBoard[blank_i][blank_j] = newBoard[blank_i - 1][blank_j];
                        newBoard[blank_i - 1][blank_j] = 0;
                        current++;
                        break;
                    case 1:
                        newBoard[blank_i][blank_j] = newBoard[blank_i][blank_j - 1];
                        newBoard[blank_i][blank_j - 1] = 0;
                        current++;
                        break;
                    case 2:
                        newBoard[blank_i][blank_j] = newBoard[blank_i + 1][blank_j];
                        newBoard[blank_i + 1][blank_j] = 0;
                        current++;
                        break;
                    case 3:
                        newBoard[blank_i][blank_j] = newBoard[blank_i][blank_j + 1];
                        newBoard[blank_i][blank_j + 1] = 0;
                        current++;
                        break;
                }

                updateCurrent();
                return new Board(newBoard);
            }

            private void findBlank() {
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < len; j++) {
                        if (board[i][j] == 0) {
                            blank_i = i;
                            blank_j = j;
                        }
                    }
                }
            }

            private void init_hasNeigborsAt() {
                if (blank_i - 1 >= 0) {
                    // 0 Has left neighbor
                    hasNeighborsAt[0] = true;
                }
                if (blank_j - 1 >= 0) {
                    // 1 Has bottom neighbor
                    hasNeighborsAt[1] = true;
                }
                if (blank_i + 1 < len) {
                    // 2 Has right neighbor
                    hasNeighborsAt[2] = true;
                }
                if (blank_j + 1 < len >) {
                    // 3 Has top neighbor
                    hasNeighborsAt[3] = true;
                }
            }

            private void updateCurrent() {
                while (current < 4 && hasNeighborsAt[current] == false) {
                    current++;
                }
            }
        }

        class NeighborsIterable implements Iterable<Board> {

            @Override
            public Iterator<Board> iterator() {
                return new NeighborsIterator();
            }
        }
        return new NeighborsIterable();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] newBoard = new int[len][len];
        for (int i = 0; i < len; i++) {
            newBoard[i] = Arrays.copyOf(board[i], len);
        }

        int i = 0, j = 0;
        if (board[i][j] == 0) {
            i++;
        }
        int temp = newBoard[i][j];
        newBoard[i][j] = newBoard[i][j + 1];
        newBoard[i][j + 1] = temp;
        return new Board(newBoard);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
    }

    private int[] calcGoalCoordinates(int x) {
        int k = x - 1;
        int i = k / len;
        int j = k - len * i;
        int[] result = { i, j };
        return result;
    }

    private int calcDistance(int i, int j, int number) {
        int[] goalCoordinates = calcGoalCoordinates(number);
        int goal_i = goalCoordinates[0];
        int goal_j = goalCoordinates[1];
        int distance = Math.abs(goal_i - i) + Math.abs(goal_j - j);
        return distance;
    }

}
