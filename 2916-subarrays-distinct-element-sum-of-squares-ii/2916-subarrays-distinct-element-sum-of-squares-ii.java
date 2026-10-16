
class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] square;
    private long[] lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;

        sum = new long[4 * n];
        square = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long answer = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;

            update(1, 0, n - 1, left, i);

            last[nums[i]] = i;
            answer = (answer + square[1]) % MOD;
        }

        return (int) answer;
    }

    private void apply(int node, int start, int end, long delta) {
        long length = end - start + 1L;
        long oldSum = sum[node];

        long d = delta % MOD;
        long sumChange = (length % MOD) * d % MOD;
        long squareChange =
                (2L * d % MOD) * oldSum % MOD;

        squareChange = (squareChange
                + (length % MOD) * d % MOD * d % MOD) % MOD;

        square[node] = (square[node] + squareChange) % MOD;
        sum[node] = (oldSum + sumChange) % MOD;

        lazy[node] += delta;
    }

    private void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = start + (end - start) / 2;
        long delta = lazy[node];

        apply(node * 2, start, mid, delta);
        apply(node * 2 + 1, mid + 1, end, delta);

        lazy[node] = 0;
    }

    private void update(int node, int start, int end,
                        int left, int right) {
        if (right < start || end < left) {
            return;
        }

        if (left <= start && end <= right) {
            apply(node, start, end, 1);
            return;
        }

        push(node, start, end);

        int mid = start + (end - start) / 2;

        update(node * 2, start, mid, left, right);
        update(node * 2 + 1, mid + 1, end, left, right);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        square[node] = (square[node * 2]
                + square[node * 2 + 1]) % MOD;
    }
}
