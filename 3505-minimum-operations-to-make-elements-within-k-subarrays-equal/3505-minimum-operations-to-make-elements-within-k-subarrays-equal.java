import java.util.*;

class Solution {
    private long[] cnt, sum;
    private int m;

    private void add(int i, long c, long s) {
        for (; i <= m; i += i & -i) {
            cnt[i] += c;
            sum[i] += s;
        }
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        // coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int[] vals = new int[n];
        m = 0;
        for (int v : sorted) {
            if (m == 0 || vals[m - 1] != v) vals[m++] = v;
        }
        int[] idx = new int[n];
        for (int i = 0; i < n; i++) {
            idx[i] = Arrays.binarySearch(vals, 0, m, nums[i]) + 1;
        }

        cnt = new long[m + 1];
        sum = new long[m + 1];
        int top = Integer.highestOneBit(m);

        // cost[i] = min ops to make window ending at i (size x) all equal
        long[] cost = new long[n];
        long windowSum = 0;
        int need = (x + 1) / 2;

        for (int i = 0; i < n; i++) {
            add(idx[i], 1, nums[i]);
            windowSum += nums[i];
            if (i >= x) {
                add(idx[i - x], -1, -nums[i - x]);
                windowSum -= nums[i - x];
            }
            if (i >= x - 1) {
                // find the median via Fenwick descent
                int pos = 0;
                long rem = need;
                for (int pw = top; pw > 0; pw >>= 1) {
                    if (pos + pw <= m && cnt[pos + pw] < rem) {
                        pos += pw;
                        rem -= cnt[pos];
                    }
                }
                long median = vals[pos]; // compressed index pos + 1

                // prefix count and sum up to the median
                long cLE = 0, sLE = 0;
                for (int p = pos + 1; p > 0; p -= p & -p) {
                    cLE += cnt[p];
                    sLE += sum[p];
                }
                cost[i] = median * cLE - sLE
                        + (windowSum - sLE) - median * (x - cLE);
            }
        }

        final long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1]; // j = 0: zero cost
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            Arrays.fill(cur, INF);
            for (int i = 1; i <= n; i++) {
                cur[i] = cur[i - 1];
                if (i >= x && prev[i - x] < INF) {
                    cur[i] = Math.min(cur[i], prev[i - x] + cost[i - 1]);
                }
            }
            prev = cur;
        }
        return prev[n];
    }
}