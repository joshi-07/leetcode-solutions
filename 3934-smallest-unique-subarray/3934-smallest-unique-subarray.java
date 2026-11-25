import java.util.Arrays;

class Solution {
    private static final long MOD1 = 1_000_000_007L;
    private static final long MOD2 = 1_000_000_009L;
    private static final long BASE1 = 100_003L;
    private static final long BASE2 = 100_019L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        long[] pref1 = new long[n + 1];
        long[] pref2 = new long[n + 1];
        long[] pow1 = new long[n + 1];
        long[] pow2 = new long[n + 1];
        pow1[0] = 1;
        pow2[0] = 1;

        for (int i = 0; i < n; i++) {
            pref1[i + 1] = (pref1[i] * BASE1 + nums[i]) % MOD1;
            pref2[i + 1] = (pref2[i] * BASE2 + nums[i]) % MOD2;
            pow1[i + 1] = (pow1[i] * BASE1) % MOD1;
            pow2[i + 1] = (pow2[i] * BASE2) % MOD2;
        }

        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (hasUnique(mid, n, pref1, pref2, pow1, pow2)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    private boolean hasUnique(int len, int n, long[] pref1, long[] pref2, long[] pow1, long[] pow2) {
        int m = n - len + 1;
        long[] hashes = new long[m];
        long p1 = pow1[len];
        long p2 = pow2[len];

        for (int i = 0; i < m; i++) {
            long h1 = (pref1[i + len] - pref1[i] * p1 % MOD1 + MOD1) % MOD1;
            long h2 = (pref2[i + len] - pref2[i] * p2 % MOD2 + MOD2) % MOD2;
            hashes[i] = (h1 << 32) | h2;
        }

        Arrays.sort(hashes);
        for (int i = 0; i < m; i++) {
            boolean leftDiff = (i == 0) || (hashes[i] != hashes[i - 1]);
            boolean rightDiff = (i == m - 1) || (hashes[i] != hashes[i + 1]);
            if (leftDiff && rightDiff) {
                return true;
            }
        }
        return false;
    }
}