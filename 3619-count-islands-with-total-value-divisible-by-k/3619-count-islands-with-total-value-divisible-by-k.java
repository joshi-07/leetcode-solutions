class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        int[] stack = new int[m * n];
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] > 0 && !visited[i][j]) {
                    int top = 0;
                    stack[top++] = i * n + j;
                    visited[i][j] = true;

                    long sum = 0;

                    while (top > 0) {
                        int cell = stack[--top];
                        int r = cell / n;
                        int c = cell % n;

                        sum += grid[r][c];

                        for (int[] d : dirs) {
                            int nr = r + d[0];
                            int nc = c + d[1];

                            if (nr >= 0 && nr < m &&
                                nc >= 0 && nc < n &&
                                grid[nr][nc] > 0 &&
                                !visited[nr][nc]) {

                                visited[nr][nc] = true;
                                stack[top++] = nr * n + nc;
                            }
                        }
                    }

                    if (sum % k == 0) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}