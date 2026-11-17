import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        
        // Precompute proper divisors for lengths 2 to n
        List<Integer>[] divisors = new List[n + 1];
        for (int len = 2; len <= n; len++) {
            divisors[len] = new ArrayList<>();
            for (int d = 1; d * d <= len; d++) {
                if (len % d == 0) {
                    divisors[len].add(d);
                    if (d * d != len && d != 1) {
                        divisors[len].add(len / d);
                    }
                }
            }
        }

        // cost[i][j] stores the minimum changes to make s[i..j] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE / 2);
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int minChanges = len;
                for (int d : divisors[len]) {
                    int changes = 0;
                    for (int r = 0; r < d; r++) {
                        int left = i + r;
                        int right = j - (d - 1 - r);
                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                            left += d;
                            right -= d;
                        }
                    }
                    minChanges = Math.min(minChanges, changes);
                }
                cost[i][j] = minChanges;
            }
        }

        // dp[i][p] = min changes to partition prefix of length i into p semi-palindromes
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;

        for (int i = 2; i <= n; i++) {
            for (int p = 1; p <= Math.min(i / 2, k); p++) {
                for (int prev = (p - 1) * 2; prev <= i - 2; prev++) {
                    dp[i][p] = Math.min(dp[i][p], dp[prev][p - 1] + cost[prev][i - 1]);
                }
            }
        }

        return dp[n][k];
    }
}