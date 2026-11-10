class Solution {
    public int climbStairs(int n, int[] costs) {
        // dp[j] stores the minimum cost to reach step j
        int[] dp = new int[n + 1];
        
        // Base case: starting at step 0 with cost = 0
        dp[0] = 0;
        
        for (int j = 1; j <= n; j++) {
            int minCost = Integer.MAX_VALUE;
            
            // We can jump to step j from i = j-1, j-2, or j-3 (if valid)
            for (int i = Math.max(0, j - 3); i < j; i++) {
                // Cast the entire result back to int to fix the compilation error
                int jumpCost = (int) (dp[i] + costs[j - 1] + (long)(j - i) * (j - i));
                minCost = Math.min(minCost, jumpCost);
            }
            
            dp[j] = minCost;
        }
        
        return dp[n];
    }
}