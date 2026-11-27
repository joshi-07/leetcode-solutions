class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int maxFactor = 1500;
        int[] freq = new int[maxFactor + 1];
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] countDivisible = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            for (int m = f; m <= maxFactor; m += f) {
                countDivisible[f] += freq[m];
            }
        }

        int[] dp = new int[budget + 1];
        for (int[] item : items) {
            int price = item[1];
            int value = countDivisible[item[0]];
            for (int c = budget; c >= price; c--) {
                dp[c] = Math.max(dp[c], dp[c - price] + value);
            }
        }

        int ans = 0;
        for (int c = 0; c <= budget; c++) {
            ans = Math.max(ans, dp[c] + (budget - c) / minPrice);
        }

        return ans;
    }
}