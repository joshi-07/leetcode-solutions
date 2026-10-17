import java.util.*;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        long[] sorted = new long[n];

        // Pack digit sum and number into one long.
        for (int i = 0; i < n; i++) {
            int sum = digitSum(nums[i]);
            sorted[i] = ((long) sum << 32) | (nums[i] & 0xffffffffL);
        }

        // Sort by digit sum, then by number.
        Arrays.sort(sorted);

        // Map each number to its correct sorted position.
        Map<Integer, Integer> target = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            target.put((int) sorted[i], i);
        }

        boolean[] visited = new boolean[n];
        int swaps = 0;

        // Count permutation cycles.
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                continue;
            }

            int j = i;
            int cycleSize = 0;

            while (!visited[j]) {
                visited[j] = true;
                j = target.get(nums[j]);
                cycleSize++;
            }

            if (cycleSize > 1) {
                swaps += cycleSize - 1;
            }
        }

        return swaps;
    }

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}