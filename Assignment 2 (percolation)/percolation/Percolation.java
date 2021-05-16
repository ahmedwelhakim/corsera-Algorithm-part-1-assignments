import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {


    private final boolean[][] grid; // grid nxn
    private int openSize; // size
    private final int gridN; // n
    private final WeightedQuickUnionUF uf; // weighted quick union find structure
    private final WeightedQuickUnionUF ufNoVbottom;
    private final int vTopSite; // virtual top site
    private final int vBottomSite; // virtual bottom site

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {


        if (n <= 0) {
            throw new IllegalArgumentException("n<=0");
        }

        gridN = n;

        grid = new boolean[n][n];

        // +2 are the top virtual site and bottom virtual site
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufNoVbottom = new WeightedQuickUnionUF(n * n + 1);
        // uf[n*n] -> top, uf[n*n+1] -> bottom
        vTopSite = n * n;
        vBottomSite = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridN || col < 1 || col > gridN) {
            throw new IllegalArgumentException("");
        }
        else {
            int r = row - 1;
            int c = col - 1;
            if (!grid[r][c]) {
                grid[r][c] = true;
                openSize++;
                connectToAdj(r, c);
                connectToVirtualSite(r, c);


            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridN || col < 1 || col > gridN) {
            throw new IllegalArgumentException("");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > gridN || col < 1 || col > gridN) {
            throw new IllegalArgumentException("");
        }
        return isOpen(row, col) && ufNoVbottom.find(vTopSite) == ufNoVbottom
                .find(colRowToNum(row - 1, col - 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(vBottomSite) == uf.find(vTopSite);
    }


    // convention (0,0) top left corner
    private int colRowToNum(int r, int c) {
        return r * gridN + c;
    }

    // convention (0,0) top left corner
    private void connectToAdj(int r, int c) {
        // Check for the left
        if (c - 1 >= 0) {
            if (grid[r][c - 1]) {
                uf.union(colRowToNum(r, c), colRowToNum(r, c - 1));
                ufNoVbottom.union(colRowToNum(r, c), colRowToNum(r, c - 1));
            }
        }
        // Check for the right
        if (c + 1 < gridN) {
            if (grid[r][c + 1]) {
                uf.union(colRowToNum(r, c), colRowToNum(r, c + 1));
                ufNoVbottom.union(colRowToNum(r, c), colRowToNum(r, c + 1));
            }

        }
        // Check for the up
        if (r - 1 >= 0) {
            if (grid[r - 1][c]) {
                uf.union(colRowToNum(r, c), colRowToNum(r - 1, c));
                ufNoVbottom.union(colRowToNum(r, c), colRowToNum(r - 1, c));
            }

        }
        // Check for the down
        if (r + 1 < gridN) {
            if (grid[r + 1][c]) {
                uf.union(colRowToNum(r, c), colRowToNum(r + 1, c));
                ufNoVbottom.union(colRowToNum(r, c), colRowToNum(r + 1, c));
            }
        }
    }

    //  connect to virtual site points if at top or bottom
    private void connectToVirtualSite(int r, int c) {
        if (r == 0) {
            uf.union(colRowToNum(r, c), vTopSite);
            ufNoVbottom.union(colRowToNum(r, c), vTopSite);
        }
        else if (r == gridN - 1) {

            uf.union(colRowToNum(r, c), vBottomSite);
        }
    }
}


