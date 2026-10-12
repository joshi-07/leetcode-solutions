class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int[] count = new int[31];
        long mod = 1_000_000_007L;

        // Count occurrences of each set bit
        for (int num : nums) {
            for (int bit = 0; bit < 31; bit++) {
                if ((num & (1 << bit)) != 0) {
                    count[bit]++;
                }
            }
        }

        long sum = 0;

        // Build the k largest possible values greedily
        for (int i = 0; i < k; i++) {
            long value = 0;

            for (int bit = 0; bit < 31; bit++) {
                if (count[bit] > 0) {
                    value |= (1L << bit);
                    count[bit]--;
                }
            }

            sum = (sum + value * value) % mod;
        }

        return (int) sum;
    }
}