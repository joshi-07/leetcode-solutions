import java.util.Arrays;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                int rotation = 0;
                int drops = 0;

                for (int i = start; i < start + k - 1; i++) {
                    if (nums[i] > nums[i + 1]) {
                        drops++;
                        rotation = i + 1 - start;
                    }
                }

                if (drops > 1) {
                    valid = false;
                    break;
                }

                for (int j = 0; j < k; j++) {
                    int idx = start + (rotation + j) % k;

                    if (nums[idx] != sorted[start + j]) {
                        valid = false;
                        break;
                    }
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}