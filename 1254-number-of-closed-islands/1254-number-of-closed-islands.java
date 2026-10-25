
class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[] stack = new int[m * n];

        // Remove all land connected to the boundary
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if ((r == 0 || c == 0 || r == m - 1 || c == n - 1)
                        && grid[r][c] == 0) {
                    floodFill(grid, r, c, stack);
                }
            }
        }

        int closedIslands = 0;

        // Count the remaining land components
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 0) {
                    closedIslands++;
                    floodFill(grid, r, c, stack);
                }
            }
        }

        return closedIslands;
    }

    private void floodFill(int[][] grid, int sr, int sc, int[] stack) {
        int m = grid.length;
        int n = grid[0].length;
        int top = 0;

        grid[sr][sc] = 1;
        stack[top++] = sr * n + sc;

        while (top > 0) {
            int pos = stack[--top];
            int r = pos / n;
            int c = pos % n;

            // Up
            if (r > 0 && grid[r - 1][c] == 0) {
                grid[r - 1][c] = 1;
                stack[top++] = (r - 1) * n + c;
            }

            // Down
            if (r + 1 < m && grid[r + 1][c] == 0) {
                grid[r + 1][c] = 1;
                stack[top++] = (r + 1) * n + c;
            }

            // Left
            if (c > 0 && grid[r][c - 1] == 0) {
                grid[r][c - 1] = 1;
                stack[top++] = r * n + c - 1;
            }

            // Right
            if (c + 1 < n && grid[r][c + 1] == 0) {
                grid[r][c + 1] = 1;
                stack[top++] = r * n + c + 1;
            }
        }
    }
}
