class Solution {
    public String[] createGrid(int m, int n) {
        String[] ans = new String[m];

        char[][] grid = new char[m][n];

        // Fill the entire grid with obstacles
        for (int i = 0; i < m; i++) {
            java.util.Arrays.fill(grid[i], '#');
        }

        // Make the first row free
        java.util.Arrays.fill(grid[0], '.');

        // Make the last column free
        for (int i = 0; i < m; i++) {
            grid[i][n - 1] = '.';
        }

        // Convert each row into a string
        for (int i = 0; i < m; i++) {
            ans[i] = new String(grid[i]);
        }

        return ans;
    }
}