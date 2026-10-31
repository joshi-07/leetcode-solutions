class Solution {
    public int uniquePaths(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length, n = grid[0].length;

        // f[i][j][0] = ways to arrive at (i,j) moving right
        // f[i][j][1] = ways to arrive at (i,j) moving down
        long[][][] f = new long[m][n][2];
        f[0][0][0] = 1; // start; (0,0) is an empty cell

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int d = 0; d < 2; d++) {
                    long cnt = f[i][j][d];
                    if (cnt == 0) continue;
                    if (i == m - 1 && j == n - 1) continue;

                    if (grid[i][j] == 0) {
                        // free choice: go right or down
                        if (j + 1 < n) f[i][j + 1][0] = (f[i][j + 1][0] + cnt) % MOD;
                        if (i + 1 < m) f[i + 1][j][1] = (f[i + 1][j][1] + cnt) % MOD;
                    } else if (d == 0) {
                        // entered mirror moving right -> forced down
                        if (i + 1 < m) f[i + 1][j][1] = (f[i + 1][j][1] + cnt) % MOD;
                    } else {
                        // entered mirror moving down -> forced right
                        if (j + 1 < n) f[i][j + 1][0] = (f[i][j + 1][0] + cnt) % MOD;
                    }
                }
            }
        }
        return (int) ((f[m - 1][n - 1][0] + f[m - 1][n - 1][1]) % MOD);
    }
}