class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int start = nums[0];

        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < n; i++) {
            if (nums[i] != (start + i) % n) {
                ascending = false;
            }

            if (nums[i] != (start - i + n) % n) {
                descending = false;
            }
        }

        int ans = Integer.MAX_VALUE;

        // Case 1: The array is a cyclic rotation of [0, 1, ..., n-1].
        if (ascending) {
            int k = (n - start) % n;
            int operations = Math.min(k, n - k + 2);
            ans = Math.min(ans, operations);
        }

        // Case 2: The array is a cyclic rotation of [n-1, ..., 1, 0].
        if (descending) {
            int p = n - 1 - start;
            int operations = 1 + Math.min(p, n - p);
            ans = Math.min(ans, operations);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}