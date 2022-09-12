import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private boolean isGoal;
    private boolean isTwinGoal;
    private BoardNode searchNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        BoardNode initialBoardNode = new BoardNode(initial, null, 0);
        BoardNode initialBoardNodeTwin = new BoardNode(initial.twin(), null, 0);
        MinPQ<BoardNode> boardNodePQ = new MinPQ<BoardNode>(new BoardNodeComparator());
        MinPQ<BoardNode> boardNodeTwinPQ = new MinPQ<BoardNode>(new BoardNodeComparator());
        boardNodePQ.insert(initialBoardNode);
        boardNodeTwinPQ.insert(initialBoardNodeTwin);
        searchNode = boardNodePQ.delMin();
        BoardNode twinSearchNode = boardNodeTwinPQ.delMin();
        while (!isGoal && !isTwinGoal) {
            // The initial board
            for (Board b : searchNode.board().neighbors()) {
                if (!isNeighborExist(b, searchNode)) {
                    boardNodePQ.insert(new BoardNode(b, searchNode, searchNode.moves + 1));
                }
            }
            isGoal = searchNode.isGoal();
            if (isGoal) break;
            searchNode = boardNodePQ.delMin();

            // the twin board
            for (Board b : twinSearchNode.board().neighbors()) {
                if (!isNeighborExist(b, twinSearchNode)) {
                    boardNodeTwinPQ
                            .insert(new BoardNode(b, twinSearchNode, twinSearchNode.moves + 1));
                }
            }
            isTwinGoal = twinSearchNode.isGoal();
            if (isTwinGoal) break;
            twinSearchNode = boardNodeTwinPQ.delMin();

        }
        //  StdOut.println(searchNode.toString());
        // StdOut.println(twinSearchNode.toString());
    }

    private boolean isNeighborExist(Board neighbor, BoardNode node) {
        BoardNode currentNode = node;
        while (currentNode.getPreviousNode() != null) {
            currentNode = currentNode.getPreviousNode();
            if (currentNode.board().equals(neighbor)) return true;
        }
        return false;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isGoal;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isGoal)
            return searchNode.moves;
        else
            return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isGoal) {
            return new SolutionIterable();
        }
        else {
            return null;
        }

    }

    private class SolutionIterable implements Iterable<Board> {

        public Iterator<Board> iterator() {
            return new SolutionIterator();
        }
    }

    private class SolutionIterator implements Iterator<Board> {
        private final ArrayList<Board> boards;
        private BoardNode tempSearchNode;
        private final Iterator<Board> bordIterator;

        public SolutionIterator() {
            boards = new ArrayList<>();
            tempSearchNode = searchNode;
            while (tempSearchNode != null) {
                boards.add(tempSearchNode.board());
                tempSearchNode = tempSearchNode.getPreviousNode();
            }
            Collections.reverse(boards);
            bordIterator = boards.iterator();
        }

        public boolean hasNext() {
            return bordIterator.hasNext();
        }

        public Board next() {
            return bordIterator.next();
        }
    }

    private class BoardNode {

        private final Board board;
        private final BoardNode previousNode;
        private final int moves;

        public BoardNode(Board b, BoardNode previousNode, int moves) {
            board = b;
            this.previousNode = previousNode;
            this.moves = moves;
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public Board board() {
            return board;
        }

        public BoardNode getPreviousNode() {
            return previousNode;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public String toString() {
            return board.toString();
        }
    }

    private class BoardNodeComparator implements Comparator<BoardNode> {

        public int compare(BoardNode o1, BoardNode o2) {
            return o1.priority() - o2.priority();
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] t = { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } };
        Board b = new Board(t);
        Solver solver = new Solver(b);
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }


}
